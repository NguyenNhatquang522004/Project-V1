package com.example.my_app.modules.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_app.common.ResponedGlobal;
import com.example.my_app.modules.Order.Request.RequestBuyItem;
import com.example.my_app.modules.Order.Request.RequestDeleteItem;
import com.example.my_app.modules.Order.Request.RequestOrderItem;
import com.example.my_app.modules.Order.Request.RequestUpdateQuantityItem;
import com.example.my_app.modules.Products.Request.RequestAdd;

@RestController
public class OrderController {
        private final OrderServices orderServices;

        @Autowired
        public OrderController(OrderServices orderServices) {
                this.orderServices = orderServices;
        }

        @PostMapping(path = "/Public/order/add/item", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handlAddnewItem(@RequestBody RequestOrderItem request)
                        throws Exception {
                try {
                        ResponedGlobal add = orderServices.handleAddNew(request);
                        if (add.getCode().equals("0")) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0")
                                                                .messages(add.getMessages().toString()).build(),
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

        @PostMapping(path = "/Public/order/buy", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handlBuyItem(@RequestBody RequestBuyItem request)
                        throws Exception {
                try {
                        ResponedGlobal add = orderServices.handleBuy(request);
                        if (add.getCode().equals("0")) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0")
                                                                .messages(add.getMessages().toString()).build(),
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

        @GetMapping(path = "/Public/order/render", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handlRender()
                        throws Exception {
                try {
                        ResponedGlobal add = orderServices.handleOrderRender();
                        if (add.getCode().equals("0")) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0")
                                                                .messages(add.getMessages().toString()).build(),
                                                HttpStatus.BAD_REQUEST);
                        }
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data(add.getData()).code("1")
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

        @PutMapping(path = "/Public/order/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handlupdate(@RequestBody RequestUpdateQuantityItem request)
                        throws Exception {
                try {
                        System.out.println(request.toString());
                        ResponedGlobal add = orderServices.handleUpdateQuantity(request);
                        if (add.getCode().equals("0")) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0")
                                                                .messages(add.getMessages().toString()).build(),
                                                HttpStatus.BAD_REQUEST);
                        }
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data(add.getData()).code("1")
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

        @DeleteMapping(path = "/Public/order/Delete", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handlDelete(@RequestBody RequestDeleteItem request)

                        throws Exception {
                try {
                        ResponedGlobal add = orderServices.handleDeleteItem(request);
                        if (add.getCode().equals("0")) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0")
                                                                .messages(add.getMessages().toString()).build(),
                                                HttpStatus.BAD_REQUEST);
                        }
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data(add.getData()).code("1")
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
