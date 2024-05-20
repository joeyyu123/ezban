package com.ezban.ticketregistration;

import com.ezban.event.model.Event;
import com.ezban.event.model.Service.EventService;
import com.ezban.fieldExample.model.FieldExample;
import com.ezban.registrationform.model.RegistrationFormService;
import com.ezban.ticketregistration.dto.Person;
import com.ezban.ticketregistration.dto.Response;
import com.ezban.ticketregistration.model.TicketRegistration;
import com.ezban.ticketregistration.model.TicketRegistrationService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpHeaders;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class BackstageTicketRegistrationController {

    @Autowired
    private TicketRegistrationService ticketRegistrationService;

    @Autowired
    private RegistrationFormService registrationFormService;

    @Autowired
    EventService eventService;

    @GetMapping("/backstage/events/{eventNo}/ticket-registrations")
    public String showTicketRegistrations(Principal principal, Model model, @PathVariable("eventNo") String eventNo) {

        Event event = eventService.findById(Integer.valueOf(eventNo));
        if (!eventService.isAuthenticated(principal, event)){
            model.addAttribute("message", "You are not authorized to access this page.");
            return "/backstage/event/warning";
        }

        List<TicketRegistration> ticketRegistrations = ticketRegistrationService.findAllByEventNo(eventNo);
        if(registrationFormService.findByEventNo(eventNo) == null){
            model.addAttribute("message", "該活動並沒有建立報名表單，無法查看報名資料。");
            return "/backstage/event/warning";
        }
        List<FieldExample> questions = registrationFormService.findByEventNo(eventNo).getQuestions();

        model.addAttribute("questions", questions);
        model.addAttribute("ticketRegistrations", ticketRegistrations);
        model.addAttribute("event", event);

        return "/backstage/event/ticket-registrations";
    }

    /**
     * 下載報名資料的CSV檔案
     */
    @GetMapping("/backstage/events/{eventNo}/ticket-registrations/download")
    @ResponseBody
    public ResponseEntity<byte[]> downloadTicketRegistrations(Principal principal, Model model, @PathVariable("eventNo") String eventNo) throws IOException {

        Event event = eventService.findById(Integer.valueOf(eventNo));
        if (!eventService.isAuthenticated(principal, event)){
            model.addAttribute("message", "You are not authorized to access this page.");
            return ResponseEntity.status(403).body(null);
        }

        List<TicketRegistration> ticketRegistrations = ticketRegistrationService.findAllByEventNo(eventNo);
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
