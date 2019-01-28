package br.com.allanlarangeiras.contentbroker.model;

/**
 * Created by allan.larangeiras on 28/01/2019.
 */
public class User {

    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValid() {
        if (this.login.equals("admin") && this.password.equals("admin")) {
            return true;
        }

        return false;
    }
}
