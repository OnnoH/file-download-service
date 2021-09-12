package com.semantica.yada.filedownloadservice;

import com.semantica.yada.events.FileUploadedEvent;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FileDownloadEventHandler {

    private FileDownloadRepository fileDownloadRepository;

    @EventHandler
    public void on(FileUploadedEvent evt) {

        FileDownloadData fileDownloadData = new FileDownloadData(
                  evt.getFileId()
                , evt.getName()
                , evt.getMimeType()
                , evt.getSize()
                , evt.getCreationDate()
                , evt.getHash()
                , evt.getOriginalPath()
        );

        fileDownloadRepository.save(fileDownloadData);
    }

}
