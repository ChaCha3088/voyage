package com.ssafy.voyage.auth.validator;

import com.ssafy.voyage.dto.member.MemberCreationDto;
import com.ssafy.voyage.exception.MemberFormValidationException;
import com.ssafy.voyage.message.cause.MemberCause;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ssafy.voyage.message.message.MemberMessages.*;
import static com.ssafy.voyage.message.message.Messages.*;

@Component
@RequiredArgsConstructor
public class AuthValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))"
    );

    private static final Pattern passwordFormatPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,30}$");


    public void validateMemberCreationDto(MemberCreationDto memberCreationDto) throws MemberFormValidationException {
        // 이름 검사
        isValidName(memberCreationDto);

        // 이메일 검사
        isValidEmail(memberCreationDto);

        // 비밀번호 검사
        isValidPassword(memberCreationDto.getEmail(), memberCreationDto.getPassword(), memberCreationDto.getPasswordAgain());
    }

    private void isValidName(MemberCreationDto memberCreationDto) throws MemberFormValidationException {
        // 이름 검사
        String name = Optional.ofNullable(memberCreationDto.getName()).orElseThrow(
            () -> new MemberFormValidationException(MemberCause.NAME.getMessage(), new StringBuffer().append(NAME).append(NOT_EXISTS.getMessage()).toString()));

        if (name.isBlank()) {
            throw new MemberFormValidationException(MemberCause.NAME.getMessage(), new StringBuffer().append(NAME).append(NOT_EXISTS.getMessage()).toString());
        }

        if (name.length() > 10) {
            throw new MemberFormValidationException(MemberCause.NAME.getMessage(), new StringBuffer().append(NAME).append(TOO_LONG).toString());
        }
    }

    private void isValidEmail(MemberCreationDto memberCreationDto) throws MemberFormValidationException {
        // 이메일 규칙 검사
        String email = Optional.ofNullable(memberCreationDto.getEmail()).orElseThrow(
            () -> new MemberFormValidationException(MemberCause.EMAIL.getMessage(), new StringBuffer().append(EMAIL).append(NOT_EXISTS.getMessage()).toString()));

        if (email.isBlank()) {
            throw new MemberFormValidationException(MemberCause.EMAIL.getMessage(), new StringBuffer().append(EMAIL).append(NOT_EXISTS.getMessage()).toString());
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);

        if (!matcher.matches()) {
            throw new MemberFormValidationException(MemberCause.EMAIL.getMessage(), new StringBuffer().append(EMAIL).append(INVALID).toString());
        }
    }

    public boolean isValidPassword(String email, String password, String passwordAgain) throws MemberFormValidationException {
        password = Optional.ofNullable(password).orElseThrow(
            () -> new MemberFormValidationException(MemberCause.PASSWORD.getMessage(), new StringBuffer().append(PASSWORD.getMessage()).append(NOT_EXISTS.getMessage()).toString()));

        passwordAgain = Optional.ofNullable(passwordAgain).orElseThrow(
            () -> new MemberFormValidationException(MemberCause.PASSWORD.getMessage(), new StringBuffer().append(PASSWORD.getMessage()).append(AGAIN.getMessage()).append(NOT_EXISTS.getMessage()).toString()));

        if (password.isBlank()) {
            throw new MemberFormValidationException(MemberCause.PASSWORD.getMessage(), new StringBuffer().append(PASSWORD.getMessage()).append(NOT_EXISTS.getMessage()).toString());
        }

        // 비밀번호 일치 검사
        if (!password.equals(passwordAgain)) {
            throw new MemberFormValidationException(MemberCause.PASSWORD.getMessage(), new StringBuffer().append(PASSWORD.getMessage()).append(AGAIN.getMessage()).append(NOT_EQUAL.getMessage()).toString());
        }

        // 비밀번호 포맷 확인(영문, 특수문자, 숫자 포함 8자 이상, 30자 이하)
        Matcher passwordFormatMatcher = passwordFormatPattern.matcher(password);

        if (!passwordFormatMatcher.find()) {
            throw new MemberFormValidationException(MemberCause.PASSWORD.getMessage(), new StringBuffer().append(PASSWORD.getMessage()).append(FORMAT.getMessage()).append(INVALID.getMessage()).toString());
        }

        // 반복된 문자 확인
        Pattern passwordRepeatPattern = Pattern.compile("(\\w)\\1{2,}");
        Matcher passwordRepeatMatcher = passwordRepeatPattern.matcher(password);

        if (passwordRepeatMatcher.find()) {
            throw new MemberFormValidationException(MemberCause.PASSWORD.getMessage(), new StringBuffer().append(PASSWORD.getMessage()).append(REPEATED).append(CHARACTER).append(CONTAINED).toString());
        }

        // 아이디 포함 확인
        String id = email.split("@")[0];
        if (password.contains(id)) {
            throw new MemberFormValidationException(MemberCause.PASSWORD.getMessage(), new StringBuffer().append(PASSWORD.getMessage()).append(ID).append(CONTAINED).toString());
        }

        return true;

//        //특정 특수문자 포함 확인
//        Pattern passwordSpecialPattern = Pattern.compile("[!?@#$%^&*( )/+\\-=~,.]");
//        Matcher passwordSpecialMatcher = passwordSpecialPattern.matcher(password);
//        if (!passwordSpecialMatcher.find()) {
//            throw new NotValidPasswordException(PasswordErrorMessage.PASSWORD_ONLY_CONTAINS_CERTAIN_SPECIAL_CHARACTER.getMessage());
//        }
    }
}
