package com.zealcoder.shiroJWT;

import com.zealcoder.entity.User;
import com.zealcoder.exception.BizException;
import com.zealcoder.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author : zwy
 * @Description :自定义Realm
 */
@Component
public class MyRealm extends AuthorizingRealm {

    @Qualifier("userMapper")
    @Autowired
    private UserMapper userMapper;

    /**
     * 限定这个realm只能处理JwtToken（不加的话会报错）
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权(授权部分这里就省略了，先把重心放在认证上)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取到用户名，查询用户权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole("jwtToken");
//        simpleAuthorizationInfo.addStringPermission(permission.getPermission());

        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) {
        String token = (String) auth.getCredentials();  //JwtToken中重写了这个方法了
        String account = JwtUtil.getAccount(token);   // 获得 account
        //用户不存在（这个在登录时不会进入，只有在token校验时才有可能进入）
        if(account == null)
            throw new UnknownAccountException();
        //根据用户名，查询数据库获取到正确的用户信息
        User user = userMapper.findOne(
                userMapper.query().where.account().eq(account).end()
        );
        //用户不存在（这个在登录时不会进入，只有在token校验时才有可能进入）
        if(user == null)
            throw new UnknownAccountException();
        //toke过期
        if(JwtUtil.isExpire(token)){
            throw new ExpiredCredentialsException();
        }
        //密码错误(这里获取到password，就是3件套处理后的保存到数据库中的凭证，作为密钥)
        if (! JwtUtil.verifyToken(token, account, user.getSecret())) {
            throw new IncorrectCredentialsException();
        }
        return new SimpleAuthenticationInfo(user, token, getName());
    }
}
