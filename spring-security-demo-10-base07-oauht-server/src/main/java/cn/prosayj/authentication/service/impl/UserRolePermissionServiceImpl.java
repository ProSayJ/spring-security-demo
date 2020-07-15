package cn.prosayj.authentication.service.impl;

import cn.prosayj.authentication.modules.Role;
import cn.prosayj.authentication.service.UserRolePermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRolePermissionServiceImpl implements UserRolePermissionService {
    @Override
    public List<Role> findRoleListByPermissionUrl(String requestUrl) {
        return null;
    }
}