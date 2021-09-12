package com.semantica.yada.filedownloadservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileDownloadRepository extends JpaRepository<FileDownloadData, UUID> {

}
