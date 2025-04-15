package org.example.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "ctdt_user")
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 3, max = 50, message = "Tên đăng nhập phải có độ dài từ 3 đến 50 ký tự")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Tên đăng nhập chỉ được chứa chữ cái, số và dấu gạch dưới")
    @Column(name = "userName", unique = true, length = 50)
    private String userName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(max = 150, message = "Email không được vượt quá 150 ký tự")
    @Column(name = "userEmail", unique = true, length = 150)
    private String userEmail;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 150, message = "Mật khẩu phải có độ dài từ 6 đến 150 ký tự")
    @Column(name = "password", length = 150)
    private String password;

    @Column(name = "role", columnDefinition = "int COMMENT '0:User, 1: Giang vien, 2: Admin'")
    private Integer role = 0; // Default: 0 (User)

    // Constructors
    public NguoiDung() {
    }

    public NguoiDung(String userName, String userEmail, String password) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
        this.role = 0;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    // Helper method to check role
    public boolean isAdmin() {
        return role != null && role == 2;
    }

    public boolean isGiangVien() {
        return role != null && role == 1;
    }

    public boolean isUser() {
        return role == null || role == 0;
    }
}
