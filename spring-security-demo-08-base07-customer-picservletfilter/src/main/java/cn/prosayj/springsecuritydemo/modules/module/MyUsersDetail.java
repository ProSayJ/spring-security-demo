package cn.prosayj.springsecuritydemo.modules.module;

import cn.prosayj.springsecuritydemo.modules.users.domain.UsersDomain;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * isAccountNonExpired、 isAccountNonLocked 和 isCredentialsNonExpired 暂且用不到， 统一返回
 * true， 否则Spring Security会认为账号异常。
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-10 上午 10:24
 * @since 1.0.0
 */
public class MyUsersDetail implements UserDetails {
    private UsersDomain usersDomain;
    /**
     * getAuthorities方法本身对应的是roles字段， 但由于结构不一致，
     * 所以此处新建一个， 并在后续进行填充。
     */
    private List<GrantedAuthority> authorities;

    private MyUsersDetail() {
    }

    public MyUsersDetail(UsersDomain usersDomain) {
        this.usersDomain = usersDomain;
    }

    public UsersDomain getUsersDomain() {
        return usersDomain;
    }

    public void setUsersDomain(UsersDomain usersDomain) {
        this.usersDomain = usersDomain;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return usersDomain.getPassword();
    }

    @Override
    public String getUsername() {
        return usersDomain.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return usersDomain.getEnabled() == 1;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

}
