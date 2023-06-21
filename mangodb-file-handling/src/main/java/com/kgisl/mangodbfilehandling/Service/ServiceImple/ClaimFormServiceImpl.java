package com.kgisl.mangodbfilehandling.Service.ServiceImple;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import com.kgisl.mangodbfilehandling.Service.ClaimFormService;

import com.mongodb.client.gridfs.model.GridFSFile;

import lombok.AllArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@AllArgsConstructor
public class ClaimFormServiceImpl implements ClaimFormService {

    // @Autowired
    // private final ClaimFormRepository claimFormRepository;

    @Autowired
    private final GridFsTemplate gridFsTemplate;

    public String storeFile(InputStream inputStream, String filename, String contentType) {
        ObjectId fileId = gridFsTemplate.store(inputStream, filename, contentType);
        return fileId.toString();
    }

    public GridFSFile getFile(String fileId) {
        return gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(new ObjectId(fileId))));
    }

    public byte[] getFileData(GridFSFile gridFsFile) {
        GridFsResource resource = gridFsTemplate.getResource(gridFsFile);
        try (InputStream inputStream = resource.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toByteArray();

        } catch (IOException e) {
            // Handle exception appropriately
            e.printStackTrace();
            return new byte[0]; // or throw an exception, return null, etc.
        }
    }

    public void deleteFile(String fileId) {
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is(new ObjectId(fileId))));
    }

}
