package com.mohan.comtroller;

import com.mohan.domain.dto.LinkPageDto;
import com.mohan.domain.entity.Link;
import com.mohan.service.LinkService;
import com.mohan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult pageList(LinkPageDto ld){
        ResponseResult result = linkService.pageList(ld);
        return result;
    }

    @PostMapping()
    public ResponseResult addLink(@RequestBody Link link){
        linkService.save(link);
        return ResponseResult.okResult();
    }

    @GetMapping("{id}")
    public ResponseResult getLink(@PathVariable("id") Long id){
        Link link = linkService.getById(id);
        return ResponseResult.okResult(link);
    }

    @PutMapping()
    public ResponseResult updateLink(@RequestBody Link link){
        linkService.updateById(link);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult delLink(@PathVariable("id") List<Long> ids){
        linkService.removeByIds(ids);
        return ResponseResult.okResult();
    }
}
