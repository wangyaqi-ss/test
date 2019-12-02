package com.fh.shop.admin.controller.area;

import com.fh.shop.admin.biz.area.IAreaService;
import com.fh.shop.admin.biz.user.IUserService;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.po.area.Area;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("area")
public class AreaController {
    @Resource(name="areaService")
    private IAreaService areaService;

    @RequestMapping("/findAreaList")
    @ResponseBody
    public ServerResponse findAreaList(){
        List<Map> areaList=areaService.findAreaList();
        return ServerResponse.success(areaList);
    }

    //跳转菜单资源页面
    @RequestMapping("toList")
    public String toList(){
        return "area/areaList";
    }

}
