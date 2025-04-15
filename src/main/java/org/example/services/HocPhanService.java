package org.example.services;

import org.example.models.HocPhan;
import org.example.repositories.HocPhanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HocPhanService {

    @Autowired
    private HocPhanRepository hocPhanRepository;

    // Thêm học phần mới
    @Transactional
    public HocPhan themHocPhan(HocPhan hocPhan) {
        // Kiểm tra mã học phần đã tồn tại
        if (hocPhanRepository.existsByMaHP(hocPhan.getMaHP())) {
            throw new RuntimeException("Mã học phần đã tồn tại: " + hocPhan.getMaHP());
        }
        
        // Tính tổng số tiết
        int tongSoTiet = hocPhan.getSoTietLyThuyet() + hocPhan.getSoTietThucHanh() + hocPhan.getSoTietThucTap();
        hocPhan.setTongSoTiet(tongSoTiet);
        
        return hocPhanRepository.save(hocPhan);
    }

    // Cập nhật học phần
    @Transactional
    public HocPhan capNhatHocPhan(String maHP, HocPhan hocPhan) {
        HocPhan existingHocPhan = hocPhanRepository.findByMaHP(maHP)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học phần với mã: " + maHP));
        
        // Nếu thay đổi mã HP, kiểm tra mã mới đã tồn tại chưa
        if (!maHP.equals(hocPhan.getMaHP()) && hocPhanRepository.existsByMaHP(hocPhan.getMaHP())) {
            throw new RuntimeException("Mã học phần mới đã tồn tại: " + hocPhan.getMaHP());
        }

        // Cập nhật thông tin
        existingHocPhan.setMaHP(hocPhan.getMaHP());
        existingHocPhan.setTenHP(hocPhan.getTenHP());
        existingHocPhan.setSoTinChi(hocPhan.getSoTinChi());
        existingHocPhan.setSoTietLyThuyet(hocPhan.getSoTietLyThuyet());
        existingHocPhan.setSoTietThucHanh(hocPhan.getSoTietThucHanh());
        existingHocPhan.setSoTietThucTap(hocPhan.getSoTietThucTap());
        existingHocPhan.setLoaiHocPhan(hocPhan.getLoaiHocPhan());
        existingHocPhan.setHeSoHocPhan(hocPhan.getHeSoHocPhan());
        
        // Tính lại tổng số tiết
        int tongSoTiet = hocPhan.getSoTietLyThuyet() + hocPhan.getSoTietThucHanh() + hocPhan.getSoTietThucTap();
        existingHocPhan.setTongSoTiet(tongSoTiet);

        return hocPhanRepository.save(existingHocPhan);
    }

    // Xóa học phần
    @Transactional
    public void xoaHocPhan(String maHP) {
        HocPhan hocPhan = hocPhanRepository.findByMaHP(maHP)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học phần với mã: " + maHP));
        hocPhanRepository.delete(hocPhan);
    }

    // Lấy học phần theo mã
    public HocPhan layHocPhanTheoMa(String maHP) {
        return hocPhanRepository.findByMaHP(maHP)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học phần với mã: " + maHP));
    }

    // Lấy tất cả học phần
    public List<HocPhan> layTatCaHocPhan() {
        return hocPhanRepository.findAll();
    }

    // Tìm kiếm học phần theo tên
    public List<HocPhan> timKiemTheoTen(String tenHP) {
        return hocPhanRepository.findByTenHPContainingIgnoreCase(tenHP);
    }

    // Tìm kiếm theo từ khóa (mã hoặc tên)
    public List<HocPhan> timKiem(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return hocPhanRepository.findAll();
        }
        return hocPhanRepository.findByMaHPContainingIgnoreCaseOrTenHPContainingIgnoreCase(keyword, keyword);
    }

    // Tìm kiếm theo số tín chỉ
    public List<HocPhan> timKiemTheoTinChi(Integer soTinChi) {
        return hocPhanRepository.findBySoTinChi(soTinChi);
    }

    // Tìm kiếm theo loại học phần
    public List<HocPhan> timKiemTheoLoai(Integer loaiHocPhan) {
        return hocPhanRepository.findByLoaiHocPhan(loaiHocPhan);
    }
}
