package com.ezban.event.model;

import java.util.List;
import java.util.Map;

public interface ServiceDemo<T> {

    /**
     * 新增物件至資料庫
     * @param vo
     * @return 新增的物件
     */
    T add(T vo);

    /**
     * 新增物件至資料庫
     * @param vo
     * @return 更新的物件
     */
    T update(T vo);

    /**
     * 根據id尋找物件
     * @param id
     * @return
     */
    T findById(Integer id);


    /**
     * @return 以List回傳查詢到的所有物件
     */
    List<T> findAll();

}
