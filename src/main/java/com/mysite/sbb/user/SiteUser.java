package com.mysite.sbb.user;

import com.mysite.sbb.config.BoardPrincipal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {

    @Id
    @Column(length = 50)
    private String userId;

    private String password;

    @Column(unique = true)
    private String email;

    private String role;

    public SiteUser(String userId, String password, String email, String role) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public SiteUser() {

    }

    public static SiteUser of(String userId, String password, String email) {
        return new SiteUser(userId, password, email, BoardPrincipal.RoleType.USER.getName());
    }
}
