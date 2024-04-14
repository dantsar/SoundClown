package SoundClown.User;

import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name="users")
public class User {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    @Column(name="user_name")
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
}
