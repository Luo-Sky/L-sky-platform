package com.rs.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rs.platform.entity.CdHistory;
import com.rs.platform.entity.HistoryConfig;

/**
 * @author : hongbo
 * @create 2022-04-15-10:17
 **/
public interface ICdHistoryService extends IService<CdHistory> {
    JSONObject process(Long historyId, String url, String fileName1, String fileName2, String type, HistoryConfig historyConfig);
    JSONObject processBoxSelection(Long historyId, String url, String fileName1, String fileName2, String type, HistoryConfig historyConfig);
}
