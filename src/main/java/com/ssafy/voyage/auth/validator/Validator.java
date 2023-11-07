package com.ssafy.voyage.auth.validator;

import com.ssafy.voyage.dto.member.MemberCreationDto;
import com.ssafy.voyage.exception.MemberCreationValidationException;
import com.ssafy.voyage.message.MessageMaker;
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
public class Validator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))"
    );

    private static final Pattern passwordFormatPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,30}$");


    public void validateMemberCreationDto(MemberCreationDto memberCreationDto) throws MemberCreationValidationException {
        // 이름 검사
        isValidName(memberCreationDto);

        // 이메일 검사
        isValidEmail(memberCreationDto);

        // 비밀번호 검사
        isValidPassword(memberCreationDto.getEmail(), memberCreationDto.getPassword(), memberCreationDto.getPasswordAgain());
    }

    private void isValidName(MemberCreationDto memberCreationDto) throws MemberCreationValidationException {
        // 이름 검사
        String name = Optional.ofNullable(memberCreationDto.getName()).orElseThrow(
            () -> new MemberCreationValidationException(MemberCause.NAME, MessageMaker.getMessageMaker().add(NAME).add(NOT_EXISTS).toString()));

        if (name.isBlank()) {
            throw new MemberCreationValidationException(MemberCause.NAME, MessageMaker.getMessageMaker().add(NAME).add(NOT_EXISTS).toString());
        }

        if (name.length() > 10) {
            throw new MemberCreationValidationException(MemberCause.NAME, MessageMaker.getMessageMaker().add(NAME).add(TOO_LONG).toString());
        }
    }

    private void isValidEmail(MemberCreationDto memberCreationDto) throws MemberCreationValidationException {
        // 이메일 규칙 검사
        String email = Optional.ofNullable(memberCreationDto.getEmail()).orElseThrow(
            () -> new MemberCreationValidationException(MemberCause.EMAIL, MessageMaker.getMessageMaker().add(EMAIL).add(NOT_EXISTS).toString()));

        if (email.isBlank()) {
            throw new MemberCreationValidationException(MemberCause.EMAIL, MessageMaker.getMessageMaker().add(EMAIL).add(NOT_EXISTS).toString());
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);

        if (!matcher.matches()) {
            throw new MemberCreationValidationException(MemberCause.EMAIL, MessageMaker.getMessageMaker().add(EMAIL).add(INVALID).toString());
        }
    }

    public boolean isValidPassword(String email, String password, String passwordAgain) throws MemberCreationValidationException {
        password = Optional.ofNullable(password).orElseThrow(
            () -> new MemberCreationValidationException(MemberCause.PASSWORD, MessageMaker.getMessageMaker().add(PASSWORD).add(NOT_EXISTS).toString()));

        passwordAgain = Optional.ofNullable(passwordAgain).orElseThrow(
            () -> new MemberCreationValidationException(MemberCause.PASSWORD, MessageMaker.getMessageMaker().add(PASSWORD).add(AGAIN).add(NOT_EXISTS).toString()));

        if (password.isBlank()) {
            throw new MemberCreationValidationException(MemberCause.PASSWORD, MessageMaker.getMessageMaker().add(PASSWORD).add(NOT_EXISTS).toString());
        }

        // 비밀번호 일치 검사
        if (!password.equals(passwordAgain)) {
            throw new MemberCreationValidationException(MemberCause.PASSWORD, MessageMaker.getMessageMaker().add(PASSWORD).add(AGAIN).add(NOT_EQUAL).toString());
        }

        // 비밀번호 포맷 확인(영문, 특수문자, 숫자 포함 8자 이상, 30자 이하)
        Matcher passwordFormatMatcher = passwordFormatPattern.matcher(password);

        if (!passwordFormatMatcher.find()) {
            throw new MemberCreationValidationException(MemberCause.PASSWORD, MessageMaker.getMessageMaker().add(PASSWORD).add(FORMAT).add(INVALID).toString());
        }

        // 반복된 문자 확인
        Pattern passwordRepeatPattern = Pattern.compile("(\\w)\\1{2,}");
        Matcher passwordRepeatMatcher = passwordRepeatPattern.matcher(password);

        if (passwordRepeatMatcher.find()) {
            throw new MemberCreationValidationException(MemberCause.PASSWORD, MessageMaker.getMessageMaker().add(PASSWORD).add(REPEATED).add(CHARACTER).add(CONTAINED).toString());
        }

        // 아이디 포함 확인
        String id = email.split("@")[0];
        if (password.contains(id)) {
            throw new MemberCreationValidationException(MemberCause.PASSWORD, MessageMaker.getMessageMaker().add(PASSWORD).add(ID).add(CONTAINED).toString());
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
