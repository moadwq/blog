package com.mohan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mohan.domain.dto.LinkDto;
import com.mohan.domain.dto.LinkPageDto;
import com.mohan.domain.entity.Link;
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

    /**
     * 分页模糊查询全部友链
     */
    ResponseResult pageList(LinkPageDto ld);

    /**
     * 修改友链状态
     */
    ResponseResult changeLinkStatus(LinkDto linkDto);
}
