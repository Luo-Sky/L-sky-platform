package com.rs.platform.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rs.platform.common.Result;
import com.rs.platform.entity.EeHistory;
import com.rs.platform.entity.HistoryConfig;
import com.rs.platform.service.IEeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/eeHistorys")
public class EeHistoryController {
    @Autowired
    IEeHistoryService eeHistoryService;

    //使用RestTemplate来发送HTTP请求
    @Autowired
    private RestTemplate restTemplate;

    @Value("${myconf.ip}")
    private String ip;

    @Value("${myconf.port}")
    private String port;

    @Value("${model.ip}")
    private String modelIp;

    @Value("${model.port}")
    private String modelPort;

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
        EeHistory history = new EeHistory();
        history.setStartTime(new Date());
        history.setProjectId(projectId);
        history.setSourceImg(resultUrl);
        history.setSourceImgName(originalFilename);
        history.setTitle(title);

        if (eeHistoryService.save(history)) {
            return Result.success(history);  // 返回结果 url
        } else {
            return Result.error("-1", "文件上传失败");
        }
    }

    @PostMapping("/process")
    public Result<?> process(@RequestParam Long historyId, @RequestParam String flag, @RequestParam(defaultValue = "0") Integer batch, @RequestBody HistoryConfig historyConfig) throws IOException {
        String basePath = System.getProperty("user.dir") + "/src/main/resources/files/";  // 定于文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);  // 获取所有的文件名称
        String fileName = basePath + fileNames.stream().filter(name -> name.contains(flag) && name.charAt(flag.length())=='.').findAny().orElse("");;  // 找到跟参数一致的文件

        //请求路径
        String url = modelIp + ":" + modelPort;

        String type = "elevation_estimates";
        JSONObject result;
        if (batch == 1) {
            result = eeHistoryService.processBoxSelection(historyId, url, fileName, type, historyConfig);
        } else {
            if (historyConfig.getTop() == null) {
                result = eeHistoryService.process(historyId, url, fileName, type, historyConfig);
            } else {
                result = eeHistoryService.processBoxSelection(historyId, url, fileName, type, historyConfig);
            }
        }
        if (result != null) {
            return Result.success(result);
        } else {
            return Result.error("-1", "高程估计失败");
        }
    }

    //操作记录内容修改,通过id修改名称和是否存在框选
    @PutMapping
    public Result<?> update(@RequestBody EeHistory eeHistory) {
        if (eeHistoryService.updateById(eeHistory)) {
            return Result.success();
        } else {
            return Result.error("-1", "地物分类历史记录更新失败");
        }
    }

    //删除一条地物分类任务记录
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        if (eeHistoryService.removeById(id)) {
            return Result.success();
        } else {
            return Result.error("-1", "任务记录删除失败");
        }
    }

    //找到一个
    @GetMapping("/{id}")
    public Result<?> getOne(@PathVariable Long id) {
        EeHistory res = eeHistoryService.getById(id);
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
        LambdaQueryWrapper<EeHistory> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(EeHistory::getProjectId, projectId);
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(EeHistory::getTitle, search);
        }
        Page<EeHistory> eePage = eeHistoryService.page(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(eePage);
    }

}
