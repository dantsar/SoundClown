package SoundClown.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name="users")
public class User implements UserDetails {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    @Column(name="user_name", unique = true)
    private String user_name;
    @Column(name="password")
    private String password;

    public void set_user_id  (Long user_id)     { this.user_id   = user_id;   }
    public void set_user_name(String user_name) { this.user_name = user_name; }
    public void set_password (String password)  { this.password  = password;  }

    public Long   get_user_id()   { return user_id;   }
    public String get_user_name() { return user_name; }
    public String get_password()  { return password;  }

    @Override
    public String toString() {
        return "User{" +
               "user_id=" + user_id +
               ", user_name=" + user_name +
               ", password=" + password + '\'' +
               '}';
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return this.user_name;
    }

    public void setUsername(String username) {
        this.user_name = username;
    }

    /* If you want account locking capabilities create variables and ways to set them for the methods below */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
