package cn.prosayj.authentication.csrf.model.user.mapper;

import cn.prosayj.authentication.csrf.model.user.domain.UserDomain;

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
