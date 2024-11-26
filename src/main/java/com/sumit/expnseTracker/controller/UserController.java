package com.sumit.expnseTracker.controller;

import com.sumit.expnseTracker.commonResponse.JwtResponse;
import com.sumit.expnseTracker.commonResponse.Response;
import com.sumit.expnseTracker.entity.UserEntity;
import com.sumit.expnseTracker.jwt.JwtUtil;
import com.sumit.expnseTracker.modal.User;
import com.sumit.expnseTracker.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtil jwtUtils;

    private UserRepo repo;

    @Autowired
    public UserController( UserRepo repo){
        this.repo= repo;
    }
//
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody  @Validated User loginRequest)  {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        System.out.println("auth"+authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        try{
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserEntity userDetails = (UserEntity) authentication.getPrincipal();
            return ResponseEntity.ok(new JwtResponse(jwt,HttpStatus.OK,
                    userDetails.getUsername()));
        }
        catch(Exception e){
            System.out.println(e.getCause());
        }

        return ResponseEntity.internalServerError().body(new JwtResponse(null ,HttpStatus.INTERNAL_SERVER_ERROR,null));







    }
    @PostMapping("/sign-up")
    public ResponseEntity<String> createUser(@RequestBody @Validated User user){
        try{
          UserEntity userPresent=  this.repo.findById(user.getEmail()).orElse(null);
          if(userPresent!=null){
              throw new Exception("User already exist");
          }
            UserEntity userEntity= new UserEntity();
            userEntity.setEmail(user.getEmail());
            userEntity.setPassword(user.getPassword());

          UserEntity result=  this.repo.saveAndFlush(userEntity);
          return new ResponseEntity<>("USer created with email"+result.getEmail(),HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
