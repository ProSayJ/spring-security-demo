package cn.prosayj.authentication.jdbc.util;

import cn.prosayj.authentication.CsrfAuthentication_05;
import cn.prosayj.authentication.jwt.util.redis.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * com.redis.RedistTest-测试用例
 *
 * @author yangjian@bubi.cn
 * @date 2020-03-13 下午 04:51
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {CsrfAuthentication_05.class})
@TestPropertySource(value = {"classpath:application-redis.properties"})
public class RedistTest {
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void test() throws IOException, ClassNotFoundException {

        /**
         * springSecurity默认使用了jdk的JdkSerializationRedisSerializer来序列化对象。
         */
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) redisUtil.get("yinuojr_auth:jwt_0baa7889-3c01-4222-a3f8-6626cbec8dc4");
        DefaultOAuth2AccessToken defaultOAuth2AccessToken2 = (DefaultOAuth2AccessToken) redisUtil.get("access:1e7b2ca5-52a7-46ed-b3a3-9ed6679785f9");


        redisUtil.put("name", "zs", 50);
        System.out.println(redisUtil.get("name"));
    }


    /**
     * 16进制转换成为string类型字符串
     *
     * @param s s
     * @return String
     */
    public static String hexStringToString(String s) throws IOException, ClassNotFoundException {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        // InputStream in_withcode = new ByteArrayInputStream(hex.getBytes(StandardCharsets.UTF_8));

//        InputStream in_nocode = new ByteArrayInputStream(baKeyword);
//        ObjectInputStream ois = new ObjectInputStream(in_nocode);
//        DefaultOAuth2AccessToken person = (DefaultOAuth2AccessToken) ois.readObject();


        try {
            s = new String(baKeyword, StandardCharsets.UTF_8);
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }


//    @Test
//    public void set() {
//        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
//        String key = "user:1";
//        //  stringRedisTemplate.expire(key, 100, TimeUnit.SECONDS);
//        ops.put(key, "name", "woody");
//        ops.put(key, "age", 18);
//        ops.put(key, "email", "343618924@qq.com");
//        ops.put(key, "address", "成都市");
//        ops.put(key, "sex", "男");
//    }
//
//    @Test
//    public void multiSet() {
//        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
//        String key = "user:2";
//        Map<String, String> map = new HashMap<>();
//        map.put("name", "吴波");
//        map.put("age", "29");
//        map.put("email", "343618924@qq.com");
//        map.put("sex", "男");
//        ops.putAll(key, map);
//    }
//
//    @Test
//    public void get() {
//        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
//        String key = "user:1";
//        String hashKey = "name";
//        String name = ops.get(key, hashKey).toString();
//        System.out.println(name);
//    }
//
//    @Test
//    public void multiGet() {
//        HashOperations<String, String, Object> ops = redisTemplate.opsForHash();
//        String key = "user:2";
//        List<String> hashKey = new ArrayList<>();
//        hashKey.add("age");
//        hashKey.add("name");
//        hashKey.add("email");
//        hashKey.add("address");
//        hashKey.add("aaa");
//        List<Object> values = ops.multiGet(key, hashKey);
//        values.stream().forEach(value -> System.out.println(value));
//    }
//
//    @Test
//    public void keys() {
//        HashOperations<String, String, Object> ops = redisTemplate.opsForHash();
//        String key = "user:2";
//        Set<String> keys = ops.keys(key);
//        keys.stream().forEach(k -> System.out.println(k));
//    }
//
//    @Test
//    public void hasKey() {
//        HashOperations<String, String, Object> ops = redisTemplate.opsForHash();
//        String key = "user:2";
//        Boolean name = ops.hasKey(key, "name");
//        System.out.println("name: " + name);
//        Boolean bar = ops.hasKey(key, "bar");
//        System.out.println("bar: " + bar);
//    }
}
