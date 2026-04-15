package in.reinventing.user_service.repository;

import in.reinventing.user_service.entity.infa.MapType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapTypeRepository extends JpaRepository<MapType,Integer> {

    @Query("FROM MapType where userId=:userId")
    List<MapType> fetchMapType(String userId);
}
