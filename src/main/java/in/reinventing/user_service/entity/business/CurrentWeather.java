package in.reinventing.user_service.entity.business;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(
        name="current_weather",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uk_current_weather",
                    columnNames = {"location", "date", "time_zone"}
            )
})
public class CurrentWeather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private BigInteger id;

    @Column(name = "temperature",nullable = false)
    private Double temperature;

    @Column(name = "wind_speed",nullable = false)
    private Double windSpeed;

    @Column(name = "humidity",nullable = false)
    private Double humidity;

    @Column(name = "visibility",nullable = false)
    private Double visibility;

    @Column(name = "weather_icon_code",nullable = false)
    private Integer weatherIconCode;

    @Column(name = "weather_icon_url",nullable = false)
    private String weatherIconUrl;

    @Column(name = "location",nullable = false)
    private String location;

    @Column(name = "date",nullable = false)
    private LocalDateTime date;

    @Column(name = "time_zone",nullable = false)
    private String timeZone;
}
