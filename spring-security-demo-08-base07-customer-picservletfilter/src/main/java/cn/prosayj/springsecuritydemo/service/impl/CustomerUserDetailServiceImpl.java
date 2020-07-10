package cn.prosayj.springsecuritydemo.service.impl;

import cn.prosayj.springsecuritydemo.modules.module.MyUsersDetail;
import cn.prosayj.springsecuritydemo.modules.users.domain.UsersDomain;
import cn.prosayj.springsecuritydemo.modules.users.mapper.UsersDomainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义UserDetailService
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-10 上午 09:45
 * @since 1.0.0
 */
@Service
public class CustomerUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsersDomainMapper usersDomainMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersDomain user = usersDomainMapper.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        MyUsersDetail usersDetail = new MyUsersDetail(user);
        //将数据库形式的roles解析为UserDetails的权限集
        //该方法是SpringSecurity提供的。方法用于将逗哈隔开的权限集合字符串切割成可用权限对象集合。
        List<GrantedAuthority> roles = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles());
        usersDetail.setAuthorities(roles);
        return usersDetail;
    }

    /**
     * 自定义切割权限集 @see:{@link AuthorityUtils#commaSeparatedStringToAuthorityList(String)}
     * <p>
     * Spring Security的权限几乎是用
     * SimpleGrantedAuthority生成的， 只要注意每种角色对应一个GrantedAuthority即可。 另外， 一定要在自
     * 己的UserDetailsService实现类上加入@Service注解， 以便被Spring Security自动发现。
     *
     * <p>
     * SimpleGrantedAuthority是GrantedAuthority的一个实现类。
     *
     * @param roles roles
     * @param rule  rule
     * @return List<GrantedAuthority>
     */
    private List<GrantedAuthority> customerCombinedPermissions(String roles, String rule) {
        return new ArrayList<GrantedAuthority>() {{
            for (String role : roles.split(rule)) {
                add(new SimpleGrantedAuthority(role));
            }
        }};

    }
}
