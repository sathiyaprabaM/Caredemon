package com.example.csv;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public void save(MultipartFile file) throws IOException, CsvException {
        List<Files> filesList = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<String[]> rows = reader.readAll();

            for (String[] row : rows) {
                if (row.length < 10) {
                    System.out.println("Skipping row due to insufficient data: " + Arrays.toString(row));
                    continue;
                }

                Files files = new Files();
                files.setId(row[0]);
                files.setDesc1(row[1]);
                files.setDesc2(row[2]);
                files.setVal1(row[3]);
                files.setVal2(row[4]);
                files.setVal3(row[5]);
                files.setVal4(row[6]);
                files.setVal5(row[7]);
                files.setVal6(row[8]);
                files.setVal7(row[9]);
                filesList.add(files);
            }

            if (!filesList.isEmpty()) {
                fileRepository.saveAll(filesList);
            } else {
                throw new RuntimeException("No valid records to save.");
            }
        } catch (IOException e) {
            throw new IOException("Error reading the CSV file: " + e.getMessage(), e);
        }
    }

    public byte[] generatePdf() throws DocumentException, IOException {
        List<Files> filesList = fileRepository.findAll();

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();

        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Paragraph title = new Paragraph("Database Information", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" ")); // Empty space after title

        PdfPTable table = new PdfPTable(10); // 10 columns for the 10 fields
        table.setWidthPercentage(100);
        
        // Add table headers
        table.addCell(new Phrase("ID"));
        table.addCell(new Phrase("Desc1"));
        table.addCell(new Phrase("Desc2"));
        table.addCell(new Phrase("Val1"));
        table.addCell(new Phrase("Val2"));
        table.addCell(new Phrase("Val3"));
        table.addCell(new Phrase("Val4"));
        table.addCell(new Phrase("Val5"));
        table.addCell(new Phrase("Val6"));
        table.addCell(new Phrase("Val7"));

        // Add table rows
        for (Files file : filesList) {
            table.addCell(file.getId());
            table.addCell(file.getDesc1());
            table.addCell(file.getDesc2());
            table.addCell(file.getVal1());
            table.addCell(file.getVal2());
            table.addCell(file.getVal3());
            table.addCell(file.getVal4());
            table.addCell(file.getVal5());
            table.addCell(file.getVal6());
            table.addCell(file.getVal7());
        }

        document.add(table);
        document.close();

        return out.toByteArray();
    }

    public void update(Files file) {
        fileRepository.save(file);
    }

    public List<Files> getAllFiles() {
        return fileRepository.findAll();
    }
}
