package com.shunyue.mall.dao;

import com.shunyue.mall.dto.PmsProductResult;
import org.apache.ibatis.annotations.Param;


/**
 * 商品管理自定义Dao
 */
public interface PmsProductDao {
    /**
     * 获取商品编辑信息
     */
    PmsProductResult getUpdateInfo(@Param("id") Long id);
}
