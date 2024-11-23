package com.example.csv;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvException;

@RestController
@RequestMapping("/api")
public class FileController {

    @Autowired
    private FileService fileService;
    @CrossOrigin(origins = "http://localhost:3000") 
    @PostMapping("/uploadFilesIntoDB")
   public ResponseEntity<String> storeFileIntoDB(@RequestParam("file") MultipartFile file) {
        try {
            fileService.save(file);
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully!");
        }catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: I/O error - " + e.getMessage());
        }catch (CsvException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File upload failed: Invalid CSV format - " + e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/viewFiles")
    public ResponseEntity<List<Files>> viewFilesFromDB() {
        List<Files> files = fileService.getAllFiles();
        return ResponseEntity.ok(files); // Return the list of files as JSON
    }

    @GetMapping("/downloadPdf")
    public ResponseEntity<byte[]> downloadPdf() throws DocumentException, IOException {
        byte[] pdfBytes = fileService.generatePdf();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "data.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    @PostMapping("/updateFile")
    public ResponseEntity<String> updateFile(@RequestBody Files file) {
        try {
            fileService.update(file);
            return ResponseEntity.status(HttpStatus.OK).body("File updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File update failed: " + e.getMessage());
        }
    }
}
