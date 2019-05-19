package com.ymm.ymmtvportal.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ymm.ymmtvcommon.exception.YmmException;
import com.ymm.ymmtvcommon.excetionEnum.ExceptionCode;
import com.ymm.ymmtvcommon.pojo.*;
import com.ymm.ymmtvcommon.result.PageResult;
import com.ymm.ymmtvportal.dao.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.ListUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private AnimeDao animeDao;
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

    /**
     * 分页查询评论
     * @param pageNum
     * @param rows
     * @param request
     * @return
     */
    public PageResult<Comment> pageQueryComment(int pageNum, int rows, HttpServletRequest request) {
        //1取得用户信息
        Userinfo userinfo = (Userinfo)request.getSession().getAttribute("userinfo");
        //2.取得评论
        PageHelper.startPage(pageNum, rows);
        Comment comment = new Comment();
        comment.setLoginAccount(userinfo.getLoginAccount());
//        comment.setLoginAccount("964106443");
        List<Comment> comments = commentDao.select(comment);
        //获得评论的番剧名称
        for (Comment comment1 : comments) {
            Anime anime = animeDao.selectByPrimaryKey(comment1.getAnimeId());
            comment1.setAnimeName(anime.getName());
        }
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
        List<Comment> items = pageInfo.getList();
        int cPageNum = pageInfo.getPageNum();
        int pagesNum = pageInfo.getPages();
        long totalNum = pageInfo.getTotal();
        return new PageResult<Comment>(items, cPageNum, pagesNum, totalNum);
    }

    /**
     * 批量删除评论
     * @param ids
     */
    public void commentsDelete(List<Integer> ids) {
        if (ListUtils.isEmpty(ids)){
            throw new YmmException(ExceptionCode.PARAM_NULL);
        }
        commentDao.deleteByIdList(ids);
    }

    /**
     * 退出登录
     */
    public void loginOut(HttpServletRequest request) {
        //更新最后登录的时间
        HttpSession session = request.getSession();
        Userinfo userinfo = (Userinfo)session.getAttribute("userinfo");
        UserLogin userLogin = new UserLogin();
        userLogin.setLoginAccount(userinfo.getLoginAccount());
        UserLogin user = userDao.select(userLogin).get(0);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = sdf.format(date);
        user.setLastLogintime(format);
        userDao.updateByPrimaryKey(user);
        session.removeAttribute("userinfo");
    }

    /**
     * 删除收藏
     * @param request
     * @param id
     */
    public void cancelCollect(HttpServletRequest request, Integer id) {
        HttpSession session = request.getSession();
        Userinfo userinfo = (Userinfo)session.getAttribute("userinfo");
        String loginAccount = userinfo.getLoginAccount();
        int i = userDao.cancelCollect(loginAccount, id);
        if (i <= 0){
            throw new YmmException(ExceptionCode.HANDLE_FALIED);
        }
    }
}
