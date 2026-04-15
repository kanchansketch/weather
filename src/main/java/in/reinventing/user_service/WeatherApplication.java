package in.reinventing.user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherApplication {
	public final static String path = "http://localhost:8083/weather-app/accuweather-data.json";
	public final static String forecast_path = "http://localhost:8083/weather-app/weather-forecast-data.json";
	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
	}

}
