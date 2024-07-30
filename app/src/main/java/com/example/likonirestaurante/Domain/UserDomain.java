package com.example.likonirestaurante.Domain;

import java.io.Serializable;

/**
 * Represents a user with personal details such as name, email, phone number, and profile image.
 */
public class UserDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    // Variables
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String profileImageUrl;

    // Default Constructor
    public UserDomain() {
    }

    /**
     * Parameterized constructor to initialize user details.
     *
     * @param firstName      User's first name
     * @param lastName       User's last name
     * @param email          User's email
     * @param phoneNumber    User's phone number
     * @param profileImageUrl User's profile image URL
     */
    public UserDomain(String firstName, String lastName, String email, String phoneNumber, String profileImageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profileImageUrl = profileImageUrl;
    }

    // Getters and Setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName != null ? firstName : "";
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName != null ? lastName : "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email != null ? email : "";
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber != null ? phoneNumber : "";
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : "";
    }

    public void setImageUrl(String imageUrl) {
    }
}
