package cn.prosayj.authentication.service;

import cn.prosayj.authentication.modules.Role;

import java.util.List;

public interface UserRolePermissionService {

    /**
     * 根据资源URL查询角色列表
     * @param requestUrl
     * @return
     */
    List<Role> findRoleListByPermissionUrl(String requestUrl);
}
