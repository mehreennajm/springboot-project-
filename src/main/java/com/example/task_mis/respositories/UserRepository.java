package com.example.task_mis.respositories;
import com.example.task_mis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    @Query("SELECT u FROM User u JOIN FETCH u.images WHERE u.id = :id")
    User findByIdWithImages(Long id);

}
