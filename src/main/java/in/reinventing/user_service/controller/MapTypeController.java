package in.reinventing.user_service.controller;


import in.reinventing.user_service.entity.infa.MapType;
import in.reinventing.user_service.service.MapTypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/map_type")
@AllArgsConstructor
public class MapTypeController {
    private final MapTypeService mapTypeService;

    @GetMapping
    public List<MapType> fetchMapType(String userId){
        return this.mapTypeService.fetchMapType(userId);
    }
}
