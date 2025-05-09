package com.example.my_app.Mapper.User;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import com.example.my_app.model.User.User;
import com.example.my_app.modules.Register.DTO.RegisterStepOneDTO;
import com.example.my_app.modules.Register.DTO.RegisterStepThreeDTO;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User toUser(RegisterStepOneDTO request);

    void upLocalDateTimeUser(@MappingTarget User user, RegisterStepOneDTO request);

    void UpLocalDateTimeCreatAccountUser(@MappingTarget User user, RegisterStepThreeDTO registerStepThreeDTO);

}
