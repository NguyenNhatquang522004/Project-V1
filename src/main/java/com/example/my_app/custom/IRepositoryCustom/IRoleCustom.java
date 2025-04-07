package com.example.my_app.custom.IRepositoryCustom;

import com.example.my_app.Enum.Role_Permission.StatusRole;
import com.example.my_app.model.Role_Permission.Role;
import com.example.my_app.model.User.User;

public interface IRoleCustom {
    public Role handleDefaultPermissionRole(StatusRole request, User user) throws Exception;
}
