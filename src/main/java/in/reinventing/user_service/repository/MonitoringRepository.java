package in.reinventing.user_service.repository;

import in.reinventing.user_service.entity.infa.Monitoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface MonitoringRepository extends JpaRepository<Monitoring, BigInteger> {
}
