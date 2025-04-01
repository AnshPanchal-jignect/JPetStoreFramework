package datafactory.login;

import dataobjects.login.LoginUserDo;

public class LoginUserData {
    LoginUserDo loginUserDo = new LoginUserDo();

    public LoginUserDo getValidLoginData() {
        loginUserDo.setUserName("fat343");
        loginUserDo.setPassword("fat@123");
        return loginUserDo;
    }

    public LoginUserDo getInvalidLoginData() {
        loginUserDo.setUserName("invalid");
        loginUserDo.setPassword("inv123");
        return loginUserDo;
    }
}
