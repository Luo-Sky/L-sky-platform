package com.rs.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rs.platform.entity.HistoryConfig;
import com.rs.platform.entity.OdHistory;

/**
 * @author : hongbo
 * @create 2022-04-15-10:17
 **/
public interface IOdHistoryService extends IService<OdHistory> {

    JSONObject process(Long historyId, String url, String fileName, String type, HistoryConfig historyConfig);
}
