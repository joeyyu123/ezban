package com.ezban.fieldExample.model;

import com.ezban.event.model.ServiceDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FieldExampleService {

    @Autowired
    private FieldExampleRepository repository;



    public List<FieldExample> findAllSortByIdNumber() {
        return repository.findAll(Sort.by("idNumber"));
    }
}
