package in.reinventing.user_service.mapper;

import in.reinventing.user_service.dto.LocationDTO;
import in.reinventing.user_service.entity.infa.Location;

public class LocationMapper {
    public static LocationDTO locationDTO(Location location){
        return LocationDTO.builder()
                .location(location.getLocation())
                .lat(location.getLat())
                .lang(location.getLang())
                .userId(location.getUserId())
                .build();
    }

    public static Location location(LocationDTO location){
        return Location.builder()
                .location(location.getLocation())
                .lat(location.getLat())
                .lang(location.getLang())
                .userId(location.getUserId())
                .build();
    }
}
