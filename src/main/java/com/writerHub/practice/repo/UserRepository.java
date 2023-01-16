package com.writerHub.practice.repo;

import com.writerHub.practice.models.WriterHubUser;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<WriterHubUser,Long> {

    public  WriterHubUser findByUsername(String username);
}
