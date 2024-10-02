package com.alakh.controller;

import com.alakh.config.JwtGenerator;
import com.alakh.request.LoginRequest;
import com.alakh.response.AuthResponse;
import com.alakh.modal.User;
import com.alakh.repository.UserRepository;
import com.alakh.service.CustomerUserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerUserServiceImplementation customerUserDetails;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception
    {
        String email = user.getEmail();
        String password = user.getPassword();
        String fullName = user.getFullName();
        String role = user.getRole();

        User isEmailUsed = userRepository.findByEmail(email);
        if(isEmailUsed != null)
        {
            throw new Exception("User with the same email id already exists");
        }

        //create new user
        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFullName(fullName);
        createdUser.setRole(role);

        User savedUser = userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtGenerator.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMassage("Register Success");
        authResponse.setStatus(true);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest)
    {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        System.out.println(username+" ----- "+password);

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtGenerator.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMassage("Login Success");
        authResponse.setStatus(true);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customerUserDetails.loadUserByUsername(username);
        System.out.println("Sign-in Details: "+ userDetails);

        if(userDetails == null)
        {
            System.out.println("Sign-in Details: Null: "+ userDetails);
            throw new BadCredentialsException("Invalid Username or Password");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword()))
        {
            System.out.println("Sign-in Details: Password does not match: "+ userDetails);
            throw new BadCredentialsException("Invalid Username or Password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }
}
