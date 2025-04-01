package utilities;

public class Constants {
    public static final String PROPERTY_FILE = "src/main/resources/constants.properties";

    public static final String ENV = JavaHelpers.setSystemVariable(PROPERTY_FILE, "Env");

    //Setting up the URLs
    public static final String URL = JavaHelpers.getPropertyValue(PROPERTY_FILE, "url_Local");

    public static final int PAGE_FACTORY_WAIT_DURATION = Integer.parseInt(JavaHelpers.getPropertyValue(PROPERTY_FILE, "PageFactoryWaitDuration"));
    public static final int WEB_DRIVER_WAIT_DURATION = Integer.parseInt(JavaHelpers.getPropertyValue(PROPERTY_FILE, "WebDriverWaitDuration"));

    public static final String USER_NOT_LOGGED_IN = "User is not logged in successfully.";
}
