    package in.reinventing.user_service.service.history;

import in.reinventing.user_service.entity.business.CurrentWeather;
import in.reinventing.user_service.util.UniversalDateTimeUtil;
import in.reinventing.user_service.util.WeatherHistoryDataInterface;
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

import static in.reinventing.user_service.util.UniversalDateTimeUtil.YYYY_MM_DD_HH_MM;

    @Primary
    @Service
    public class WAPIWeatherHistoryAPIProvider implements WeatherHistoryDataInterface {
        private final RestTemplate restTemplate;
        private final String weatherKey;
        private final String weatherHistoryCurrentUrl;

        public WAPIWeatherHistoryAPIProvider(
                RestTemplate restTemplate,
                @Value("${weather.api-key}") String weatherKey,
                @Value("${weather.history-url}") String weatherHistoryCurrentUrl
        ) {
            this.restTemplate = restTemplate;
            this.weatherKey = weatherKey;
            this.weatherHistoryCurrentUrl = weatherHistoryCurrentUrl;
        }

        @Data
        public static class CurrentWeatherResponse {

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
                private double vis_km;
            }

            @Data
            public static class Condition {
                private String icon;
                private int code;
            }
        }

        @Override
        public List<CurrentWeather> extractWeather(String location,String date) {
            final CurrentWeatherResponse accuweather = weatherData(location,date);
            return convertAccuweatherToLocal(accuweather);
        }

        private CurrentWeatherResponse weatherData(String location,String date){
            //Need to revisit because not in mind
            URI uri = UriComponentsBuilder.fromUriString(weatherHistoryCurrentUrl)
                    .queryParam("q",location)
                    .queryParam("dt",date)
                    .build().toUri();

            return restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    CurrentWeatherResponse.class
            ).getBody();
        }

        private List<CurrentWeather> convertAccuweatherToLocal(CurrentWeatherResponse accuweather){
            return accuweather.getForecast().getForecastday()
                    .stream().flatMap(d->d.hour.stream())
                    .map(data->currentWeatherFromData(data))
                    .peek(c->c.setLocation(accuweather.location.name))
                    .peek(c->c.setTimeZone(accuweather.location.tz_id))
                    .collect(Collectors.toList());
        }

        private CurrentWeather currentWeatherFromData(CurrentWeatherResponse.Hour data){
            return CurrentWeather.builder()
                    .date(UniversalDateTimeUtil.convertDateTime(data.time, YYYY_MM_DD_HH_MM))
                    .temperature(data.temp_c)
                    .visibility(data.vis_km)
                    .humidity((double) data.humidity)
                    .weatherIconCode(data.condition.code)
                    .weatherIconUrl(data.condition.icon)
                    .windSpeed(data.wind_kph)
                    .build();
        }
    }
