package com.springboot.demojpa.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import static  org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDaoService userDaoService;

    //GET /users
    //retrieveAllUsers
    @GetMapping(path = "/users")
    public List<User> retrieveAllUsers(){
        return userDaoService.findAll();
    }

    //GET /users/{id}
    @GetMapping(path = "/user/{id}")
    public Resource<User> retrieveUser(@PathVariable int id){
        User user = userDaoService.findOne(id);
        if(user==null){
            throw new UserNotFoundException("id-"+ id);
        }

        //"all-users", SERVER_PATH + "/users"
        //retrieveAllUsers
        Resource<User> resource = new Resource<User>(user);
        ControllerLinkBuilder linkTo =
                linkTo(methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkTo.withRel("all-users"));

        //HATEOAS
        return resource;
    }

    //input - details of user
    //output - CREATED and Return the created user
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = userDaoService.save(user);

        //Created
        // /user/5
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    //DELETE /users/{id}
    @DeleteMapping(path = "/user/{id}")
    public void deleteUser(@PathVariable int id){
        User user = userDaoService.deleteById(id);
        if(user==null){
            throw new UserNotFoundException("id-"+ id);
        }
    }
}
