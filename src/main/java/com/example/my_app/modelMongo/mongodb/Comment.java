package com.example.my_app.modelMongo.mongodb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "post")
public class Comment {
    @Id
    String id;

    @NotBlank
    String content;

    @Indexed
    String postId;

    @NotBlank
    String authorId;

    @NotBlank
    String authorName;

    Date timestamp = new Date();

    String products_id;

    int rate;

    String url;

    int like;

    List<Reply> replies = new ArrayList<>();

    @Data
    public static class Reply {
        String content;
        String authorId;
        String authorName;
        Date timestamp;
        int like;
    }

}
