package com.cqupt.project.shop.controller.backend;

import com.cqupt.project.shop.common.Constant;
import com.cqupt.project.shop.common.ResponseCode;
import com.cqupt.project.shop.common.ServerResponse;
import com.cqupt.project.shop.pojo.Product;
import com.cqupt.project.shop.pojo.User;
import com.cqupt.project.shop.service.ProductService;
import com.cqupt.project.shop.service.UserService;
import com.cqupt.project.shop.vo.ProductListVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author weigs
 * @date 2017/11/29 0029
 */
@Controller
@RequestMapping(value = "/manage/product/")
public class ProductManageController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    /**
     * 保存商品
     *
     * @param session
     * @param product
     * @return
     */
    @RequestMapping(value = "sava_product.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> saveProduct(HttpSession session, Product product) {

        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "需要登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            return productService.saveOrUpdateProduct(product);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }


    /**
     * 修改商品销售状态
     *
     * @param session
     * @param productId
     * @param status
     * @return
     */
    @RequestMapping(value = "set_sale_status.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> setSaleStatus(HttpSession session,
                                                long productId, Integer status) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return ServerResponse
                    .createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            return productService.setSaleStatus(productId, status);
        }
        return ServerResponse.createByErrorMessage("无权限操作");
    }

    /**
     * 获取商品详情
     *
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping(value = "get_detail", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getProductDetail(HttpSession session, Long productId) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return ServerResponse
                    .createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            return productService.getProductDetail(productId);
        }
        return ServerResponse.createByErrorMessage("无权限操作");
    }

    /**
     * 分页查询商品
     *
     * @param session
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list_product.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> listProduct(HttpSession session, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return ServerResponse
                    .createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            return productService.productList(pageNo, pageSize);
        }
        return ServerResponse.createByErrorMessage("无权限操作");
    }

    /**
     * 商品搜索功能
     *
     * @param session
     * @param productName
     * @param productId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "search.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> productSearch(HttpSession session, String productName, Long productId,
                                                  @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return ServerResponse
                    .createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            return productService.searchProduct(productName, productId, pageNo, pageSize);
        }
        return ServerResponse.createByErrorMessage("无权限操作");
    }

    public ServerResponse<Map> upload(MultipartFile file, HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("upload");
        //todo
        return null;
    }


}
