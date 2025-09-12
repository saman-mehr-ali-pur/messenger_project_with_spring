package com.example.demo.controller;


import com.example.demo.model.User;
import com.example.demo.model.UserDetailsImp;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userSservices;

//    @CrossOrigin(origins = "*")
    @PostMapping("/save")
    public User saveUser(@RequestBody User user){
        user.setEnable(false);
        user.setRole("USER");

        System.out.println(user);


        return  userSservices.saveUser(user);
    }

    @GetMapping("/getUsername")
    public String getUsername() {
        // Get the principal from the SecurityContext
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Check if the principal is a UserDetails object
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            return userDetails.getUsername(); // Return the username from the UserDetails object
        } else if (principal instanceof String) {
            // This case handles the "anonymousUser" or other String principals
            return (String) principal;
        }

        // Handle cases where the principal is not a UserDetails or String
        return "Not Authenticated or Principal Type Unknown";
    }

    @PostMapping("/get")
    public User getUser(@RequestBody User user){

        return  userSservices.findByUsername(user);

    }

    @GetMapping("/all/")
    public List<User> getAllUsers(@RequestParam("page") int page, @RequestParam("size") int size){

        return  userSservices.
                getAll(page,size).
                    stream().
                        toList();

    }

    @GetMapping("/check_username/{username}")
    public ResponseEntity checkUsername(@PathVariable("username") String username){

        if (! userSservices.isExist(username)){
            return ResponseEntity.status(404).body(false);
        }

        return ResponseEntity.status(HttpStatus.FOUND).body(true);
    }

    @PostMapping("/search")
    public List<User> search(@RequestBody User user){
        return userSservices.searchUsername(user);
    }

//
//    @GetMapping("/check_username/{username}")
//    public boolean checkUsername(@PathVariable("username") String username){
//
//        if (! userSservices.isExist(username)){
//            throw  new NotFoundException("user not found");
//        }
//
//        return true;
//    }
//

}
