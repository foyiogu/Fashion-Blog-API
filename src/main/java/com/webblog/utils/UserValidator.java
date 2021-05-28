package com.webblog.utils;

import org.springframework.beans.factory.annotation.Autowired;

public class UserValidator{

    private Validator validator = new Validator();
    /**
     * validation for registration
     * @param email
     * @param fullName
     * @param password
     * @return boolean
     * */
    public String validateRegistration(String email, String password, String username,
                                              String fullName){

        String message = "Successful validation";

        if(!validator.validateEmail(email))
            message = "Invalid Email Entered";

        if(!validator.validatePassword(password))
            message = "Enter Password greater than 5 characters length";

        if(!validator.validateFullName(fullName))
            message = "Enter your full name(Surname and Firstname)";

        if(!validator.validateUserName(username))
            message = "Enter Username greater than 3 characters length";

        return message;

    }
}
