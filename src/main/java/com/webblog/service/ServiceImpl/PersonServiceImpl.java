package com.webblog.service.ServiceImpl;

import com.webblog.POJO.PersonMapper;
import com.webblog.model.Person;
import com.webblog.repository.PersonRepository;
import com.webblog.service.PersonService;
import com.webblog.utils.PasswordHashing;
import com.webblog.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    public String register(Person person){
        String flag = "failed";

        //validating users input
        UserValidator userValidator = new UserValidator();
        String message = userValidator.validateRegistration(person.getEmail(),
                person.getPassword(), person.getUsername(),person.getName());

        if(!message.equals("Successful validation")) return message;


        try {
            Optional check = personRepository.findPersonByEmail(person.getEmail());

            if(check.isEmpty()) {
                person.setPassword(PasswordHashing.encryptPassword(person.getPassword()));
                personRepository.save(person);
                flag = "successfully created";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * GET operation on Person
     * @param email
     * @param password
     * @return Person
     * */
    public String loginUser(String email, String password){

        String message = "";

        try {

            Optional<Person> person = personRepository.findPersonByEmail(email);

            if(person.isPresent()){

                if(person.get().getIsDelete() == 1) return "user not found";

                if(!password.equals(PasswordHashing.decryptPassword(person.get().getPassword())))
                    message = "password incorrect";
                else
                    message = "successful";

            }else{
                message = "email not found";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }

    /**
     * GET operation on Person
     * @return Person(all)
     * */
    public List<PersonMapper> getUsers(){

        List<PersonMapper> persons = new ArrayList<>();

        try {

            List<Person> personData = personRepository.findAll();

            personData.forEach(person->{
                PersonMapper personMapper = new PersonMapper();

                personMapper.setName(person.getName());
                personMapper.setUsername(person.getUsername());
                personMapper.setId(person.getId());
                personMapper.setEmail(person.getEmail());

                if(person.getProfilePics() == null) personMapper.setProfilePics("No image");
                else personMapper.setProfilePics(person.getProfilePics());

                persons.add(personMapper);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return persons;
    }

    /**
     * GET operation on Person
     * @return Person
     * */
    public PersonMapper getUserById(Long id){

        PersonMapper personMapper = new PersonMapper();

        try {

            Person person = personRepository.findById(id).get();

            personMapper.setId(person.getId());
            personMapper.setEmail(person.getEmail());
            personMapper.setUsername(person.getUsername());
            personMapper.setName(person.getName());

            if(person.getProfilePics() == null) personMapper.setProfilePics("No image");
            else personMapper.setProfilePics(person.getProfilePics());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return personMapper;
    }

    public String updateImage(MultipartFile file, Long personId, Person user){
        String flag = "failed";

        try {

            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            //Extract the image extension
            String ext = fileName.substring(fileName.indexOf(".")+1);

            if(fileName.isEmpty()){
                return "Please select an image";
            }

            //find person by id
            Person person = personRepository.findPersonByEmail(user.getEmail()).get();

            System.out.println(person.getId());
            System.out.println(personId);

            if(person.getId() == personId){
                //set person image
                person.setProfilePics("data:image/"+ext+";base64,"+Base64.getEncoder().encodeToString(file.getBytes()));

                personRepository.save(person);

                flag = "successfully uploaded image";

            }else{
                flag = "user not authorized";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    public boolean deleteUser(Long personId, Person person){
        boolean flag = false;

        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

        try {

            Person user = personRepository.findPersonByEmail(person.getEmail()).get();

            if(user.getId() == personId){

                Calendar c = Calendar.getInstance();
                c.add(Calendar.MINUTE, 1);
                String presentDate = DateFor.format(c.getTime());

                user.setPersonDeactivated(1);
                user.setRemoveDate(presentDate);

                personRepository.save(user);

                flag = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }


    public String reverseDeleteActionUserAccount(Person person, Long personId){

        String flag = "Server Error";

        try {
            Person person1 = personRepository.findPersonByEmail(person.getEmail()).get();

            if(person1.getId() != personId) flag = "user not authorized";
            else{
                if(person1.getIsDelete() == 0){
                    person1.setPersonDeactivated(0);

                    personRepository.save(person1);

                    flag = "successfully reversed";
                }else{
                    flag = "user not found";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    public void deactivatedPersonScheduler(){

        List<Person> persons = personRepository.findAllByPersonDeactivated(1);

        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

        System.out.println("scheduler working");

        persons.forEach(person -> {

            String presentDate = DateFor.format(date);
            String deleteDate = person.getRemoveDate();

            int actionDelete = presentDate.compareTo(deleteDate);

            if(actionDelete > 0 || actionDelete == 0) {

                System.out.println("user finally deleted");
                person.setIsDelete(1);

                personRepository.save(person);
            }

        });
    }


}
