package com.jobportal.app.spring_rest_api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.jobportal.app.spring_rest_api.model.RevokedToken;

@Repository
public interface RevokedRepository extends CrudRepository<RevokedToken, String> {

}

