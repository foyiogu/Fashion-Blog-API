package com.webblog.controller;

import com.webblog.POJO.LoginMapper;
import com.webblog.POJO.PersonMapper;
import com.webblog.POJO.ResponseHandler;
import com.webblog.model.Person;
import com.webblog.service.PersonService;
import com.webblog.service.ServiceImpl.FollowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(path = "/api/users")
public class PersonController {

    @Autowired
    PersonService personService;

    @Autowired
    FollowServiceImpl followService;

    @PostMapping("/register")
    public ResponseHandler registerUser(@RequestBody Person person){

        String message = personService.register(person);

        //http server response
        ResponseHandler res = new ResponseHandler();

         if(message.equals("success")){
             res.setStatusCode(201);
             res.setMessage(message);
         }else{
             res.setMessage(message);

             if(message.equals("failed")) {
                 res.setStatusCode(409);
                 res.setMessage("Email already exist");
             }else{
                 res.setStatusCode(403);
                 res.setMessage(message);
             }
         }

        return res;
    }

    @PostMapping("/login")
    public ResponseHandler loginUser(@RequestBody LoginMapper login, HttpServletRequest req){

        HttpSession httpSession = req.getSession();
        String message = personService.loginUser(login.getEmail(), login.getPassword());

        //http server response
        ResponseHandler res = new ResponseHandler();

        if(message.equals("successful")){
            //put user inside session
            Person person = new Person();
            person.setEmail(login.getEmail());
            person.setPassword(login.getPassword());

            httpSession.setAttribute("person", person);

            res.setStatusCode(200);
        }else{
            res.setStatusCode(404);
        }

        res.setMessage(message);

        return res;
    }

    @GetMapping("")
    public List<PersonMapper> getUsers(){
        return personService.getUsers();
    }

    @GetMapping("/{id}")
    public PersonMapper getUserById(@PathVariable Long id){
        return personService.getUserById(id);
    }

    @PutMapping("/upload_image/{id}")
    public ResponseHandler uploadImage(@RequestParam("imageFile") MultipartFile file, @PathVariable Long id, HttpSession httpSession){

        Person person = (Person) httpSession.getAttribute("person");

        //http server response
        ResponseHandler res = new ResponseHandler();

        if(person == null) {
            res.setStatusCode(401);
            res.setMessage("user not login!!!");

            return res;
        }

        String message = personService.updateImage(file, id, person);

        if(message.equals("successfully uploaded image")){
            res.setStatusCode(201);
        }else{

            if(message.equals("user not authorized")) res.setStatusCode(401);

            if(message.equals("Please select an image")) res.setStatusCode(400);

            if(message.equals("failed")) {
                res.setStatusCode(500);
                res.setMessage("Internal Server error");
                return res;
            }
        }

        res.setMessage(message);

        return res;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseHandler deleteUser(@PathVariable Long id, HttpSession httpSession){

        Person person = (Person) httpSession.getAttribute("person");

        //http server response
        ResponseHandler res = new ResponseHandler();

        if(person == null) {
            res.setStatusCode(401);
            res.setMessage("user not login!!!");

            return res;
        }

        if(personService.deleteUser(id, person)){
            res.setStatusCode(204);
            res.setMessage("account deletion pending");
        }else{
            res.setStatusCode(401);
            res.setMessage("user not authorized");
        }

        return res;
    }

    @PostMapping("/reverseDelete/{id}")
    public ResponseEntity<?> reverseAccountDeletion(@PathVariable Long id,  HttpSession httpSession){
        Person person = (Person) httpSession.getAttribute("person");

        //http server response
        ResponseHandler res = new ResponseHandler();

        if(person == null) {
            res.setStatusCode(401);
            res.setMessage("user not login!!!");

            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }

        String message = personService.reverseDeleteActionUserAccount(person, id);

        if(message.equals("successfully reversed")){
            res.setStatusCode(204);
            res.setMessage(message);

            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
        }else{

            res.setMessage(message);

            if(message.equals("user not authorized")){
                res.setStatusCode(401);

                return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
            }else if(message.equals("user not found")){
                res.setStatusCode(404);

                return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/follow/{id}")
    public ResponseEntity<ResponseHandler> followUser(@PathVariable Long id, HttpSession httpSession){
        Person person = (Person) httpSession.getAttribute("person");

        //http server response
        ResponseHandler res = new ResponseHandler();

        if(person == null) {
            res.setStatusCode(401);
            res.setMessage("user not login!!!");

            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }

        String message = followService.followPerson(id, person);

        if(message.equals("successful followed") || message.equals("successfully unfollowed")){
            res.setStatusCode(201);
            res.setMessage(message);

            return new ResponseEntity<>(res, HttpStatus.OK);

        }else{
            res.setStatusCode(500);
            res.setMessage("Server Error");

            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/followers/{id}")
    public ResponseEntity<?> getFollowers(@PathVariable Long id, HttpSession httpSession){
        Person person = (Person) httpSession.getAttribute("person");

        //http server response
        ResponseHandler res = new ResponseHandler();

        var data = followService.getFollowersById(id, person);

        if(person == null) {
            res.setStatusCode(401);
            res.setMessage("user not login!!!");

            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }

        if(data.equals("user not authorized")){
            res.setStatusCode(401);
            res.setMessage((String) data);

            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(followService.getFollowersById(id, person), HttpStatus.OK);
    }

    @GetMapping("/followees/{id}")
    public ResponseEntity<?> getFollowee(@PathVariable Long id, HttpSession httpSession){
        Person person = (Person) httpSession.getAttribute("person");

        //http server response
        ResponseHandler res = new ResponseHandler();

        if(person == null) {
            res.setStatusCode(401);
            res.setMessage("user not login!!!");

            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(followService.getFolloweesByCurrentUserId(id, person), HttpStatus.OK);
    }


}
