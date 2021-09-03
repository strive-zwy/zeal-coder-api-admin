package com.zealcoder.controller;

import com.zealcoder.dto.AccessTokenDTO;
import com.zealcoder.dto.LoginDTO;
import com.zealcoder.entity.User;
import com.zealcoder.enums.UserLoginEnums;
import com.zealcoder.exception.BizException;
import com.zealcoder.exception.ResultBody;
import com.zealcoder.github.GithubProvider;
import com.zealcoder.github.GithubUser;
import com.zealcoder.mapper.UserMapper;
import com.zealcoder.shiroJWT.JwtToken;
import com.zealcoder.shiroJWT.JwtUtil;
import com.zealcoder.utils.EmailUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author : zwy
 * @version : 1.0
 * @createTime : 2021/8/29 20:58
 * @Description : 验证
 */
@RestController
@RequestMapping(value = "/author")
public class AuthorController {

    private final String DEFAULT_AVATAR = "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png";

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Autowired
    private GithubProvider githubProvider;
    @Qualifier("userMapper")
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EmailUtils emailUtils;

    @GetMapping("/callback")
    public ResultBody githubAuthCallback(@RequestParam("code") String code,
                                         @RequestParam("state") String state,
                                         HttpSession session) {
        AccessTokenDTO accessTokenDto = new AccessTokenDTO();
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setClientI_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser u = githubProvider.getUser(accessToken);
        System.out.println(u);
        return ResultBody.success(u);
    }

    @PostMapping("sendEmail")
    @ResponseBody
    public ResultBody sendEmail(String email, HttpSession session) {
        emailUtils.sendMail(email, session);
        return ResultBody.success("验证码发送成功！");
    }

    @PostMapping(value = "/register")
    public ResultBody register(@RequestBody LoginDTO loginDTO, HttpSession session){
        //        获取session中的验证信息
        String email = (String) session.getAttribute("email");
        String code = (String) session.getAttribute("code");
        if (!(loginDTO.getEmail().equals(email) && loginDTO.getCode().equals(code))) {
            return ResultBody.error("-1","验证码错误");
        }
        User old = userMapper.findOne(
                userMapper.query().where.account().eq(loginDTO.getAccount()).end()
        );
        if (old != null) {
            return ResultBody.error("-1","该账号已注册");
        }
        User user = new User();
        String hash = "MD5";
        Long gmtCreat = System.currentTimeMillis();
        SimpleHash result = new SimpleHash(hash, loginDTO.getPassword(), gmtCreat+"", 1024);
        Md5Hash md5Hash = new Md5Hash(loginDTO.getPassword(), gmtCreat+"", 1024);
        String token = JwtUtil.getJwtToken(loginDTO.getAccount(), md5Hash.toHex());
        String secret = result.toString();
        user.setAccount(loginDTO.getAccount())
        .setJwtToken(token)
        .setSecret(secret)
        .setEmail(loginDTO.getEmail())
        .setGmtCreate(gmtCreat)
        .setGmtModified(gmtCreat)
        .setAvatar(DEFAULT_AVATAR)
        .setType(UserLoginEnums.LOGIN_USER.getType());
        int count = userMapper.insert(user);
        if (count < 1) {
            throw new BizException("-1","用户插入失败");
        }
        return ResultBody.success();
    }

    @PostMapping("/login")
    public ResultBody login(@RequestBody LoginDTO loginDTO) {
        User user = userMapper.findOne(
                userMapper.query().where.account().eq(loginDTO.getAccount()).end()
        );
        if (user == null)
            throw new BizException("-1","该用户不存在");
        String salt = user.getGmtCreate()+"";
        Md5Hash md5Hash = new Md5Hash(loginDTO.getPassword(), salt, 1024);
        String token = JwtUtil.getJwtToken(loginDTO.getAccount(), md5Hash.toHex());
        JwtToken jwtToken = new JwtToken(token);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(jwtToken);
            return ResultBody.success(token);
        } catch (UnknownAccountException e){
            throw new BizException("-1","无效用户，用户不存在");
        } catch (IncorrectCredentialsException e){
            throw new BizException("-1","密码输入错误");
        } catch (ExpiredCredentialsException e){
            throw new BizException("-1","token过期，请重新登录");
        }
    }

    @PostMapping("/logout")
    public ResultBody logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultBody.success();
    }
}
