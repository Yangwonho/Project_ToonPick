package com.toonpick.toonpick_backend.common;

/**
 * ResponseCode 인터페이스
 * API 응답 시 사용할 간단한 문자열 코드 상수 정의
 */
public interface ResponseCode {

    // HTTP Status 200
    /** 성공 (Success) */
    String SUCCESS = "SU";

    // HTTP Status 400
    /** 유효성 검사 실패 (Validation Failed) */
    String VALIDATION_FAILED = "VF";
    /** 중복된 이메일 (Duplicate Email) */
    String DUPLICATE_EMAIL = "DE";
    /** 중복된 전화번호 (Duplicate Phone Number) */
    String DUPLICATE_PHONE_NUMBER = "DP";
    /** 중복된 닉네임 (Duplicate Nickname) */
    String DUPLICATE_NICKNAME = "DN";
    /** 존재하지 않는 유저 (User Not Found) */
    String USER_NOT_FOUND = "UNF";
    /** 존재하지 않는 게시물 (Post Not Found) */
    String POST_NOT_FOUND = "PNF";

    // HTTP Status 401
    /** 로그인 실패 (Login Failed) */
    String LOGIN_FAILED = "LF";
    /** 인증 실패 (Authentication Failed) */
    String AUTHENTICATION_FAILED = "AF";

    // HTTP Status 402
    /** 권한 없음 (Access Denied) */
    String ACCESS_DENIED = "AD";

    // HTTP Status 500
    /** 데이터베이스 에러 (Database Error) */
    String DATABASE_ERROR = "DE";

}