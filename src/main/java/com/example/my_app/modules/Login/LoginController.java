package com.example.my_app.modules.Login;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_app.common.ResponedGlobal;
import com.example.my_app.model.User.User;
import com.example.my_app.modules.Login.DTO.LoginNormalDTO;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginController {
    LoginServices loginServices;

    @Autowired
    public LoginController(LoginServices loginServices) {
        this.loginServices = loginServices;
    }

    @PatchMapping(path = "/Public/Login/Normal", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponedGlobal> handleNormalLogin(@RequestBody @Valid LoginNormalDTO request)
            throws Exception {
        try {
            Optional<User> searchUser = loginServices.handleFetchByEmail(request);
            if (!searchUser.isPresent()) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                        HttpStatusCode.valueOf(400));
            }
            boolean checkpassword = loginServices.handleDecode(request.getPassword(), searchUser.get().getPassword());
            if (!checkpassword) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                        HttpStatusCode.valueOf(400));
            }

            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data("").code("1").messages("thành công").build(),
                    HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                    HttpStatusCode.valueOf(400));
        }
    }
}
