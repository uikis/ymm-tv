package com.ymm.ymmtvportal.service;

import com.ymm.ymmtvcommon.exception.YmmException;
import com.ymm.ymmtvcommon.excetionEnum.ExceptionCode;
import com.ymm.ymmtvcommon.pojo.UserLogin;
import com.ymm.ymmtvcommon.pojo.Userinfo;
import com.ymm.ymmtvportal.config.Email;
import com.ymm.ymmtvportal.dao.UserDao;
import com.ymm.ymmtvportal.dao.UserinfoDao;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.thymeleaf.util.ListUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class LoginService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserinfoDao userinfoDao;

    @Autowired
    private Email email1;

    /**
     * 注册
     *
     * @param userLogin
     * @return
     */
    public Boolean registUser(UserLogin userLogin) {
        //1.判断用户传入来参入是否为空
        if (StringUtils.isEmpty(userLogin)) {
            throw new YmmException(ExceptionCode.USER_NULL);
        }
        //2.执行用户注册逻辑
        String loginAccount = userLogin.getLoginAccount();
        String email = userLogin.getEmail();
        Example example = new Example(UserLogin.class);
        example.createCriteria().andEqualTo("loginAccount", loginAccount).orEqualTo("email", email);
        List<UserLogin> userLogins = userDao.selectByExample(example);
        if (!ListUtils.isEmpty(userLogins)) {
            throw new YmmException(ExceptionCode.USER_EXIST);
        }
        String password = userLogin.getPassword();
        SimpleHash simpleHash = new SimpleHash("md5", password, loginAccount, 3);
        String newPassword = simpleHash.toHex();
        //3.更新用户信息
        userLogin.setPassword(newPassword);
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String createTime = dateFormat.format(date);
        userLogin.setCreateTime(createTime);
        userLogin.setState(true);
        //4.建立并存入用户额外信息表
        Userinfo userinfo = new Userinfo();
        userinfo.setLoginAccount(userLogin.getLoginAccount());
        userinfo.setHeadImg("img/himg/tx.jpg");
        userinfo.setRole("0");
        userinfoDao.insert(userinfo);
        //4.执行存储
        int insert = userDao.insert(userLogin);
        if (insert == 0) {
            throw new YmmException(ExceptionCode.HANDLE_FALIED);
        }

        return true;
    }

    /**
     * 远程校验邮箱是否存在
     *
     * @param email
     * @return
     */
    public Boolean remoteEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            throw new YmmException(ExceptionCode.PARAM_NULL);
        }
        Example example = new Example(UserLogin.class);
        example.createCriteria().andEqualTo("email", email);
        List<UserLogin> userLogins = userDao.selectByExample(example);
        if (!ListUtils.isEmpty(userLogins)) {
            return false;
        }
        return true;
    }

    /**
     * 远程校验账号是否存在
     *
     * @param loginaccount
     * @return
     */
    public Boolean remoteAccount(String loginaccount) {
        if (StringUtils.isEmpty(loginaccount)) {
            throw new YmmException(ExceptionCode.PARAM_NULL);
        }
        Example example = new Example(UserLogin.class);
        example.createCriteria().andEqualTo("loginAccount", loginaccount);
        List<UserLogin> userLogins = userDao.selectByExample(example);
        if (!ListUtils.isEmpty(userLogins)) {
            return false;
        }
        return true;
    }

    /**
     * 登录操作完成
     *
     * @param loginaccount
     * @param password
     * @param session
     * @param response
     * @param flag
     * @return
     */
    public void login(String loginaccount, String password, HttpSession session, HttpServletResponse response, Integer flag) {
        //1.创建登录对象
        UserLogin userLogin = new UserLogin();
        userLogin.setLoginAccount(loginaccount);
        //得到加密后的密码
        String newPassword = new SimpleHash("md5", password, loginaccount, 3).toHex();
        userLogin.setPassword(newPassword);
        //2.进行比对
        List<UserLogin> logins = userDao.select(userLogin);
        if (ListUtils.isEmpty(logins)) {
            throw new YmmException(ExceptionCode.USER_ERROR);
        }
        UserLogin user = logins.get(0);
        //3.存入session当中
        Userinfo userinfo = userinfoDao.selectByPrimaryKey(loginaccount);
        if (userinfo == null){
            throw new YmmException(ExceptionCode.HANDLE_FALIED);
        }
        userinfo.setLastLoginTime(user.getLastLogintime());
        userinfo.setEmail(user.getEmail());
        userinfo.setCreateTime(user.getCreateTime());
        session.setAttribute("userinfo", userinfo);
        //4.判断用户是否选择自动登录
        if (flag == 1) {
            //写入token
            String string = UUID.randomUUID().toString();
            String encode = Md5Hash.toString(string.getBytes());
            ;
            Cookie cookie = new Cookie("token", encode);
            cookie.setMaxAge(60 * 24 * 3);
            response.addCookie(cookie);
            //存入数据库
            userLogin.setToken(encode);
            //***一定要先创建，连续写返回的是实例对象
            Example example = new Example(UserLogin.class);
            example.createCriteria().andEqualTo("loginAccount", loginaccount);
            userDao.updateByExampleSelective(userLogin, example);
        }

    }

    /**
     * 发送邮箱验证码
     *
     * @param email
     * @return
     */
    public Boolean sendCode(String email) {
        if (StringUtils.isEmpty(email)) {
            throw new YmmException(ExceptionCode.PARAM_NULL);
        }
        //1.查询找回密码的对应用户
        Example example = new Example(UserLogin.class);
        example.createCriteria().andEqualTo("email", email);
        List<UserLogin> userLogins = userDao.selectByExample(example);
        UserLogin userLogin = userLogins.get(0);
        if (userLogin == null){
            throw new YmmException(ExceptionCode.USER_NULL);
        }
        //2.给用户创建PWD令牌并且发送重置连接
        String string = Md5Hash.toString(UUID.randomUUID().toString().getBytes());
        userLogin.setPwdToken(string);
        userDao.updateByExampleSelective(userLogin, example);
        email1.sendSimpleMail(userLogin);

        return true;
    }

    /**
     * 重置密码操作
     * @param token
     * @param password
     * @return
     */
    public Boolean resetPwd(String token, String password) {
        //1.查询需要重置密码的用户
        if (StringUtils.isEmpty(token)){
            throw new YmmException(ExceptionCode.PARAM_NULL);
        }
        Example example = new Example(UserLogin.class);
        example.createCriteria().andEqualTo("pwdToken", token);
        List<UserLogin> userLogins = userDao.selectByExample(example);
        UserLogin userLogin = userLogins.get(0);
        if (userLogin == null){
            throw new YmmException(ExceptionCode.USER_NULL);
        }
        //2.重置密码
        String newPassword = new SimpleHash("md5", password, userLogin.getLoginAccount(), 3).toHex();
        userLogin.setPassword(newPassword);
        userDao.updateByExampleSelective(userLogin, example);
        //3.删除token防止利用token无限修改密码
        userLogin.setPwdToken(null);
        userDao.updateByExample(userLogin, example);

        return true;
    }
}
