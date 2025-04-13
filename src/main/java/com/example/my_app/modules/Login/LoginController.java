package com.example.my_app.modules.Login;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.my_app.common.ResponedGlobal;
import com.example.my_app.custom.Helper.Helper;
import com.example.my_app.model.Role_Permission.Permission;
import com.example.my_app.model.Role_Permission.Role;
import com.example.my_app.model.User.User;
import com.example.my_app.modules.Login.DTO.LoginNormalDTO;
import com.example.my_app.modules.Login.DTO.Responeduser;
import com.fasterxml.jackson.databind.ObjectMapper;
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

        Helper helper;

        @Autowired
        public LoginController(LoginServices loginServices,
                        Helper helper) {
                this.loginServices = loginServices;

                this.helper = helper;
        }

        public Set<String> getAllPermissionsFromUser(User user) {
                return Optional.ofNullable(user.getUser_role()) // Kiểm tra user_role có null không
                                .map(Role::getRole_permission) // Lấy danh sách PermissionDTO từ RoleDTO
                                .stream()
                                .flatMap(Set::stream) // "Làm phẳng" Stream<Set<PermissionDTO>> thành
                                                      // Stream<PermissionDTO>
                                .map(Permission::getDescription) // Lấy trường description (hoặc trường khác tùy
                                .map(Enum::name) // Chuyển enum thành tên (nếu description là enum)
                                .collect(Collectors.toSet());
        }

        @PostMapping(path = "/Public/Login/Normal", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResponedGlobal> handleNormalLogin(@RequestBody @Valid LoginNormalDTO request,
                        HttpServletResponse response)
                        throws Exception {
                try {
                        System.out.println(request.toString());
                        Optional<User> searchUser = loginServices.handleFetchByEmail(request);
                        if (searchUser.isEmpty() == true) {
                                return new ResponseEntity<ResponedGlobal>(
                                                ResponedGlobal.builder().data("").code("0").messages("lỗi").build(),
                                                HttpStatus.BAD_REQUEST);
                        }
                        Set<String> a = getAllPermissionsFromUser(searchUser.get());

                        Responeduser response1 = new Responeduser();
                        response1.setId(searchUser.get().getId().toString());
                        response1.setName(searchUser.get().getUsername());
                        response1.setRole(searchUser.get().getUser_role().getDescription().name());
                        response1.setUrl(searchUser.get().getUrl());
                        response1.setPermission(a);

                        return new ResponseEntity<ResponedGlobal>(
                                        ResponedGlobal.builder()
                                                        .data(response1)
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

}
