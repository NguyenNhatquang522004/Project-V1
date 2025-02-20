package com.example.my_app.modules.Register;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.my_app.common.ResponedGlobal;
import com.example.my_app.common.SenderEmail;
import com.example.my_app.model.User.User;
import com.example.my_app.modules.Register.DTO.RegisterStepOneDTO;
import com.example.my_app.modules.Register.DTO.RegisterStepThreeDTO;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(path = "/Public/Register")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegisterController {

    RegisterServices registerServices;

    SenderEmail senderEmail;

    @Autowired
    public RegisterController(RegisterServices registerServices, SenderEmail senderEmail) {
        this.registerServices = registerServices;
        this.senderEmail = senderEmail;
    }

    @PostMapping(path = "/StepOne", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponedGlobal> handleRegisterStepOne(
            @RequestBody @Validated RegisterStepOneDTO request) throws Exception {
        try {

            boolean searchEmail = registerServices.CheckEmail(request.getEmail());
            if (searchEmail) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("email đã tồn tại hoặc lỗi").build(),
                        HttpStatusCode.valueOf(400));
            }
            boolean saveUser = registerServices.handleaddUser(request);
            if (!saveUser) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("lưu dữ liệu bị lỗi").build(),
                        HttpStatusCode.valueOf(400));
            }
            boolean sendEmail = senderEmail.handleSenderEmai("nguyennhatquang522004@gmail.com", request.getCode());
            if (!sendEmail) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("gửi email thất bại").build(),
                        HttpStatusCode.valueOf(400));
            }
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data(request.getEmail()).code("1").messages("thành công").build(),
                    HttpStatusCode.valueOf(200));
        } catch (Exception e) {

            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                    HttpStatusCode.valueOf(200));
        }
    }

    @PutMapping(path = "/StepTwo", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponedGlobal> handleRegisterStepTwo(@RequestBody @Valid RegisterStepOneDTO request)

            throws Exception {
        try {
            Optional<User> searchUser = registerServices.handleFetchByEmail(request.getEmail());
            if (!searchUser.isPresent()) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("không tìm thấy User").build(),
                        HttpStatusCode.valueOf(400));
            }
            boolean checkCodeExpired = registerServices.handleValidTime(searchUser.get().getCode_expired());
            if (!checkCodeExpired) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("code đã hết hạn").build(),
                        HttpStatusCode.valueOf(400));
            }
            boolean CheckCodeUrl = searchUser.get().getCode().equals(request.getCode());
            if (!CheckCodeUrl) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("code không hợp lệ").build(),
                        HttpStatusCode.valueOf(400));
            }
            boolean updateUser = registerServices.handleUpdateUser(request, searchUser.get());
            if (!updateUser) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("xảy ra lỗi khi update User").build(),
                        HttpStatusCode.valueOf(400));
            }
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data(request.getEmail()).code("1").messages("thành công").build(),
                    HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                    HttpStatusCode.valueOf(400));
        }

    }

    @PutMapping(path = "/StepThree", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponedGlobal> handleRegisterStepThree(@RequestBody @Valid RegisterStepThreeDTO request)
            throws Exception {
        try {
            Optional<User> searchUser = registerServices.handleFetchByEmail(request.getEmail());
            if (!searchUser.isPresent()) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("không tìm thấy User").build(),
                        HttpStatusCode.valueOf(400));
            }
            String hashPass = registerServices.handleHashPassword(request.getPassword());
            request.setPassword(hashPass);
            boolean updateUser = registerServices.handleUpdateUser(request, searchUser.get());
            if (!updateUser) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("update lỗi").build(),
                        HttpStatusCode.valueOf(400));
            }
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data(request.getEmail()).code("1").messages("thành công").build(),
                    HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                    HttpStatusCode.valueOf(400));
        }
    }

}
