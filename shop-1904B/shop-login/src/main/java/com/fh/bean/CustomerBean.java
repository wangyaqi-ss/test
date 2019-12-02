package com.fh.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("customer_login")
public class CustomerBean {

    @TableId(value = "customer_id",type = IdType.AUTO)
    private String customerId;
    private String loginName;
    private String phone;
    private Integer userStats;
    private Date modifedTime;
    private String password;
    private String cartId;

}









