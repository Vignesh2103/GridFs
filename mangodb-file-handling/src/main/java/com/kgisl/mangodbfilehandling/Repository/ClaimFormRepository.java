package com.kgisl.mangodbfilehandling.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kgisl.mangodbfilehandling.Model.ClaimForm;

public interface ClaimFormRepository extends MongoRepository<ClaimForm, String> {

}
