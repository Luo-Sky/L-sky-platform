package com.rs.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rs.platform.entity.EeHistory;
import com.rs.platform.entity.HistoryConfig;


public interface IEeHistoryService extends IService<EeHistory> {
    JSONObject process(Long historyId, String url, String fileName, String type, HistoryConfig historyConfig);

    JSONObject processBoxSelection(Long historyId, String url, String fileName, String type, HistoryConfig historyConfig);
}
