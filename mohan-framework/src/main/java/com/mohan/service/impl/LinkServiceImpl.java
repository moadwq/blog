package com.mohan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohan.contants.SystemConstants;
import com.mohan.domain.dto.LinkDto;
import com.mohan.domain.dto.LinkPageDto;
import com.mohan.domain.entity.Link;
import com.mohan.domain.vo.PageVo;
import com.mohan.mapper.LinkMapper;
import com.mohan.service.LinkService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.ResponseResult;
import com.mohan.domain.vo.LinkVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-03-09 18:43:49
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> lingQuery = new LambdaQueryWrapper<>();
        // 查询所有审核通过的
        lingQuery.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(lingQuery);
        // 转换VO
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        // 封装返回
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult pageList(LinkPageDto ld) {
        LambdaQueryWrapper<Link> qw = new LambdaQueryWrapper<>();
        qw.like(StringUtils.hasText(ld.getName()),Link::getName,ld.getName());
        qw.like(StringUtils.hasText(ld.getStatus()),Link::getStatus,ld.getStatus());
        Page<Link> linkPage = new Page<>(ld.getPageNum(), ld.getPageSize());
        page(linkPage,qw);

        PageVo pageVo = new PageVo(linkPage.getRecords(), linkPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeLinkStatus(LinkDto linkDto) {
        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        updateById(link);
        return ResponseResult.okResult();
    }
}

