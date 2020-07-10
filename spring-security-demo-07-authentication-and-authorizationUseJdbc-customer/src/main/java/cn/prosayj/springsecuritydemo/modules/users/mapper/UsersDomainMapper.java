package cn.prosayj.springsecuritydemo.modules.users.mapper;

import cn.prosayj.springsecuritydemo.modules.users.domain.UsersDomain;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDomainMapper {

    UsersDomain findByUserName(@Param("username") String username);
}