package com.feeprocess.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class GenerateReceiptUtil {
	 private static final String RECEIPT_PATH = "receipts/";
	
	public static String generateReceipt(String studentId,String studentName,long className, String orderId, Double amount) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "receipt_" + studentId + "_" + timestamp + ".pdf";
        String filePath = RECEIPT_PATH + fileName;

        new File(RECEIPT_PATH).mkdirs();

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);

            contentStream.showText("SCHOOL FEE RECEIPT");
            contentStream.newLineAtOffset(0, -30);

            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.showText("Student ID: " + studentId);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Student Name: " + studentName);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Class: " + className);
            contentStream.newLineAtOffset(0, -20);
            
            contentStream.showText("Order ID: " + orderId);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Amount Paid: $" + amount);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            contentStream.endText();
            contentStream.close();

            document.save(filePath);
            return filePath;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating receipt PDF");
        }
    }

}
