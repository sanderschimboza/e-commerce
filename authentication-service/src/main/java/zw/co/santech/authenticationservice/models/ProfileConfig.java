package zw.co.santech.authenticationservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.santech.authenticationservice.enums.CommunicationMethod;
import zw.co.santech.authenticationservice.enums.TwoFactorConfig;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProfileConfig implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "profile_config_id")
    private Long profileConfigId;

    private Boolean twoFactorEnabled;

    @JsonIgnore
    @OneToOne()
    @JoinColumn(name = "email_address", unique = true, foreignKey = @ForeignKey(name = "email_address_prof_fk"), referencedColumnName = "email_address")
    private UserProfile userProfile;

    private CommunicationMethod communicationMethod;

    @Enumerated(EnumType.ORDINAL)
    private TwoFactorConfig twoFactorConfig;

}
