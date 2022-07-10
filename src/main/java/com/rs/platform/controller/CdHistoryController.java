package com.rs.platform.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rs.platform.common.Result;
import com.rs.platform.entity.CdHistory;
import com.rs.platform.entity.HistoryConfig;
import com.rs.platform.entity.OdHistory;
import com.rs.platform.entity.Project;
import com.rs.platform.service.ICdHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author : hongbo
 * @create 2022-05-06-20:37
 **/
@RestController
@RequestMapping("/cdHistorys")
public class CdHistoryController {
    @Autowired
    ICdHistoryService cdHistoryService;

    //使用Restemplate来发送HTTP请求
    @Autowired
    private RestTemplate restTemplate;

    @Value("${myconf.port}")
    private String port;

    @Value("${model.ip}")
    private String modelIp;

    @Value("${model.port}")
    private String modelPort;

    @Value("${myconf.ip}")
    private String ip;

    /**
     * 创建目标提取任务，保存操作
     *
     * @param file
     * @param projectId
     * @return
     * @throws IOException
     */
    @PostMapping
    public Result<?> upload(MultipartFile file1, MultipartFile file2, @RequestParam Long projectId, @RequestParam String title) throws IOException {
        String originalFilename1 = file1.getOriginalFilename();  // 获取源文件的名称
        String originalFilename2 = file2.getOriginalFilename();  // 获取源文件的名称
        String[] originFileStrArray1 = originalFilename1.split("\\.");
        String[] originFileStrArray2 = originalFilename2.split("\\.");
        String suffix1 = originFileStrArray1[originFileStrArray1.length - 1];  //获取文件名的后缀即格式
        String suffix2 = originFileStrArray2[originFileStrArray2.length - 1];  //获取文件名的后缀即格式

        // 定义文件的唯一标识（前缀）
        String flag1 = IdUtil.fastSimpleUUID();
        String flag2 = IdUtil.fastSimpleUUID();
        String rootFilePath1 = System.getProperty("user.dir") + "/src/main/resources/files/" + flag1 + "." + suffix1;  // 获取上传的路径
        String rootFilePath2 = System.getProperty("user.dir") + "/src/main/resources/files/" + flag2 + "." + suffix2;  // 获取上传的路径

        FileUtil.writeBytes(file1.getBytes(), rootFilePath1);  // 把文件写入到上传的路径
        FileUtil.writeBytes(file2.getBytes(), rootFilePath2);  // 把文件写入到上传的路径
        String resultUrl1 = ip + ":" + port + "/files/" + flag1;
        String resultUrl2 = ip + ":" + port + "/files/" + flag2;

        CdHistory history = new CdHistory();
        history.setStartTime(new Date());
        history.setProjectId(projectId);
        history.setSourceImg1(resultUrl1);
        history.setSourceImg2(resultUrl2);
        history.setTitle(title);
//        Map<String, String> urls = new HashMap<>();
//        urls.put("img1", resultUrl1);
//        urls.put("img2", resultUrl2);
        if (cdHistoryService.save(history)) {
            return Result.success(history);  // 返回结果 url
        } else {
            return Result.error("-1", "文件上传失败");
        }
    }

    @PostMapping("/process")
    public Result<?> process(@RequestParam Long historyId, @RequestParam String flag1, @RequestParam String flag2, @RequestBody HistoryConfig historyConfig) throws IOException {
        String basePath = System.getProperty("user.dir") + "/src/main/resources/files/";  // 定于文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);  // 获取所有的文件名称
        String fileName1 = basePath + fileNames.stream().filter(name -> name.contains(flag1)).findAny().orElse("");  // 找到跟参数一致的文件
        String fileName2 = basePath + fileNames.stream().filter(name -> name.contains(flag2)).findAny().orElse("");  // 找到跟参数一致的文件

        //请求路径
        String url =  modelIp + ":" + modelPort;

        String type = "change_detection";

        JSONObject result;
        if (historyConfig.getTop() == null) {
            result = cdHistoryService.process(historyId, url, fileName1, fileName2, type, historyConfig);
        } else {
            result = cdHistoryService.processBoxSelection(historyId, url, fileName1, fileName2, type, historyConfig);
        }
        if (result != null) {
            return Result.success(result);
        } else {
            return Result.error("-1", "变化检测失败");
        }
    }


    //操作记录内容修改,通过id修改名称和是否存在框选
    @PutMapping
    public Result<?> update(@RequestBody CdHistory cdHistory) {
        if (cdHistoryService.updateById(cdHistory)) {
            return Result.success();
        } else {
            return Result.error("-1", "变化检测历史记录更新失败");
        }
    }

    //删除一条变化检测任务记录
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        if (cdHistoryService.removeById(id)) {
            return Result.success();
        } else {
            return Result.error("-1", "任务记录删除失败");
        }
    }

    //找到一个
    @GetMapping("/{id}")
    public Result<?> getOne(@PathVariable Long id) {
        CdHistory res = cdHistoryService.getById(id);
        if (res == null) {
            return Result.error("-1", "id不存在");
        }
        return Result.success(res);
    }


    //分页展示全部
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") Long projectId,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<CdHistory> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(CdHistory::getProjectId, projectId);
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(CdHistory::getTitle, search);
        }
        Page<CdHistory> repairPage = cdHistoryService.page(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(repairPage);
    }
}
