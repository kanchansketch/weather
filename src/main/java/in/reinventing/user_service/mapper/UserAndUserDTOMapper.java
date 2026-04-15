package in.reinventing.user_service.mapper;

import in.reinventing.user_service.dto.UserDTO;
import in.reinventing.user_service.entity.infa.User;

public class UserAndUserDTOMapper {
    public static UserDTO userToUserDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .userName(user.getUserName())
                .role(user.getRole())
                .build();
    }
    public static User userDTOToUser(UserDTO userDTO){
        return User.builder()
                .name(userDTO.getName())
                .userName(userDTO.getUserName())
                .email(userDTO.getEmail())
                .role(userDTO.getRole())
                .password(userDTO.getPassword())
                .build();
    }
}
