package in.reinventing.user_service.entity.business;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(
        name="weather_forecast",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uk_current_weather",
                    columnNames = {"location", "forecast_date", "time_zone"}
            )
})
public class WeatherForecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private BigInteger id;

    @Column(name = "high_temp",nullable = false)
    private Double highTemp;

    @Column(name = "low_temp",nullable = false)
    private Double lowTemp;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "forecast_date",nullable = false)
    private LocalDateTime forecastDate;

    @Column(name = "time_zone",nullable = false)
    private String timeZone;
}
