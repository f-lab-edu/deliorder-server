package com.deliorder.api.api.dto;

import com.deliorder.api.entity.ImageFileType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PresignedUrlRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "image.jpg",
            "menu_01.jpeg",
            "menu_02.png",
            "menu_02.PNG",
            "profile.webp",
            "profile.wEbP",
            "profile.JPeG",
            "a/b/c/d/e.png"
    })
    @DisplayName("유효한 이미지 파일 이름은 검증을 통과해야 한다.")
    public void testValidFilenames(String fileName) throws Exception {
        //given
        ImageFileType mockFileType = ImageFileType.JPEG;
        PresignedUrlRequest presignedUrlRequest = new PresignedUrlRequest(fileName, mockFileType);

        //when
        Set<ConstraintViolation<PresignedUrlRequest>> violations = validator.validate(presignedUrlRequest);

        //then
        assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "메뉴.pdf",
            "data.gif",
            "archive.zip",
            "확장자-없음",
            "dotfile.",
            ".jpg",
            "file-with-double-ext.png.txt"
    })
    @DisplayName("유효하지 않은 파일 이름 또는 확장자는 검증에 실패해야 한다.")
    void testInvalidFileNames(String fileName) {
        // given
        ImageFileType mockFileType = ImageFileType.JPEG;
        String violationMessage = "이미지 파일(jpg, jpeg, png, webp)만 업로드할 수 있습니다.";
        PresignedUrlRequest request = new PresignedUrlRequest(fileName, mockFileType);

        // when
        Set<ConstraintViolation<PresignedUrlRequest>> violations = validator.validate(request);

        // then
        // 1. 위반 1건 발생
        assertThat(violations).hasSize(1);

        // 2. fileName 필드에 대한 위반이 발생하는지 확인
        ConstraintViolation<PresignedUrlRequest> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("fileName");

        // 3. 위반 메시지 검증
        assertThat(violation.getMessage()).isEqualTo(violationMessage);
    }

}
