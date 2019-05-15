package com.ymm.ymmtvportal.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ymm.ymmtvcommon.exception.YmmException;
import com.ymm.ymmtvcommon.excetionEnum.ExceptionCode;
import com.ymm.ymmtvcommon.pojo.Anime;
import com.ymm.ymmtvcommon.result.PageResult;
import com.ymm.ymmtvportal.dao.AnimeDao;
import com.ymm.ymmtvportal.dao.NormalShowDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.thymeleaf.util.ListUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Transactional
@Service
public class SearchService {
    @Autowired
    private NormalShowDao normalShowDao;

    @Autowired
    private AnimeDao animeDao;

    /**
     * 分页搜索
     * @param pageNum
     * @param rows
     * @param key
     * @return
     */
    public PageResult<Anime> queryItems(int pageNum, int rows, String key) {
        //查询
        if (StringUtils.isEmpty(key)){
            throw new YmmException(ExceptionCode.PARAM_NULL);
        }
        PageHelper.startPage(pageNum, rows);
        Example example = new Example(Anime.class);
        example.createCriteria().andLike("name", "%"+key+"%");
        List<Anime> animes = animeDao.selectByExample(example);
        //分页
        PageInfo<Anime> pageInfo = new PageInfo<>(animes);
        long totalNum = pageInfo.getTotal();
        int pagesNum = pageInfo.getPages();
        List<Anime> items = pageInfo.getList();
        for (Anime item : items) {
            item.setTypeList(JSON.parseArray(item.getType(), String.class));
        }
        int cPageNum = pageInfo.getPageNum();

        return new PageResult<Anime>(items, cPageNum, pagesNum, totalNum);

    }
}
