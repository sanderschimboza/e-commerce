package zw.co.santech.authenticationservice.enums;

import lombok.Getter;

public enum TwoFactorConfig {
    ONCE_A_DAY(24, "Once a day"),
    ONCE_A_WEEK(168, "Once every Week"),
    EVERYTIME(0, "Everytime");
    @Getter
    private final int offSet;
    @Getter
    private final String description;

    TwoFactorConfig(int offSet, String description) {
        this.offSet = offSet;
        this.description = description;
    }
}
