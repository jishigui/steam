package com.service;

import com.bean.GameTagBean;
import com.util.PageUtil;

public interface GameTagService {

    /**
     * 类型分页查询
     * @param gameTagBean
     * @param pageSize
     * @param currentPage
     * @return
     * @throws Exception
     */
    public PageUtil queryGameByTagPage(GameTagBean gameTagBean, int pageSize, int currentPage) throws Exception;

    /**
     * 添加类型信息
     * @param gameTagBean
     * @return
     * @throws Exception
     */
    public int insert(GameTagBean gameTagBean) throws Exception;
}