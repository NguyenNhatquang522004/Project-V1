package com.example.my_app.Repository.Role;

import com.example.my_app.Enum.StatusRole;

import com.example.my_app.model.Role_Permission.Role;
import com.example.my_app.model.User.User;

public interface IRoleCustom {
    public Role handleInitPermissionRole(StatusRole request, User user) throws Exception;
}
