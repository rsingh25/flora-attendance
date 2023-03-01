package com.rps.flora.service.dto;

import java.io.Serializable;

/**
 * A DTO representing a password change required data - current and new password.
 */
public class PasswordChangeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String currentPassword;
    private String newPassword;

    private String login;

    public PasswordChangeDTO() {
        // Empty constructor needed for Jackson.
    }

    public PasswordChangeDTO(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
