package org.example.services;

import org.example.models.NguoiDung;
import org.example.repositories.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class NguoiDungServices {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Đăng ký người dùng mới
    public NguoiDung dangKy(NguoiDung nguoiDung) {
        if (nguoiDungRepository.existsByUserName(nguoiDung.getUserName())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại");
        }

        if (nguoiDungRepository.existsByUserEmail(nguoiDung.getUserEmail())) {
            throw new RuntimeException("Email đã được sử dụng");
        }

        // Kiểm tra role hợp lệ
        if (nguoiDung.getRole() == null || nguoiDung.getRole() < 0 || nguoiDung.getRole() > 2) {
            throw new RuntimeException("Role không hợp lệ. Role phải là 0 (User), 1 (Giảng viên) hoặc 2 (Admin)");
        }

        nguoiDung.setPassword(passwordEncoder.encode(nguoiDung.getPassword()));
        return nguoiDungRepository.save(nguoiDung);
    }

    // Đăng nhập
    public NguoiDung dangNhap(String userName, String password) {
        Optional<NguoiDung> optionalNguoiDung = nguoiDungRepository.findByUserName(userName);
        
        if (optionalNguoiDung.isPresent()) {
            NguoiDung nguoiDung = optionalNguoiDung.get();
            if (passwordEncoder.matches(password, nguoiDung.getPassword())) {
                return nguoiDung;
            }
        }
        throw new RuntimeException("Tên đăng nhập hoặc mật khẩu không đúng");
    }

    // Lấy thông tin người dùng theo ID
    public NguoiDung layThongTinNguoiDung(int id) {
        return nguoiDungRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
    }

    // Cập nhật thông tin người dùng
    public NguoiDung capNhatThongTin(NguoiDung nguoiDung) {
        NguoiDung existingUser = nguoiDungRepository.findById(nguoiDung.getId())
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        // Kiểm tra email mới có bị trùng không
        Optional<NguoiDung> userWithEmail = nguoiDungRepository.findByUserEmail(nguoiDung.getUserEmail());
        if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(nguoiDung.getId())) {
            throw new RuntimeException("Email đã được sử dụng bởi người dùng khác");
        }

        // Kiểm tra role hợp lệ nếu được cung cấp
        if (nguoiDung.getRole() != null) {
            if (nguoiDung.getRole() < 0 || nguoiDung.getRole() > 2) {
                throw new RuntimeException("Role không hợp lệ. Role phải là 0 (User), 1 (Giảng viên) hoặc 2 (Admin)");
            }
        } else {
            // Nếu không cung cấp role, giữ nguyên role cũ
            nguoiDung.setRole(existingUser.getRole());
        }

        // Giữ nguyên password cũ
        nguoiDung.setPassword(existingUser.getPassword());
        
        return nguoiDungRepository.save(nguoiDung);
    }

    // Đổi mật khẩu
    public NguoiDung doiMatKhau(int id, String matKhauCu, String matKhauMoi) {
        NguoiDung nguoiDung = layThongTinNguoiDung(id);

        // Kiểm tra mật khẩu cũ
        if (matKhauCu == null || matKhauCu.trim().isEmpty()) {
            throw new RuntimeException("Mật khẩu cũ không được để trống");
        }

        if (!passwordEncoder.matches(matKhauCu, nguoiDung.getPassword())) {
            throw new RuntimeException("Mật khẩu cũ không đúng");
        }

        // Kiểm tra mật khẩu mới
        if (matKhauMoi == null || matKhauMoi.trim().isEmpty()) {
            throw new RuntimeException("Mật khẩu mới không được để trống");
        }

        if (matKhauMoi.length() < 6) {
            throw new RuntimeException("Mật khẩu mới phải có ít nhất 6 ký tự");
        }

        // Mã hóa và cập nhật mật khẩu mới
        String encodedNewPassword = passwordEncoder.encode(matKhauMoi);
        nguoiDung.setPassword(encodedNewPassword);
        
        // Lưu và trả về người dùng đã cập nhật
        return nguoiDungRepository.save(nguoiDung);
    }

    // Xóa người dùng
    public void xoaNguoiDung(int id) {
        if (!nguoiDungRepository.existsById(id)) {
            throw new RuntimeException("Người dùng không tồn tại");
        }
        nguoiDungRepository.deleteById(id);
    }

    // Lấy danh sách tất cả người dùng
    public List<NguoiDung> layDanhSachNguoiDung() {
        return nguoiDungRepository.findAll();
    }

    // Thêm tài khoản mới (dành cho admin)
    public NguoiDung themTaiKhoan(NguoiDung nguoiDung, int adminId) {
        // Kiểm tra quyền admin
        NguoiDung admin = layThongTinNguoiDung(adminId);
        if (!admin.isAdmin()) {
            throw new RuntimeException("Không có quyền thực hiện thao tác này");
        }

        if (nguoiDungRepository.existsByUserName(nguoiDung.getUserName())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại");
        }

        if (nguoiDungRepository.existsByUserEmail(nguoiDung.getUserEmail())) {
            throw new RuntimeException("Email đã được sử dụng");
        }

        nguoiDung.setPassword(passwordEncoder.encode(nguoiDung.getPassword()));
        return nguoiDungRepository.save(nguoiDung);
    }

    // Cập nhật role người dùng (chỉ admin)
    public NguoiDung capNhatRole(int userId, int newRole, int adminId) {
        // Kiểm tra quyền admin
        NguoiDung admin = layThongTinNguoiDung(adminId);
        if (!admin.isAdmin()) {
            throw new RuntimeException("Không có quyền thực hiện thao tác này");
        }

        if (newRole < 0 || newRole > 2) {
            throw new RuntimeException("Role không hợp lệ");
        }

        NguoiDung nguoiDung = layThongTinNguoiDung(userId);
        nguoiDung.setRole(newRole);
        return nguoiDungRepository.save(nguoiDung);
    }

    // Tìm kiếm người dùng
    public List<NguoiDung> timKiem(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return nguoiDungRepository.findAll();
        }
        return nguoiDungRepository.findByUserNameContainingIgnoreCaseOrUserEmailContainingIgnoreCase(keyword, keyword);
    }
}
