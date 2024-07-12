package com.example.Spring_boot_2.services;

import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface DownloadCustomerData {

    Workbook fromCustomerId(List<Long> customerIds);
}
