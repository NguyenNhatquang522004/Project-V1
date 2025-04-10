package com.example.my_app.custom.Helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import java.util.function.Function;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

@Repository
public class Helper {

    private int maxSize = 2048;

    private double quality = 0.8;

    public <T, D> Optional<T> handleFindOne(JpaRepository<T, D> repository, D id) {
        return repository.findById(id);

    }

    public <T, ID> Optional<T> handleFindList(Collection<T> data, Function<T, ID> extract, ID id) {
        return data.stream().filter(value -> extract.apply(value).equals(id)).findFirst();
    }

    public <T, E> Optional<T> handlefind(E input, Function<E, Optional<T>> finder) throws Exception {
        return finder.apply(input);
    }

    public byte[] processImage(MultipartFile file) throws IOException {
        // Validate image format
        validateImageFormat(file.getContentType());

        // Read the original image
        BufferedImage image = ImageIO.read(file.getInputStream());

        // Create a ByteArrayOutputStream to hold the output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Optimize image and write to the ByteArrayOutputStream
        Thumbnails.of(image)
                .size(maxSize, maxSize)
                .outputFormat("WEBP")
                .outputQuality((double) quality)
                .toOutputStream(baos);

        // Return the byte array
        return baos.toByteArray();
    }

    private void validateImageFormat(String contentType) {
        try {
            List<String> allowedTypes = Arrays.asList(
                    "image/jpeg",
                    "image/png",
                    "image/webp");

            if (!allowedTypes.contains(contentType.toLowerCase())) {
                throw new Exception("Unsupported image format");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
