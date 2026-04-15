package in.reinventing.user_service.controller;

import in.reinventing.user_service.entity.business.CurrentWeather;
import in.reinventing.user_service.exception.LocationNotFoundException;
import in.reinventing.user_service.service.current.WeatherService;
import in.reinventing.user_service.service.history.WeatherHistoryService;
import in.reinventing.user_service.util.UniversalDateTimeUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RequestMapping("/current_weather")
@RestController
@AllArgsConstructor
public class WeatherController {
    private final WeatherService  weatherService;
    private final WeatherHistoryService weatherHistoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CurrentWeather> fetchWeatherData(@RequestParam("location") String location) throws LocationNotFoundException {
        return this.weatherService.getCurrentWeathers(location);
    }

    @PostMapping("/sync")
    @ResponseStatus(HttpStatus.OK)
    public List<CurrentWeather> syncWeatherData(@RequestParam("location") String location){
       return this.weatherService.syncWeather(location);
    }

    @PostMapping("/sync_history")
    public List<CurrentWeather> syncHistoryWeatherData(
            @RequestParam("location") String location,
            @Valid
            @Pattern(
                    regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                    message = "Date must be in format yyyy-MM-dd"
            )
            @RequestParam("date") String date
    ){
        return this.weatherHistoryService.syncWeather(location, date);
    }

    @GetMapping("/history")
    public Page<CurrentWeather> currentWeatherHistoryPage(
            @Valid
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$",message = "Start Date must be in format yyyy-MM-dd")
            @RequestParam("start-date") String startDate,

            @Valid
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$",message = "End Date must be in format yyyy-MM-dd")
            @RequestParam("end-date") String endDate,

            @RequestParam(value = "page-number",defaultValue = "0") int pageNumber,
            @RequestParam(value = "page-size", defaultValue = "10") int pageSize,
            @RequestParam(value = "location") String location
    ){
         return this.weatherHistoryService.fetchWeatherDataPage(location,
                UniversalDateTimeUtil.convertDate(startDate,UniversalDateTimeUtil.YYYY_MM_DD),
                 UniversalDateTimeUtil.convertDate(endDate,UniversalDateTimeUtil.YYYY_MM_DD), PageRequest.of(pageNumber,pageSize));
    }
}
