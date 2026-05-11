package sales_kodnest.salessavvy.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sales_kodnest.salessavvy.entity.User;
import sales_kodnest.salessavvy.service.UserService;




@RestController
@CrossOrigin(origins = "http://localhost:5174")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")   // So here first the controller will be taking the request from the user in the format of json either it can come from front-end or u can pass it through postman, whatever the json is coming as a user it is passing it to the service, then service is check    
    public ResponseEntity<?> registerUser(@RequestBody User user) { 
        try {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(Map.of("message", "User registered successfully", "user", registeredUser));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
