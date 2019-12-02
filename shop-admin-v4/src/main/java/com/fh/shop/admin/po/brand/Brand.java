package com.fh.shop.admin.po.brand;

import com.fh.shop.admin.common.Page;

import java.io.Serializable;

public class Brand extends Page implements Serializable {
    private Long id;

    private String brandName;

    private String photo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
