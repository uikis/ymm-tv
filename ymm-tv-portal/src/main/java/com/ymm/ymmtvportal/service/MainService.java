package com.ymm.ymmtvportal.service;

import com.ymm.ymmtvcommon.exception.YmmException;
import com.ymm.ymmtvcommon.excetionEnum.ExceptionCode;
import com.ymm.ymmtvcommon.pojo.Carousel;
import com.ymm.ymmtvcommon.pojo.NormalShow;
import com.ymm.ymmtvportal.dao.CarouselDao;
import com.ymm.ymmtvportal.dao.NormalShowDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import java.util.List;

@Service
public class MainService {
    @Autowired
    private CarouselDao carouselDao;

    @Autowired
    private NormalShowDao normalShowDao;

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
}
