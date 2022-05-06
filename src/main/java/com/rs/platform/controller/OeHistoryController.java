package com.rs.platform.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rs.platform.common.Result;
import com.rs.platform.entity.OcHistory;
import com.rs.platform.entity.OeHistory;
import com.rs.platform.service.IOeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * @author : hongbo
 * @create 2022-05-06-20:37
 **/
@RestController
@RequestMapping("/oeHistorys")
public class OeHistoryController {
    @Autowired
    IOeHistoryService oeHistoryService;

    @Value("${server.port}")
    private String port;
    private static final String ip = "http://localhost";

    /**
     * 创建目标提取任务，保存操作
     * @param file
     * @param projectId
     * @return
     * @throws IOException
     */
    @PostMapping
    public Result<?> upload(MultipartFile file, Long projectId) throws IOException {
        String originalFilename = file.getOriginalFilename();  // 获取源文件的名称
        // 定义文件的唯一标识（前缀）
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/src/main/resources/files/" + flag + "_" + originalFilename;  // 获取上传的路径
        FileUtil.writeBytes(file.getBytes(), rootFilePath);  // 把文件写入到上传的路径
        String resultUrl = ip + ":" + port + "/files/" + flag;
        OeHistory history = new OeHistory();
        history.setStartTime(new Date());
        history.setProjectId(projectId);
        history.setSourceImg(resultUrl);
        if(oeHistoryService.save(history)) {
            return Result.success(resultUrl);  // 返回结果 url
        }else{
            return Result.error("-1","文件上传失败");
        }
    }

//    //目标提取任务内容修改
//    @PutMapping
//    public Result<?> update(@RequestBody OeHistory oeHistory){
//        if(oeHistoryService.updateById(oeHistory)) {
//            return Result.success();
//        }else{
//            return Result.error("-1","项目更新失败");
//        }
//    }

    //删除一条目标提取任务记录
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){
        if(oeHistoryService.removeById(id)) {
            return Result.success();
        }else{
            return Result.error("-1","任务记录删除失败");
        }
    }

    //找到一个
    @GetMapping("/{id}")
    public Result<?> getOne(@PathVariable Long id){
        OeHistory res = oeHistoryService.getById(id);
        if(res == null){
            return Result.error("-1","id不存在");
        }
        return Result.success(res);
    }


    //分页展示全部
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") Long projectId){
        LambdaQueryWrapper<OeHistory> wrapper = Wrappers.<OeHistory>lambdaQuery();
        wrapper.like(OeHistory::getProjectId, projectId);

        Page<OeHistory> repairPage = oeHistoryService.page(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(repairPage);
    }
}