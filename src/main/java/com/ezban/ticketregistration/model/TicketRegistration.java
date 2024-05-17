package com.ezban.ticketregistration.model;

import com.ezban.ticketregistration.dto.Person;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;


@NoArgsConstructor
@Data
@Document(collection="ticketRegistration")
public class TicketRegistration {
    @Id
    private String id;
    private String eventNo;
    private String ticketOrderNo;

    private List<Person> persons;
}







