package org.example.services;

import org.example.models.Diem;
import org.example.repositories.DiemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
@Transactional
public class DiemService {

    private final DiemRepository diemRepository;

    public DiemService(DiemRepository diemRepository) {
        this.diemRepository = diemRepository;
    }

    private void validateDiem(Diem diem) {
        if (diem.getDiemChuyenCan() != null && (diem.getDiemChuyenCan() < 0 || diem.getDiemChuyenCan() > 10)) {
            throw new IllegalArgumentException("Điểm chuyên cần phải từ 0 đến 10");
        }
        if (diem.getDiemThucHanh() != null && (diem.getDiemThucHanh() < 0 || diem.getDiemThucHanh() > 10)) {
            throw new IllegalArgumentException("Điểm thực hành phải từ 0 đến 10");
        }
        if (diem.getDiemGiuaKy() != null && (diem.getDiemGiuaKy() < 0 || diem.getDiemGiuaKy() > 10)) {
            throw new IllegalArgumentException("Điểm giữa kỳ phải từ 0 đến 10");
        }
        if (diem.getDiemCuoiKy() != null && (diem.getDiemCuoiKy() < 0 || diem.getDiemCuoiKy() > 10)) {
            throw new IllegalArgumentException("Điểm cuối kỳ phải từ 0 đến 10");
        }
        if (diem.getHocKy() != null && (diem.getHocKy() < 1 || diem.getHocKy() > 8)) {
            throw new IllegalArgumentException("Học kỳ phải từ 1 đến 8");
        }
    }

    public Diem themDiem(Diem diem) {
        validateDiem(diem);
        return diemRepository.save(diem);
    }

    public Diem capNhatDiem(Integer id, Diem diem) {
        validateDiem(diem);
        Diem existingDiem = diemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy điểm với ID: " + id));
        
        existingDiem.setMaSV(diem.getMaSV());
        existingDiem.setTenSV(diem.getTenSV());
        existingDiem.setLop(diem.getLop());
        existingDiem.setBangDiemMon(diem.getBangDiemMon());
        existingDiem.setHocKy(diem.getHocKy());
        existingDiem.setNam(diem.getNam());
        existingDiem.setDiemChuyenCan(diem.getDiemChuyenCan());
        existingDiem.setDiemThucHanh(diem.getDiemThucHanh());
        existingDiem.setDiemGiuaKy(diem.getDiemGiuaKy());
        existingDiem.setDiemCuoiKy(diem.getDiemCuoiKy());
        
        return diemRepository.save(existingDiem);
    }

    public void xoaDiem(Integer id) {
        if (!diemRepository.existsById(id)) {
            throw new EntityNotFoundException("Không tìm thấy điểm với ID: " + id);
        }
        diemRepository.deleteById(id);
    }

    public Diem layDiemTheoId(Integer id) {
        return diemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy điểm với ID: " + id));
    }

    public List<Diem> layTatCaDiem() {
        return diemRepository.findAll();
    }

    public List<Diem> timKiemTheoMaSV(String maSV) {
        if (maSV == null || maSV.trim().isEmpty()) {
            return layTatCaDiem();
        }
        return diemRepository.findByMaSVContainingIgnoreCase(maSV);
    }

    public List<Diem> timKiemTheoTenSV(String tenSV) {
        if (tenSV == null || tenSV.trim().isEmpty()) {
            return layTatCaDiem();
        }
        return diemRepository.findByTenSVContainingIgnoreCase(tenSV);
    }

    public List<Diem> timKiem(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return layTatCaDiem();
        }
        return diemRepository.findByMaSVContainingIgnoreCaseOrTenSVContainingIgnoreCase(keyword, keyword);
    }

    public List<Diem> timKiemTheoLop(String lop) {
        return diemRepository.findByLop(lop);
    }

    public List<Diem> timKiemTheoHocKyVaNam(Integer hocKy, String nam) {
        return diemRepository.findByHocKyAndNam(hocKy, nam);
    }

    public List<Diem> timKiemTheoHocPhan(String hocPhan) {
        return diemRepository.findByBangDiemMon(hocPhan);
    }

    public List<Diem> timKiemTheoLopVaHocPhan(String lop, String hocPhan) {
        return diemRepository.findByLopAndBangDiemMon(lop, hocPhan);
    }

    public float tinhDiemTrungBinh(Diem diem) {
        return (diem.getDiemChuyenCan() + diem.getDiemThucHanh() + 
                diem.getDiemGiuaKy() * 2 + diem.getDiemCuoiKy() * 3) / 7.0f;
    }

    public Map<String, Long> thongKeDiemTheoLop(String lop, String hocPhan) {
        Map<String, Long> thongKe = new HashMap<>();
        thongKe.put("Giỏi", diemRepository.countByLopAndBangDiemMonAndDiemCuoiKyGreaterThanEqual(lop, hocPhan, 8.0f));
        thongKe.put("Khá", diemRepository.countByLopAndBangDiemMonAndDiemCuoiKyGreaterThanEqual(lop, hocPhan, 7.0f));
        thongKe.put("Trung bình", diemRepository.countByLopAndBangDiemMonAndDiemCuoiKyGreaterThanEqual(lop, hocPhan, 5.0f));
        return thongKe;
    }

    public List<Diem> timKiemTheoBangDiemMon(String bangDiemMon) {
        return timKiemTheoHocPhan(bangDiemMon);
    }

    // Giữ lại các phương thức mới
    public Float tinhDiemTrungBinhLopMon(String lop, String bangDiemMon) {
        return diemRepository.calculateAverageScoreByLopAndMon(lop, bangDiemMon);
    }

    public Map<String, Long> thongKeSoLuongDat(String lop, String bangDiemMon) {
        Long soLuongDat = diemRepository.countPassingStudents(lop, bangDiemMon);
        Long soLuongKhongDat = diemRepository.countFailingStudents(lop, bangDiemMon);
        
        Map<String, Long> ketQua = new HashMap<>();
        ketQua.put("soLuongDat", soLuongDat);
        ketQua.put("soLuongKhongDat", soLuongKhongDat);
        return ketQua;
    }

    public List<Diem> timTheoDiemCuoiKy(Float min, Float max) {
        return diemRepository.findByDiemCuoiKyBetween(min, max);
    }
}
