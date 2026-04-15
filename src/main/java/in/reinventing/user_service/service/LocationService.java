package in.reinventing.user_service.service;

import in.reinventing.user_service.dto.LocationDTO;
import in.reinventing.user_service.entity.infa.Location;
import in.reinventing.user_service.exception.LocationNotFoundException;
import in.reinventing.user_service.mapper.LocationMapper;
import in.reinventing.user_service.repository.LocationRepository;
import in.reinventing.user_service.service.current.WAPIWeatherAPIProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final RestTemplate restTemplate;

    @Value("${weather.location}")
    private String locationUrl;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class LocationInfo{
        private Location location;

        @Getter
        @Setter
        @AllArgsConstructor
        public static class Location{
           private String name;
           private String region;
           private String country;
           private String lat;
           private String lon;
           private String tz_id;
           private String localtime_epoch;
           private String localtime;
       }
    }

    public LocationService(LocationRepository locationRepository, RestTemplate restTemplate) {
        this.locationRepository = locationRepository;
        this.restTemplate = restTemplate;
    }

    public List<Location> fetchLocation(String userId) {
        return this.locationRepository.fetchLocation(userId);
    }

    public LocationDTO createLocation(LocationDTO location){
        final Location locationEntity = LocationMapper.location(location);
        final Location savedLocation = this.locationRepository.save(locationEntity);
        return LocationMapper.locationDTO(savedLocation);
    }

    public Location fetchZoneByLocationName(String location) throws LocationNotFoundException {
        final LocationInfo.Location location1 = getLocation(location);
        return Location.builder()
                .lat(String.valueOf(location1.getLat()))
                .lang(String.valueOf(location1.getLon()))
                .location(location1.getName())
                .timeZone(location1.getTz_id())
                .build();
    }

    public Location saveLocation(String location) throws LocationNotFoundException {
        final LocationInfo.Location location1 = getLocation(location);
        Location locationObj = Location.builder()
                .lat(String.valueOf(location1.getLat()))
                .lang(String.valueOf(location1.getLon()))
                .location(location1.getName())
                .timeZone(location1.getTz_id())
                .build();
        return this.locationRepository.save(locationObj);
    }

    private LocationInfo.Location getLocation(String location) {
        final LocationInfo location1 =
                restTemplate.exchange(
                        locationUrl,
                        HttpMethod.GET,
                        null,
                        LocationInfo.class).getBody();
        return location1.location;
    }

    public Location fetchLocationByLocationName(String location) throws LocationNotFoundException {
        return this.locationRepository.findByLocationName(location)
                .orElse(fetchZoneByLocationName(location));
    }
}
