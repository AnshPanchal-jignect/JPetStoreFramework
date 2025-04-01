package datafactory.registration;

import dataobjects.registration.RegistrationDo;

import java.util.Random;

public class RegistrationData {
    public RegistrationDo getRegisterCustomerData(){

        RegistrationDo registerCustomerDO = new RegistrationDo();
        Random random = new Random();

        registerCustomerDO.setUserId("fat" + random.nextInt(1000));
        registerCustomerDO.setPasswordRegistration("fat@123");
        registerCustomerDO.setRepeatPassword(registerCustomerDO.getPasswordRegistration());
        registerCustomerDO.setFirstName("iron");
        registerCustomerDO.setLastName("Test");
        registerCustomerDO.setEmail("iron" + random.nextInt(1000) + "@gmail.com");

        registerCustomerDO.setPhoneNumber( "600" +(1000000 + random.nextInt(9000000)));
        registerCustomerDO.setAddress1("Rots");
        registerCustomerDO.setAddress2("MRoad");
        registerCustomerDO.setCity("Boston");
        registerCustomerDO.setState("Meyant");
        registerCustomerDO.setZip("10000" + random.nextInt(20000));
        registerCustomerDO.setCountry("Arabic");

        return registerCustomerDO;
    }
}
