package in.reinventing.user_service.util;

import in.reinventing.user_service.entity.business.CurrentWeather;
import in.reinventing.user_service.entity.business.WeatherForecast;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WeatherForecastDataInterface {
    List<WeatherForecast> extractWeather(String location,int days);
}
