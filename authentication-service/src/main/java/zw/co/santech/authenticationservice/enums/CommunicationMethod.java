package zw.co.santech.authenticationservice.enums;

import lombok.Getter;

public enum CommunicationMethod {
    SMS("SMS","A security code SMS has been sent to you mobile"),
    EMAIL("EMAIL","A security code Email has been sent to you account"),
    BOTH("BOTH","A security code has been send to both your mobile and email accounts ");

    @Getter
    String name;
    @Getter
    String message;
    CommunicationMethod(String name, String message){
        this.name = name;
        this.message = message;
    }

}
