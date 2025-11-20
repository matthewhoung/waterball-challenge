package tw.waterballsa.challenge.features.auth.common;

import tw.waterballsa.challenge.contexts.identity.domain.enums.Roles;
import tw.waterballsa.challenge.contexts.identity.domain.enums.StudentRank;

import java.util.UUID;

public class AuthResponse {
    private String token;
    private String refreshToken;
    private UUID userId;
    private Integer studentId;
    private String username;
    private String displayName;
    private String email;
    private Roles role;
    private StudentRank rank;

    public AuthResponse() {}

    public AuthResponse(String token, String refreshToken, UUID userId, Integer studentId,
                        String username, String displayName, String email, Roles role, StudentRank rank) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.studentId = studentId;
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.role = role;
        this.rank = rank;
    }

    // All getters and setters...
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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