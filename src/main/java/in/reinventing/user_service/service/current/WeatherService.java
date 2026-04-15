package in.reinventing.user_service.service.current;

import in.reinventing.user_service.WeatherApplication;
import in.reinventing.user_service.entity.business.CurrentWeather;
import in.reinventing.user_service.entity.infa.Location;
import in.reinventing.user_service.exception.LocationNotFoundException;
import in.reinventing.user_service.repository.CurrentWeatherRepository;
import in.reinventing.user_service.service.LocationService;
import in.reinventing.user_service.util.UniversalDateTimeUtil;
import in.reinventing.user_service.util.WeatherDataInterface;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class WeatherService {
    private final CurrentWeatherRepository currentWeatherRepository;
    private final LocationService locationService;

    @Qualifier("WAPIWeatherAPIProvider")
    private final WeatherDataInterface weatherDataInterface;

    public List<CurrentWeather> fetchWeatherData(String location, LocalDateTime start, LocalDateTime end) {
        return this.currentWeatherRepository.fetchCurrentWeatherData(location, start, end);
    }

    public List<CurrentWeather> getCurrentWeathers(String location) throws LocationNotFoundException {
        Location location1 = locationService.fetchZoneByLocationName(location);
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of(location1.getTimeZone()));
        return fetchWeatherData(location,
                UniversalDateTimeUtil.startOfDate(location1.getTimeZone()),
                UniversalDateTimeUtil.endOfDate(location1.getTimeZone()));
    }

    public void sycAccuweatherWeather() {
        final List<CurrentWeather> currentWeathers = this.weatherDataInterface.extractWeather(WeatherApplication.path);
        this.currentWeatherRepository.saveAll(currentWeathers);
    }

    public List<CurrentWeather> syncWeather(String location) {
        final List<CurrentWeather> currentWeathers =
                this.weatherDataInterface.extractWeather(location);

        List<CurrentWeather> toSave = new ArrayList<>();

        for (CurrentWeather weather : currentWeathers) {

            Optional<CurrentWeather> existing =
                    currentWeatherRepository
                            .findByLocationAndDateAndTimeZone(
                                    weather.getLocation(),
                                    weather.getDate(),
                                    weather.getTimeZone()
                            );

            if (existing.isPresent()) {
                CurrentWeather entity = existing.get();

                entity.setTemperature(weather.getTemperature());
                entity.setWindSpeed(weather.getWindSpeed());
                entity.setHumidity(weather.getHumidity());
                entity.setVisibility(weather.getVisibility());
                entity.setWeatherIconCode(weather.getWeatherIconCode());
                entity.setWeatherIconUrl(weather.getWeatherIconUrl());

                toSave.add(entity);

            } else {
                toSave.add(weather);
            }
        }

        return currentWeatherRepository.saveAll(toSave);
    }


}
