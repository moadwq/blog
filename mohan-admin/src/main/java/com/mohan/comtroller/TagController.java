package com.mohan.comtroller;

import com.mohan.domain.entity.Tag;
import com.mohan.service.TagService;
import com.mohan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult list(){
        List<Tag> list = tagService.list();
        return ResponseResult.okResult(list);
    }

}
