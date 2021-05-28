package com.webblog.controller;

import com.webblog.POJO.LikesMapper;
import com.webblog.POJO.ResponseHandler;
import com.webblog.model.Person;
import com.webblog.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/")
public class LikesController {

    @Autowired
    LikesService likesService;

    @PostMapping("/likes")
    public ResponseEntity<ResponseHandler> likePost(@RequestBody LikesMapper likesMapper, HttpSession httpSession){

        ResponseHandler responseHandler = new ResponseHandler();

        Person person = (Person) httpSession.getAttribute("person");

        if(person == null) {
            responseHandler.setMessage("User not login");
            responseHandler.setStatusCode(401);

            return new ResponseEntity<>(responseHandler, HttpStatus.UNAUTHORIZED);
        }

        System.out.println(likesMapper);

        if(likesService.likePost(person, likesMapper.getPostId(), likesMapper.getAction())){
            responseHandler.setMessage("successful");
            responseHandler.setStatusCode(204);

            return new ResponseEntity<>(responseHandler, HttpStatus.OK);
        }else {
            responseHandler.setMessage("Server Error");
            responseHandler.setStatusCode(500);

            return new ResponseEntity<>(responseHandler, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
