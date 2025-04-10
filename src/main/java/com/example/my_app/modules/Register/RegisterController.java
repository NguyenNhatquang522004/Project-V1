package com.example.my_app.modules.Register;

import java.math.BigInteger;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_app.Repository.Employee.EmployeeRepository;
import com.example.my_app.common.ResponedGlobal;
import com.example.my_app.common.SenderEmail;
import com.example.my_app.model.Admin.Employee;
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

    EmployeeRepository employeeRepository;

    @Autowired
    public RegisterController(RegisterServices registerServices, SenderEmail senderEmail,
            EmployeeRepository employeeRepository) {
        this.registerServices = registerServices;
        this.senderEmail = senderEmail;
        this.employeeRepository = employeeRepository;
    }

    @PostMapping(path = "/StepOne", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponedGlobal> handleRegisterStepOne(
            @RequestBody @Valid RegisterStepOneDTO request) throws Exception {
        try {

            boolean searchEmail = registerServices.CheckEmail(request.getEmail());
            if (searchEmail) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("email đã tồn tại hoặc lỗi").build(),
                        HttpStatus.BAD_REQUEST);
            }
            String code = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
            String uuid16digits = code.substring(code.length() - 4);
            request.setCode(uuid16digits);
            boolean saveUser = registerServices.handleaddUser(request);
            if (!saveUser) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("lưu dữ liệu bị lỗi").build(),
                        HttpStatus.BAD_REQUEST);
            }
            boolean sendEmail = senderEmail.handleSenderEmai("nguyennhatquang522004@gmail.com", uuid16digits);
            if (!sendEmail) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("gửi email thất bại").build(),
                        HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data(request.getEmail()).code("1").messages("thành công").build(),
                    HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                    HttpStatus.BAD_REQUEST);
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
                        HttpStatus.BAD_REQUEST);
            }
            boolean checkCodeExpired = registerServices.handleValidTime(searchUser.get().getCode_expired());
            if (!checkCodeExpired) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("code đã hết hạn").build(),
                        HttpStatus.BAD_REQUEST);
            }
            boolean CheckCodeUrl = searchUser.get().getCode().equals(request.getCode());
            if (!CheckCodeUrl) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("code không hợp lệ").build(),
                        HttpStatus.BAD_REQUEST);
            }
            boolean upLocalDateTimeUser = registerServices.handleUpLocalDateTimeUser(request, searchUser.get());
            if (!upLocalDateTimeUser) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("xảy ra lỗi khi upLocalDateTime User")
                                .build(),
                        HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data(request.getEmail()).code("1").messages("thành công").build(),
                    HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                    HttpStatus.BAD_REQUEST);
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
                        HttpStatus.BAD_REQUEST);
            }
            String hashPass = registerServices.handleHashPassword(request.getPassword());
            request.setPassword(hashPass);
            boolean upLocalDateTimeUser = registerServices.handleUpLocalDateTimeUser(request, searchUser.get());
            if (!upLocalDateTimeUser) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("upLocalDateTime lỗi").build(),
                        HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data(request.getEmail()).code("1").messages("thành công").build(),
                    HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/admin/StepOne", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponedGlobal> handleAdminRegisterStepOne(
            @RequestBody @Valid RegisterStepOneDTO request) throws Exception {
        try {
            boolean searchEmail = registerServices.CheckEmail(request.getEmail());
            if (searchEmail) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("email đã tồn tại hoặc lỗi").build(),
                        HttpStatus.BAD_REQUEST);
            }
            String uuid16digits = request.getCode().substring(request.getCode().length() - 4);
            request.setCode(uuid16digits);
            boolean saveUser = registerServices.handleaddUseradmin(request);
            if (!saveUser) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("lưu dữ liệu bị lỗi").build(),
                        HttpStatus.BAD_REQUEST);
            }
            boolean sendEmail = senderEmail.handleSenderEmai("nguyennhatquang522004@gmail.com", uuid16digits);
            if (!sendEmail) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("gửi email thất bại").build(),
                        HttpStatus.BAD_REQUEST);
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

    @PutMapping(path = "/admin/StepTwo", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponedGlobal> handleAdminRegisterStepTwo(@RequestBody @Valid RegisterStepOneDTO request)

            throws Exception {
        try {
            Optional<Employee> searchUser = registerServices.handleFetchByEmailAdmin(request.getEmail());
            if (!searchUser.isPresent()) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("không tìm thấy User").build(),
                        HttpStatus.BAD_REQUEST);
            }
            boolean checkCodeExpired = registerServices.handleValidTime(searchUser.get().getCode_expired());
            if (!checkCodeExpired) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("code đã hết hạn").build(),
                        HttpStatus.BAD_REQUEST);
            }
            boolean CheckCodeUrl = searchUser.get().getCode().equals(request.getCode());
            if (!CheckCodeUrl) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("code không hợp lệ").build(),
                        HttpStatus.BAD_REQUEST);
            }
            employeeRepository.saveAndFlush(searchUser.get());
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data(request.getEmail()).code("1").messages("thành công").build(),
                    HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                    HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(path = "/admin/StepThree", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponedGlobal> handleAdminRegisterStepThree(@RequestBody @Valid RegisterStepThreeDTO request)
            throws Exception {
        try {
            Optional<Employee> searchUser = registerServices.handleFetchByEmailAdmin(request.getEmail());
            if (!searchUser.isPresent()) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("không tìm thấy User").build(),
                        HttpStatus.BAD_REQUEST);
            }
            String hashPass = registerServices.handleHashPassword(request.getPassword());
            request.setPassword(hashPass);
            searchUser.get().setEmail(hashPass);
            employeeRepository.saveAndFlush(searchUser.get());

            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data(request.getEmail()).code("1").messages("thành công").build(),
                    HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
