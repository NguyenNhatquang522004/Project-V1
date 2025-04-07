package com.example.my_app.modules.ProductsDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_app.common.ResponedGlobal;

@RestController
@RequestMapping(path = "/Public")
public class ProducstController {

    @Autowired
    ProductsServices productsServices;

    @Transactional
    @GetMapping(path = "/cart", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponedGlobal> handleRenderCart() throws Exception {
        try {

            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data("").code("1")
                            .messages("thành công").build(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data("").code("0")
                            .messages("lỗi").build(),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
