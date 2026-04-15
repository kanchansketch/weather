package in.reinventing.user_service.repository;

import in.reinventing.user_service.entity.business.WeatherForecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherForecastRepository extends JpaRepository<WeatherForecast, BigInteger> {

    //Need to make start of now day
    @Query("FROM WeatherForecast where location=:location and forecastDate>=:start and forecastDate<:end")
    List<WeatherForecast> fetchWeatherForecast(String location, LocalDateTime start,LocalDateTime end);

    @Query("FROM WeatherForecast where location=:location and forecastDate=:forecastDate and timeZone=:timeZone")
    Optional<WeatherForecast> findWeatherForecast(String location, LocalDateTime forecastDate, String timeZone);
}
