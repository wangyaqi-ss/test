package com.fh.shop.admin.biz.brand;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.mapper.brand.IBrandMapper;
import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.admin.vo.brand.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("brandService")
public class IBrandServiceImpl implements IBrandService {
    @Autowired
    private IBrandMapper brandMapper;

    @Override
    public void addBrand(Brand brand) {
        brandMapper.addBrand(brand);
    }

    @Override
    public void deleteBrandById(Long id) {
        brandMapper.deleteBrandById(id);
    }

    @Override
    public BrandVo findBrand(Long id) {
        Brand brand = brandMapper.findBrand(id);
        BrandVo BrandVo = getBrandVo(brand);
        return BrandVo;
    }

    @Override
    public void updateBrand(Brand brand) {
        brandMapper.updateBrand(brand);
    }

    @Override
    public DataTableResult findBrandPageList(Brand brand) {
        //查询总条数
        Long totalCount = brandMapper.findBrandCount();
        //查询分页数据
        List<Brand> brandList = brandMapper.findBrandPageList(brand);
        //po转vo
        List<BrandVo> brandVoList = new ArrayList<BrandVo>();
        for (Brand brandInfo : brandList) {
            BrandVo brandVo = getBrandVo(brandInfo);
            brandVoList.add(brandVo);
        }
        return new DataTableResult(brand.getDraw(),totalCount,totalCount,brandVoList);
    }

    @Override
    public List<Brand> findAllBrand() {
        List<Brand> brandList = brandMapper.findAllBrand();
        return brandList;
    }

    private BrandVo getBrandVo(Brand brand) {
        BrandVo brandVo=new BrandVo();
        brandVo.setId(brand.getId());
        brandVo.setBrandName(brand.getBrandName());
        brandVo.setPhoto(brand.getPhoto());
        return brandVo;
    }


}
