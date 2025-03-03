package com.example.my_app.Modules_Admin.Products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_app.Modules_Admin.Products.Request.RequestAdd;
import com.example.my_app.common.ResponedGlobal;

@RestController
@RequestMapping(path = "/admin/Products")
public class Admin_ProductsController {
        private final Admin_ProductsServices productsServices;

        @Autowired
        public Admin_ProductsController(Admin_ProductsServices productsServices) {
                this.productsServices = productsServices;
        }

        @PostMapping(path = "/Add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handlAddProducts(@RequestBody RequestAdd request)
                        throws Exception {
                try {
                        boolean add = productsServices.addNewProducts(request);
                        if (add == false) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0")
                                                                .messages("lỗi").build(),
                                                HttpStatus.BAD_REQUEST);
                        }
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data("").code("1")
                                                        .messages("thành công").build(),
                                        HttpStatus.OK);
                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data("").code("0")
                                                        .messages("lỗi").build(),
                                        HttpStatus.BAD_REQUEST);
                }
        }

        @DeleteMapping(path = "/Delete", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handlDeleteProducts() throws Exception {

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

        @PutMapping(path = "/Update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handlUpdateProducts() throws Exception {

                try {

                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data("").code("1")
                                                        .messages("thành công").build(),
                                        HttpStatus.OK);
                } catch (Exception e) {
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data("").code("0")
                                                        .messages("thành công").build(),
                                        HttpStatus.BAD_REQUEST);
                }
        }

        @PutMapping(path = "/Render", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handlRenderProducts() throws Exception {

                try {

                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data("").code("1")
                                                        .messages("thành công").build(),
                                        HttpStatus.OK);
                } catch (Exception e) {
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data("").code("0")
                                                        .messages("thành công").build(),
                                        HttpStatus.BAD_REQUEST);
                }
        }
}
