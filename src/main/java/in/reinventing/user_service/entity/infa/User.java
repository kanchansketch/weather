package in.reinventing.user_service.entity.infa;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TBL_USER")
public class User {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @PrePersist
    public void generateUUID(){
        this.id = UUID.randomUUID().toString();
    }
}
