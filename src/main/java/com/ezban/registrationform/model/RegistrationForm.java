package com.ezban.registrationform.model;

import com.ezban.fieldExample.model.FieldExample;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "registrationForm")
public class RegistrationForm {

    @Id
    private String id;
    private String eventNo;
    private String name;
    private List<FieldExample> questions;
}
