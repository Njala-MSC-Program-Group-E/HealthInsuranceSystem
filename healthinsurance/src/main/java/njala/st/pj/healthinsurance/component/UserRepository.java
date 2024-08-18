package njala.st.pj.healthinsurance.component;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import njala.st.pj.healthinsurance.model.User;

public interface UserRepository extends JpaRepository<User,Long> { 
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String string);
}