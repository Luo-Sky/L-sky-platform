package com.rs.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rs.platform.entity.BoxSelection;
import com.rs.platform.mapper.BoxSelectionMapper;
import com.rs.platform.service.IBoxSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : hongbo
 * @create 2022-06-24-14:58
 **/
@Service
public class BoxSelectionServiceImpl extends ServiceImpl<BoxSelectionMapper, BoxSelection> implements IBoxSelectionService {
    @Autowired
    private BoxSelectionMapper boxSelectionMapper;
}
