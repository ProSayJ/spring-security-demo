/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package cn.prosayj.authentication.jdbc.util;

import cn.prosayj.authentication.jwt.model.user.CustomUserDetails;
import cn.prosayj.authentication.jwt.model.user.domain.UserDomain;
import cn.prosayj.authentication.jwt.util.jwt.JWTUtils;
import org.junit.Test;

/**
 * JWTUtilsTest
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-14 下午 07:02
 * @since 1.0.0
 */
public class JWTUtilsTest {

    @Test
    public void create() {
        System.out.println(
                JWTUtils.create(
                        "zs",
                        false,
                        new CustomUserDetails(new UserDomain("zs", "12345", "USER"))));
    }

    @Test
    public void subject() {
        System.out.println(
                JWTUtils.subject(
                        JWTUtils.create(
                                "zs",
                                false,
                                new CustomUserDetails(new UserDomain("zs", "12345", "USER"))))
        );
    }

    @Test
    public void userDetails() {
        System.out.println(
                JWTUtils.userDetails(
                        JWTUtils.create(
                                "zs",
                                false,
                                new CustomUserDetails(new UserDomain("zs", "12345", "USER"))))
        );
    }
}
