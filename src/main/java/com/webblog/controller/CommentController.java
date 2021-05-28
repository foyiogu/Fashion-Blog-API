package com.webblog.controller;

import com.webblog.POJO.CommentMapper;
import com.webblog.POJO.ResponseHandler;
import com.webblog.model.Person;
import com.webblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<ResponseHandler> createComment(@RequestBody CommentMapper commentMapper, HttpSession httpSession){

        ResponseHandler responseHandler = new ResponseHandler();

        Person person = (Person) httpSession.getAttribute("person");

        if(person == null) {
            responseHandler.setMessage("User not login");
            responseHandler.setStatusCode(401);
            return new ResponseEntity<>(responseHandler, HttpStatus.UNAUTHORIZED);
        }

        if(commentService.createComment(commentMapper.getUserId(), commentMapper.getPostId(), commentMapper.getComment())){
            responseHandler.setStatusCode(201);
            responseHandler.setMessage("successfully created comment");

            return new ResponseEntity<>(responseHandler, HttpStatus.CREATED);
        }else {
            responseHandler.setStatusCode(400);
            responseHandler.setMessage("couldn't create comment");

            return new ResponseEntity<>(responseHandler, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ResponseHandler> editComment(@PathVariable Long id, @RequestBody CommentMapper commentMapper, HttpSession httpSession){

        ResponseHandler responseHandler = new ResponseHandler();

        Person person = (Person) httpSession.getAttribute("person");

        if(person == null) {
            responseHandler.setMessage("User not login");
            responseHandler.setStatusCode(401);
            return new ResponseEntity<>(responseHandler, HttpStatus.UNAUTHORIZED);
        }

        if(commentService.editComment(id, person, commentMapper.getPostId(), commentMapper.getComment())){
            responseHandler.setStatusCode(204);
            responseHandler.setMessage("successfully updated comment");

            return new ResponseEntity<>(responseHandler, HttpStatus.ACCEPTED);
        }else{
            responseHandler.setStatusCode(400);
            responseHandler.setMessage("couldn't update comment");

            return new ResponseEntity<>(responseHandler, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> getCommentByPostId(@PathVariable Long id, HttpSession httpSession){

        ResponseHandler responseHandler = new ResponseHandler();

        Person person = (Person) httpSession.getAttribute("person");

        if(person == null) {
            responseHandler.setMessage("User not login");
            responseHandler.setStatusCode(401);
            return new ResponseEntity<>(responseHandler, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(commentService.getCommentsByPostId(id), HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, HttpSession httpSession){
        ResponseHandler responseHandler = new ResponseHandler();

        Person person = (Person) httpSession.getAttribute("person");

        if(person == null) {
            responseHandler.setMessage("User not login");
            responseHandler.setStatusCode(401);

            return new ResponseEntity<>(responseHandler, HttpStatus.UNAUTHORIZED);
        }

        if(commentService.deleteComment(id)){
            responseHandler.setMessage("successfully deleted comment");
            responseHandler.setStatusCode(204);

            return new ResponseEntity<>(responseHandler, HttpStatus.OK);
        }else{
            responseHandler.setMessage("error deleting comment");
            responseHandler.setStatusCode(400);

            return new ResponseEntity<>(responseHandler, HttpStatus.BAD_REQUEST);
        }
    }
}
