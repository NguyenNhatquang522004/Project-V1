package com.example.my_app.modules.ForgotPassWord;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.my_app.Repository.User.UserRepository;
import com.example.my_app.common.ResponedGlobal;
import com.example.my_app.common.SenderEmail;
import com.example.my_app.custom.Helper.Helper;
import com.example.my_app.model.User.User;

@Service
public class ForgotPassWordService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    Helper helper;



    @Autowired
    SenderEmail senderEmail;

    public void handleUpdateCode(User user) {
        String code = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
        String uuid16digits = code.substring(code.length() - 4);
        user.setCode(uuid16digits);
        user.setCode_expired(LocalDateTime.now());
    }

    @Transactional
    public ResponedGlobal handleStepOne(User user) throws Exception {
        try {
            handleUpdateCode(user);
            boolean sendemail = senderEmail.handleSenderEmai("nguyennhatquang522004@gmail.com", user.getCode());
            if (sendemail == false) {
                return ResponedGlobal.builder().code("0").data("").messages("lỗi gửi email").build();
            }
            userRepository.saveAndFlush(user);
            return ResponedGlobal.builder().code("1").data(user.getEmail()).messages("").build();
        } catch (Exception e) {
            return ResponedGlobal.builder().code("0").data("").messages(e.toString()).build();
        }
    }

    @Transactional
    public ResponedGlobal handleStepTwo(User user, String code) throws Exception {
        try {
            boolean checkcode = user.getCode().equals(code);
            if (checkcode == false) {
                return ResponedGlobal.builder().code("0").data("").messages("mã otp không hợp lệ ").build();
            }
            boolean checkTime = handleValidTime(user.getCode_expired());
            if (checkTime == false) {
                return ResponedGlobal.builder().code("0").data("").messages("mã otp đã quá thời gian").build();
            }
            user.setCode(null);
            user.setCode_expired(null);
            userRepository.saveAndFlush(user);
            return ResponedGlobal.builder().code("1").data(user.getEmail()).messages("").build();
        } catch (Exception e) {
            return ResponedGlobal.builder().code("0").data("").messages(e.toString()).build();
        }
    }

    @Transactional
    public ResponedGlobal handleStepThree(User user, String password) throws Exception {
        try {
       
         
            user.setPassword(password);
            userRepository.saveAndFlush(user);
            return ResponedGlobal.builder().code("1").data(user.getEmail()).messages("").build();
        } catch (Exception e) {
            return ResponedGlobal.builder().code("0").data("").messages(e.toString()).build();
        }
    }

    boolean handleValidTime(LocalDateTime data) throws Exception {
        try {
            LocalDateTime now = LocalDateTime.now();
            return Math.abs(Duration.between(data, now).toMinutes()) <= 30 ? true : false;
        } catch (Exception e) {
            return false;
        }
    }

  

}
