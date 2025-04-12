package com.example.my_app.modules.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_app.common.ResponedGlobal;
import com.example.my_app.modules.Order.OrderServices;
import com.example.my_app.modules.Order.Request.RequestBuyItem;
import com.example.my_app.modules.Payment.Request.RequestOrder;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class PaymentController {
        private final PaymentSevices paymentSevices;
        private final OrderServices orderServices;

        @Autowired
        public PaymentController(PaymentSevices paymentSevices, OrderServices orderServices) {
                this.paymentSevices = paymentSevices;
                this.orderServices = orderServices;
        }

        @GetMapping(path = "/Public/creat_payment")
        public ResponseEntity<ResponedGlobal> createpayment(HttpServletRequest request)
                        throws Exception {
                try {
                        // ResponedGlobal buy = orderServices.handleBuy(data);
                        // if (buy.getCode().equals("0")) {
                        // return new ResponseEntity<ResponedGlobal>(
                        // ResponedGlobal.builder().data("").code("0")
                        // .messages(buy.getData().toString()).build(),
                        // HttpStatus.BAD_REQUEST);
                        // }
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data(paymentSevices.createVnPayPayment(request))
                                                        .code("1")
                                                        .messages("thành công").build(),
                                        HttpStatusCode.valueOf(200));
                } catch (Exception e) {
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data("").code("0")
                                                        .messages(e.toString()).build(),
                                        HttpStatus.BAD_REQUEST);
                }
        }

        @GetMapping(path = "/Public/vn-pay-callback")
        public ResponseEntity<ResponedGlobal> payCallbackHandler(
                        @RequestParam("vnp_Amount") Long vnpAmount,
                        @RequestParam("vnp_BankCode") String vnpBankCode,
                        @RequestParam("vnp_BankTranNo") String vnpBankTranNo,
                        @RequestParam("vnp_CardType") String vnpCardType,
                        @RequestParam("vnp_OrderInfo") String vnpOrderInfo,
                        @RequestParam("vnp_PayDate") String vnp_PayDate,
                        @RequestParam("vnp_ResponseCode") String vnpResponseCode,
                        @RequestParam("vnp_TmnCode") String vnpTmnCode,
                        @RequestParam("vnp_TransactionNo") String vnpTransactionNo,
                        @RequestParam("vnp_TransactionStatus") String vnpTransactionStatus,
                        @RequestParam("vnp_TxnRef") String vnpTxnRef,
                        @RequestParam("vnp_SecureHash") String vnpSecureHash)
                        throws Exception {
                try {
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data("").code("1")
                                                        .messages("thành côngfsdfsdf").build(),
                                        HttpStatusCode.valueOf(200));
                } catch (Exception e) {
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data("").code("1")
                                                        .messages("lỗi").build(),
                                        HttpStatusCode.valueOf(400));
                }
        }
}
