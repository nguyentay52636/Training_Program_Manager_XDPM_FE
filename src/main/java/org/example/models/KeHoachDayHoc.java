package org.example.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "ctdt_kehoachdayhoc")
public class KeHoachDayHoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idChuyenNganh")
    private Integer idChuyenNganh;

    @NotBlank(message = "Tên chuyên ngành không được để trống")
    @Size(max = 150, message = "Tên chuyên ngành không được vượt quá 150 ký tự")
    @Column(name = "tenChuyenNganh", nullable = false, length = 150)
    private String tenChuyenNganh;

    @NotBlank(message = "Danh sách học phần không được để trống")
    @Column(name = "idHocPhan", nullable = false)
    private String idHocPhan;

    @NotNull(message = "Học kỳ thực hiện không được để trống")
    @Column(name = "hocKyThucHien", nullable = false)
    private Integer hocKyThucHien;

    @Transient
    private List<HocPhanInfo> hocPhans;

    @Data
    public static class HocPhanInfo {
        private Integer idHocPhan;
        private String maHP;
        private String tenHP;
    }
} 