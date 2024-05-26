package com.ezban.ticketregistration.model;

import com.ezban.fieldExample.model.FieldExample;
import com.ezban.registrationform.model.RegistrationFormService;
import com.ezban.ticketregistration.dto.Person;
import com.ezban.ticketregistration.dto.Response;
import com.google.gson.Gson;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class TicketRegistrationService {
    @Autowired
    TicketRegistrationRepository registrationFormRepository;

    @Autowired
    RegistrationFormService registrationFormService;


    public void save(TicketRegistration ticketRegistration) {
        registrationFormRepository.save(ticketRegistration);
    }

    public TicketRegistration saveAll(String request) {
        Gson gson = new Gson();
        TicketRegistration ticketRegistration = gson.fromJson(request, TicketRegistration.class);
        ticketRegistration = registrationFormRepository.save(ticketRegistration);

        return ticketRegistration;
    }

    public List<TicketRegistration> findAllByEventNo(String eventNo){

        return registrationFormRepository.findAllByEventNo(eventNo);
    }

    public void deleteByTicketOrderNo(String ticketNo) {
        registrationFormRepository.deleteByTicketOrderNo(ticketNo);
    }

    public ResponseEntity<byte[]> downloadTicketRegistrations(String eventNo) throws IOException {

        List<TicketRegistration> ticketRegistrations = findAllByEventNo(eventNo);

        List<FieldExample> questions = registrationFormService.findByEventNo(eventNo).getQuestions();



        // 創建Excel文件

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Ticket Registrations");



        // 創建標題行

        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < questions.size(); i++) {

            Cell cell = headerRow.createCell(i);

            cell.setCellValue(questions.get(i).getLabel());

        }



        // 填充數據

        int rowNum = 1;

        for (TicketRegistration registration : ticketRegistrations) {

            List<Person> persons = registration.getPersons();

            for (Person person : persons) {

                Row row = sheet.createRow(rowNum++);



                List<Response> responses = person.getResponses();

                for (int i = 0; i < responses.size(); i++) {

                    row.createCell(i).setCellValue(responses.get(i).parseResponse());

                }

            }

        }



        // 將Excel文件寫入ByteArrayOutputStream

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {

            workbook.write(bos);

        } catch (IOException e) {

            throw new RuntimeException(e);

        }

        workbook.close();



        // 準備ResponseEntity

        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", "attachment; filename=ticket_registrations.xlsx");

        return ResponseEntity.ok().headers(headers).body(bos.toByteArray());

    }
}


