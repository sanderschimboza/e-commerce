package zw.co.santech.authenticationservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.co.santech.authenticationservice.models.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
    UserProfile findByEmailAddress(String email);
}
