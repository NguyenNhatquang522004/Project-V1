package com.example.my_app.modules.Products;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_app.common.ResponedGlobal;
import com.example.my_app.model.Product.Products;
import com.example.my_app.model.Product.Products_Support_Attribute;
import com.example.my_app.model.Product.Products_Supports;
import com.example.my_app.modules.Products.Request.RequestAdd;
import com.example.my_app.modules.Products.Request.RequestUpdate;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping(path = "/Public/Products")
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
                        ResponedGlobal add = productsServices.addNewProducts(request);

                        if (add.getCode().equals("0")) {
                                System.out.println(add.getMessages().toString());
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0")
                                                                .messages(add.getMessages()).build(),
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

        @Transactional
        @DeleteMapping(path = "/Delete", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handlDeleteProducts(@RequestParam("id") String request) throws Exception {

                try {
                        System.out.println(request);
                        UUID convertUuid = UUID.fromString(request);
                        Optional<Products> searchProducts = productsServices.handleFindOneProducts(convertUuid);
                        if (searchProducts.isEmpty()) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0")
                                                                .messages("không tìm thấy products").build(),
                                                HttpStatus.BAD_REQUEST);
                        }
                        boolean DeleteItem = productsServices.handleDeleteProducts(searchProducts.get());
                        if (DeleteItem == false) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0")
                                                                .messages("xóa thất bại").build(),
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

        @DeleteMapping(path = "/Delete/item", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handlDeleteProductsSupports(@RequestParam("id") String request)
                        throws Exception {

                try {
                        UUID convertToUUID = UUID.fromString(request);
                        Optional<Products_Supports> searchProducts = productsServices
                                        .handleFindOneProductsSupports(convertToUUID);

                        if (searchProducts.isEmpty()) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0")
                                                                .messages("không tìm thấy products").build(),
                                                HttpStatus.BAD_REQUEST);
                        }
                        boolean DeleteItem = productsServices.handleDeleteProductsSupports(searchProducts.get());
                        if (DeleteItem == false) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0")
                                                                .messages("xóa thất bại").build(),
                                                HttpStatus.BAD_REQUEST);
                        }
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

        @DeleteMapping(path = "/Delete/item/attribute", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handlDeleteProductsSupportsAttribute(@RequestParam("id") String request)
                        throws Exception {

                try {
                        UUID convertToUUID = UUID.fromString(request);
                        Optional<Products_Support_Attribute> searchProducts = productsServices
                                        .handleFindOneProductsSupportsAttribute(convertToUUID);
                        System.out.println(request);
                        if (searchProducts.isEmpty()) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0")
                                                                .messages("không tìm thấy products").build(),
                                                HttpStatus.BAD_REQUEST);
                        }
                        boolean DeleteItem = productsServices
                                        .handleDeleteProductsSupportsAttribute(searchProducts.get());
                        if (DeleteItem == false) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0")
                                                                .messages("xóa thất bại").build(),
                                                HttpStatus.BAD_REQUEST);
                        }
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

        @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handlUpLocalDateTimeProducts(@RequestBody RequestUpdate request)
                        throws Exception {
                try {
System.out.println(request.toString());
                        Optional<Products> products = productsServices
                                        .handleFindOneProducts(request.getProductsData().getId());
                        if (products.isEmpty()) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0")
                                                                .messages("không tìm thấy sản phẩm ").build(),
                                                HttpStatus.BAD_REQUEST);
                        }
                        boolean updateProducts = productsServices.UpdateProducts(request,
                                        products.get());
                        if (updateProducts == false) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0")
                                                                .messages("update không thành công").build(),
                                                HttpStatus.BAD_REQUEST);
                        }
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

        @GetMapping(path = "/Render", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handlRenderProducts(@RequestParam("id") String request) throws Exception {
                try {
                        UUID convertToUUID = UUID.fromString(request);
                        Optional<Products> searchProducts = productsServices.handleFindOneProducts(convertToUUID);
                        if (searchProducts.isEmpty()) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0")
                                                                .messages("thành công").build(),
                                                HttpStatus.BAD_REQUEST);
                        }

                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data(searchProducts).code("1")
                                                        .messages("thành công").build(),
                                        HttpStatus.OK);
                } catch (Exception e) {
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data("").code("0")
                                                        .messages("thành công").build(),
                                        HttpStatus.BAD_REQUEST);
                }
        }

        @GetMapping(path = "/Render/panigation", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handlRenderProductsPanigation(@RequestParam("id") int page,
                        @RequestParam("id") int size) throws Exception {
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

        @GetMapping(path = "/Render/all", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handlRenderall() throws Exception {
                try {
                        ResponedGlobal data = productsServices.handlePanigation();
                        if (data.getCode().equals("0")) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0")
                                                                .messages(data.getMessages()).build(),
                                                HttpStatus.BAD_REQUEST);
                        }
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data(data.getData()).code("1")
                                                        .messages("thành công").build(),
                                        HttpStatus.OK);
                } catch (Exception e) {
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data("").code("0")
                                                        .messages(e.toString()).build(),
                                        HttpStatus.BAD_REQUEST);
                }
        }

        @PostMapping(path = "/test")
        public ResponseEntity<ResponedGlobal> test()
                        throws Exception {
                try {
                        System.out.println("123");
                        productsServices.test();

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
}
