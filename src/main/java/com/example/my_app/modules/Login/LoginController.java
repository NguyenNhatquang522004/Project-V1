package com.example.my_app.modules.Login;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_app.common.ResponedGlobal;
import com.example.my_app.model.User.User;
import com.example.my_app.modules.Auth.JwtServices;
import com.example.my_app.modules.Login.DTO.GetDataGoogleDTO;
import com.example.my_app.modules.Login.DTO.LoginNormalDTO;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginController {
    LoginServices loginServices;

    JwtServices jwtServices;

    AuthenticationManager authenticationManager;

    @Autowired
    public LoginController(LoginServices loginServices, AuthenticationManager authenticationManager,
            JwtServices jwtServices) {
        this.loginServices = loginServices;
        this.authenticationManager = authenticationManager;
        this.jwtServices = jwtServices;
    }

    @PostMapping(path = "/Public/login/test", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponedGlobal> handleTest(@RequestBody @Valid LoginNormalDTO request)
            throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            System.out.println(userDetails.getAuthorities());
          
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data(jwtServices.generateToken(userDetails.getUsername())).code("1")
                            .messages("thành công").build(),
                    HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                    HttpStatusCode.valueOf(400));
        }
    }

    @PostMapping(path = "/Public/Login/Normal", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(path = "Public/oauth2/redirect", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<ResponedGlobal> handleLoginGoogleSucess1(OAuth2AuthenticationToken request)
            throws Exception {
        try {
            GetDataGoogleDTO requestDTO = GetDataGoogleDTO.builder()
                    .picture(request.getPrincipal().getAttribute("picture"))
                    .name(request.getPrincipal().getAttribute("name"))
                    .email(request.getPrincipal().getAttribute("email"))
                    .build();
            boolean checkUser = loginServices.handleFindbyEmailAndStatusEntry(requestDTO);
            if (checkUser) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("1").messages("thành công").build(),
                        HttpStatusCode.valueOf(200));
            }
            boolean addUser = loginServices.handleAddUser(requestDTO);
            if (!addUser) {
                return new ResponseEntity<ResponedGlobal>(
                        ResponedGlobal.builder().data("").code("1").messages("thành công").build(),
                        HttpStatusCode.valueOf(200));
            }

            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data("").code("1").messages("thành công").build(),
                    HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<ResponedGlobal>(
                    ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                    HttpStatusCode.valueOf(400));
        }
    }

}
