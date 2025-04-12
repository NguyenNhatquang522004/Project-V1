package com.example.my_app.modules.ProductsDetail;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_app.common.ResponedGlobal;

@RestController
@RequestMapping(path = "/Public")
public class ProducstController {

        @Autowired
        ProductsServices productsServices;

        @Transactional
        @GetMapping(path = "/card", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handleRenderCart(@RequestParam("n") int pageNumber,
                        @RequestParam("s") int pageSize) throws Exception {
                try {
                        ResponedGlobal data = productsServices.handlePanigationCart(pageNumber, pageSize);
                        if (data.getCode().equals("0")) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0")
                                                                .messages("lỗi").build(),
                                                HttpStatus.BAD_REQUEST);
                        }
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data(data.getData()).code("1")
                                                        .messages("thành công").build(),
                                        HttpStatus.OK);
                } catch (Exception e) {
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data("").code("0")
                                                        .messages("lỗi").build(),
                                        HttpStatus.BAD_REQUEST);
                }
        }

        @GetMapping(path = "/allcard", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handleallRenderCard() throws Exception {
                try {
                        ResponedGlobal data = productsServices.handleGetAllCart();
                        if (data.getCode().equals("0")) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0")
                                                                .messages("lỗi").build(),
                                                HttpStatus.BAD_REQUEST);
                        }

                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data(data.getData()).code("1")
                                                        .messages("thành công").build(),
                                        HttpStatus.OK);
                } catch (Exception e) {
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data("").code("0")
                                                        .messages("lỗi").build(),
                                        HttpStatus.BAD_REQUEST);
                }
        }

        @GetMapping(path = "/detail", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handleallRenderDetail(@RequestParam("id") String id) throws Exception {
                try {
                        UUID convert = UUID.fromString(id);
                        ResponedGlobal data = productsServices.handleRenderDetailProducts(convert);
                        System.out.println(data.getMessages());
                        if (data.getCode().equals("0")) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0")
                                                                .messages("lỗi").build(),
                                                HttpStatus.BAD_REQUEST);
                        }

                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data(data.getData()).code("1")
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
