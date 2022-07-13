package com.rs.platform.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.rs.platform.tools.impl.FileServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipFile;

/**
 * @author : hongbo
 * @create 2022-05-06-22:23
 **/

@RestController
@RequestMapping("/files")
public class FileController {
    /**
     * 下载接口
     *
     * @param flag
     * @param response
     */
    @GetMapping("/{flag}")
    public void getFiles(@PathVariable String flag, HttpServletResponse response) {
        OutputStream os;  // 新建一个输出流对象
        String basePath = System.getProperty("user.dir") + "/src/main/resources/files/";  // 定于文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);  // 获取所有的文件名称
        String fileName = fileNames.stream().filter(name -> name.contains(flag) && name.charAt(flag.length())=='.').findAny().orElse("");;  // 找到跟参数一致的文件
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName);  // 通过文件的路径读取文件字节流
                os = response.getOutputStream();   // 通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }

    @GetMapping("/project/{flag}")
    public void getPojectImg(@PathVariable String flag, HttpServletResponse response) {
        OutputStream os;  // 新建一个输出流对象
        String basePath = System.getProperty("user.dir") + "/src/main/resources/cover/";  // 定于文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);  // 获取所有的文件名称
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  // 找到跟参数一致的文件
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName);  // 通过文件的路径读取文件字节流
                os = response.getOutputStream();   // 通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }

    @PostMapping("/zip")
    public void getZipFiles(@RequestBody HashMap<String,Object> map, HttpServletResponse response) {
        List<String> flagList = (List<String>) map.get("flagList");
        String zipFileName = (String) map.get("zipFileName");
        OutputStream os;  // 新建一个输出流对象
        String basePath = System.getProperty("user.dir") + "/src/main/resources/files/";  // 定于文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);  // 获取所有的文件名称
        File[] srcFiles = new File[flagList.size()];
        for (int i = 0; i < srcFiles.length; i++) {
            String flag = flagList.get(i);
            String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  // 找到跟参数一致的文件
            srcFiles[i] = new File(basePath + fileName);
        }

        File zipFile = new File(basePath + zipFileName + ".zip");

        FileServiceImpl fileService = new FileServiceImpl();

        fileService.zipFiles(srcFiles, zipFile);

        try {
            if (StrUtil.isNotEmpty(zipFileName)) {
                response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(zipFileName + ".zip", "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + zipFileName + ".zip");  // 通过文件的路径读取文件字节流
                os = response.getOutputStream();   // 通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("压缩包下载失败");
        }
    }
}
