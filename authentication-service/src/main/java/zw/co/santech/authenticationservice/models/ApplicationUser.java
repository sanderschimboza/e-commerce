package zw.co.santech.authenticationservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.santech.authenticationservice.utils.AttributeEncryptor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "email_address", unique = true, foreignKey = @ForeignKey(name = "email_address_fk"), referencedColumnName = "email_address")
    private UserProfile userProfile;

    private String encryptedPass;

    @JsonIgnore
    @Convert(converter = AttributeEncryptor.class)
    @Column(name = "two_factor_pass",columnDefinition = "TEXT")
    private String securityToken;

    @Column(name = "security_code",columnDefinition = "TEXT")
    private String securityCode;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "next_security_code_check")
    private LocalDateTime nextSecurityCodeCheck;

    @Column(name = "code_verified")
    private Boolean sessionVerified;
}
