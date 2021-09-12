package com.semantica.yada.filedownloadservice.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class StorageServiceFileSystem implements StorageService {

    private final Path rootLocation;
    private final Integer depth;
    private Integer length;

    @Autowired
    public StorageServiceFileSystem(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.depth = properties.getDepth();
        this.length = properties.getLength();
    }
    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(getStorageLocation(filename) + "/" + filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize app.storage", e);
        }
    }

    private String getStorageLocation(String filename) {
        if (filename.length() < this.length * this.depth) {
            this.length = Math.round(filename.length() / this.depth);
        }

        String filepath = this.rootLocation.toString() + File.separator;
        for (int i = 0; i < this.depth; i++) {
            filepath += filename.substring(i * this.length, (i * this.length) + this.length) + File.separator   ;
        }

        return filepath;
    }
}
