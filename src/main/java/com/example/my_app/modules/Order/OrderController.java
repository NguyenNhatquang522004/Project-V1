package com.example.my_app.modules.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_app.common.ResponedGlobal;
import com.example.my_app.modules.Products.Request.RequestAdd;

@RestController
public class OrderController {
    private final OrderServices orderServices;

    @Autowired
    public OrderController(OrderServices orderServices) {
        this.orderServices = orderServices;
    }

    @PostMapping(path = "/admin/order/Item", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponedGlobal> handlAddnewItem(@RequestBody RequestAdd request)
            throws Exception {
        try {

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
