package com.example.Spring_boot_2.controllers;

import com.example.Spring_boot_2.services.DownloadCustomerData;
import org.apache.catalina.filters.ExpiresFilter;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@CrossOrigin("*")
public class ExcelController {

    @Autowired
    DownloadCustomerData downloadCustomerData;
    private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);

    @GetMapping(value = "downloadCustomer-data")
    private void downloadCustomerData(HttpServletRequest request, HttpServletResponse response ,
                                      @RequestParam (value = "customerIds") String customerIds){
        logger.info(" Download customer data for ids --->{}", customerIds);
        List<Long> ids = Stream.of(customerIds.split(",")).map(Long :: valueOf).collect(Collectors.toList());
        Workbook workbook = downloadCustomerData.fromCustomerId(ids);
        ServletOutputStream os =null;
        try{
            response.setHeader("content-Disposition","attachment; filename=\"Customer Data.xls\"");
            response.setContentType("application/vnd.ms-excel");
            os=response.getOutputStream();
            workbook.write(os);
            workbook.close();
            os.flush();
            response.flushBuffer();
        }
        catch (IOException e){
         logger.error("Error in writing to outputStream in downloadCustomerDataExcel for customer Ids :{}",customerIds);
        }
    }

}
