package com.deliorder.api.api.dto;


import com.deliorder.api.entity.UserRole;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SignupRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "abcde123",
            "a1b2c3d4",
            "password1",
            "abc12345!",
            "a1!b2@c3#",
            "z9x8c7v6",
            "Abcde12309823",
            "z!!!!!!3"
    })
    public void testValidPassword(String password) throws Exception {
        //given
        String email = "test@test.com";
        String name = "test";
        String nickname = "test";
        String phoneNumber = "01011112222";
        UserRole userRole = UserRole.ROLE_USER;
        SignupRequest signupRequest = new SignupRequest(email, password, name, nickname, phoneNumber, userRole);

        //when
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        //then
        assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "ABCDEFG1",     // 소문자 없음
            "abcdefghi",    // 숫자 없음
            "12345678",     // 소문자 없음
            "abc12",        // 길이 부족
            "ABCdefgh",     // 숫자 없음
            "ab_cd123",     // _ 는 허용 안 됨
            "ab cd1234",    // 공백 포함
    })
    public void testInvalidPassword(String password) throws Exception {
        //given
        String email = "test@test.com";
        String name = "test";
        String nickname = "test";
        String phoneNumber = "01011112222";
        UserRole userRole = UserRole.ROLE_USER;
        String violationMessage = "비밀번호 형식이 올바르지 않습니다.";
        SignupRequest signupRequest = new SignupRequest(email, password, name, nickname, phoneNumber, userRole);

        //when
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        //then
        assertThat(violations).hasSize(1);

        ConstraintViolation<SignupRequest> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("password");

        assertThat(violation.getMessage()).isEqualTo(violationMessage);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "0101234567",
            "01012345678",
            "0212345678",
            "03198765432"
    })
    public void testValidPhoneNumber(String phoneNumber) throws Exception {
        //given
        String email = "test@test.com";
        String password = "abcde123";
        String name = "test";
        String nickname = "test";
        UserRole userRole = UserRole.ROLE_USER;
        SignupRequest signupRequest = new SignupRequest(email, password, name, nickname, phoneNumber, userRole);

        //when
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        //then
        assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "010-1234-5678", // 하이픈 포함
            "010 1234 5678", // 공백 포함
            "+821012345678", // 국가번호 포함
            "010123456",     // 9자리 (짧음)
            "010123456789",  // 12자리 (김)
            "abcdefghijk",   // 문자 포함
            "0101234abcd",   // 혼합
            "010１２３４５６７８", // 전각 숫자
            "0101234\n567",  // 개행 포함
    })
    public void testInvalidPhoneNumber(String phoneNumber) throws Exception {
        //given
        String email = "test@test.com";
        String password = "abcde123";
        String name = "test";
        String nickname = "test";
        UserRole userRole = UserRole.ROLE_USER;
        String violationMessage = "핸드폰 번호 형식이 올바르지 않습니다. 숫자만 입력해주세요.";
        SignupRequest signupRequest = new SignupRequest(email, password, name, nickname, phoneNumber, userRole);

        //when
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        //then
        assertThat(violations).hasSize(1);

        ConstraintViolation<SignupRequest> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("phoneNumber");

        assertThat(violation.getMessage()).isEqualTo(violationMessage);
    }
}