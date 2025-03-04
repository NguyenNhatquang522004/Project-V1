package com.example.my_app.Modules_Admin.Products;

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

import com.example.my_app.Modules_Admin.Products.Request.RequestAdd;
import com.example.my_app.common.ResponedGlobal;
import com.example.my_app.model.Product.Products;
import com.example.my_app.model.Product.Products_Support_Attribute;
import com.example.my_app.model.Product.Products_Supports;

import jakarta.transaction.Transactional;

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
        @Transactional
        public ResponseEntity<ResponedGlobal> handlDeleteProducts(@RequestParam("id") String request) throws Exception {

                try {
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

        @PutMapping(path = "/UpLocalDateTime", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handlUpLocalDateTimeProducts(@RequestBody RequestAdd request)
                        throws Exception {
                try {

                        Optional<Products> products = productsServices
                                        .handleFindOneProducts(request.getProductsData().getId());
                        if (products.isEmpty()) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0")
                                                                .messages("không tìm thấy sản phẩm ").build(),
                                                HttpStatus.BAD_REQUEST);
                        }
                        boolean updateProducts = productsServices.UpLocalDateTimeProducts(request,
                                        products.get());
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
