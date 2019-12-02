package com.fh.service;

import com.fh.bean.ProductParamter;
import com.fh.bean.TShopProduct;
import com.fh.utils.page.PageBean;

public interface IProductService {
    PageBean<TShopProduct> queryProductPageList(PageBean<TShopProduct> page,ProductParamter paramter);
}
