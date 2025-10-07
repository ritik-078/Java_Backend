package com.springboot.user_management.controller;

import com.springboot.user_management.dto.UserDto;
import com.springboot.user_management.entity.User;
import com.springboot.user_management.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(
        name = "CRUD REST APIs for User Management",
        description = "CRUD REST APIs - Create User, Update User, Get User, Get All Users, Delete User"
)
@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

   private UserService userService;

   // These tags are used to customize the description of api endpoints
    @Operation(
            summary = "Create User REST API",
            description = "Create User REST API is used to save user in a database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    @PostMapping("create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user){
        UserDto savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId){
        UserDto user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId,
                                              @RequestBody @Valid UserDto user){
        user.setId(userId);
        UserDto updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
    }
}

//public class UserController {
//
//    private UserService userService;
//
//    @PostMapping("create")
//    public ResponseEntity<User> createUser(@RequestBody User user){
//        return  new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
//    }
//
//    @GetMapping("{id}")
//    public ResponseEntity<User> getUserById(@PathVariable("id") Long userId){
//        User user = userService.getUserById(userId);
//        System.out.println(user);
//        if(user == null)
//            return  new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
//
//        return  new ResponseEntity<>(user,HttpStatus.OK);
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<List<User>> getAllUsers(){
//        List<User> users = userService.getAllUsers();
//        return  new ResponseEntity<>(users,HttpStatus.OK);
//    }
//
//    @PutMapping("{id}")
//    public ResponseEntity<User> updateUser(@PathVariable("id") Long userId,
//                                           @RequestBody User user){
//        user.setId(userId);
//        User updatedUser = userService.updateUser(user);
//
//        return  new ResponseEntity<>(updatedUser,HttpStatus.OK);
//    }
//
//    @DeleteMapping("{id}")
//    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId){
//        userService.deleteUser(userId);
//
//        return new ResponseEntity<>("User Deleted Successfully", HttpStatus.OK);
//    }
//}

//