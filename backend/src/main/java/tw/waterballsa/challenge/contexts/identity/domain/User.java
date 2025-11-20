package tw.waterballsa.challenge.contexts.identity.domain;

import jakarta.persistence.*;
import tw.waterballsa.challenge.common.BaseEntity;
import tw.waterballsa.challenge.contexts.identity.domain.enums.Roles;
import tw.waterballsa.challenge.contexts.identity.domain.enums.StudentRank;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private Integer studentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles role = Roles.VISITOR;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudentRank rank = StudentRank.NONE;

    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getDisplayName() {
        if (studentId == null) {
            return username;
        }
        return username + "#" + studentId;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public StudentRank getRank() {
        return rank;
    }

    public void setRank(StudentRank rank) {
        this.rank = rank;
    }
}