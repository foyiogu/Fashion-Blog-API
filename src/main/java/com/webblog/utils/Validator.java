package com.webblog.utils;

import org.springframework.stereotype.Component;

@Component
public class Validator {

    /**
     * ^ - start of string
     * $ - end of string.
     * @param email
     * @return boolean
     * */
    public boolean validateEmail(String email){
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    /**
     * Allows password only greater than 5 in length
     * @param password
     * @return boolean
     * */
    public boolean validatePassword(String password){
        if(password.length() > 5) return true;
        return false;
    }

    /**
     * ^ - start of string
     * [a-zA-Z]{3,} - 3 or more ASCII letters
     * (?: [a-zA-Z]+){0,2} - 0 to 2 occurrences of a space followed with one or more ASCII letters
     * $ - end of string.
     * @param fullName
     * @return boolean
     * */
    public boolean validateFullName(String fullName){
        String regex = "^[a-zA-Z]{3,}(?: [a-zA-Z]+){1,2}$";
        return fullName.matches(regex);
    }

    /**
     * ^ - start of string
     * [a-zA-Z]{4,} - 4 or more ASCII letters
     * $ - end of string.
     * @param username
     * @return boolean
     * */
    public boolean validateUserName(String username){
        String regex = "^[a-zA-Z]{3,}$";
        return username.matches(regex);
    }

}
