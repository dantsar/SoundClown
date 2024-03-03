package com.SoundClown;

public class Users {

    private int    id;
    private String user_name;
    private String password;

    public void set_id(int id)                  { this.id = id; }
    public void set_user_name(String user_name) { this.user_name = user_name; }
    public void set_password(String password)   { this.password = password; }


    public int    get_id()        { return id; }
    public String get_user_name() { return user_name; }
    public String get_password()  { return password; }

    public String to_string() {
        return "User{" + 
               "id=" + id +
               ", user_name=" + user_name +
               ", password=" + password + '\'' +
               '}';
    }
}
