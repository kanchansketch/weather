package in.reinventing.user_service.controller;

import in.reinventing.user_service.dto.UserDTO;
import in.reinventing.user_service.exception.UsernameNotFoundException;
import in.reinventing.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    //Get one by Id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO fetchUserById(@PathVariable("id") String id) throws UsernameNotFoundException {
        return this.userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@Valid @RequestBody UserDTO userRequest){
        return this.userService.createUser(userRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDTO> fetchUsers(@RequestParam(value = "page_number",required = false,defaultValue = "0") Integer pageNumber,
                                    @RequestParam(value = "page_size",required = false,defaultValue = "10") Integer pageSize){
        return this.userService.findAll(PageRequest.of(pageNumber,pageSize));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDTO updateUser(@PathVariable("id") String id,@Valid @RequestBody UserDTO userRequest) throws UsernameNotFoundException {
        return this.userService.update(id,userRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") String id){
        this.userService.deleteUser(id);
    }
}
