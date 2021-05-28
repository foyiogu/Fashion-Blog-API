package com.webblog.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {
    private Validator validator = new Validator();
    @Test
    void testEmail(){
        assertTrue(validator.validateEmail("ade@gmail.com"));
        assertFalse(validator.validateEmail("ade@gmailcom"));
        assertFalse(validator.validateEmail("adgmail"));
        assertFalse(validator.validateEmail("@gmail.com"));
    }

    @Test
    void testPassword(){
        assertTrue(validator.validatePassword("ade1234"));
        assertFalse(validator.validatePassword("ade1"));
        assertFalse(validator.validatePassword("ade12"));
        assertFalse(validator.validatePassword("ade"));
    }

    @Test
    void testFullName(){
        assertTrue(validator.validateFullName("Ade damilola"));
        assertTrue(validator.validateFullName("ayo adewale seyi"));
        assertFalse(validator.validateFullName("Ade"));
        assertFalse(validator.validateFullName("ayo"));
        assertFalse(validator.validateFullName("adewale"));
        assertFalse(validator.validateFullName(""));
    }

}