package in.reinventing.user_service.service.history;

import in.reinventing.user_service.entity.business.CurrentWeather;
import in.reinventing.user_service.repository.CurrentWeatherRepository;
import in.reinventing.user_service.service.LocationService;
import in.reinventing.user_service.util.WeatherHistoryDataInterface;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class WeatherHistoryService {
    private final CurrentWeatherRepository currentWeatherRepository;
    private final WeatherHistoryDataInterface weatherHistoryDataInterface;
    private final LocationService locationService;

    public List<CurrentWeather> fetchWeatherData(String location, LocalDateTime start, LocalDateTime end) {
        return this.currentWeatherRepository.fetchCurrentWeatherData(location, start, end);
    }

    public Page<CurrentWeather> fetchWeatherDataPage(String location, LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return this.currentWeatherRepository.fetchCurrentWeatherDataPage(location, start, end,pageable);
    }

    public List<CurrentWeather> syncWeather(String location,String date) {
        final List<CurrentWeather> currentWeathers =
                this.weatherHistoryDataInterface.extractWeather(location,date);

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
