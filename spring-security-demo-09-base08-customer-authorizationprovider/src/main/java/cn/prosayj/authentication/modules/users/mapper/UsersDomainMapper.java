package cn.prosayj.authentication.modules.users.mapper;

import cn.prosayj.authentication.modules.users.domain.UsersDomain;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDomainMapper {

    UsersDomain findByUserName(@Param("username") String username);
}