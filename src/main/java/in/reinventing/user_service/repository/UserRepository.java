package in.reinventing.user_service.repository;

import in.reinventing.user_service.entity.infa.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    @Query("From User where userName=:userName")
    Optional<User> findByUserName(String userName);
}
