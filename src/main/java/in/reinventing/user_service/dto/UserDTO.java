package in.reinventing.user_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private String id;

    @Size(min = 5,max = 20,message = "User name must have length between 5 - 20!")
    private String userName;
    private String name;

    @Email(message = "Invalid email!")
    private String email;

    @Size(min = 5,max = 36,message = "User name must have length between 5 - 36!")
    private String password;
    private String role;
}
