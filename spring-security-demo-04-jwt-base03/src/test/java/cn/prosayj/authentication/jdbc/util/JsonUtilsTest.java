/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package cn.prosayj.authentication.jdbc.util;

import cn.prosayj.authentication.jwt.model.user.CustomUserDetails;
import cn.prosayj.authentication.jwt.util.JsonUtils;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * TODO
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-14 下午 06:57
 * @since 1.0.0
 */
public class JsonUtilsTest {
    private static final String SECRET = "5ae95dd3c5f811b9b819434910c52820ae7cfb3d9f7961e7117b24a7012873767d79f61f81fc2e06ebb6fd4f09ab47764d6e20607f843c22a0a2a6a6ed829680";

    public static void main(String[] args) throws IOException {
//        Student zs = new Student("zs", 12);
//        System.out.println("JsonUtils.toMap(zs) = " + JsonUtils.toMap(zs));
//
//
//        String map = "{\"alg\":\"HS512\"}";
//        System.out.println(JsonUtils.toMap(map));

        String stringObj = "{\"name\":\"zs\",\"password\":\"12345\",\"authorities\":[{\"authority\":\"USER\"}],\"enabled\":true,\"username\":\"zs\",\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"sub\":\"zs\",\"exp\":1594746056,\"iat\":1594742456,\"iss\":\"ProSayJ\"}";
        CustomUserDetails customUserDetails1 = JSON.parseObject(stringObj, CustomUserDetails.class);


        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JodaModule());

//        objectMapper.registerModule(BaseWriteOperationDeserializer.getModule());


//        objectMapper.setTimeZone(DateTimeZone.getDefault().toTimeZone());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        CustomUserDetails customUserDetails = objectMapper.readValue(stringObj, CustomUserDetails.class);


        System.out.println(JsonUtils.toBean(stringObj, CustomUserDetails.class));


    }


    static class Student {
        private String name;
        private int age;
        private String address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

}
