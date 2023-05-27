package com.example.task_mis.services;
import com.example.task_mis.entities.Image;
import com.example.task_mis.entities.User;
import com.example.task_mis.respositories.ImageRepository;
import com.example.task_mis.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private ImageRepository imageRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageRepository = imageRepository;
    }


    // User registration
    public User registerUser(User user) {
        // Validate username and email uniqueness
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new IllegalArgumentException("NOT");
        }
    }


    public List<Image> getUserImages(Long id) throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        return imageRepository.findByUser(user);
    }
}