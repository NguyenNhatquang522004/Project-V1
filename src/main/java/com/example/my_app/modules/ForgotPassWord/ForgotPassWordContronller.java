package com.example.my_app.modules.ForgotPassWord;

import java.math.BigInteger;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_app.Repository.User.UserRepository;
import com.example.my_app.common.ResponedGlobal;
import com.example.my_app.common.SenderEmail;
import com.example.my_app.custom.Helper.Helper;
import com.example.my_app.model.User.User;
import com.example.my_app.modules.ForgotPassWord.DTO.ForgotPassWordDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/Public")
public class ForgotPassWordContronller {
    @Autowired
    ForgotPassWordService forgotPassWordService;
    @Autowired
    SenderEmail senderEmail;
    @Autowired
    Helper helper;
    @Autowired
    UserRepository userRepository;

    @Transactional
    @PostMapping(path = "/Forgot/StepOne", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<ResponedGlobal> handleForgotStepOne(@RequestBody @Valid ForgotPassWordDTO request) {
        try {
            Optional<User> searchUser = helper.handlefind(request.getEmail(), userRepository::findByEmail);
            if (searchUser.isEmpty()) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("không tìm thấy user ").build(),
                        HttpStatus.BAD_REQUEST);
            }
            ResponedGlobal responedGlobal = forgotPassWordService.handleStepOne(searchUser.get());
            if (responedGlobal.getCode().equals("0")) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages(responedGlobal.getMessages()).build(),
                        HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data("").code("1")
                            .messages("thành công").build(),
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @PostMapping(path = "/Forgot/StepTwo", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<ResponedGlobal> handleForgotStepTwo(@RequestBody @Valid ForgotPassWordDTO request) {
        try {
            Optional<User> searchUser = helper.handlefind(request.getEmail(), userRepository::findByEmail);
            if (searchUser.isEmpty()) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("không tìm thấy user ").build(),
                        HttpStatus.BAD_REQUEST);
            }
            ResponedGlobal responedGlobal = forgotPassWordService.handleStepTwo(searchUser.get(), request.getOTP());
            if (responedGlobal.getCode().equals("0")) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages(responedGlobal.getMessages()).build(),
                        HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data(responedGlobal.getData()).code("1")
                            .messages("thành công").build(),
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @PostMapping(path = "/Forgot/StepThree", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<ResponedGlobal> handleForgotStepThree(@RequestBody @Valid ForgotPassWordDTO request) {
        try {
            Optional<User> searchUser = helper.handlefind(request.getEmail(), userRepository::findByEmail);
            if (searchUser.isEmpty()) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("không tìm thấy user ").build(),
                        HttpStatus.BAD_REQUEST);
            }

            ResponedGlobal responedGlobal = forgotPassWordService.handleStepThree(searchUser.get(),
                    request.getPassword());
            if (responedGlobal.getCode().equals("0")) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages(responedGlobal.getMessages()).build(),
                        HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data(responedGlobal.getData()).code("1")
                            .messages("thành công").build(),
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
