package in.reinventing.user_service.util;

import in.reinventing.user_service.entity.business.CurrentWeather;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WeatherHistoryDataInterface {
    List<CurrentWeather> extractWeather(String location,String data);
}
