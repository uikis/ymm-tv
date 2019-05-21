package com.ymm.ymmtvportal.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ymm.ymmtvcommon.exception.YmmException;
import com.ymm.ymmtvcommon.excetionEnum.ExceptionCode;
import com.ymm.ymmtvcommon.pojo.Anime;
import com.ymm.ymmtvcommon.pojo.NormalShow;
import com.ymm.ymmtvcommon.pojo.SearchRequest;
import com.ymm.ymmtvcommon.pojo.Type;
import com.ymm.ymmtvcommon.result.PageResult;
import com.ymm.ymmtvportal.dao.AnimeDao;
import com.ymm.ymmtvportal.dao.TypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.thymeleaf.util.ListUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class SearchService {

    @Autowired
    private AnimeDao animeDao;

    @Autowired
    private TypeDao typeDao;

    /**
     * 分页搜索
     *
     * @param pageNum
     * @param rows
     * @param key
     * @return
     */
    public PageResult<Anime> queryItems(int pageNum, int rows, String key) {
        //查询
        if (StringUtils.isEmpty(key)) {
            throw new YmmException(ExceptionCode.PARAM_NULL);
        }
        PageHelper.startPage(pageNum, rows);
        Example example = new Example(Anime.class);
        example.createCriteria().andLike("name", "%" + key + "%");
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

    /**
     * 番剧门户页面
     *
     * @param request
     * @return
     */
    public PageResult<Anime> animeGet(SearchRequest request) {
        if (StringUtils.isEmpty(request.getPageNum())) {
            request.setPageNum(1);
        }
        if (StringUtils.isEmpty(request.getRows())) {
            request.setRows(12);
        }
        if (StringUtils.isEmpty(request.getOrder())) {
            request.setOrder("play_num desc");
        }
        //1.先分页
        List<Integer> list = new ArrayList<>();
        List<Anime> animes = new ArrayList<>();
        List<Anime> result = new ArrayList<>();
        Example example = new Example(Anime.class);

        //2.分类过滤
        //地区过滤
        if (!StringUtils.isEmpty(request.getRegion()) && StringUtils.isEmpty(request.getYear())) {
            example.createCriteria().andEqualTo("region", request.getRegion());
        }
        //年份过滤
        if (!StringUtils.isEmpty(request.getYear()) && StringUtils.isEmpty(request.getRegion())) {
            String year = request.getYear();
            example.createCriteria().andEqualTo("year", year);
        }
        if (!StringUtils.isEmpty(request.getYear()) && !StringUtils.isEmpty(request.getRegion())) {
            String year = request.getYear();
            example.createCriteria().andEqualTo("year", year).andEqualTo("region", request.getRegion());
        }
        //类型过滤
        if (!StringUtils.isEmpty(request.getType())) {
            String type = request.getType();
            if (StringUtils.isEmpty(request.getYear()) && StringUtils.isEmpty(request.getRegion())) {
                animes = animeDao.selectAll();

            } else {
                animes = animeDao.selectByExample(example);
            }
            for (Anime anime : animes) {
                List<String> types = JSON.parseArray(anime.getType(), String.class);
                if (!ListUtils.isEmpty(types)) {
                    if (types.contains(type)) {
                        list.add(anime.getId());
                    }
                }
            }
            //list用listutils判断
            if (!ListUtils.isEmpty(list)){
                PageHelper.startPage(request.getPageNum(), request.getRows(), request.getOrder());
                result = animeDao.selectByIdList(list);
            }else{
                return new PageResult<Anime>(null, 0, 1, 0);
            }
        } else {
            PageHelper.startPage(request.getPageNum(), request.getRows(), request.getOrder());
            result = animeDao.selectByExample(example);
        }
        PageInfo<Anime> pageInfo = new PageInfo<>(result);
        //2.先判断是否有筛选条件
        //返回结果
        long totalNum = pageInfo.getTotal();
        int pagesNum = pageInfo.getPages();
        int cPageNum = pageInfo.getPageNum();
        if (pagesNum == 0) {
            pagesNum = 1;
        }
        List<Anime> items = pageInfo.getList();

        return new PageResult<>(items, cPageNum, pagesNum, totalNum);
    }

    /**
     * 查询分类列表
     *
     * @return
     */
    public List<Type> typeGet() {
        List<Type> types = typeDao.selectAll();
        return types;
    }

}
