package com.scraper.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.scraper.constant.Constants;

@Service
public class XLSXGenerator {
	
	public String generateXLSX(List<HashMap<String, String>> productList) {
	
		String dateStr = new Date().toString().replace(" ", "");
		String randomString = RandomStringUtils.randomAlphanumeric(8);
		Path filepath = Paths.get(System.getProperty("user.home"), "public", "temp" + dateStr + randomString + ".xlsx");
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("attributions");

		XSSFCellStyle cs = workbook.createCellStyle();
		cs.setFillForegroundColor(new XSSFColor(new java.awt.Color(201, 204, 181)));
		cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 14);
		font.setBold(true);
		cs.setFont(font);
		
		HashMap<String, Integer> headerMap = Constants.getHeaderMap();
		
		Row headerRow = sheet.createRow(0);
		
		for(String header : headerMap.keySet()) {
			String headerString = header;
			Cell cell = headerRow.createCell(headerMap.get(header));
			cell.setCellValue(headerString);
			cell.setCellStyle(cs);
		}
		
		int rowNum = 1;
		
		for(HashMap<String, String> product : productList) {
			Row row = sheet.createRow(rowNum++);
			Set<String> keys = product.keySet();
			
			for(String key : keys) {
				Cell cell = row.createCell(headerMap.get(key));
				cell.setCellValue(product.get(key));
			}
		}
		
		for (int i = 0; i < 15; i++) {
			sheet.autoSizeColumn(i);
		}
		
		try {
			FileOutputStream outputStream = new FileOutputStream(filepath.toFile());
			workbook.write(outputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("saved to path => " + filepath.toString());
		
		return filepath.toString();
	}

}
