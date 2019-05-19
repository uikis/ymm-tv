package com.ymm.ymmtvportal.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ymm.ymmtvcommon.exception.YmmException;
import com.ymm.ymmtvcommon.excetionEnum.ExceptionCode;
import com.ymm.ymmtvcommon.pojo.*;
import com.ymm.ymmtvcommon.result.PageResult;
import com.ymm.ymmtvportal.dao.AnimeDao;
import com.ymm.ymmtvportal.dao.CommentDao;
import com.ymm.ymmtvportal.dao.UserCollectDao;
import com.ymm.ymmtvportal.dao.UserinfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AnimeService {

    @Autowired
    private AnimeDao animeDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserinfoDao userinfoDao;

    @Autowired
    private UserCollectDao userCollectDao;

    /**
     * 番剧详情页面
     *
     * @param id
     * @return
     */
    public Anime animeDetail(Integer id) {
        if (id == null) {
            throw new YmmException(ExceptionCode.PARAM_NULL);
        }
        Anime anime = animeDao.selectByPrimaryKey(id);
        if (anime == null) {
            throw new YmmException(ExceptionCode.ANIME_CANNOT_FIND);
        }

        anime.setTypeList(JSON.parseArray(anime.getType(), String.class));
        return anime;
    }

    /**
     * 查找番剧对应的评论
     *
     * @param pageNum
     * @param rows
     * @param id
     * @return
     */
    public PageResult<Comment> commentGet(int pageNum, int rows, Integer id) {
        PageHelper.startPage(pageNum, rows);
        if (id == null) {
            throw new YmmException(ExceptionCode.PARAM_NULL);
        }
//        Comment comment = new Comment();
//        comment.setAnimeId(id);
        Example example = new Example(Comment.class);
        example.setOrderByClause("comment_time desc");
        example.createCriteria().andEqualTo("animeId", id);
        //加入显示内容
        List<Comment> comments = commentDao.selectByExample(example);
        for (Comment comment : comments) {
            //注意是否有账号
            Userinfo userinfo = userinfoDao.selectByPrimaryKey(comment.getLoginAccount());
            comment.setUserHeadImg(userinfo.getHeadImg());
            comment.setUserName(userinfo.getUserName());
        }
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
        long totalNum = pageInfo.getTotal();
        int pagesNum = pageInfo.getPages();
        List<Comment> items = pageInfo.getList();
        int cPageNum = pageInfo.getPageNum();

        return new PageResult<>(items, cPageNum, pagesNum, totalNum);
    }

    /**
     * 评论
     * @param request
     * @param id
     * @return
     */
    public Boolean commentAdd(HttpServletRequest request, Integer id, String comment) {
        //取得用户信息
        Userinfo userinfo = (Userinfo)request.getSession().getAttribute("userinfo");
        if (userinfo == null){
            return false;
        }
        //设置评论的相关操作
        Comment comment1 = new Comment();
        comment1.setComment(comment);
        comment1.setAnimeId(id);
        comment1.setAgree("0");
        comment1.setLoginAccount(userinfo.getLoginAccount());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = sdf.format(date);
        comment1.setCommentTime(format);
        int insert = commentDao.insert(comment1);
        if (insert <= 0){
            throw new YmmException(ExceptionCode.HANDLE_FALIED);
        }

        return true;
    }

    /**
     * 判断是否点赞过
     * @param request
     * @return
     */
    public List<Integer> commentCheck(HttpServletRequest request) {
        //取得用户信息
        Userinfo userinfo = (Userinfo)request.getSession().getAttribute("userinfo");
        if (userinfo == null){
            return null;
        }
        String account = userinfo.getLoginAccount();
        List<Integer> list = commentDao.selectCommented(account);

        return list;
    }

    /**
     *
     * @param request
     * @param id
     * @return
     */
    public Boolean commentAgree(HttpServletRequest request, Integer id) {
        Userinfo userinfo = (Userinfo)request.getSession().getAttribute("userinfo");
        if (userinfo == null){
            throw new YmmException(ExceptionCode.USER_NULL);
        }
        String loginAccount = userinfo.getLoginAccount();
        Comment comment = commentDao.selectByPrimaryKey(id);
        String agree = comment.getAgree();
        int newAgree = Integer.parseInt(agree) + 1;
        String num = newAgree + "";
        comment.setAgree(num);
        commentDao.updateByPrimaryKey(comment);
        int i = commentDao.addAgree(id, loginAccount);
        if (i != 0){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 播放对应集
     * @param id
     * @param cid
     * @return
     */
    public Anime animePlay(Integer id, Integer cid) {
        if (id == null || cid == null) {
            throw new YmmException(ExceptionCode.PARAM_NULL);
        }
        Anime anime = animeDao.selectByPrimaryKey(id);
        if (anime == null) {
            throw new YmmException(ExceptionCode.ANIME_CANNOT_FIND);
        }

        String path = animeDao.selectPlayPath(id, cid);
        if (path == null){
            throw new YmmException(ExceptionCode.ANIME_CANNOT_FIND);
        }
        anime.setPlayPath(path);
        return anime;
    }

    /**
     * 添加收藏
     *
     * @param request
     * @param id
     * @return
     */
    public Boolean addCollect(HttpServletRequest request, Integer id) {
        //判断是否登录
        HttpSession session = request.getSession();
        Userinfo userinfo = (Userinfo)session.getAttribute("userinfo");
        if (userinfo == null){
            throw new YmmException(ExceptionCode.HANDLE_FALIED);
        }
        String loginAccount = userinfo.getLoginAccount();
        UserCollect userCollect = new UserCollect();
        userCollect.setAnimeId(id);
        userCollect.setLoginAccount(loginAccount);
        List<UserCollect> select = userCollectDao.select(userCollect);
        if (!ListUtils.isEmpty(select)){
            return false;
        }else {
            int i = userCollectDao.insert(userCollect);
            Anime anime = animeDao.selectByPrimaryKey(id);
            if (anime.getCollectnum() == null){
                anime.setCollectnum(0);
            }
            Integer collectnum = anime.getCollectnum() + 1;
            anime.setCollectnum(collectnum);
            animeDao.updateByPrimaryKey(anime);
            return true;
        }
    }

    /**
     * 增加播放量
     * @param id
     */
    public void animeAddPlay(Integer id) {
        Anime anime = animeDao.selectByPrimaryKey(id);
        if (anime.getPlayNum() == null){
            anime.setPlayNum(0);
        }
        anime.setPlayNum(anime.getPlayNum()+1);
        if (anime.getTotalPnum() == null){
            anime.setTotalPnum(0);
        }
        anime.setTotalPnum(anime.getTotalPnum()+1);
        animeDao.updateByPrimaryKey(anime);
    }
}
