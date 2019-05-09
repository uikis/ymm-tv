package com.ymm.ymmtvportal;

import com.ymm.ymmtvcommon.pojo.Carousel;
import com.ymm.ymmtvportal.dao.CarouselDao;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.tomcat.util.security.MD5Encoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import sun.security.provider.MD5;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileNotFoundException;
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
    public void contextLoads() throws FileNotFoundException {

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

//        Example example = new Example(Carousel.class);
//        example.setOrderByClause("weight DESC");
//        List<Carousel> carousels = carouselDao.selectByExample(example);
//        System.out.println(carousels);

//        File path = new File(ResourceUtils.getURL("classpath:").getPath());
//        if(!path.exists()) path = new File("");
//        System.out.println("path:"+path.getAbsolutePath());
//
////如果上传目录为/static/images/upload/，则可以如下获取：
//        File upload = new File(path.getAbsolutePath(),"static/images/upload/");
//        if(!upload.exists()) upload.mkdirs();
//        System.out.println("upload url:"+upload.getAbsolutePath());

        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        System.out.println(path);
    }}
