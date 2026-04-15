package in.reinventing.user_service.repository;

import in.reinventing.user_service.entity.business.CurrentWeather;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CurrentWeatherRepository extends JpaRepository<CurrentWeather, BigInteger> {

    //Need to make start of UTC time of now then pass
    @Query("FROM CurrentWeather where location=:location and  date between :start and :end")
    List<CurrentWeather> fetchCurrentWeatherData(String location, LocalDateTime start,LocalDateTime end);

    @Query("FROM CurrentWeather where location=:location and  date=:date and timeZone=:timeZone")
    Optional<CurrentWeather> findByLocationAndDateAndTimeZone(String location, LocalDateTime date, String timeZone);

    @Query("FROM CurrentWeather where location=:location and  date between :start and :end")
    Page<CurrentWeather> fetchCurrentWeatherDataPage(String location, LocalDateTime start, LocalDateTime end, Pageable pageable);
}
