package com.example.my_app.modules.Comment;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.my_app.Repository.CommentRepository;
import com.example.my_app.common.ResponedGlobal;
import com.example.my_app.modelMongo.mongodb.Comment;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    public ResponedGlobal handleaddComment(Comment comment) throws Exception {
        try {
            comment.setTimestamp(new Date());
            commentRepository.save(comment);
            return ResponedGlobal.builder().code("1").data("").messages("thành công").build();
        } catch (Exception e) {
            return ResponedGlobal.builder().code("0").data("").messages(e.toString()).build();
        }

    }

    public ResponedGlobal handleaddCommentReply(Comment.Reply reply, String id) throws Exception {
        try {
            Comment parentComment = commentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Comment not found"));
            reply.setTimestamp(new Date());
            parentComment.getReplies().add(reply);
            commentRepository.save(parentComment);
            return ResponedGlobal.builder().code("1").data("").messages("thành công").build();
        } catch (Exception e) {
            return ResponedGlobal.builder().code("0").data("").messages(e.toString()).build();
        }

    }

    public ResponedGlobal handleDeleteComment(String id) throws Exception {
        try {
            Comment parentComment = commentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Comment not found"));
            commentRepository.delete(parentComment);
            return ResponedGlobal.builder().code("1").data("").messages("thành công").build();
        } catch (Exception e) {
            return ResponedGlobal.builder().code("0").data("").messages(e.toString()).build();
        }

    }

    public ResponedGlobal handleupdateRateComment(String id, int rate) throws Exception {
        try {
            Comment parentComment = commentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Comment not found"));
            parentComment.setRate(rate);
            commentRepository.save(parentComment);
            return ResponedGlobal.builder().code("1").data("").messages("thành công").build();
        } catch (Exception e) {
            return ResponedGlobal.builder().code("0").data("").messages(e.toString()).build();
        }

    }

    public ResponedGlobal handleupdateRateComment(String id, int like, int flag) throws Exception {
        try {
            Comment parentComment = commentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Comment not found"));

            if (flag == 0) {
                parentComment.setLike(parentComment.getLike() - like);
                commentRepository.save(parentComment);
                return ResponedGlobal.builder().code("1").data("").messages("thành công").build();
            }
            parentComment.setLike(parentComment.getLike() + like);
            commentRepository.save(parentComment);
            return ResponedGlobal.builder().code("1").data("").messages("thành công").build();
        } catch (Exception e) {
            return ResponedGlobal.builder().code("0").data("").messages(e.toString()).build();
        }

    }

}
