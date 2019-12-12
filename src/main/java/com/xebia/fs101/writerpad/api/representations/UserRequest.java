package com.xebia.fs101.writerpad.api.representations;

import com.xebia.fs101.writerpad.domain.User;
import com.xebia.fs101.writerpad.domain.WriterpadRole;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserRequest {

    @NotBlank
    private String username;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotNull
    private WriterpadRole role;

    public UserRequest() {
    }

    public WriterpadRole getRole() {
        return role;
    }

    public void setRole(WriterpadRole role) {
        this.role = role;
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

    public UserRequest(@NotBlank String username,
                       @NotBlank String email,
                       @NotBlank String password,
                       @NotBlank WriterpadRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRequest{"
                + "username='" + username + '\''
                + ", email='" + email + '\''
                + ", password='" + password + '\''
                + ", role='" + role + '\''
                + '}';
    }

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(this.username, this.email, passwordEncoder.encode(this.password), this.role);
    }
}
