package com.rs.platform.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rs.platform.common.Result;
import com.rs.platform.entity.BoxSelection;
import com.rs.platform.service.IBoxSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : hongbo
 * @create 2022-06-24-17:53
 **/

@RestController
@RequestMapping("/boxSelections")
public class BoxSelectionController {
    @Autowired
    IBoxSelectionService boxSelectionService;

    //找到一个
    @GetMapping("/{id}")
    public Result<?> getOne(@PathVariable Long id) {
        BoxSelection res = boxSelectionService.getById(id);
        if (res == null) {
            return Result.error("-1", "框选的记录id不存在");
        }
        return Result.success(res);
    }

    //删除所有框选
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long historyid) {
        LambdaQueryWrapper<BoxSelection> wrapper = Wrappers.<BoxSelection>lambdaQuery();
        wrapper.eq(BoxSelection::getHistoryId, historyid);
        if (boxSelectionService.remove(wrapper)) {
            return Result.success();
        } else {
            return Result.error("-1", "删除全部框选失败");
        }
    }


    //分页展示全部
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") Long historyId,
                              @RequestParam(defaultValue = "") Integer type) {
        LambdaQueryWrapper<BoxSelection> wrapper = Wrappers.<BoxSelection>lambdaQuery();
        wrapper.eq(BoxSelection::getHistoryId, historyId);
        wrapper.eq(BoxSelection::getType, type);
        Page<BoxSelection> res = boxSelectionService.page(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(res);
    }
}
