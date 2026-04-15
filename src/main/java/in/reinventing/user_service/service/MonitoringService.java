package in.reinventing.user_service.service;

import in.reinventing.user_service.repository.MonitoringRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MonitoringService {
    private final MonitoringRepository monitoringRepository;
}
