package com.ezban.fieldExample.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FieldExampleRepository extends MongoRepository<FieldExample,String> {
}
