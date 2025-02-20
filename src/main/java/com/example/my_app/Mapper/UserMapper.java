package com.example.my_app.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.example.my_app.model.User.User;
import com.example.my_app.modules.Register.DTO.RegisterStepOneDTO;
import com.example.my_app.modules.Register.DTO.RegisterStepThreeDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User toUser(RegisterStepOneDTO request);

    void updateUser(@MappingTarget User user, RegisterStepOneDTO request);

    void UpdateCreatAccountUser(@MappingTarget User user, RegisterStepThreeDTO registerStepThreeDTO);

}
