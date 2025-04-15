package org.example.repositories;

import org.example.models.HocPhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface HocPhanRepository extends JpaRepository<HocPhan, Integer> {
    // Tìm kiếm theo mã học phần
    Optional<HocPhan> findByMaHP(String maHP);
    
    // Tìm kiếm theo tên học phần (không phân biệt hoa thường)
    List<HocPhan> findByTenHPContainingIgnoreCase(String tenHP);
    
    // Tìm kiếm theo số tín chỉ
    List<HocPhan> findBySoTinChi(Integer soTinChi);
    
    // Tìm kiếm theo loại học phần
    List<HocPhan> findByLoaiHocPhan(Integer loaiHocPhan);
    
    // Tìm kiếm theo mã hoặc tên học phần
    List<HocPhan> findByMaHPContainingIgnoreCaseOrTenHPContainingIgnoreCase(String maHP, String tenHP);
    
    // Kiểm tra mã học phần đã tồn tại
    boolean existsByMaHP(String maHP);
}
