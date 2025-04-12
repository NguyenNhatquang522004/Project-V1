package com.example.my_app.modules.Login;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_app.Enum.Role_Permission.StatusRole;
import com.example.my_app.common.ResponedGlobal;
import com.example.my_app.custom.Helper.Helper;
import com.example.my_app.model.Admin.Employee;
import com.example.my_app.model.User.User;
import com.example.my_app.modules.Auth.JwtServices;
import com.example.my_app.modules.Auth.DTO.AuthDTO;

import com.example.my_app.modules.Login.DTO.GetDataGoogleDTO;
import com.example.my_app.modules.Login.DTO.LoginNormalDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginController {
        ObjectMapper objectMapper = new ObjectMapper();

        LoginServices loginServices;

        JwtServices jwtServices;

        AuthenticationManager authenticationManager;

        Helper helper;

        @Autowired
        public LoginController(LoginServices loginServices, AuthenticationManager authenticationManager,
                        JwtServices jwtServices, Helper helper) {
                this.loginServices = loginServices;
                this.authenticationManager = authenticationManager;
                this.jwtServices = jwtServices;
                this.helper = helper;
        }

        @PostMapping(path = "/Public/Login/Normal", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handleNormalLogin(@RequestBody @Valid LoginNormalDTO request,
                        HttpServletResponse response)
                        throws Exception {
                try {
                        System.out.println("123");
                        Optional<User> searchUser = loginServices.handleFetchByEmail(request);
                        if (searchUser.isEmpty() == true) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                                                HttpStatus.BAD_REQUEST);
                        }
                        boolean checkpassword = loginServices.handleDecode(request.getPassword(),
                                        searchUser.get().getPassword());
                        if (!checkpassword) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                                                HttpStatus.BAD_REQUEST);
                        }
                        Authentication authentication = authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                                        request.getEmail(),
                                                        request.getPassword()));
                        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                        Set<String> permissions = loginServices.handleGetPermisson(searchUser);
                        AuthDTO authDTO = AuthDTO.builder()
                                        .username(userDetails.getUsername())
                                        .role(StatusRole.Customers)
                                        .permission(permissions)
                                        .build();
                        String data = objectMapper.writeValueAsString(authDTO);
                        String encodedData = URLEncoder.encode(data, StandardCharsets.UTF_8);
                        Cookie cookieAccessToken = loginServices.handleCookie("accessToken", encodedData, 36000, false,
                                        false);
                        Cookie cookieRefreshToken = loginServices.handleCookie("refeshToken",
                                        UUID.randomUUID().toString(), 36000, false,
                                        false);
                        response.addCookie(cookieAccessToken);
                        response.addCookie(cookieRefreshToken);
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder()
                                                        .data(jwtServices.generateToken(data,
                                                                        StatusRole.Customers.toString()))
                                                        .code("1")
                                                        .messages("thành công").build(),
                                        HttpStatus.OK);
                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                                        HttpStatus.BAD_REQUEST);
                }
        }

        @GetMapping(path = "Public/oauth2/redirect", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
        public ResponseEntity<ResponedGlobal> handleLoginGoogleSucess1(OAuth2AuthenticationToken request,
                        HttpServletResponse response)
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
                                                ResponedGlobal.builder().data("").code("1").messages("thành công")
                                                                .build(),
                                                HttpStatus.OK);
                        }
                        Optional<User> addUser = loginServices.handleAddUser(requestDTO);
                        if (addUser == null) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("1").messages("thành công")
                                                                .build(),
                                                HttpStatus.OK);
                        }
                        // Authentication authentication = authenticationManager.authenticate(
                        // new UsernamePasswordAuthenticationToken(
                        // request.getPrincipal().getAttribute("email"),
                        // request.getPrincipal().getAttribute("name")));
                        // System.out.println(authentication.getAuthorities());
                        Set<String> permissions = loginServices.handleGetPermisson(addUser);
                        AuthDTO authDTO = AuthDTO.builder()
                                        .username(addUser.get().getEmail())
                                        .role(addUser.get().getUser_role().getDescription())
                                        .permission(permissions)
                                        .build();
                        String data = objectMapper.writeValueAsString(authDTO);
                        Cookie cookieAccessToken = loginServices.handleCookie("accessToken", data, 36000, false,
                                        false);
                        Cookie cookieRefreshToken = loginServices.handleCookie("refeshToken",
                                        UUID.randomUUID().toString(), 36000, false,
                                        false);
                        response.addCookie(cookieAccessToken);
                        response.addCookie(cookieRefreshToken);
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder()
                                                        .data(jwtServices.generateToken(data,
                                                                        StatusRole.Staff.toString()))
                                                        .code("1")
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
        @PostMapping(path = "/Public/admin/Login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handleAdminLogin(@RequestBody @Valid LoginNormalDTO request,
                        HttpServletResponse response) throws Exception {
                try {
                        Optional<Employee> searchEmp = loginServices.handleFetcAdminhByEmail(request);
                        if (searchEmp.isEmpty()) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                                                HttpStatus.BAD_REQUEST);
                        }
                        boolean checkpassword = loginServices.handleDecode(request.getPassword(),
                                        searchEmp.get().getPassword());
                        if (!checkpassword) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                                                HttpStatus.BAD_REQUEST);
                        }
                        Authentication authentication = authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                                        request.getEmail(),
                                                        request.getPassword()));
                        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                        Set<String> permissions = loginServices.handleGetAdminPermisson(searchEmp.get());
                        Set<String> department = loginServices.handleGetDeparment(searchEmp.get());
                        AuthDTO authDTO = AuthDTO.builder()
                                        .username(userDetails.getUsername())
                                        .role(searchEmp.get().getStatusRole())
                                        .permission(permissions)
                                        .deparment(department)
                                        .build();
                        String data = objectMapper.writeValueAsString(authDTO);
                        String encodedData = URLEncoder.encode(data, StandardCharsets.UTF_8);
                        Cookie cookieAccessToken = loginServices.handleCookie("accessToken",
                                        encodedData, 36000, false,
                                        false);
                        Cookie cookieRefreshToken = loginServices.handleCookie("refeshToken",
                                        UUID.randomUUID().toString(), 36000, false,
                                        false);
                        response.addCookie(cookieAccessToken);
                        response.addCookie(cookieRefreshToken);
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data("").code("1")
                                                        .messages("thành công").build(),
                                        HttpStatus.OK);
                } catch (Exception e) {
                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                                        HttpStatus.BAD_REQUEST);
                }
        }
}
