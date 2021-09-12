package com.semantica.yada.filedownloadservice;

import com.semantica.yada.filedownloadservice.storage.StorageFileNotFoundException;
import com.semantica.yada.filedownloadservice.storage.StorageService;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/filedownloadservice/v1")
public class FileDownloadServiceController {

    private QueryGateway queryGateway;
    private StorageService storageService;
    private FileDownloadRepository fileDownloadRepository;

    @GetMapping("/")
    public ResponseEntity<List<FileDownloadData>> listFiles() {

        List<FileDownloadData> filelist = fileDownloadRepository.findAll();
        return ResponseEntity.ok().body(filelist);

    }

    @GetMapping("/file/{fileId}")
    public ResponseEntity<FileDownloadData> listFileById(@PathVariable UUID fileId) {
        FileDownloadData fileDownloadData = fileDownloadRepository.getOne(fileId);
        return ResponseEntity.ok().body(fileDownloadData);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        System.out.println("Download: " + filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}