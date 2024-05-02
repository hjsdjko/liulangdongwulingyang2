package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.ChongwuzhishiEntity;
import java.util.Map;

/**
 * 宠物知识科普 服务类
 * @author 
 * @since 2021-04-26
 */
public interface ChongwuzhishiService extends IService<ChongwuzhishiEntity> {

    /**
    * @param params 查询参数
    * @return 带分页的查询出来的数据
    */
     PageUtils queryPage(Map<String, Object> params);
}