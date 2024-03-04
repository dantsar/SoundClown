package com.SoundClown;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Users {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String username;
    private String password;

    public void set_id(int id)                { this.id = id;             }
    public void set_username(String username) { this.username = username; }
    public void set_password(String password) { this.password = password; }

    public int get_id()           { return id;       }
    public String get_username()  { return username; }
    public String get_password()  { return password; }

    public String to_string() {
        return "User{" + 
               "id=" + id +
               ", username=" + username +
               ", password=" + password + '\'' +
               '}';
    }
}
