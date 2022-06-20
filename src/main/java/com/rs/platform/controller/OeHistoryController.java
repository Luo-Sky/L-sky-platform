package com.rs.platform.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rs.platform.common.Result;
import com.rs.platform.entity.OeHistory;
import com.rs.platform.service.IOeHistoryService;
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
@RequestMapping("/oeHistorys")
public class OeHistoryController {
    @Autowired
    IOeHistoryService oeHistoryService;

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
        OeHistory history = new OeHistory();
        history.setStartTime(new Date());
        history.setProjectId(projectId);
        history.setSourceImg(resultUrl);
        history.setTitle(title);
        if (oeHistoryService.save(history)) {
            return Result.success(history);  // 返回结果 url
        } else {
            return Result.error("-1", "文件上传失败");
        }
    }

    @PostMapping("/process")
    public Result<?> process(@RequestParam Long historyId, @RequestParam String flag) throws IOException {
        String basePath = System.getProperty("user.dir") + "/src/main/resources/files/";  // 定于文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);  // 获取所有的文件名称
        String fileName = basePath + fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  // 找到跟参数一致的文件//请求路径

        String url = ip + ":" + modelPort;

        // LinkedMultiValueMap 有点像JSON，用于传递post数据，网络上其他教程都使用
        // MultiValueMpat<>来传递post数据
        // 但传递的数据类型有限，不能像这个这么灵活，可以传递多种不同数据类型的参数
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap();
        body.add("type", "road_extraction");
        body.add("img", fileName);

//        设置请求header 为 APPLICATION_JSON
        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setContentType(MediaType.APPLICATION_JSON);


        // 请求体，包括请求数据 body 和 请求头 headers
        HttpEntity httpEntity = new HttpEntity(body, headers);


        try {
            //使用 exchange 发送请求，以String的类型接收返回的数据
            //ps，我请求的数据，其返回是一个json
            ResponseEntity<String> strbody = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            //解析返回的数据
            JSONObject jsTemp = JSONObject.parseObject(strbody.getBody());
            System.out.println(jsTemp);

            String resultImg = (String) jsTemp.get("label");

            OeHistory oeHistory = new OeHistory();
            oeHistory.setId(historyId);
            oeHistory.setEndTime(new Date());
            oeHistory.setResultImg(resultImg);
            oeHistory.setResult(jsTemp.toJSONString());
            if(oeHistoryService.updateById(oeHistory)){
                return Result.success(jsTemp);
            }
            else{
                return Result.error("-1", "保存结果失败");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return Result.error("-1", "目标提取失败");
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
    public Result<?> delete(@PathVariable Long id) {
        if (oeHistoryService.removeById(id)) {
            return Result.success();
        } else {
            return Result.error("-1", "任务记录删除失败");
        }
    }

    //找到一个
    @GetMapping("/{id}")
    public Result<?> getOne(@PathVariable Long id) {
        OeHistory res = oeHistoryService.getById(id);
        if (res == null) {
            return Result.error("-1", "id不存在");
        }
        return Result.success(res);
    }


    //分页展示全部
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") Long projectId) {
        LambdaQueryWrapper<OeHistory> wrapper = Wrappers.<OeHistory>lambdaQuery();
        wrapper.like(OeHistory::getProjectId, projectId);

        Page<OeHistory> repairPage = oeHistoryService.page(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(repairPage);
    }
}
