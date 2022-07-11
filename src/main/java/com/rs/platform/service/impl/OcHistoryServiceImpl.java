package com.rs.platform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rs.platform.entity.BoxSelection;
import com.rs.platform.entity.HistoryConfig;
import com.rs.platform.entity.OcHistory;
import com.rs.platform.mapper.BoxSelectionMapper;
import com.rs.platform.mapper.OcHistoryMapper;
import com.rs.platform.service.IOcHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * @author : hongbo
 * @create 2022-04-15-11:09
 **/
@Service
public class OcHistoryServiceImpl extends ServiceImpl<OcHistoryMapper, OcHistory> implements IOcHistoryService {

    @Autowired
    private OcHistoryMapper ocHistoryMapper;

    @Autowired
    private BoxSelectionMapper boxSelectionMapper;

    //使用RestTemplate来发送HTTP请求
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public JSONObject process(Long historyId, String url, String fileName, String type, HistoryConfig historyConfig) {
        // LinkedMultiValueMap 有点像JSON，用于传递post数据，网络上其他教程都使用
        // MultiValueMpat<>来传递post数据
        // 但传递的数据类型有限，不能像这个这么灵活，可以传递多种不同数据类型的参数
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap();
        body.add("type", type);
        body.add("img", fileName);
        if (historyConfig.getConfidence() != null) {
            body.add("confidence", historyConfig.getConfidence());
        }
        if (historyConfig.getMinPixel() != null) {
            body.add("min_pixel", historyConfig.getMinPixel().toString());
        }

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

            OcHistory ocHistory = new OcHistory();
            ocHistory.setId(historyId);
            ocHistory.setEndTime(new Date());
            ocHistory.setResultImg(resultImg);
            if(type == "segmentation_5classes"){
                ocHistory.setResultImgName("粗分类结果图");
            }
            else if(type == "segmentation_15classes"){
                ocHistory.setResultImgName("精分类结果图");
            }
            ocHistory.setResult(jsTemp.toJSONString());
            ocHistory.setChoose(0);
            if (ocHistoryMapper.updateById(ocHistory) == 1) {
                return jsTemp;
            } else {
                System.out.println("地物分类的保存记录操作失败");
                return null;
            }
        } catch (Exception e) {
            System.out.println(e + "地物分类发送请求异常");
        }
        return null;
    }

    @Override
    public JSONObject processBoxSelection(Long historyId, String url, String fileName, String type, HistoryConfig historyConfig) {
        BoxSelection boxSelection = new BoxSelection();
        boxSelection.setStartTime(new Date());
        boxSelection.setType(1);
        boxSelection.setTask(type);
        boxSelection.setTopRow(historyConfig.getTop());
        boxSelection.setLeftColumn(historyConfig.getLeft());
        boxSelection.setBottomRow(historyConfig.getBottom());
        boxSelection.setRightColumn(historyConfig.getRight());

        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap();
        body.add("type", type);
        body.add("img", fileName);
        body.add("top", historyConfig.getTop().toString());
        body.add("left", historyConfig.getLeft().toString());
        body.add("bottom", historyConfig.getBottom().toString());
        body.add("right", historyConfig.getRight().toString());
        if (historyConfig.getConfidence() != null) {
            body.add("confidence", historyConfig.getConfidence());
        }
        if (historyConfig.getMinPixel() != null) {
            body.add("min_pixel", historyConfig.getMinPixel().toString());
        }

        HttpHeaders headers = new HttpHeaders();
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

            boxSelection.setHistoryId(historyId);
            boxSelection.setEndTime(new Date());
            boxSelection.setResultImg(resultImg);
            if(type == "segmentation_5classes"){
                boxSelection.setResultImgName("粗分类结果图");
            }
            else if(type == "segmentation_15classes"){
                boxSelection.setResultImgName("精分类结果图");
            }
            boxSelection.setResult(jsTemp.toJSONString());

            OcHistory ocHistory = new OcHistory();
            ocHistory.setId(historyId);
            ocHistory.setChoose(1);
            if (boxSelectionMapper.insert(boxSelection) == 1 && ocHistoryMapper.updateById(ocHistory) == 1) {
                return jsTemp;
            } else {
                System.out.println("地物分类的保存记录操作失败");
                return null;
            }

        } catch (Exception e) {
            System.out.println(e + "地物分类发送请求异常");
        }
        return null;
    }
}
