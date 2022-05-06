package com.rs.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rs.platform.entity.OcHistory;
import com.rs.platform.mapper.OcHistoryMapper;
import com.rs.platform.service.IOcHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : hongbo
 * @create 2022-04-15-11:09
 **/
@Service
public class OcHistoryServiceImpl extends ServiceImpl<OcHistoryMapper, OcHistory> implements IOcHistoryService {

    @Autowired
    private OcHistoryMapper ocHistorytMapper;
}
