package com.SoundClown;

public class User {

    private int    user_id;
    private String user_name;
    private String password;

    public void set_user_id(int user_id)        { this.user_id = user_id;     }
    public void set_user_name(String user_name) { this.user_name = user_name; }
    public void set_password(String password)   { this.password = password;   }

    public int    get_user_id()   { return user_id;   }
    public String get_user_name() { return user_name; }
    public String get_password()  { return password;  }

    public String to_string() {
        return "User{" + 
               "user_id=" + user_id +
               ", user_name=" + user_name +
               ", password=" + password + '\'' +
               '}';
    }
}