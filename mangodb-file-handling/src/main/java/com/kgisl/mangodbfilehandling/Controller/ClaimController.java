package com.kgisl.mangodbfilehandling.Controller;

import java.io.IOException;

// import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.kgisl.mangodbfilehandling.Model.ClaimForm;
import com.kgisl.mangodbfilehandling.Repository.ClaimFormRepository;
import com.kgisl.mangodbfilehandling.Service.ClaimFormService;
import com.mongodb.client.gridfs.model.GridFSFile;


import lombok.NoArgsConstructor;



@RestController
@NoArgsConstructor
@RequestMapping("/api/claims")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClaimController {

    @Autowired
    private ClaimFormRepository claimFormRepository;

    @Autowired
    private ClaimFormService claimFormService;

    @Autowired
    private ClaimForm claimForm;

   

    


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("files") MultipartFile[] files, @RequestParam("userId") String userId)
            throws IOException {
        List<String> fileIds = new ArrayList<>();
        claimForm.setUserId(userId);


        for (MultipartFile file : files) {
            try {
                String fileId = claimFormService.storeFile(file.getInputStream(), file.getOriginalFilename(),
                        file.getContentType());
                fileIds.add(fileId);
                claimFormRepository.save(claimForm);
            } catch (IOException e) {
                // Handle exception if file upload fails
                System.out.println(e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(fileIds, HttpStatus.OK);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileId) throws IOException {
        GridFSFile gridFsFile = claimFormService.getFile(fileId);

        if (gridFsFile == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        byte[] fileData = claimFormService.getFileData(gridFsFile);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + gridFsFile.getFilename() + "\"")
                .body(fileData);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable String fileId) {
        claimFormService.deleteFile(fileId);
        return new ResponseEntity<>(HttpStatus.OK);  
    }
    

}

