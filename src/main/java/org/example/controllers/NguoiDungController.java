package org.example.controllers;

import org.example.models.NguoiDung;
import org.example.services.NguoiDungServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nguoidung")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class NguoiDungController {

    @Autowired
    private NguoiDungServices nguoiDungServices;

    // Đăng ký tài khoản mới
    @PostMapping("/dangky")
    public ResponseEntity<?> dangKy(@RequestBody NguoiDung nguoiDung) {
        try {
            NguoiDung newUser = nguoiDungServices.dangKy(nguoiDung);
            return ResponseEntity.ok(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Đăng nhập
    @PostMapping("/dangnhap")
    public ResponseEntity<?> dangNhap(@RequestBody Map<String, String> credentials) {
        try {
            String userName = credentials.get("userName");
            String password = credentials.get("password");
            NguoiDung user = nguoiDungServices.dangNhap(userName, password);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }

    // Lấy thông tin người dùng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> layThongTinNguoiDung(@PathVariable("id") int id) {
        try {
            NguoiDung user = nguoiDungServices.layThongTinNguoiDung(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }

    // Cập nhật thông tin người dùng
    @PutMapping("/{id}")
    public ResponseEntity<?> capNhatThongTin(
            @PathVariable("id") int id,
            @RequestBody NguoiDung nguoiDung) {
        try {
            nguoiDung.setId(id);
            NguoiDung updatedUser = nguoiDungServices.capNhatThongTin(nguoiDung);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }

    // Đổi mật khẩu
    @PutMapping("/{id}/doimatkhau")
    public ResponseEntity<?> doiMatKhau(
            @PathVariable("id") int id,
            @RequestBody Map<String, String> passwords) {
        try {
            String matKhauCu = passwords.get("matKhauCu");
            String matKhauMoi = passwords.get("matKhauMoi");
            NguoiDung updatedUser = nguoiDungServices.doiMatKhau(id, matKhauCu, matKhauMoi);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }

    // Xóa người dùng
    @DeleteMapping("/{id}")
    public ResponseEntity<?> xoaNguoiDung(@PathVariable("id") int id) {
        try {
            nguoiDungServices.xoaNguoiDung(id);
            return ResponseEntity.ok(Map.of("message", "Xóa người dùng thành công"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }

    // Lấy danh sách người dùng
    @GetMapping
    public ResponseEntity<List<NguoiDung>> layDanhSachNguoiDung() {
        List<NguoiDung> users = nguoiDungServices.layDanhSachNguoiDung();
        return ResponseEntity.ok(users);
    }

    // Thêm tài khoản mới (yêu cầu quyền admin)
    @PostMapping("/admin/them")
    public ResponseEntity<?> themTaiKhoan(
            @RequestBody NguoiDung nguoiDung,
            @RequestHeader("Admin-Id") int adminId) {
        try {
            NguoiDung newUser = nguoiDungServices.themTaiKhoan(nguoiDung, adminId);
            return ResponseEntity.ok(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }

    // Cập nhật role người dùng (yêu cầu quyền admin)
    @PutMapping("/{id}/role")
    public ResponseEntity<?> capNhatRole(
            @PathVariable("id") int id,
            @RequestParam int newRole,
            @RequestHeader("Admin-Id") int adminId) {
        try {
            NguoiDung updatedUser = nguoiDungServices.capNhatRole(id, newRole, adminId);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }

    // Tìm kiếm người dùng
    @GetMapping("/timkiem")
    public ResponseEntity<List<NguoiDung>> timKiemNguoiDung(
            @RequestParam(required = false) String keyword) {
        List<NguoiDung> users = nguoiDungServices.timKiem(keyword);
        return ResponseEntity.ok(users);
    }
}
