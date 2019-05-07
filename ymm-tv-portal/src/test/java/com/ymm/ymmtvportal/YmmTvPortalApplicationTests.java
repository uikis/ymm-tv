package com.ymm.ymmtvportal;

import com.ymm.ymmtvcommon.pojo.Carousel;
import com.ymm.ymmtvportal.dao.CarouselDao;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.tomcat.util.security.MD5Encoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.security.provider.MD5;
import tk.mybatis.mapper.entity.Example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YmmTvPortalApplicationTests {

    @Autowired
    private CarouselDao carouselDao;
    @Test
    public void contextLoads() {

//        Date date = new Date();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String format = dateFormat.format(date);
//        System.out.println(format);
//
//        SimpleHash simpleHash = new SimpleHash("md5", "970925mm", "964106443", 3);
//        String newPassword = simpleHash.toHex();
//        System.out.println(newPassword);
//
//        String string = UUID.randomUUID().toString();
//        String toString = Md5Hash.toString(string.getBytes());
//        System.out.println(toString);

        Example example = new Example(Carousel.class);
        example.setOrderByClause("weight DESC");
        List<Carousel> carousels = carouselDao.selectByExample(example);
        System.out.println(carousels);
    }

}
