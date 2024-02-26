package User;

public class User {

    private String id;
    private String username;
    private String password;

    public void set_id(String id) {
        this.id = id;
    }

    public void set_username(String name) {
        this.username = name;
    }

    public void set_password(String password) {
        this.password = password;
    }

    public String get_id() {
        return id;
    }

    public String get_username() {
        return username;
    }

    public String get_password() {
        return password;
    }

}
