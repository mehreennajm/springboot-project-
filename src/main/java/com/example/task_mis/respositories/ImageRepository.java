package com.example.task_mis.respositories;
import com.example.task_mis.entities.Image;
import com.example.task_mis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByUser(User user);


}

