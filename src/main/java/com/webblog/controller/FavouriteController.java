package com.webblog.controller;

import com.webblog.POJO.ResponseHandler;
import com.webblog.model.Person;
import com.webblog.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/favourites")
public class FavouriteController {

    @Autowired
    private FavouriteService favouriteService;

    @PutMapping("/{id}")
    public ResponseEntity<?> saveFavourite(@PathVariable Long id, HttpSession httpSession){
        ResponseHandler res = new ResponseHandler();
        System.out.println("wow "+id);
        Person person = (Person) httpSession.getAttribute("person");

        if(person == null) {
            res.setMessage("User not login");
            res.setStatusCode(401);
            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }

        if(favouriteService.saveFavourite(person, id)){
            res.setMessage("successfully added to favourites");
            res.setStatusCode(201);

            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }else {
            res.setMessage("Couldn't add to favourites");
            res.setStatusCode(400);

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("")
    public ResponseEntity<?> getUserFavourites(HttpSession httpSession){

        ResponseHandler res = new ResponseHandler();

        Person person = (Person) httpSession.getAttribute("person");

        if(person == null) {
            res.setMessage("User not login");
            res.setStatusCode(401);
            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }

       return new ResponseEntity<>(favouriteService.getFavouritesByUser(person), HttpStatus.OK);
    }
}
