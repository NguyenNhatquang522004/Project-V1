package com.example.my_app.modules.Comment;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_app.Repository.CommentRepository;
import com.example.my_app.common.ResponedGlobal;
import com.example.my_app.modelMongo.mongodb.Comment;

@RestController
public class CommentController {

    @PostMapping("/comments")
    public ResponseEntity<ResponedGlobal> createComment(@RequestBody Comment comment) throws Exception {
        try {
            
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data("").code("1")
                            .messages("thành công").build(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
