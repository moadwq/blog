package com.mohan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mohan.entity.Link;
import com.mohan.utils.ResponseResult;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-03-09 18:43:49
 */
public interface LinkService extends IService<Link> {

    /**
     * 获取全部友链
     */
    ResponseResult getAllLink();
}
