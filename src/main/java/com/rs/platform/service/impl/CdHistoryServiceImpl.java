package com.rs.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rs.platform.entity.CdHistory;
import com.rs.platform.mapper.CdHistoryMapper;
import com.rs.platform.service.ICdHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : hongbo
 * @create 2022-04-15-11:09
 **/
@Service
public class CdHistoryServiceImpl extends ServiceImpl<CdHistoryMapper, CdHistory> implements ICdHistoryService {

    @Autowired
    private CdHistoryMapper cdHistorytMapper;
}
