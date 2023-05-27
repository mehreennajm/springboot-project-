package com.example.task_mis.services;
import com.example.task_mis.entities.Image;
import com.example.task_mis.entities.User;
import com.example.task_mis.errors.CustomError;
import com.example.task_mis.respositories.ImageRepository;
import com.example.task_mis.respositories.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Environment environment;



    public Image saveImage(Long userId,String name, MultipartFile file) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Image image = new Image();
        image.setName(name);
        image.setUser(user);

        //Storing file in Database and the Directory
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String FILE_DIR = "images/";
        image.setImageName(fileName);
        FileUpload.saveFile(FILE_DIR, fileName, file);
        return imageRepository.save(image);
    }

    public Resource getImageFile(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("Image not found"));

        String uploadDirectory = environment.getProperty("image.upload.directory");
        String filePath = uploadDirectory + "/" + image.getImageName();

        try {
            Resource resource = new UrlResource(Paths.get(filePath).toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Failed to read the image file");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to retrieve the image file");
        }
    }


    public void deleteImage(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalStateException(CustomError.ID_NOT_FOUND_ERROR));
        Path imagesPath = Paths.get("images/" +
                image.getImageName());

        try {
            Files.delete(imagesPath);
            System.out.println("File " + imagesPath.toAbsolutePath() + " successfully removed");
        } catch (IOException e) {
            System.err.println("Unable to delete " + imagesPath.toAbsolutePath() + " due to...");
            e.printStackTrace();
        }

        imageRepository.delete(image);
    }
}
