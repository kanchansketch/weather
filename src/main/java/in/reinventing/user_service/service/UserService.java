package in.reinventing.user_service.service;

import in.reinventing.user_service.dto.UserDTO;
import in.reinventing.user_service.entity.infa.User;
import in.reinventing.user_service.exception.UsernameNotFoundException;
import in.reinventing.user_service.mapper.UserAndUserDTOMapper;
import in.reinventing.user_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO findById(String id) throws UsernameNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException(String.format("User %s not found!",id)));
        return UserAndUserDTOMapper.userToUserDTO(user);
    }

    public UserDTO createUser(UserDTO userDTO){
        User user = UserAndUserDTOMapper.userDTOToUser(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = this.userRepository.save(user);
        return UserAndUserDTOMapper.userToUserDTO(user);
    }

    public Page<UserDTO> findAll(PageRequest of) {
        return this.userRepository.findAll(of).map(user-> UserAndUserDTOMapper.userToUserDTO(user));
    }

    public UserDTO update(String id,UserDTO userRequest) throws UsernameNotFoundException {
        User user  = this.userRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException(String.format("User %s not found!",id)));
        user.setName(userRequest.getName());
        user.setRole(userRequest.getRole());
        user = this.userRepository.save(user);
        return UserAndUserDTOMapper.userToUserDTO(user);
    }

    public void deleteUser(String id) {
        this.userRepository.deleteById(id);
    }
}
