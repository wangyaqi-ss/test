package com.fh.shop.admin.biz.brand;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.admin.vo.brand.BrandVo;

import java.util.List;

public interface IBrandService {

    void addBrand(Brand brand);

    void deleteBrandById(Long id);

    BrandVo findBrand(Long id);

    void updateBrand(Brand brand);

    DataTableResult findBrandPageList(Brand brand);

    List<Brand> findAllBrand();
}
