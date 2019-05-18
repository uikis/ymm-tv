package com.ymm.ymmtvportal.service;

import com.ymm.ymmtvcommon.exception.YmmException;
import com.ymm.ymmtvcommon.excetionEnum.ExceptionCode;
import com.ymm.ymmtvcommon.pojo.Anime;
import com.ymm.ymmtvcommon.pojo.Carousel;
import com.ymm.ymmtvcommon.pojo.NormalShow;
import com.ymm.ymmtvportal.dao.AnimeDao;
import com.ymm.ymmtvportal.dao.CarouselDao;
import com.ymm.ymmtvportal.dao.NormalShowDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class MainService {
    @Autowired
    private CarouselDao carouselDao;

    @Autowired
    private NormalShowDao normalShowDao;

    @Autowired
    private AnimeDao animeDao;

    /**
     * 查询轮播图,取权重最大的5个
     * @return
     */
    public List<Carousel> carouselGet() {
        List<Carousel> carousels = carouselDao.carouselGet();
        List<Carousel> list = carousels.subList(0, 5);
        if (ListUtils.isEmpty(list)){
            throw new YmmException(ExceptionCode.HANDLE_FALIED);
        }
        return list;
    }

    /**
     * 查询展示图，去权重最大的6个
     * @return
     */
    public List<NormalShow> portalShowGet() {
        List<NormalShow> normalShows = normalShowDao.NormalShowGet();
        List<NormalShow> list = normalShows.subList(0, 6);
        if (ListUtils.isEmpty(list)){
            throw new YmmException(ExceptionCode.HANDLE_FALIED);
        }
        return list;
    }

    /**
     * 今日热播取日播放量最大的6个
     * @return
     */
    public List<NormalShow> hostShowGet() {
        List<NormalShow> hotShows = normalShowDao.hotShowGet();
        List<NormalShow> list = hotShows.subList(0, 5);
        if (ListUtils.isEmpty(list)){
            throw new YmmException(ExceptionCode.HANDLE_FALIED);
        }
        return list;
    }

    /**
     * 查询新番更新表
     * @return
     */
    public List<NormalShow> updateShow() {
        //1.查询所有还在更新的番剧ID
        List<Anime> animes = animeDao.selectUpdateAnime();
        //2.封装返回的视图信息
        List<NormalShow> list = new ArrayList<>();
        for (Anime anime : animes) {
            NormalShow normalShow = normalShowDao.selectNormalShowById(anime.getId());
            normalShow.setUpdateTime(anime.getUpdateTime());
            list.add(normalShow);
        }
        if (ListUtils.isEmpty(list)){
            throw new YmmException(ExceptionCode.HANDLE_FALIED);
        }
        return list;
    }

    /**
     * 查询首页排行榜信息
     * @return
     */
    public List<Anime> rankShow() {
        List<Anime> list = animeDao.selectTop10();
        return list;
    }

    /**
     * 番剧推荐
     * @return
     */
    public List<NormalShow> animeShow() {
        List<NormalShow> normalShows = normalShowDao.selectAnimeTop12();
        return normalShows;
    }
}
