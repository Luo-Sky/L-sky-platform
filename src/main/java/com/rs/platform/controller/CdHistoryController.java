package com.rs.platform.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rs.platform.common.Result;
import com.rs.platform.entity.CdHistory;
import com.rs.platform.service.ICdHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

/**
 * @author : hongbo
 * @create 2022-05-06-20:37
 **/
@RestController
@RequestMapping("/cdHistorys")
public class CdHistoryController {
    @Autowired
    ICdHistoryService cdHistoryService;

    @Value("${server.port}")
    private String port;

    @Value("${myconf.ip}")
    private  String ip;

    /**
     * 创建目标提取任务，保存操作
     * @param file
     * @param projectId
     * @return
     * @throws IOException
     */
    @PostMapping
    public Result<?> upload(MultipartFile file1, MultipartFile file2, Long projectId) throws IOException {
        String originalFilename1 = file1.getOriginalFilename();  // 获取源文件的名称
        String originalFilename2 = file2.getOriginalFilename();  // 获取源文件的名称
        // 定义文件的唯一标识（前缀）
        String flag1 = IdUtil.fastSimpleUUID();
        String flag2 = IdUtil.fastSimpleUUID();
        String rootFilePath1 = System.getProperty("user.dir") + "/src/main/resources/files/" + flag1;  // 获取上传的路径
        String rootFilePath2 = System.getProperty("user.dir") + "/src/main/resources/files/" + flag2;  // 获取上传的路径

        FileUtil.writeBytes(file1.getBytes(), rootFilePath1);  // 把文件写入到上传的路径
        FileUtil.writeBytes(file1.getBytes(), rootFilePath2);  // 把文件写入到上传的路径
        String resultUrl1 = ip + ":" + port + "/files/" + flag1;
        String resultUrl2 = ip + ":" + port + "/files/" + flag2;

        CdHistory history = new CdHistory();
        history.setStartTime(new Date());
        history.setProjectId(projectId);
        history.setSourceImg1(resultUrl1);
        history.setSourceImg2(resultUrl2);
        if(cdHistoryService.save(history)) {
            return Result.success("任务创建成功");  // 返回结果 url
        }else{
            return Result.error("-1","文件上传失败");
        }
    }


    //删除一条变化检测任务记录
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){
        if(cdHistoryService.removeById(id)) {
            return Result.success();
        }else{
            return Result.error("-1","任务记录删除失败");
        }
    }

    //找到一个
    @GetMapping("/{id}")
    public Result<?> getOne(@PathVariable Long id){
        CdHistory res = cdHistoryService.getById(id);
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
        LambdaQueryWrapper<CdHistory> wrapper = Wrappers.lambdaQuery();
        wrapper.like(CdHistory::getProjectId, projectId);

        Page<CdHistory> repairPage = cdHistoryService.page(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(repairPage);
    }
}
