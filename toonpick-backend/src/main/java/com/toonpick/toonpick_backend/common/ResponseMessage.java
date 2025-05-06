package com.toonpick.toonpick_backend.common;

/**
 * ResponseMessage interface
 * Defines simple message constants used in API responses.
 */
public interface ResponseMessage {

    // HTTP Status 200
    /** Success message */
    String SUCCESS = "Request processed successfully.";

    // HTTP Status 400
    /** Validation failed message */
    String VALIDATION_FAILED = "Validation failed.";
    /** Duplicate email message */
    String DUPLICATE_EMAIL = "Email is already in use.";
    /** Duplicate phone number message */
    String DUPLICATE_PHONE_NUMBER = "Phone number is already in use.";
    /** Duplicate nickname message */
    String DUPLICATE_NICKNAME = "Nickname is already in use.";
    /** User not found message */
    String USER_NOT_FOUND = "User not found.";
    /** Post not found message */
    String POST_NOT_FOUND = "Post not found.";

    // HTTP Status 401
    /** Login failed message */
    String LOGIN_FAILED = "Login failed.";
    /** Authentication failed message */
    String AUTHENTICATION_FAILED = "Authentication failed.";

    // HTTP Status 403
    /** Access denied message */
    String ACCESS_DENIED = "Access is denied.";

    // HTTP Status 500
    /** Database error message */
    String DATABASE_ERROR = "A database error has occurred.";
}