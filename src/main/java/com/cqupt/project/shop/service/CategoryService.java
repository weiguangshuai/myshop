package com.cqupt.project.shop.service;

import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.pojo.Category;

import java.util.List;

/**
 * @author weigs
 * @date 2017/11/29 0029
 */
public interface CategoryService {
    /**
     * 添加分类
     *
     * @param categoryName
     * @param parentId
     * @return
     */
    ServerResponse addCategory(String categoryName, Integer parentId);

    /**
     * 更新分类名称
     *
     * @param categoryId
     * @param categoryName
     * @return
     */
    ServerResponse<String> updateCategoryName(Integer categoryId, String categoryName);

    /**
     * 查找孩子的平行节点
     *
     * @param categoryId
     * @return
     */
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    /**
     * 递归查找本节点的id和孩子节点的id
     *
     * @param categoryId
     * @return
     */
    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
