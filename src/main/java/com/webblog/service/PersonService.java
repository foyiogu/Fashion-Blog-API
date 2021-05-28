package com.webblog.service;

import com.webblog.POJO.PersonMapper;
import com.webblog.model.Person;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PersonService {
    String register(Person person);
    String loginUser(String email, String password);
    List<PersonMapper> getUsers();
    PersonMapper getUserById(Long id);
    String updateImage(MultipartFile file, Long personId, Person person);
    void deactivatedPersonScheduler();
    boolean deleteUser(Long personId, Person person);
    String reverseDeleteActionUserAccount(Person person, Long personId);
}
