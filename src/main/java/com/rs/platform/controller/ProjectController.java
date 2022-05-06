package com.rs.platform.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rs.platform.common.Result;
import com.rs.platform.entity.Project;
import com.rs.platform.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : hongbo
 * @create 2022-05-06-20:26
 **/

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    IProjectService projectService;

    //创建项目，保存操作
    @PostMapping
    public Result<?> save(@RequestBody Project project){
        if(projectService.save(project)){
            return Result.success();
        }else{
            return Result.error("-1","保存失败");
        }
    }

    //项目内容修改,通过id
    @PutMapping
    public Result<?> update(@RequestBody Project project){
        if(projectService.updateById(project)) {
            return Result.success();
        }else{
            return Result.error("-1","项目更新失败");
        }
    }

    //删除项目
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){
        if(projectService.removeById(id)) {
            return Result.success();
        }else{
            return Result.error("-1","项目删除失败");
        }
    }

    //找到一个
    @GetMapping("/{id}")
    public Result<?> getOne(@PathVariable Long id){
        Project res = projectService.getById(id);
        if(res == null){
            return Result.error("-1","项目id不存在");
        }
        return Result.success(res);
    }


    //分页展示全部
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") Long userId,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Project> wrapper = Wrappers.<Project>lambdaQuery();
        wrapper.like(Project::getUserId, userId);
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Project::getTitle, search);
            wrapper.like(Project::getDescription, search);
        }

        Page<Project> repairPage = projectService.page(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(repairPage);
    }
}
