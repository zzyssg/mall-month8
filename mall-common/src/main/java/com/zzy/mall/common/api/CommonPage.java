package com.zzy.mall.common.api;



import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * @program: mall
 * @description: 统一分页结果对象
 * @author: zzy
 * @create: 2024-07-20
 */
@Data
public class CommonPage<T> {

    private Integer pageNum;
    private Integer pageSize;

    private Integer totalPage;

    private Long total;

    private List<T> dataList;


    public static <T> CommonPage<T> restPage(List<T> data){
        PageInfo<T> pageInfo = new PageInfo<>(data);
        CommonPage res = new CommonPage<>();
        res.setPageSize(pageInfo.getPageSize());
        res.setPageNum(pageInfo.getPageNum());
        res.setTotalPage(pageInfo.getPages());
        res.setTotal(pageInfo.getTotal());
        res.setDataList(data);
        return  res;
    }

    public static <T> CommonPage<T> resPage(Page<T> page){
        CommonPage res = new CommonPage();
        res.setTotal(page.getTotal());
        res.setTotalPage(page.getPages());
        res.setPageNum(page.getPageNum());
        res.setPageSize(page.getPageSize());
        res.setDataList(page.getResult());
        return res;
    }

}