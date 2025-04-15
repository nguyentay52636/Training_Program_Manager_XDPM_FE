package org.example.controllers;

import org.example.models.HocPhan;
import org.example.services.HocPhanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hocphan")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class HocPhanController {

    @Autowired
    private HocPhanService hocPhanService;

    // Thêm học phần mới
    @PostMapping
    public ResponseEntity<?> themHocPhan(@Valid @RequestBody HocPhan hocPhan) {
        try {
            HocPhan savedHocPhan = hocPhanService.themHocPhan(hocPhan);
            return ResponseEntity.ok(savedHocPhan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Cập nhật học phần
    @PutMapping("/{maHP}")
    public ResponseEntity<?> capNhatHocPhan(
            @PathVariable String maHP,
            @Valid @RequestBody HocPhan hocPhan) {
        try {
            HocPhan updatedHocPhan = hocPhanService.capNhatHocPhan(maHP, hocPhan);
            return ResponseEntity.ok(updatedHocPhan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Xóa học phần
    @DeleteMapping("/{maHP}")
    public ResponseEntity<?> xoaHocPhan(@PathVariable String maHP) {
        try {
            hocPhanService.xoaHocPhan(maHP);
            return ResponseEntity.ok(Map.of("message", "Xóa học phần thành công"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Lấy học phần theo mã
    @GetMapping("/{maHP}")
    public ResponseEntity<?> layHocPhanTheoMa(@PathVariable String maHP) {
        try {
            HocPhan hocPhan = hocPhanService.layHocPhanTheoMa(maHP);
            return ResponseEntity.ok(hocPhan);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Lấy tất cả học phần
    @GetMapping
    public ResponseEntity<List<HocPhan>> layTatCaHocPhan() {
        List<HocPhan> danhSachHocPhan = hocPhanService.layTatCaHocPhan();
        return ResponseEntity.ok(danhSachHocPhan);
    }

    // Tìm kiếm theo từ khóa (mã hoặc tên)
    @GetMapping("/timkiem")
    public ResponseEntity<List<HocPhan>> timKiem(
            @RequestParam(required = false) String keyword) {
        List<HocPhan> ketQua = hocPhanService.timKiem(keyword);
        return ResponseEntity.ok(ketQua);
    }

    // Tìm kiếm theo tên học phần
    @GetMapping("/timkiem/ten")
    public ResponseEntity<List<HocPhan>> timKiemTheoTen(
            @RequestParam String tenHP) {
        List<HocPhan> ketQua = hocPhanService.timKiemTheoTen(tenHP);
        return ResponseEntity.ok(ketQua);
    }

    // Tìm kiếm theo số tín chỉ
    @GetMapping("/timkiem/tinchi")
    public ResponseEntity<List<HocPhan>> timKiemTheoTinChi(
            @RequestParam Integer soTinChi) {
        List<HocPhan> ketQua = hocPhanService.timKiemTheoTinChi(soTinChi);
        return ResponseEntity.ok(ketQua);
    }

    // Tìm kiếm theo loại học phần
    @GetMapping("/timkiem/loai")
    public ResponseEntity<List<HocPhan>> timKiemTheoLoai(
            @RequestParam Integer loaiHocPhan) {
        List<HocPhan> ketQua = hocPhanService.timKiemTheoLoai(loaiHocPhan);
        return ResponseEntity.ok(ketQua);
    }
}
