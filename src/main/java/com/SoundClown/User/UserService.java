package SoundClown.User;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List get_users() {
        Iterable users = this.userRepository.findAll();
        List userList = new ArrayList<>();
        users.forEach(user->{userList.add(user);});
        return userList;
    }

    public boolean create_user(String user_name, String password) {
        User user = new User();
        user.set_user_name(user_name);
        user.set_password(password);
        System.out.println(user);
        this.userRepository.save(user);
        return true;
    }

    public boolean update_user(User user) {
        this.userRepository.save(user);
        return true;
    }

    public void delete_user(Long user_id){
        this.userRepository.deleteById(user_id);
    }

}
