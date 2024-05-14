package com.ezban.fieldExample.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Data
@Document(collection = "fieldExample")
public class FieldExample implements Serializable {
    @Id
    private String id;
    private Integer idNumber;
    private String component;
    private String label;
    private String description;
    private String placeHolder;
    private List<String> options;
    private boolean required;
    private String validation;
    private Integer orderBy;


}