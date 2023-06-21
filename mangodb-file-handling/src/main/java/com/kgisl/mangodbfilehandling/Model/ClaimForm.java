package com.kgisl.mangodbfilehandling.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "claimForms")
@Component
public class ClaimForm {

    @Id
    private String userId;
    private String claimType;
    private String fileId;
    private String filename;
    private String contentType;
    private long fileSize;

    // Constructors, getters, and setters
}
