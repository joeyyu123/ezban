package com.ezban.registrationform.model;

import com.ezban.fieldExample.model.FieldExample;
import com.ezban.ticketorder.model.dto.Question;
import com.ezban.ticketorder.model.dto.TicketOrderRegistrationForm;
import com.ezban.ticketorderdetail.model.TicketOrderDetail;
import com.ezban.ticketorderdetail.model.TicketOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationFormService {
    @Autowired
    RegistrationFormRepository registrationFormRepository;

    @Autowired
    TicketOrderDetailService ticketOrderDetailService;

    public RegistrationForm findByEventNo(String eventNo) {
        return registrationFormRepository.getByEventNo(eventNo);
    }

    public void save(RegistrationForm registrationForm) {
        registrationFormRepository.save(registrationForm);
    }

    public List<TicketOrderRegistrationForm> getTicketOrderRegistrationForms(Integer eventNo, Integer orderNo) {
        RegistrationForm registrationForm = this.findByEventNo(String.valueOf(eventNo));

        // 將活動報名表轉換成會員填寫的報名表物件
        List<Question> ticketOrderRegistrationFormQuestions = new ArrayList<>();
        List<FieldExample> questions = registrationForm.getQuestions();
        questions.forEach(question -> {
            Question ticketOrderRegistrationFormQuestion = new Question();
            ticketOrderRegistrationFormQuestion.setComponent(question.getComponent());
            ticketOrderRegistrationFormQuestion.setLabel(question.getLabel());
            ticketOrderRegistrationFormQuestion.setDescription(question.getDescription());
            ticketOrderRegistrationFormQuestion.setOptions(question.getOptions());
            ticketOrderRegistrationFormQuestion.setRequired(question.isRequired());
            ticketOrderRegistrationFormQuestions.add(ticketOrderRegistrationFormQuestion);
        });


        List<TicketOrderRegistrationForm> ticketOrderRegistrationForms = new ArrayList<>();
        List<TicketOrderDetail> ticketOrderDetails = ticketOrderDetailService.findByOrderNo(orderNo);
        ticketOrderDetails.forEach(ticketOrderDetail -> {
            TicketOrderRegistrationForm ticketOrderRegistrationForm = new TicketOrderRegistrationForm();
            ticketOrderRegistrationForm.setTicketTypeNo(String.valueOf(ticketOrderDetail.getTicketType().getTicketTypeNo()));
            ticketOrderRegistrationForm.setTicketTypeName(ticketOrderDetail.getTicketType().getTicketTypeName());
            ticketOrderRegistrationForm.setTicketTypeQty(String.valueOf(ticketOrderDetail.getTicketTypeQty()));
            ticketOrderRegistrationForm.setIncludedTickets(String.valueOf(ticketOrderDetail.getIncludedTicketQty()));
            ticketOrderRegistrationForm.setQuestions(ticketOrderRegistrationFormQuestions);
            ticketOrderRegistrationForms.add(ticketOrderRegistrationForm);
        });

        return ticketOrderRegistrationForms;
    }
}
