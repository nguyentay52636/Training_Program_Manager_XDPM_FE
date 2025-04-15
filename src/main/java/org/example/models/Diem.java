package org.example.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "ctdt_cotdiem")
public class Diem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCotDiem")
    private Integer idCotDiem;

    @NotBlank(message = "Mã sinh viên không được để trống")
    @Pattern(regexp = "^SV\\d{3}$", message = "Mã sinh viên phải có định dạng SVxxx (x là số)")
    @Column(name = "maSV", length = 50)
    private String maSV;

    @NotBlank(message = "Tên sinh viên không được để trống")
    @Column(name = "tenSV", length = 255)
    private String tenSV;

    @NotNull(message = "Điểm chuyên cần không được để trống")
    @DecimalMin(value = "0.0", message = "Điểm chuyên cần phải từ 0 đến 10")
    @DecimalMax(value = "10.0", message = "Điểm chuyên cần phải từ 0 đến 10")
    @Column(name = "diemChuyenCan")
    private Float diemChuyenCan;

    @NotNull(message = "Điểm thực hành không được để trống")
    @DecimalMin(value = "0.0", message = "Điểm thực hành phải từ 0 đến 10")
    @DecimalMax(value = "10.0", message = "Điểm thực hành phải từ 0 đến 10")
    @Column(name = "diemThucHanh")
    private Float diemThucHanh;

    @NotNull(message = "Điểm giữa kỳ không được để trống")
    @DecimalMin(value = "0.0", message = "Điểm giữa kỳ phải từ 0 đến 10")
    @DecimalMax(value = "10.0", message = "Điểm giữa kỳ phải từ 0 đến 10")
    @Column(name = "diemGiuaKy")
    private Float diemGiuaKy;

    @NotNull(message = "Điểm cuối kỳ không được để trống")
    @DecimalMin(value = "0.0", message = "Điểm cuối kỳ phải từ 0 đến 10")
    @DecimalMax(value = "10.0", message = "Điểm cuối kỳ phải từ 0 đến 10")
    @Column(name = "diemCuoiKy")
    private Float diemCuoiKy;

    @NotBlank(message = "Học phần không được để trống")
    @Column(name = "bangDiemMon", length = 255)
    private String bangDiemMon;

    @NotNull(message = "Học kỳ không được để trống")
    @Min(value = 1, message = "Học kỳ phải từ 1 đến 3")
    @Max(value = 3, message = "Học kỳ phải từ 1 đến 3")
    @Column(name = "hocKy")
    private Integer hocKy;

    @NotBlank(message = "Năm học không được để trống")
    @Column(name = "nam", length = 50)
    private String nam;

    @NotBlank(message = "Lớp không được để trống")
    @Column(name = "lop", length = 50)
    private String lop;

    // Constructors
    public Diem() {
    }

    // Getters and Setters
    public Integer getIdCotDiem() {
        return idCotDiem;
    }

    public void setIdCotDiem(Integer idCotDiem) {
        this.idCotDiem = idCotDiem;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getTenSV() {
        return tenSV;
    }

    public void setTenSV(String tenSV) {
        this.tenSV = tenSV;
    }

    public Float getDiemChuyenCan() {
        return diemChuyenCan;
    }

    public void setDiemChuyenCan(Float diemChuyenCan) {
        this.diemChuyenCan = diemChuyenCan;
    }

    public Float getDiemThucHanh() {
        return diemThucHanh;
    }

    public void setDiemThucHanh(Float diemThucHanh) {
        this.diemThucHanh = diemThucHanh;
    }

    public Float getDiemGiuaKy() {
        return diemGiuaKy;
    }

    public void setDiemGiuaKy(Float diemGiuaKy) {
        this.diemGiuaKy = diemGiuaKy;
    }

    public Float getDiemCuoiKy() {
        return diemCuoiKy;
    }

    public void setDiemCuoiKy(Float diemCuoiKy) {
        this.diemCuoiKy = diemCuoiKy;
    }

    public String getBangDiemMon() {
        return bangDiemMon;
    }

    public void setBangDiemMon(String bangDiemMon) {
        this.bangDiemMon = bangDiemMon;
    }

    public Integer getHocKy() {
        return hocKy;
    }

    public void setHocKy(Integer hocKy) {
        this.hocKy = hocKy;
    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }
}
