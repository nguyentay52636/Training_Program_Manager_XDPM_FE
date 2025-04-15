package org.example.repositories;

import org.example.models.Diem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface DiemRepository extends JpaRepository<Diem, Integer> {
    // Tìm kiếm theo mã sinh viên
    List<Diem> findByMaSVContainingIgnoreCase(String maSV);
    
    // Tìm kiếm theo tên sinh viên
    List<Diem> findByTenSVContainingIgnoreCase(String tenSV);
    
    // Tìm kiếm theo cả mã và tên sinh viên
    List<Diem> findByMaSVContainingIgnoreCaseOrTenSVContainingIgnoreCase(String maSV, String tenSV);
    
    // Tìm kiếm theo lớp
    List<Diem> findByLop(String lop);
    
    // Tìm kiếm theo học kỳ và năm
    List<Diem> findByHocKyAndNam(Integer hocKy, String nam);

    // Tìm kiếm theo học phần
    List<Diem> findByBangDiemMon(String bangDiemMon);

    // Tìm kiếm theo lớp và học phần
    List<Diem> findByLopAndBangDiemMon(String lop, String bangDiemMon);

    // Tìm kiếm theo lớp và học kỳ
    List<Diem> findByLopAndHocKy(String lop, Integer hocKy);

    // Tìm điểm theo khoảng điểm cuối kỳ
    List<Diem> findByDiemCuoiKyBetween(Float min, Float max);

    // Thống kê số lượng sinh viên theo điểm
    @Query("SELECT COUNT(d) FROM Diem d WHERE d.lop = ?1 AND d.bangDiemMon = ?2 AND d.diemCuoiKy >= ?3")
    Long countByLopAndBangDiemMonAndDiemCuoiKyGreaterThanEqual(String lop, String bangDiemMon, Float minDiem);

    // Thêm các phương thức thống kê mới
    @Query("SELECT AVG(d.diemCuoiKy) FROM Diem d WHERE d.lop = ?1 AND d.bangDiemMon = ?2")
    Float calculateAverageScoreByLopAndMon(String lop, String bangDiemMon);
    
    @Query("SELECT COUNT(d) FROM Diem d WHERE d.lop = ?1 AND d.bangDiemMon = ?2 AND d.diemCuoiKy >= 5.0")
    Long countPassingStudents(String lop, String bangDiemMon);
    
    @Query("SELECT COUNT(d) FROM Diem d WHERE d.lop = ?1 AND d.bangDiemMon = ?2 AND d.diemCuoiKy < 5.0")
    Long countFailingStudents(String lop, String bangDiemMon);
}
