package in.reinventing.user_service.controller;

import in.reinventing.user_service.dto.LocationDTO;
import in.reinventing.user_service.entity.infa.Location;
import in.reinventing.user_service.exception.LocationNotFoundException;
import in.reinventing.user_service.service.LocationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/location")
@RestController
@AllArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Location> fetchLocation(String userId){
        return this.locationService.fetchLocation(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDTO createLocation(@Valid @RequestBody LocationDTO locationDTO){
        return this.locationService.createLocation(locationDTO);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Location searchLocation(@RequestParam("location") String searchLocation) throws LocationNotFoundException {
        return this.locationService.fetchLocationByLocationName(searchLocation);
    }
}
