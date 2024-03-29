package com.example.demo.controller;

import com.example.demo.exception.BizException;
import com.example.demo.model.Product;
import com.example.demo.response.SysResult;
import com.example.demo.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    /**
     * 查询商品
     * @param query 查询词
     * @param page 起始页
     * @param rows 查询条数
     * @return 商品列表
     */
    @RequestMapping("/manage/query")
    @ResponseBody
    public List<Product> searchProducts(
            @RequestParam("query") String query,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "30") Integer rows
    ){
        try{
            return this.searchService.searchProducts(query, page, rows);
        }catch (BizException e){
            e.printStackTrace();
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 新增商品
     * @param product 商品
     * @return vo
     */
    @RequestMapping("/manage/add")
    @ResponseBody
    public SysResult addProduct(
            @RequestBody Product product
    ){
        try{
            this.searchService.addProduct(product);
            return SysResult.ok();
        }catch(BizException e){
            return SysResult.build(201, e.getMessage(), e);
        }catch (Exception e){
            e.printStackTrace();
            return SysResult.build(500, e.getMessage(), e);
        }
    }

    /**
     * 删除商品
     * @param product 商品
     * @return vo
     */
    @RequestMapping("/manage/delete")
    @ResponseBody
    public SysResult deleteProduct(
            @RequestBody Product product
    ){
        try{
            this.searchService.deleteProduct(product);
            return SysResult.ok();
        }catch (BizException e){
            return SysResult.build(201, e.getMessage(), e);
        }catch (Exception e){
            e.printStackTrace();
            return SysResult.build(500, e.getMessage(), e);
        }
    }


}
