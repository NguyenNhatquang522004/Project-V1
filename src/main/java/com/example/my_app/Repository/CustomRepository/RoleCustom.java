package com.example.my_app.Repository.CustomRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.my_app.Enum.Role_Permission.StatusPermission;
import com.example.my_app.Enum.Role_Permission.StatusRole;
import com.example.my_app.Repository.IRepositoryCustom.IRoleCustom;
import com.example.my_app.Repository.Permission.PermissionRepository;
import com.example.my_app.Repository.Role.RoleRepository;
import com.example.my_app.model.Role_Permission.Permission;
import com.example.my_app.model.Role_Permission.Role;
import com.example.my_app.model.User.User;

import jakarta.transaction.Transactional;

@Repository
public class RoleCustom implements IRoleCustom {
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    RoleRepository roleRepository;

    public Set<Permission> handleGetSpecificPermission(List<StatusPermission> data) {
        List<Permission> searchPermission = permissionRepository.findAll();
        return searchPermission.stream()
                .filter(permission -> data.contains(permission.getDescription()))
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public Role handleDefaultPermissionRole(StatusRole request, User user) throws Exception {
        try {
            Role role = new Role();
            switch (request) {
                case Customers:

                    List<StatusPermission> allowedPermissionscustomers = List.of(
                            StatusPermission.Order,
                            StatusPermission.Payment,
                            StatusPermission.Comment);
                    System.out.println(allowedPermissionscustomers.size());
                    Set<Permission> searchSpecificPermissionCustomers = handleGetSpecificPermission(
                            allowedPermissionscustomers);
                    if (searchSpecificPermissionCustomers == null
                            && searchSpecificPermissionCustomers.isEmpty() == true) {
                        return null;
                    }
                    for (Permission value : searchSpecificPermissionCustomers) {
                        value.getPermission_role().add(role);
                    }
                    role.setDescription(StatusRole.Customers);
                    role.setRole_user(user);
                    role.setRole_permission(searchSpecificPermissionCustomers);
                    roleRepository.saveAndFlush(role);
                    break;
                case Owner:

                    List<StatusPermission> allowedPermissionsOwner = List.of(
                            StatusPermission.Update,
                            StatusPermission.Create,
                            StatusPermission.Delete,
                            StatusPermission.Read);

                    Set<Permission> searchSpecificPermissionOwner = handleGetSpecificPermission(
                            allowedPermissionsOwner);

                    if (searchSpecificPermissionOwner == null && searchSpecificPermissionOwner.isEmpty()) {
                        return null;
                    }
                    for (Permission value : searchSpecificPermissionOwner) {
                        value.getPermission_role().add(role);
                    }
                    role.setDescription(StatusRole.Owner);
                    role.setRole_user(user);
                    role.setRole_permission(searchSpecificPermissionOwner);
                    roleRepository.saveAndFlush(role);

                    break;
                case Staff:

                    List<StatusPermission> allowedPermissionsStaff = List.of(
                            StatusPermission.Update,
                            StatusPermission.Create,
                            StatusPermission.Delete,
                            StatusPermission.Read);

                    Set<Permission> searchSpecificPermissionStaff = handleGetSpecificPermission(
                            allowedPermissionsStaff);

                    if (searchSpecificPermissionStaff == null && searchSpecificPermissionStaff.isEmpty()) {
                        return null;
                    }
                    for (Permission value : searchSpecificPermissionStaff) {
                        value.getPermission_role().add(role);
                    }
                    role.setDescription(StatusRole.Staff);
                    role.setRole_user(user);
                    role.setRole_permission(searchSpecificPermissionStaff);
                    roleRepository.saveAndFlush(role);

                    break;
                default:

                    break;
            }
            return role;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }
}
