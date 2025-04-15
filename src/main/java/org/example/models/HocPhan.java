package org.example.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "ctdt_hocphan")
public class HocPhan {
    @Id
    @Column(name = "idHocPhan")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHocPhan;

    @Column(name = "maHP", unique = true, nullable = false, length = 150)
    @Pattern(regexp = "^HP\\d{3}$", message = "Mã học phần phải có định dạng HPxxx")
    private String maHP;

    @Column(name = "tenHP", nullable = false, length = 150)
    @NotBlank(message = "Tên học phần không được để trống")
    private String tenHP;

    @Column(name = "soTinChi")
    @Min(value = 1, message = "Số tín chỉ phải lớn hơn 0")
    @Max(value = 6, message = "Số tín chỉ không được vượt quá 6")
    private Integer soTinChi;

    @Column(name = "soTietLyThuyet")
    @Min(value = 0, message = "Số tiết lý thuyết không được âm")
    private Integer soTietLyThuyet;

    @Column(name = "soTietThucHanh")
    @Min(value = 0, message = "Số tiết thực hành không được âm")
    private Integer soTietThucHanh;

    @Column(name = "soTietThucTap")
    @Min(value = 0, message = "Số tiết thực tập không được âm")
    private Integer soTietThucTap;

    @Column(name = "tongSoTiet")
    @Min(value = 0, message = "Tổng số tiết không được âm")
    private Integer tongSoTiet;

    @Column(name = "loaiHocPhan")
    @Min(value = 1, message = "Loại học phần không hợp lệ")
    @Max(value = 3, message = "Loại học phần không hợp lệ")
    private Integer loaiHocPhan;

    @Column(name = "heSoHocPhan")
    @Min(value = 1, message = "Hệ số học phần phải lớn hơn 0")
    @Max(value = 3, message = "Hệ số học phần không được vượt quá 3")
    private Integer heSoHocPhan;

    // Getters and Setters
    public Integer getIdHocPhan() {
        return idHocPhan;
    }

    public void setIdHocPhan(Integer idHocPhan) {
        this.idHocPhan = idHocPhan;
    }

    public String getMaHP() {
        return maHP;
    }

    public void setMaHP(String maHP) {
        this.maHP = maHP;
    }

    public String getTenHP() {
        return tenHP;
    }

    public void setTenHP(String tenHP) {
        this.tenHP = tenHP;
    }

    public Integer getSoTinChi() {
        return soTinChi;
    }

    public void setSoTinChi(Integer soTinChi) {
        this.soTinChi = soTinChi;
    }

    public Integer getSoTietLyThuyet() {
        return soTietLyThuyet;
    }

    public void setSoTietLyThuyet(Integer soTietLyThuyet) {
        this.soTietLyThuyet = soTietLyThuyet;
    }

    public Integer getSoTietThucHanh() {
        return soTietThucHanh;
    }

    public void setSoTietThucHanh(Integer soTietThucHanh) {
        this.soTietThucHanh = soTietThucHanh;
    }

    public Integer getSoTietThucTap() {
        return soTietThucTap;
    }

    public void setSoTietThucTap(Integer soTietThucTap) {
        this.soTietThucTap = soTietThucTap;
    }

    public Integer getTongSoTiet() {
        return tongSoTiet;
    }

    public void setTongSoTiet(Integer tongSoTiet) {
        this.tongSoTiet = tongSoTiet;
    }

    public Integer getLoaiHocPhan() {
        return loaiHocPhan;
    }

    public void setLoaiHocPhan(Integer loaiHocPhan) {
        this.loaiHocPhan = loaiHocPhan;
    }

    public Integer getHeSoHocPhan() {
        return heSoHocPhan;
    }

    public void setHeSoHocPhan(Integer heSoHocPhan) {
        this.heSoHocPhan = heSoHocPhan;
    }
}
