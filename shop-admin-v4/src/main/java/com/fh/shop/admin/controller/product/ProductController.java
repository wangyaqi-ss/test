package com.fh.shop.admin.controller.product;

import com.fh.shop.admin.biz.product.IProductService;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.product.ProductSearchParam;
import com.fh.shop.admin.po.product.Product;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.admin.util.DateUtil;
import com.fh.shop.admin.util.FileUtil;
import com.fh.shop.admin.util.SystemConstant;
import com.fh.shop.admin.vo.product.ProductVo;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Resource
    private IProductService productService;

    /*商品分页查询*/
    @RequestMapping("/findProductList")
    @ResponseBody
    public DataTableResult findProductList(ProductSearchParam productSearchParam){
        DataTableResult dataTableResult=productService.findProductPageList(productSearchParam);
        return dataTableResult;
    }

    /*回显*/
    @RequestMapping("/findProduct")
    @ResponseBody
    public ServerResponse findProduct(Long id){
        ProductVo productVo=productService.findProduct(id);
        return ServerResponse.success(productVo);
    }

    /*修改*/
    @RequestMapping("/updateProduct")
    @ResponseBody
    public ServerResponse updateProduct(Product product){
        productService.updateProduct(product);
        return ServerResponse.success();

    }

    /*商品新增*/
    @RequestMapping("/addProduct")
    @ResponseBody
    public ServerResponse addProduct(Product product){
        productService.addProduct(product);
        return ServerResponse.success();

    }

    /*删除*/
    @RequestMapping("/deleteProductById")
    @ResponseBody
    public ServerResponse deleteProductById(Long id){
        productService.deleteProductById(id);
        return ServerResponse.success();

    }

    //批量删除
    @RequestMapping("batchDelete")
    @ResponseBody
    public ServerResponse batchDelete(@RequestParam("ids[]") List<Integer> ids){
        productService.batchDelete(ids);
        return ServerResponse.success();
    }

    //上下架
    @RequestMapping("updateValid")
    @ResponseBody
    public ServerResponse updateValid(Long id){
        productService.updateValid(id);
        return ServerResponse.success();
    }
    //跳转商品页面
    @RequestMapping("toList")
    public String toList(){
        return "product/productList";
    }

    //excel导出
    @RequestMapping("/expordExcel")
    public void exportExcel(ProductSearchParam productSearchParam, HttpServletResponse response) {
        List<Product> productList = productService.findProductList(productSearchParam);
        XSSFWorkbook workbook = buildWorkbook(productList);
        FileUtil.excelDownload(workbook, response);
    }

    private XSSFWorkbook buildWorkbook(List<Product> productList) {
        //创建workbook
        XSSFWorkbook xwk = new XSSFWorkbook();
        //创建sheet
        XSSFSheet sheet = xwk.createSheet("商品信息【"+productList.size()+"】");
        buildTitle(sheet,xwk);
        builtTitleRow(sheet);
        //内容
        buildBody(productList, sheet);
        return xwk;
    }

    private void buildTitle(XSSFSheet sheet,XSSFWorkbook xwk) {
        XSSFRow row = sheet.createRow(3);
        XSSFCell cell = row.createCell(7);
        cell.setCellValue("商品列表");
        CellRangeAddress cellRangeAddress = new CellRangeAddress(3, 5, 7, 10);
        sheet.addMergedRegion(cellRangeAddress);
        //构建样式
        XSSFCellStyle cellStyle = buildTitleStyle(xwk);
        //设置样式
        cell.setCellStyle(cellStyle);
    }

    private XSSFCellStyle buildTitleStyle(XSSFWorkbook xwk) {
        //通过workbook创建样式
        XSSFCellStyle cellStyle = xwk.createCellStyle();
        cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(cellStyle.ALIGN_CENTER);
        //通过wookbook创建字体
        XSSFFont font = xwk.createFont();
        //加粗
        font.setBold(true);
        font.setFontHeightInPoints((short)22);
        font.setColor(HSSFColor.RED.index);
        //将单元格样式和字体合二为一
        cellStyle.setFont(font);
        //背景色
        cellStyle.setFillBackgroundColor(HSSFColor.BLUE.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return cellStyle;
    }

    private void buildBody(List<Product> productList, XSSFSheet sheet) {
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            //创建row
            XSSFRow row = sheet.createRow(i + 7);
            //创建单元格
            row.createCell(7).setCellValue(product.getProductName());
            row.createCell(8).setCellValue(product.getPrice());
            row.createCell(9).setCellValue(product.getStock());
            row.createCell(10).setCellValue(product.getBrandName());
        }
    }

    private void builtTitleRow(XSSFSheet sheet) {
        //创建row
        XSSFRow row = sheet.createRow(6);
        String[] title = {"商品名","价格","库存量","品牌"};
        for (int i = 0; i < title.length; i++) {
            row.createCell(i+7).setCellValue(title[i]);
        }
    }

    //导出pdf
    @RequestMapping("/exportPdf")
    private void exportPdf(ProductSearchParam productSearchParam,HttpServletResponse response){
        //根据条件查询数据
        List<Product> productList = productService.findProductList(productSearchParam);
        // 构建模板数据
        Map data = buildData(productList);
        // 生成模板对应的html
        String htmlContent = FileUtil.buildPdfHtml(data, SystemConstant.PRODUCT_PDF_TEMPLATE_FILE);
        // 转为pdf并进行下载
        FileUtil.pdfDownloadFile(response, htmlContent);
    }


    private Map buildData(List<Product> productList) {
        Map data = new HashMap();
        //单位
        data.put("companyName", SystemConstant.COMPANY_NAME);
        //数据
        data.put("products", productList);
        //导出的时间
        data.put("createDate", DateUtil.date2str(new Date(), DateUtil.Y_M_D));
        return data;
    }

    //上传图片
    @RequestMapping("/uploadMainImage")
    @ResponseBody
    private ServerResponse uploadMainImage(MultipartFile myfile){
        //判断文件是否为空
        if (!myfile.isEmpty()) {
            String fileName=UUID.randomUUID()+myfile.getOriginalFilename().substring(myfile.getOriginalFilename().lastIndexOf("."));
            InputStream inputStream;
            try {
                inputStream = myfile.getInputStream();
                FileUtil.FTP(fileName, inputStream);
                return ServerResponse.success(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println();
                return ServerResponse.error();
            }
        } else {
            return ServerResponse.error();
        }
    }
}
