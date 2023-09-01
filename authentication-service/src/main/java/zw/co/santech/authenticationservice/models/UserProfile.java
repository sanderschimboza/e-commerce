package zw.co.santech.authenticationservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.santech.authenticationservice.enums.RolesEnum;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile implements Serializable {
    @Id
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "employee_id")
    private String employeeId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number", columnDefinition = "VARCHAR(32)")
    private String phoneNumber;
    @Column(name = "enabled")
    private Boolean enabled;
    @Column(name = "profile_pic",columnDefinition = "TEXT")
    private String profilePic;

    @OneToOne
    @JoinColumn(name = "profile_config_id",referencedColumnName = "profile_config_id",unique = true,foreignKey = @ForeignKey(name = "profile_config_id_fk"))
    private ProfileConfig config;

    @Enumerated(EnumType.STRING)
    private RolesEnum userRole;

    public UserProfile(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
