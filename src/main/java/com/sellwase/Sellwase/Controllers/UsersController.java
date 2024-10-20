package com.sellwase.Sellwase.Controllers;

import com.sellwase.Sellwase.Model.Users;
import com.sellwase.Sellwase.Service.UsersService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class UsersController {

    @Autowired
    UsersService usersService;

    @PostMapping("/addUser")
    public ResponseEntity<Users> addUser(@RequestBody Users user)
    {
        usersService.addNewUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/checkLogin/{uname}/{pass}")
    public ResponseEntity<Users> checkLogin(@PathVariable("uname") String uname, @PathVariable("pass") String pass)
    {
        Users user = usersService.checkLogin(uname, pass);

        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/checkUniqueEmail/{uemail}")
    public ResponseEntity<Users> checkUniqueEmail(@PathVariable("uemail") String email)
    {
        Users user = usersService.checkEmail(email);

        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(user, HttpStatus.FOUND);

    }


    @PostMapping("/addImage")
    public String addProfilePic(@RequestParam("file") MultipartFile file, @RequestParam("uid") int uid) throws IOException
    {
        String path = usersService.addProfilePic(file, uid);
        return path;
    }

    @GetMapping("/getUserDetails/{uid}")
    public ResponseEntity<Users> getUserDetails(@PathVariable("uid") int uid)
    {
        Users user = usersService.getUserDetails(uid);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/deleteProfile/{uid}")
    public ResponseEntity<String> deleteProfile(@PathVariable("uid") int uid)
    {
        usersService.deleteProfile(uid);

        return new ResponseEntity<>("Deleted successfully!", HttpStatus.OK);
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<String> updateProfile(@RequestBody Users users)
    {
        usersService.updateProfile(users);
        return new ResponseEntity<>("Updated successfully!", HttpStatus.OK);
    }

    @PutMapping("/updateRating/{uid}/{rating}")
    public ResponseEntity<String> updateUserRating(@PathVariable("uid") int uid, @PathVariable("rating") int rating)
    {
        usersService.updateRating(uid, rating);
        return new ResponseEntity<>("Ratings updated successfully!", HttpStatus.OK);
    }

}
