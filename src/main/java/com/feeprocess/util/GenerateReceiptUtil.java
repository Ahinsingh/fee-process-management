package com.feeprocess.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import com.feeprocess.model.Student;
import com.feeprocess.model.Payment;

public class GenerateReceiptUtil {

    private static final String RECEIPT_PATH = "receipts/";

    public static String generateReceipt(Student student, Payment payment) {

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "receipt_" + student.getName() + "_" + timestamp + ".pdf";
        String filePath = RECEIPT_PATH + fileName;

        new File(RECEIPT_PATH).mkdirs();

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Header Background
            contentStream.setNonStrokingColor(71, 37, 152); // Purple
            contentStream.addRect(0, 760, page.getMediaBox().getWidth(), 40);
            contentStream.fill();

            // Header Text
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
            contentStream.setNonStrokingColor(255, 255, 255);
            contentStream.newLineAtOffset(160, 775);
            contentStream.showText("Your transaction is Successful");
            contentStream.endText();

            float y = 730;
            contentStream.setNonStrokingColor(0, 0, 0); // Reset to black

            // Greeting
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(50, y);
            contentStream.showText("Hi " + student.getName() + ",");
            contentStream.endText();

            y -= 20;
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(50, y);
            contentStream.showText("Congratulations! Your payment transaction is successful.");
            contentStream.endText();

            y -= 40;
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, y);
            contentStream.showText("Transaction Details:");
            contentStream.endText();

            String formattedDate = payment.getCreatedAt() != null
                    ? payment.getCreatedAt().format(DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm"))
                    : LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm"));

            String[][] info = {
                    {"Date & Time", formattedDate},
                    {"Student Name", student.getName()},
                    {"Student ID", student.getId()+""},
                    {"Reference #", payment.getId()+""},
                    {"Card #", "1234-5678-1236-0081"},
                    {"Card Type", "MasterCard"}
            };

            y -= 20;
            for (String[] entry : info) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 11);
                contentStream.newLineAtOffset(60, y);
                contentStream.showText(" > " + entry[0] + " : " + entry[1]);
                contentStream.endText();
                y -= 15;
            }

            y -= 20;
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, y);
            contentStream.showText("Purchase Details:");
            contentStream.endText();

            y -= 20;
            contentStream.setFont(PDType1Font.HELVETICA, 11);
            contentStream.beginText();
            contentStream.newLineAtOffset(60, y);
            contentStream.showText("Tuition Fees: AED " + String.format("%.2f", payment.getPendingAmount() + payment.getPaidAmount()));
            contentStream.endText();

            y -= 15;
            contentStream.beginText();
            contentStream.newLineAtOffset(60, y);
            contentStream.showText("Course Code: " + student.getCourseCode());
            contentStream.endText();

            y -= 15;
            contentStream.beginText();
            contentStream.newLineAtOffset(60, y);
            contentStream.showText("Amount: AED " + String.format("%.2f", payment.getPaidAmount()));
            contentStream.endText();

            y -= 20;
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(60, y);
            contentStream.showText("Pending Amount: AED " + String.format("%.2f", payment.getPendingAmount()));
            contentStream.endText();

            // Footer
            y = 50;
            contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 9);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, y);
            contentStream.showText("This is an automated email, please do not reply. For any other query, please email us at contact@school.ae");
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
