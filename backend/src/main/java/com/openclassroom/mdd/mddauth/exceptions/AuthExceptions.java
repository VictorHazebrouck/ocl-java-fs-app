package com.openclassroom.mdd.mddauth.exceptions;

import lombok.experimental.StandardException;

public class AuthExceptions {

    @StandardException
    public static class UserNotFound extends RuntimeException {}

    @StandardException
    public static class WrongPassword extends RuntimeException {}

    @StandardException
    public static class SessionNotFound extends RuntimeException {}

    @StandardException
    public static class SessionSuspicious extends RuntimeException {}

    @StandardException
    public static class IllegalState extends RuntimeException {}

    @StandardException
    public static class AccountNotFound extends RuntimeException {}
    // static class WrongPassword extends RuntimeException {}
}
