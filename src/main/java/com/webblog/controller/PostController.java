package com.webblog.controller;

import com.webblog.POJO.ResponseHandler;
import com.webblog.model.Person;
import com.webblog.model.Post;
import com.webblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/create")
    public ResponseEntity<ResponseHandler> createPost(@RequestPart("imageFile") MultipartFile file, @RequestPart("body") String body, @RequestPart("title") String title, HttpSession httpSession){

        ResponseHandler res = new ResponseHandler();

        Person person = (Person) httpSession.getAttribute("person");

        if(person == null) {
            res.setMessage("User not login");
            res.setStatusCode(401);
            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }

        boolean valid = postService.createPost(file, person, body, title);

        if(!valid){
            res.setMessage("Post Already Exist");
            res.setStatusCode(409);
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }

        res.setMessage("successfully created post");
        res.setStatusCode(201);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostByPostId(@PathVariable Long id, HttpSession httpSession){
        ResponseHandler res = new ResponseHandler();

        Person person = (Person) httpSession.getAttribute("person");

        if(person == null) {
            res.setMessage("User not login");
            res.setStatusCode(401);
            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getPost(HttpSession httpSession){
        ResponseHandler res = new ResponseHandler();

        Person person = (Person) httpSession.getAttribute("person");

        if(person == null) {
            res.setMessage("User not login");
            res.setStatusCode(401);
            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(postService.getPost(person), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseHandler> getUpdatePost(@PathVariable Long id, @RequestBody Post post, HttpSession httpSession){
        ResponseHandler res = new ResponseHandler();

        Person person = (Person) httpSession.getAttribute("person");

        if(person == null) {
            res.setMessage("User not login");
            res.setStatusCode(401);

            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }

        String message = postService.updatePost(person, post);
        res.setMessage(message);

        if(message.equals("successfully updated post")){
            res.setStatusCode(204);

            return new ResponseEntity<>(res, HttpStatus.OK);
        }else{
            if(message.equals("user not authorized")) res.setStatusCode(401);
            else res.setStatusCode(400);

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseHandler> deletePost(@PathVariable Long id, HttpSession httpSession){
        ResponseHandler res = new ResponseHandler();

        Person person = (Person) httpSession.getAttribute("person");

        if(person == null) {
            res.setMessage("User not login");
            res.setStatusCode(401);

            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }

        String message = postService.deletePost(id, person);
        res.setMessage(message);

        if(message.equals("successfully deleted post")){
            res.setStatusCode(200);

            return new ResponseEntity<>(res, HttpStatus.OK);
        }else{
            if(message.equals("user not authorized")) res.setStatusCode(401);
            else res.setStatusCode(400);

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/followeepost/{id}")
    public ResponseEntity<?> getPostByFollowee(@PathVariable Long id, HttpSession httpSession){
        ResponseHandler res = new ResponseHandler();

        Person person = (Person) httpSession.getAttribute("person");

        if(person == null) {
            res.setMessage("User not login");
            res.setStatusCode(401);

            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(postService.displayAllPostByFollower(id, person), HttpStatus.OK);
    }

}
