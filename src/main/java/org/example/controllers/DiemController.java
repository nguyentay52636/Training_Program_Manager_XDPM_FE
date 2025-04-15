package org.example.controllers;

import org.example.models.Diem;
import org.example.services.DiemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/diem")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class DiemController {

    private final DiemService diemService;

    public DiemController(DiemService diemService) {
        this.diemService = diemService;
    }

    // Thêm điểm mới
    @PostMapping
    public ResponseEntity<Diem> themDiem(@Valid @RequestBody Diem diem) {
        try {
            Diem diemMoi = diemService.themDiem(diem);
            return ResponseEntity.ok(diemMoi);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // Cập nhật điểm
    @PutMapping("/{id}")
    public ResponseEntity<Diem> capNhatDiem(@PathVariable Integer id, @Valid @RequestBody Diem diem) {
        try {
            Diem diemCapNhat = diemService.capNhatDiem(id, diem);
            return ResponseEntity.ok(diemCapNhat);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // Xóa điểm
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaDiem(@PathVariable Integer id) {
        try {
            diemService.xoaDiem(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Lấy điểm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> layDiemTheoId(@PathVariable Integer id) {
        try {
            Diem diem = diemService.layDiemTheoId(id);
            return ResponseEntity.ok(diem);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Lấy tất cả điểm
    @GetMapping
    public ResponseEntity<List<Diem>> layTatCaDiem() {
        List<Diem> dsDiem = diemService.layTatCaDiem();
        return ResponseEntity.ok(dsDiem);
    }

    // Tìm kiếm điểm theo mã sinh viên
    @GetMapping("/timkiem/masv")
    public ResponseEntity<List<Diem>> timKiemTheoMaSV(@RequestParam String maSV) {
        List<Diem> dsDiem = diemService.timKiemTheoMaSV(maSV);
        return ResponseEntity.ok(dsDiem);
    }

    // Tìm kiếm điểm theo tên sinh viên
    @GetMapping("/timkiem/tensv")
    public ResponseEntity<List<Diem>> timKiemTheoTenSV(@RequestParam String tenSV) {
        List<Diem> dsDiem = diemService.timKiemTheoTenSV(tenSV);
        return ResponseEntity.ok(dsDiem);
    }

    // Tìm kiếm điểm theo từ khóa (mã hoặc tên sinh viên)
    @GetMapping("/tim-kiem")
    public ResponseEntity<List<Diem>> timKiem(
            @RequestParam(required = false) String maSV,
            @RequestParam(required = false) String tenSV,
            @RequestParam(required = false) String lop,
            @RequestParam(required = false) Integer hocKy,
            @RequestParam(required = false) String nam,
            @RequestParam(required = false) String bangDiemMon) {
        
        try {
            List<Diem> ketQua;
            if (maSV != null) {
                ketQua = diemService.timKiemTheoMaSV(maSV);
            } else if (tenSV != null) {
                ketQua = diemService.timKiemTheoTenSV(tenSV);
            } else if (lop != null) {
                ketQua = diemService.timKiemTheoLop(lop);
            } else if (hocKy != null && nam != null) {
                ketQua = diemService.timKiemTheoHocKyVaNam(hocKy, nam);
            } else if (bangDiemMon != null) {
                ketQua = diemService.timKiemTheoBangDiemMon(bangDiemMon);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vui lòng cung cấp ít nhất một tiêu chí tìm kiếm");
            }
            return ResponseEntity.ok(ketQua);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi khi tìm kiếm: " + e.getMessage());
        }
    }

    // Tìm kiếm điểm theo lớp
    @GetMapping("/timkiem/lop")
    public ResponseEntity<List<Diem>> timKiemTheoLop(@RequestParam String lop) {
        List<Diem> dsDiem = diemService.timKiemTheoLop(lop);
        return ResponseEntity.ok(dsDiem);
    }

    // Tìm kiếm điểm theo học kỳ và năm
    @GetMapping("/timkiem/hocky-nam")
    public ResponseEntity<List<Diem>> timKiemTheoHocKyVaNam(
            @RequestParam Integer hocKy,
            @RequestParam String nam) {
        List<Diem> dsDiem = diemService.timKiemTheoHocKyVaNam(hocKy, nam);
        return ResponseEntity.ok(dsDiem);
    }

    // Tìm kiếm điểm theo học phần
    @GetMapping("/timkiem/hocphan")
    public ResponseEntity<List<Diem>> timKiemTheoHocPhan(@RequestParam String hocPhan) {
        List<Diem> dsDiem = diemService.timKiemTheoHocPhan(hocPhan);
        return ResponseEntity.ok(dsDiem);
    }

    // Tìm kiếm điểm theo lớp và học phần
    @GetMapping("/timkiem/lop-hocphan")
    public ResponseEntity<List<Diem>> timKiemTheoLopVaHocPhan(
            @RequestParam String lop,
            @RequestParam String hocPhan) {
        List<Diem> dsDiem = diemService.timKiemTheoLopVaHocPhan(lop, hocPhan);
        return ResponseEntity.ok(dsDiem);
    }

    // Tính điểm trung bình
    @GetMapping("/{id}/diemtrungbinh")
    public ResponseEntity<?> tinhDiemTrungBinh(@PathVariable Integer id) {
        try {
            Diem diem = diemService.layDiemTheoId(id);
            float diemTB = diemService.tinhDiemTrungBinh(diem);
            return ResponseEntity.ok(Map.of("diemTrungBinh", diemTB));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Thống kê điểm theo lớp và học phần
    @GetMapping("/thongke")
    public ResponseEntity<Map<String, Long>> thongKeDiem(
            @RequestParam String lop,
            @RequestParam String hocPhan) {
        Map<String, Long> thongKe = diemService.thongKeDiemTheoLop(lop, hocPhan);
        return ResponseEntity.ok(thongKe);
    }

    @GetMapping("/thong-ke/{lop}/{bangDiemMon}")
    public ResponseEntity<Map<String, Object>> thongKe(
            @PathVariable String lop,
            @PathVariable String bangDiemMon) {
        try {
            Map<String, Object> thongKe = new HashMap<>();
            
            Float diemTrungBinh = diemService.tinhDiemTrungBinhLopMon(lop, bangDiemMon);
            thongKe.put("diemTrungBinh", diemTrungBinh);
            
            Map<String, Long> thongKeSoLuong = diemService.thongKeSoLuongDat(lop, bangDiemMon);
            thongKe.put("thongKeSoLuong", thongKeSoLuong);
            
            return ResponseEntity.ok(thongKe);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi khi thống kê: " + e.getMessage());
        }
    }

    @GetMapping("/tim-kiem-diem/{min}/{max}")
    public ResponseEntity<List<Diem>> timTheoDiem(
            @PathVariable Float min,
            @PathVariable Float max) {
        try {
            if (min > max) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Giá trị min không được lớn hơn max");
            }
            return ResponseEntity.ok(diemService.timTheoDiemCuoiKy(min, max));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi khi tìm kiếm theo điểm: " + e.getMessage());
        }
    }
}
