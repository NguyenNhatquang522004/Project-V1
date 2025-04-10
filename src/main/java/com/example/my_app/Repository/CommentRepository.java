package com.example.my_app.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.my_app.modelMongo.mongodb.Comment;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findByPostIdOrderByTimestampDesc(String postId);

    // List<Comment> findByParentCommentIdOrderByTimestampAsc(String
    // parentCommentId);

    // long countByPostId(String postId);
}
