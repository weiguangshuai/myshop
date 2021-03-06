package com.cqupt.project.shop.controller.backend;

import com.cqupt.project.shop.common.Constant;
import com.cqupt.project.shop.common.ResponseCode;
import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.pojo.Category;
import com.cqupt.project.shop.pojo.User;
import com.cqupt.project.shop.redis.JedisClient;
import com.cqupt.project.shop.service.CategoryService;
import com.cqupt.project.shop.service.UserService;
import com.cqupt.project.shop.util.CookieUtil;
import com.cqupt.project.shop.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author weigs
 * @date 2017/11/29 0029
 */
@Controller
@RequestMapping(value = "/manage/category/")
public class CategoryManageController {
    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private JedisClient jedisClient;

    /**
     * 添加分类
     *
     * @param request
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping(value = "add_category.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addCategory(HttpServletRequest request, String categoryName,
                                      @RequestParam(value = "parentId",
                                              defaultValue = "0") int parentId) {
        String token = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(token)) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        String userJsonStr = jedisClient.get(token);
        if (StringUtils.isEmpty(userJsonStr)) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        User user = JsonUtil.stringToObj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "用户未登录，请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            return categoryService.addCategory(categoryName, parentId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    /**
     * 更新分类名称
     *
     * @param request
     * @param categoryId
     * @param categoryName
     * @return
     */
    @RequestMapping(value = "set_category_name.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> setCategoryName(HttpServletRequest request, Integer categoryId, String categoryName) {
        String token = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(token)) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        String userJsonStr = jedisClient.get(token);
        if (StringUtils.isEmpty(userJsonStr)) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        User user = JsonUtil.stringToObj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "用户未登录，请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            return categoryService.updateCategoryName(categoryId, categoryName);
        } else {
            return ServerResponse.createByErrorMessage("用户没有权限");
        }

    }


    /**
     * 获取同级孩子节点的分类
     *
     * @param request
     * @param categoryId
     * @return
     */

    @RequestMapping(value = "get_category.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<List<Category>> getChildrenParallelCategory(HttpServletRequest request,
                                                                      @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        String token = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(token)) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        String userJsonStr = jedisClient.get(token);
        if (StringUtils.isEmpty(userJsonStr)) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        User user = JsonUtil.stringToObj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "用户未登录，请登录");
        }
        return categoryService.getChildrenParallelCategory(categoryId);
    }

    /**
     * 获取该分类下的所有子分类id
     *
     * @param request
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "get_deep_category.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<List<Integer>> getCategoryAndDeepChildrenCategory(HttpServletRequest request,
                                                                            @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        String token = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(token)) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        String userJsonStr = jedisClient.get(token);
        if (StringUtils.isEmpty(userJsonStr)) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        User user = JsonUtil.stringToObj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "用户未登录，请登录");
        }
        return categoryService.selectCategoryAndChildrenById(categoryId);
    }
}
