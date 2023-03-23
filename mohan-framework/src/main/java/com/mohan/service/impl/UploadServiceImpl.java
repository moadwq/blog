package com.mohan.service.impl;

import com.google.gson.Gson;
import com.mohan.annotation.SystemLog;
import com.mohan.contants.SystemConstants;
import com.mohan.enums.AppHttpCodeEnum;
import com.mohan.exception.SystemException;
import com.mohan.service.UploadService;
import com.mohan.utils.FilePathUtil;
import com.mohan.utils.RedisCache;
import com.mohan.utils.ResponseResult;
import com.mohan.utils.SecurityUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Data
@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService {
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult img(MultipartFile img) {
        // 判断用户今天是否已经上传过头像
        String val = redisCache.getCacheObject("uploadTimes:" + SystemConstants.userId);
        if (StringUtils.hasText(val)){
            throw new SystemException(AppHttpCodeEnum.UPLOAD_TIMES);
        }
        // 获取原始文件名
        String filename = img.getOriginalFilename();
        // 文件类型判断
        for (String type : SystemConstants.IMG_TYPE) {
            String fileType = filename.substring(filename.lastIndexOf(".") + 1);
            if (type.equals(fileType)){
                // 生成文件路径
                String filePath = FilePathUtil.generateFilePath(filename);
                // 上传文件到oss
                String url = uploadImgToOss(img,filePath);
                // 每天只能上传一次头像，在redis中存一个标记
                uploadTimes();
                return ResponseResult.okResult(url);
            }
        }
        throw new SystemException(AppHttpCodeEnum.FILETYPE_ERROR);
    }

    // 设置一个当天24时过期的键
    public void uploadTimes(){
        redisCache.setCacheObject("uploadTimes:" + SystemConstants.userId,SystemConstants.userId,getSeconds(), TimeUnit.SECONDS);
    }

    // 获得现在距凌晨0点还有多少秒
    private Integer getSeconds(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String format = sdf.format(date);

        String[] split = format.split(":");
        int h = Integer.parseInt(split[0]) * 60 * 60;
        int m = Integer.parseInt(split[1])*60;
        int s = Integer.parseInt(split[2]);
        return 24*60*60 - (h+m+s);
    }


    // 上传凭证
    private String accessKey;
    private String secretKey;
    private String bucket;
    // 访问域名
    private String url;
    /**
     * 图片上传到七牛云oss
     */
    private String uploadImgToOss(MultipartFile img, String filePath) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);

        //指定上传文件的存放路径和名称
        String key = filePath;

        try {
            InputStream inputStream = img.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

                System.err.println(putRet.key);
                System.err.println(putRet.hash);

                return url + key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "failed";
    }


}
