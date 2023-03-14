package com.mohan.comtroller;

import com.mohan.domain.dto.MenuDto;
import com.mohan.domain.entity.Menu;
import com.mohan.service.MenuService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public ResponseResult list(MenuDto menuDto){
        ResponseResult result = menuService.likeList(menuDto);
        return result;
    }

    @PostMapping()
    public ResponseResult addMenu(@RequestBody Menu menu){
        System.err.println(menu);
        menuService.save(menu);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult getMenu(@PathVariable("id") Long id){
        Menu menu = menuService.getById(id);
        return ResponseResult.okResult(menu);
    }

    @PutMapping()
    public ResponseResult updateMenu(@RequestBody Menu menu){
        ResponseResult result = menuService.updateMenu(menu);
        return result;
    }

}
