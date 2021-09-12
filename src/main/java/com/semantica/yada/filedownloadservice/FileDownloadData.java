package com.semantica.yada.filedownloadservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "filedata")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDownloadData implements Serializable {

    @Id
    private UUID fileId;

    private String name;
    private String mimeType;
    private Long size;
    private Date creationDate;
    private String hash;
    private String originalPath;

}
