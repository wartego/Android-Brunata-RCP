package pl.sda.myapplication.ui.home.sendRequest;


public class LoginAndPassword {
    private String login;
    private String password;
    private String ip;
    private TypeEnum typeEnum;

    public LoginAndPassword(String login, String password, String ip, TypeEnum typeEnum) {
        this.login = login;
        this.password = password;
        this.ip = ip;
        this.typeEnum = typeEnum;
    }

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

    public TypeEnum getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(TypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
