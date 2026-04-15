package in.reinventing.user_service.controller;

import in.reinventing.user_service.entity.business.WeatherForecast;
import in.reinventing.user_service.entity.infa.Location;
import in.reinventing.user_service.exception.LocationNotFoundException;
import in.reinventing.user_service.service.LocationService;
import in.reinventing.user_service.service.forecast.WeatherForecastService;
import in.reinventing.user_service.util.UniversalDateTimeUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RequestMapping("/weather_forecast")
@RestController
@AllArgsConstructor
public class WeatherForecastController {
    private final WeatherForecastService weatherForecastService;
    private final LocationService locationService;

    @GetMapping
    public List<WeatherForecast> fetchWeatherForecast(@RequestParam("location") String location,@RequestParam(value = "days",defaultValue = "1") int days) throws LocationNotFoundException {
        Location locationEntity = this.locationService.fetchZoneByLocationName(location);
        return this.weatherForecastService.fetchWeatherForecast(location,
                UniversalDateTimeUtil.startOfDate(locationEntity.getTimeZone()),
                UniversalDateTimeUtil.startOfDate(locationEntity.getTimeZone(),days)
                );
    }

    @PostMapping("/sync")
    public List<WeatherForecast> syncForecast(@RequestParam("location") String location,@RequestParam(value = "days",defaultValue = "1") int days){
       return this.weatherForecastService.sycForecast(location,days);
    }
}
