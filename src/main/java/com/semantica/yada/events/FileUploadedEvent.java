package com.semantica.yada.events;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class FileUploadedEvent {

    private UUID fileId;
    private String name;
    private String mimeType;
    private Long size;
    private Date creationDate;
    private String hash;
    private String originalPath;

}