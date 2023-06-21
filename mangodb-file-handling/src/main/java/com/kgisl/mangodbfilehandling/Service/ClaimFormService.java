package com.kgisl.mangodbfilehandling.Service;

import java.io.InputStream;

import com.mongodb.client.gridfs.model.GridFSFile;

public interface ClaimFormService {
    String storeFile(InputStream inputStream, String filename, String contentType);

    GridFSFile getFile(String fileId);

    void deleteFile(String fileId);

    byte[] getFileData(GridFSFile gridFsFile);
}
