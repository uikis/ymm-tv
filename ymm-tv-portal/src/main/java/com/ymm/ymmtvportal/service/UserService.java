package com.ymm.ymmtvportal.service;

import com.ymm.ymmtvcommon.exception.YmmException;
import com.ymm.ymmtvcommon.excetionEnum.ExceptionCode;
import com.ymm.ymmtvcommon.pojo.NormalShow;
import com.ymm.ymmtvcommon.pojo.UserLogin;
import com.ymm.ymmtvcommon.pojo.Userinfo;
import com.ymm.ymmtvportal.dao.NormalShowDao;
import com.ymm.ymmtvportal.dao.UserDao;
import com.ymm.ymmtvportal.dao.UserinfoDao;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserinfoDao userinfoDao;

    @Autowired
    private NormalShowDao normalShowDao;

    @Autowired
    private UserDao userDao;

    /**
     * 修改用户额外信息
     * @param userName
     * @param date
     * @param gender
     * @param signature
     * @return
     */
    public Boolean updateUserInfo(String userName, String date, String gender, String signature,
                                  HttpServletRequest request) {

        //1.获取用户信息
        HttpSession session = request.getSession();
        Userinfo userinfo = (Userinfo)session.getAttribute("userinfo");
        if (userinfo == null){
            throw new YmmException(ExceptionCode.USER_NULL);
        }
        userinfo.setUserName(userName);
        userinfo.setGender(gender);
        userinfo.setSignature(signature);
        userinfo.setBirthday(date);
        userinfoDao.updateByPrimaryKey(userinfo);
        //再更新seesion
        session.setAttribute("userinfo", userinfo);

        return true;
    }

    /**
     * 获取收藏信息
     * @return
     */
    public List<NormalShow> collectGet(HttpServletRequest request) {
        //1取得用户信息
        Userinfo userinfo = (Userinfo)request.getSession().getAttribute("userinfo");
        if (userinfo == null){
            throw new YmmException(ExceptionCode.USER_NULL);
        }
        String loginAccount = userinfo.getLoginAccount();
        List<NormalShow> normalShows = normalShowDao.selectCollectAnime(loginAccount);

        return normalShows;
    }

    /**
     * 上传用户头像
     * @param file
     */
    public String updateHeadImg(MultipartFile file, HttpServletRequest request) throws IOException {
        //1取得用户信息
        Userinfo userinfo = (Userinfo)request.getSession().getAttribute("userinfo");
        if (userinfo == null){
            throw new YmmException(ExceptionCode.USER_NULL);
        }
        //2.上传文件
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        String path1 = path.replace("target/classes/", "src/main/resources/");
        String string = UUID.randomUUID().toString();
        int i = file.getOriginalFilename().lastIndexOf(".");
        String substring = file.getOriginalFilename().substring(i);

        if (!(".jpg".equals(substring)) && !(".png".equals(substring))){
            throw new YmmException(ExceptionCode.IMG_ERROR);
        }
        File file1 = new File(path1 + "/static/img/himg/" + string + substring);
        file.transferTo(file1);
        String newPath = "img/himg/"+ string + substring;
        userinfo.setHeadImg(newPath);
        request.getSession().setAttribute("userinfo", userinfo);
        userinfoDao.updateByPrimaryKey(userinfo);

        return newPath;
    }

    /**
     * 返回用户的展示信息
     * @return
     */
    public Userinfo userinfoGet(HttpServletRequest request) {
        //1取得用户信息
        Userinfo userinfo = (Userinfo)request.getSession().getAttribute("userinfo");
        if (userinfo == null){
            throw new YmmException(ExceptionCode.USER_NULL);
        }

        return userinfo;
    }

    /**
     * 个人页面重置密码
     * @param password
     * @return
     */
    public Boolean resetPwd(HttpServletRequest request, String password) {
        //1取得用户信息
        Userinfo userinfo = (Userinfo)request.getSession().getAttribute("userinfo");
        UserLogin userLogin = new UserLogin();
        userLogin.setLoginAccount(userinfo.getLoginAccount());
        List<UserLogin> select = userDao.select(userLogin);
        if (select == null){
            throw new YmmException(ExceptionCode.HANDLE_FALIED);
        }
        UserLogin userLogin1 = select.get(0);
        String md5 = new SimpleHash("md5", password, userinfo.getLoginAccount(), 3).toHex();
        userLogin1.setPassword(md5);
        userDao.updateByPrimaryKey(userLogin1);

        return true;
    }
}
