package com.dao;

import com.entity.ChongwuCollectionEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.ChongwuCollectionView;

/**
 * 宠物收藏 Dao 接口
 *
 * @author 
 * @since 2021-04-26
 */
public interface ChongwuCollectionDao extends BaseMapper<ChongwuCollectionEntity> {

   List<ChongwuCollectionView> selectListView(Pagination page,@Param("params")Map<String,Object> params);

}
