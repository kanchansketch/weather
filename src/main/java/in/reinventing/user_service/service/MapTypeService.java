package in.reinventing.user_service.service;

import in.reinventing.user_service.entity.infa.MapType;
import in.reinventing.user_service.repository.MapTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MapTypeService {
    private final MapTypeRepository mapTypeRepository;
    public List<MapType> fetchMapType(String userId) {
        return this.mapTypeRepository.fetchMapType(userId);
    }
}
