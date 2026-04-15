package in.reinventing.user_service.service.forecast;

import in.reinventing.user_service.entity.business.WeatherForecast;
import in.reinventing.user_service.util.UniversalDateTimeUtil;
import in.reinventing.user_service.util.WeatherForecastDataInterface;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static in.reinventing.user_service.util.UniversalDateTimeUtil.YYYY_MM_DD;

@Service
@Primary
public class WAPIWeatherForecastAPIProvider implements WeatherForecastDataInterface {
    private final RestTemplate restTemplate;
    private final String weatherKey;
    private final String weatherForecastUrl;

    public WAPIWeatherForecastAPIProvider(
            RestTemplate restTemplate,
            @Value("${weather.api-key}") String weatherKey,
            @Value("${weather.forecast-url}") String weatherForecastUrl
    ) {
        this.restTemplate = restTemplate;
        this.weatherKey = weatherKey;
        this.weatherForecastUrl = weatherForecastUrl;
    }

    @Data
    public static class WeatherResponse {

        private Location location;
        private Current current;
        private Forecast forecast;

        @Data
        public static class Location {
            private String name;
            private String region;
            private String country;
            private double lat;
            private double lon;
            private String tz_id;
            private long localtime_epoch;
            private String localtime;
        }

        @Data
        public static class Current {
            private String last_updated;
            private double temp_c;
            private Condition condition;
            private double wind_kph;
            private int humidity;
            private int cloud;
            private double vis_km;
        }

        @Data
        public static class Forecast {
            private List<ForecastDay> forecastday;
        }

        @Data
        public static class ForecastDay {
            private String date;
            private Day day;
            private List<Hour> hour;
        }

        @Data
        public static class Day {
            private double maxtemp_c;
            private double mintemp_c;
            private Condition condition;
        }

        @Data
        public static class Hour {
            private String time;
            private double temp_c;
            private Condition condition;
            private double wind_kph;
            private int humidity;
            private int cloud;
        }

        @Data
        public static class Condition {
            private String icon;
            private int code;
        }
    }

    @Override
    public List<WeatherForecast> extractWeather(String location,int days) {
        final WeatherResponse accuweather = weatherData(location,days);
        return convertAccuweatherToLocal(accuweather);
    }

    private WeatherResponse weatherData(String location,int days){
        //Need to revisit because not in mind
        URI uri = UriComponentsBuilder.fromUriString(weatherForecastUrl)
                .queryParam("q",location)
                .queryParam("days",days)
                .build().toUri();

        return restTemplate.exchange(
                uri,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                WeatherResponse.class
        ).getBody();
    }

    private List<WeatherForecast> convertAccuweatherToLocal(WeatherResponse accuweather){
        return accuweather
                .getForecast()
                .getForecastday()
                .stream()
                .map(data->currentWeatherFromData(data))
                .peek(f->f.setLocation(accuweather.location.name))
                .peek(c->c.setTimeZone(accuweather.location.tz_id))
                .collect(Collectors.toList());
    }

    private WeatherForecast currentWeatherFromData(WeatherResponse.ForecastDay data){
        return WeatherForecast.builder()
                .forecastDate(UniversalDateTimeUtil.convertDate(data.date, YYYY_MM_DD))
                .highTemp(data.day.getMaxtemp_c())
                .lowTemp(data.day.getMintemp_c())
                .build();
    }
}
