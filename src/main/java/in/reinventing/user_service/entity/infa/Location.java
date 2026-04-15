package in.reinventing.user_service.entity.infa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "lat",nullable = false)
    private String lat;

    @Column(name = "lang",nullable = false)
    private String lang;

    @Column(name = "location",nullable = false)
    private String location;

    @Column(name = "time_zone", nullable = false)
    private String timeZone;

    @Column(name = "user_id")
    private String userId;
}
