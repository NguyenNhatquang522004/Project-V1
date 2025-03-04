package com.example.my_app.modules.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.my_app.common.ResponedGlobal;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class PaymentController {

        private final PaymentSevices paymentSevices;

        @Autowired
        public PaymentController(PaymentSevices paymentSevices) {
                this.paymentSevices = paymentSevices;
        }

        @GetMapping(path = "/Public/creat_payment")
        public ResponseEntity<ResponedGlobal> createpayment(HttpServletRequest request)
                        throws Exception {

                try {
                        System.out.println(request);
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data(paymentSevices.createVnPayPayment(request))
                                                        .code("1")
                                                        .messages("thành công").build(),
                                        HttpStatusCode.valueOf(200));
                } catch (Exception e) {
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data("").code("1")
                                                        .messages("thành công").build(),
                                        HttpStatusCode.valueOf(200));
                }
        }

        @GetMapping(path = "/Public/vn-pay-callback")
        public ResponseEntity<ResponedGlobal> payCallbackHandler(
                        @RequestParam("vnp_Amount") Long vnpAmount,
                        @RequestParam("vnp_BankCode") String vnpBankCode,
                        @RequestParam("vnp_BankTranNo") String vnpBankTranNo,
                        @RequestParam("vnp_CardType") String vnpCardType,
                        @RequestParam("vnp_OrderInfo") String vnpOrderInfo,
                        @RequestParam("vnp_PayLocalDateTime") String vnpPayLocalDateTime,
                        @RequestParam("vnp_ResponseCode") String vnpResponseCode,
                        @RequestParam("vnp_TmnCode") String vnpTmnCode,
                        @RequestParam("vnp_TransactionNo") String vnpTransactionNo,
                        @RequestParam("vnp_TransactionStatus") String vnpTransactionStatus,
                        @RequestParam("vnp_TxnRef") String vnpTxnRef,
                        @RequestParam("vnp_SecureHash") String vnpSecureHash)
                        throws Exception {
                try {
                        System.out.println(vnpAmount);
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data("").code("1")
                                                        .messages("thành côngfsdfsdf").build(),
                                        HttpStatusCode.valueOf(200));
                } catch (Exception e) {
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data("").code("1")
                                                        .messages("thành công   ").build(),
                                        HttpStatusCode.valueOf(200));
                }
        }

}
