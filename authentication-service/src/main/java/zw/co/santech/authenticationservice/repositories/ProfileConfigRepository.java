package zw.co.santech.authenticationservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.santech.authenticationservice.models.ProfileConfig;
import zw.co.santech.authenticationservice.models.UserProfile;

import java.util.Optional;

public interface ProfileConfigRepository extends JpaRepository<ProfileConfig, Long> {

    Optional<ProfileConfig> findProfileConfigByUserProfile(UserProfile userProfile);
}