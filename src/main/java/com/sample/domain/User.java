/**
 * Created by philip a senger on October 22, 2015
 */
package com.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

// ignore any extra parameters sent to the the serialize process
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String id;
    @NotNull
    @NotEmpty
    private String username;
    @Email
    @NotNull
    @NotEmpty
    private String email;
    private String[] roles;
    private String hashedPassword;

    public User() {
    }

    public User(String id, String username, String email, String[] roles, String hashedPassword) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.hashedPassword = hashedPassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}
