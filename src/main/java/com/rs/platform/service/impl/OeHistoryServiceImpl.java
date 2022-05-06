package com.rs.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rs.platform.entity.OeHistory;
import com.rs.platform.mapper.OeHistoryMapper;
import com.rs.platform.service.IOeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : hongbo
 * @create 2022-04-15-11:09
 **/
@Service
public class OeHistoryServiceImpl extends ServiceImpl<OeHistoryMapper, OeHistory> implements IOeHistoryService {

    @Autowired
    private OeHistoryMapper oeHistorytMapper;
}
