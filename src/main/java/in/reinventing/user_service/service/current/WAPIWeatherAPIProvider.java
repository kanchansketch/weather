    package in.reinventing.user_service.service.current;

import in.reinventing.user_service.entity.business.CurrentWeather;
import in.reinventing.user_service.util.UniversalDateTimeUtil;
import in.reinventing.user_service.util.WeatherDataInterface;
import lombok.Data;
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
import java.util.stream.Stream;

import static in.reinventing.user_service.util.UniversalDateTimeUtil.YYYY_MM_DD_HH_MM;

@Primary
@Service
public class WAPIWeatherAPIProvider implements WeatherDataInterface {
    private final RestTemplate restTemplate;
    private final String weatherKey;
    private final String weatherCurrentUrl;

    public WAPIWeatherAPIProvider(
            RestTemplate restTemplate,
            @Value("${weather.api-key}") String weatherKey,
            @Value("${weather.current-url}") String weatherCurrentUrl
    ) {
        this.restTemplate = restTemplate;
        this.weatherKey = weatherKey;
        this.weatherCurrentUrl = weatherCurrentUrl;
    }

    @Data
    public static class CurrentWeatherResponse {

        private Location location;
        private Current current;

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
        public static class Condition {
            private String icon;
            private int code;
        }
    }

    @Override
    public List<CurrentWeather> extractWeather(String location) {
        final CurrentWeatherResponse accuweather = weatherData(location);
        return convertAccuweatherToLocal(accuweather);
    }

    private CurrentWeatherResponse weatherData(String location){
        //Need to revisit because not in mind
        URI uri = UriComponentsBuilder.fromUriString(weatherCurrentUrl)
                .queryParam("q",location)
                .build().toUri();

        return restTemplate.exchange(
                uri,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                CurrentWeatherResponse.class
        ).getBody();
    }

    private List<CurrentWeather> convertAccuweatherToLocal(CurrentWeatherResponse accuweather){
        return Stream.of(accuweather.getCurrent())
                .map(data->currentWeatherFromData(data))
                .peek(c->c.setLocation(accuweather.location.name))
                .peek(c->c.setTimeZone(accuweather.location.tz_id))
                .collect(Collectors.toList());
    }

    private CurrentWeather currentWeatherFromData(CurrentWeatherResponse.Current data){
        return CurrentWeather.builder()
                .date(UniversalDateTimeUtil.convertDateTime(data.last_updated, YYYY_MM_DD_HH_MM))
                .temperature(data.temp_c)
                .visibility(data.vis_km)
                .humidity((double) data.humidity)
                .weatherIconCode(data.condition.code)
                .weatherIconUrl(data.condition.icon)
                .windSpeed(data.wind_kph)
                .build();
    }
}
