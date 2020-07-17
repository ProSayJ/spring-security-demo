## 1:注册：
POST: http://localhost:60004/auth/register
```shell script
{
    "name":"root",
    "password":"root"
}
```
![注册-json.png](./png/注册-json.png)

## 2:登录：支持form表单和json数据格式
### json数据格式
![登录-json.png](./png/登录-json.png)
### form表单数据格式
![登录-form.png](./png/登录-form.png)


## 3：拿着上一步登录成功的数据去请求受保护的资源

### 登录成功-返回jwt
![登录成功-返回jwt.png](./png/登录成功-返回jwt.png)

### 携带jwt请求受保护的资源
![携带jwt请求受保护的资源.png](./png/携带jwt请求受保护的资源.png)

### Jwt-已过期.png
![Jwt-已过期.png](./png/Jwt-已过期.png)

### Jwt解析成功的用户无权限时,抛出异常：
![Jwt解析成功的用户无权限时,抛出异常.png](./png/Jwt解析成功的用户无权限时,抛出异常.png)
