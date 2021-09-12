package com.semantica.yada.filedownloadservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.semantica.yada.filedownloadservice.storage.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class FileDownloadService {

    public static void main(String[] args) {
        SpringApplication.run(FileDownloadService.class, args);
    }

}
