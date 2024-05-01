package SoundClown.User;

import SoundClown.User.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

     @Query("select u from #{#entityName} u where u.user_name = ?1")
     User findUserByUsername(String user_name);

     @Query("select u from #{#entityName} u where u.user_id = ?1")
     User findUserByUserId(Long user_id);
}
