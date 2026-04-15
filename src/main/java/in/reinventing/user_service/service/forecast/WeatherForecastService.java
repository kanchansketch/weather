package in.reinventing.user_service.service.forecast;

import in.reinventing.user_service.WeatherApplication;
import in.reinventing.user_service.entity.business.WeatherForecast;
import in.reinventing.user_service.repository.WeatherForecastRepository;
import in.reinventing.user_service.util.WeatherForecastDataInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WeatherForecastService {
    private final WeatherForecastRepository weatherForecastRepository;
    private final WeatherForecastDataInterface weatherForecastDataInterface;

    public List<WeatherForecast> fetchWeatherForecast(String location, LocalDateTime start, LocalDateTime end) {
        return this.weatherForecastRepository.fetchWeatherForecast(location,start,end);
    }

    public void syncForecast(String location) {
        final List<WeatherForecast> weatherForecasts = weatherForecastDataInterface.extractWeather(location,1);
        this.weatherForecastRepository.saveAll(weatherForecasts);
    }

    public List<WeatherForecast> sycForecast(String location,int days){
        final List<WeatherForecast> weatherForecasts = weatherForecastDataInterface.extractWeather(location,days);
        List<WeatherForecast> weatherForecastList = new ArrayList<>();

        for(WeatherForecast weatherForecast:weatherForecasts){
            Optional<WeatherForecast> optionalWeather = this.weatherForecastRepository.findWeatherForecast(
                    weatherForecast.getLocation(),
                    weatherForecast.getForecastDate(),
                    weatherForecast.getTimeZone()
            );

            if(optionalWeather.isPresent()){
                WeatherForecast existingWeatherForecast =  optionalWeather.get();

                existingWeatherForecast.setHighTemp(weatherForecast.getHighTemp());
                existingWeatherForecast.setLowTemp(weatherForecast.getLowTemp());
                weatherForecastList.add(existingWeatherForecast);
            }else{
                weatherForecastList.add(weatherForecast);
            }

        }
        return this.weatherForecastRepository.saveAll(weatherForecastList);
    }
}
