package cn.prosayj.authentication.jwt.model.user.mapper;

import cn.prosayj.authentication.jwt.model.user.domain.UserDomain;

import java.util.ArrayList;

/**
 * UserDomainMapper
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-15 下午 11:13
 * @since 1.0.0
 */
public interface UserDomainMapper {

    UserDomain findByUsername(String username);

    int save(UserDomain userDomain);
}
