package com.example.my_app.modules.Payment;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.my_app.Configuration.VNPAYConfiguration;
import com.example.my_app.common.VNPayUtil;
import com.example.my_app.modules.Payment.Request.PaymentDTO;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PaymentSevices {
    private final VNPAYConfiguration vnPayConfig;

    @Autowired
    public PaymentSevices(VNPAYConfiguration vnPayConfig) {
        this.vnPayConfig = vnPayConfig;
    }

    public PaymentDTO createVnPayPayment(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        // build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return PaymentDTO.builder()
                .paymentUrl(paymentUrl).build();
    }
}
