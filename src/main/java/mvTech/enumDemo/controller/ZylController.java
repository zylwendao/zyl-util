package mvTech.enumDemo.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import mvTech.enumDemo.bean.User;
import mvTech.enumDemo.config.IExcelDictHandlerImpl;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class ZylController {

    @GetMapping("test2")
    public String test2(HttpServletResponse response) throws Exception {

        List<User> list = new ArrayList<>();
        User user = new User();
        user.setId(123);
//        user.setName("2");
        user.setName("城域网统一DPI");
        list.add(user);
        OutputStream outputStream = null;
        try {
            String fileName = "测试.xls";
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20"); // 使用UTF-8进行编码，并将加号转为空格，因为浏览器会将加号解释为空格
            response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
            outputStream = response.getOutputStream();
            ExportParams exportParams = new ExportParams("哈哈哈", "第一sheet");
            exportParams.setDictHandler(new IExcelDictHandlerImpl());
            Workbook workbook = ExcelExportUtil.exportExcel(exportParams, User.class, list);
            workbook.write(outputStream);
            outputStream.flush();
        } finally {
            outputStream.close();
//            IOUtils.closeQuietly(outputStream);
        }
        return "ok";
    }
}