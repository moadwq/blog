package com.mohan;

import com.google.gson.Gson;
import com.mohan.mapper.CommentMapper;
import com.mohan.mapper.UserMapper;
import com.mohan.service.ArticleService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.io.*;

@SpringBootTest()
public class MoHanBlogApplicationTests {

    @Test
    void contextLoads() {
    }

}
