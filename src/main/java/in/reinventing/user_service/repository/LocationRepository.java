package in.reinventing.user_service.repository;

import in.reinventing.user_service.entity.infa.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location,Integer> {

    @Query("FROM Location where userId=:userId")
    List<Location> fetchLocation(String userId);

    @Query("FROM Location where location=:location")
    Optional<Location> fetchZoneByLocationName(String location);

    @Query("FROM Location where LOWER(location) like CONCAT('%',LOWER(:location),'%')")
    Optional<Location> findByLocationName(String location);
}
