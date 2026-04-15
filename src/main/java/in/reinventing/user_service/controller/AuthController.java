package in.reinventing.user_service.controller;

import in.reinventing.user_service.dto.UserDTO;
import in.reinventing.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RequestMapping("/auth")
@RestController
@AllArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@Valid @RequestBody UserDTO userRequest){
        return this.userService.createUser(userRequest);
    }
}
