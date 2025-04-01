package dataobjects.registration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDo {
    private String userId;

    private String passwordRegistration;

    private String repeatPassword;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address1;

    private String address2;

    private String city;

    private String state;

    private String zip;

    private String country;
}
