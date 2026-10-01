# codec-spring-boot-starter

Spring Boot Starter：基于注解的**请求解密 / 响应加密**（AES），引入即生效、零配置可用。

## 快速开始

用 `@Decrypt` 标注请求、`@Encrypt` 标注返回值即可：

```java
@Decrypt                                  // 请求体解密
@PostMapping("/user")
public Result<Void> save(@RequestBody String body) { ... }

@Encrypt                                  // 返回值加密
@GetMapping("/user")
public Result<User> user() { ... }
```

两个注解都支持自定义密钥：`@Encrypt(key = "...")` / `@Decrypt(key = "...")`。

## 配置

| 配置项 | 默认值 | 说明 |
| --- | --- | --- |
| `codec.key` | `0123456789abcdef` | AES 密钥（16 位），注解上的 `key` 优先 |

> 默认密钥只用于演示。生产环境请通过 `codec.key` 覆盖（建议走配置中心 / 密钥管理），不要把密钥写进代码。

## 组成

| 包 | 内容 |
| --- | --- |
| `annotation` | `@Encrypt`（响应加密）、`@Decrypt`（请求解密） |
| `advice` | `EncryptResponse`、`DecryptRequest`（加密/解密的切面实现） |
| `config` | `CodecAutoConfiguration`（`@ComponentScan("org.zero.codec")`）、`CodecProperties`（前缀 `codec`） |
| `util` | `AesUtil`（AES 加解密，带单元测试） |
| `model` / `constant` | `Result` 统一返回体、`SysError` 错误码 |

## 自动装配

`src/main/resources/META-INF/spring.factories` 注册了 `CodecAutoConfiguration`（Spring Boot 2.x 的装配机制），依赖引入后自动生效。
