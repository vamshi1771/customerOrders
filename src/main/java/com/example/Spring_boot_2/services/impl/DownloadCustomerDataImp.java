package com.example.Spring_boot_2.services.impl;

import com.example.Spring_boot_2.dto.Customerdto;
import com.example.Spring_boot_2.entity.Customers;
import com.example.Spring_boot_2.enums.DownloadCustomerEnum;
import com.example.Spring_boot_2.repository.CustomerRepository;
import com.example.Spring_boot_2.services.DownloadCustomerData;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class DownloadCustomerDataImp implements DownloadCustomerData {
    @Autowired
    CustomerRepository CustomerRepository;
    private static final Logger logger = LoggerFactory.getLogger(DownloadCustomerDataImp.class);
    @Override
    public Workbook fromCustomerId(List<Long> customerIds) {
        logger.info("Fetching Data for Excel Creation");
        List <Customers> customerData = CustomerRepository.findBycustomeridIn(customerIds);
        logger.info("CustomersData Fetch Sucessfully" + customerData.size());


        try(Workbook workbook = new HSSFWorkbook()){
            logger.info("Creating Workbook");
            CellStyle TopicStyle;
            HSSFCellStyle style, wrapText;

            TopicStyle = workbook.createCellStyle();
            TopicStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            TopicStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            TopicStyle.setVerticalAlignment(VerticalAlignment.TOP);
            TopicStyle.setBorderBottom(BorderStyle.MEDIUM);
            TopicStyle.setBorderTop(BorderStyle.MEDIUM);
            TopicStyle.setBorderRight(BorderStyle.MEDIUM);
            TopicStyle.setBorderLeft(BorderStyle.MEDIUM);
            TopicStyle.setWrapText(true);

            wrapText = (HSSFCellStyle) workbook.createCellStyle();
            wrapText.setWrapText(true);
            wrapText.setVerticalAlignment(VerticalAlignment.TOP);
            style = (HSSFCellStyle) workbook.createCellStyle();
            style.setWrapText(true);

            logger.info("Creating sheets");
            Sheet sheet = workbook.createSheet("customers");
            Row row = sheet.createRow(0);

            logger.info("Creating header in the sheet");
            for(DownloadCustomerEnum.customerSheet customerSheetEnum : DownloadCustomerEnum.customerSheet.values()){
                Cell cell = row.createCell(customerSheetEnum.getColumnIndex());
                cell.setCellValue(customerSheetEnum.getColumName());
                cell.setCellStyle(TopicStyle);
            }
            for(int i = 0; i<customerData.size();i++){
                Customers customers = customerData.get(i);

                sheet = workbook.getSheet("customers");
                row = sheet.createRow(i+1);
                Cell cell;

                logger.info("writing in Sheets");
                cell = row.createCell(DownloadCustomerEnum.customerSheet.CustomerID.getColumnIndex());
                cell.setCellValue(customers.getCustomerid());
                cell.setCellStyle(wrapText);

                cell = row.createCell(DownloadCustomerEnum.customerSheet.CustomerName.getColumnIndex());
                cell.setCellValue(customers.getCustomername());
                cell.setCellStyle(wrapText);

                cell = row.createCell(DownloadCustomerEnum.customerSheet.Region.getColumnIndex());
                cell.setCellValue(customers.getRegion());
                cell.setCellStyle(wrapText);

                cell = row.createCell(DownloadCustomerEnum.customerSheet.Gender.getColumnIndex());
                cell.setCellValue(customers.getGender());
                cell.setCellStyle(wrapText);

                for(DownloadCustomerEnum.customerSheet customerSheetEnum : DownloadCustomerEnum.customerSheet.values()){
                    if(!customerSheetEnum.getDefaultValue().equals("")){
                        cell = row.createCell(customerSheetEnum.getColumnIndex());
                        cell.setCellValue(customerSheetEnum.getDefaultValue());
                        cell.setCellStyle(wrapText);
                    }
                }
            }
            return workbook;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
