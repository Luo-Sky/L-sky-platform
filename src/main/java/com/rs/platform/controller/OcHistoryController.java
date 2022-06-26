package com.rs.platform.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rs.platform.common.Result;
import com.rs.platform.entity.*;
import com.rs.platform.service.IOcHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
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
@RequestMapping("/ocHistorys")
public class OcHistoryController {
    @Autowired
    IOcHistoryService ocHistoryService;

    //使用Restemplate来发送HTTP请求
    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.port}")
    private String port;

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
    public Result<?> upload(MultipartFile file, @RequestParam Long projectId, @RequestParam String title) throws IOException {
        String originalFilename = file.getOriginalFilename();  // 获取源文件的名称
        String[] originFileStrArray = originalFilename.split("\\.");
        String suffix = originFileStrArray[originFileStrArray.length - 1];  //获取文件名的后缀即格式
        // 定义文件的唯一标识（前缀）
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/src/main/resources/files/" + flag + "." + suffix;  // 获取上传的路径
        FileUtil.writeBytes(file.getBytes(), rootFilePath);  // 把文件写入到上传的路径
        String resultUrl = ip + ":" + port + "/files/" + flag;
        OcHistory history = new OcHistory();
        history.setStartTime(new Date());
        history.setProjectId(projectId);
        history.setSourceImg(resultUrl);
        history.setTitle(title);

        if (ocHistoryService.save(history)) {
            return Result.success(history);  // 返回结果 url
        } else {
            return Result.error("-1", "文件上传失败");
        }
    }

    @PostMapping("/process/5")
    public Result<?> process5classes(@RequestParam Long historyId, @RequestParam String flag, @RequestBody HistoryConfig historyConfig) throws IOException {
        String basePath = System.getProperty("user.dir") + "/src/main/resources/files/";  // 定于文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);  // 获取所有的文件名称
        String fileName = basePath + fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  // 找到跟参数一致的文件

        //请求路径
        String url = ip + ":" + modelPort;

        String type = "segmentation_5classes";
        JSONObject result;
        if (historyConfig.getTop() == null) {
            result = ocHistoryService.process(historyId, url, fileName, type, historyConfig);
        }
        else{
            result = ocHistoryService.processBoxSelection(historyId, url, fileName, type, historyConfig);
        }
        if (result != null) {
            return Result.success(result);
        } else {
            return Result.error("-1", "地物分类5类失败");
        }
    }

    @PostMapping("/process/15")
    public Result<?> process15classes(@RequestParam Long historyId, @RequestParam String flag, @RequestBody HistoryConfig historyConfig) throws IOException {
        String basePath = System.getProperty("user.dir") + "/src/main/resources/files/";  // 定于文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);  // 获取所有的文件名称
        String fileName = basePath + fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  // 找到跟参数一致的文件

        //请求路径
        String url = ip + ":" + modelPort;

        String type = "segmentation_15classes";
        JSONObject result;
        if (historyConfig.getTop() == null) {
            result = ocHistoryService.process(historyId, url, fileName, type, historyConfig);
        }
        else{
            result = ocHistoryService.processBoxSelection(historyId, url, fileName, type, historyConfig);
        }
        if (result != null) {
            return Result.success(result);
        } else {
            return Result.error("-1", "地物分类15类失败");
        }
    }


    //操作记录内容修改,通过id修改名称和是否存在框选
    @PutMapping
    public Result<?> update(@RequestBody OcHistory ocHistory) {
        if (ocHistoryService.updateById(ocHistory)) {
            return Result.success();
        } else {
            return Result.error("-1", "地物分类历史记录更新失败");
        }
    }

    //删除一条地物分类任务记录
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        if (ocHistoryService.removeById(id)) {
            return Result.success();
        } else {
            return Result.error("-1", "任务记录删除失败");
        }
    }

    //找到一个
    @GetMapping("/{id}")
    public Result<?> getOne(@PathVariable Long id) {
        OcHistory res = ocHistoryService.getById(id);
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
        LambdaQueryWrapper<OcHistory> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(OcHistory::getProjectId, projectId);
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(OcHistory::getTitle, search);
        }
        Page<OcHistory> repairPage = ocHistoryService.page(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(repairPage);
    }

}
