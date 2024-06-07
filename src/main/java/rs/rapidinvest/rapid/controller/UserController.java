package rs.rapidinvest.rapid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rs.rapidinvest.rapid.DTO.LoginForm;
import rs.rapidinvest.rapid.model.User;
import rs.rapidinvest.rapid.repository.UserRepository;
import rs.rapidinvest.rapid.service.UserService;

import java.util.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://127.0.0.1:5500")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;


    @PostMapping("/addUser")
    public ResponseEntity<Object> newUser(@RequestBody User newUser){
        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>("User saved successfully", HttpStatus.OK);

    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }


    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginForm loginDTO){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        // Authenticate user
        Authentication authentication = authenticationProvider.authenticate(authenticationToken);

        // Set authentication to SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Return success response
        return ResponseEntity.ok("User logged in successfully");

    }





}
