$(document).ready(function () {
    let totalPrice = 0;
    let totalQty = 0;

    // 當使用者選擇票種數量時，更新總票券價格和數量
    let updateTotalPriceAndQty = function () {
        totalPrice = 0;
        totalQty = 0;
        $(".ticket-type").each(function () {
            let qty = parseInt($(this).find("input[name='ticketTypeQty']").val());
            let price = parseFloat($(this).find("#ticketTypePrice").val());
            totalQty += qty;
            totalPrice += qty * price;
        });
        $(".total-price").text('共 ' + totalQty + ' 張，' + 'NT$ ' + totalPrice);

        if (totalQty === 0) {
            $("#buyTicketBtn").prop("disabled", true);
        } else {
            $("#buyTicketBtn").prop("disabled", false);
        }
    }

    // 增加票券數量按鈕
    $("button.btn-plus").click(function () {
        let ticketType = $(this).closest(".ticket-type");
        $(ticketType).find(".quantity-info").removeClass("hidden");
        let limitQty = parseInt($(ticketType).find("#ticketTypeLimitQty").val());
        let qty = parseInt($(ticketType).find("input[name='ticketTypeQty']").val())
        if (qty >= limitQty) {
            return;
        }
        qty++;
        $(ticketType).find("input[name='ticketTypeQty']").val(qty);
        $(ticketType).find("#ticketTypeQtyLabel").text(qty);

        updateTotalPriceAndQty();
    });


    // 減少票券購買數量按鈕
    $("button.btn-minus").click(function () {
        let ticketType = $(this).closest(".ticket-type");
        let qty = parseInt($(ticketType).find("input[name='ticketTypeQty']").val())

        if (qty === 1) {
            $(ticketType).find(".quantity-info").toggleClass("hidden");
        }

        if (qty > 0) {
            qty--;
            $(ticketType).find("input[name='ticketTypeQty']").val(qty);
            $(ticketType).find("#ticketTypeQtyLabel").text(qty);
        }

        updateTotalPriceAndQty();
    });

    // 當使用者按下立即購票時，將會送出Ajax請求，取得購買資訊建立訂單並產生報名表單
    $("#buyTicketBtn").click(function (event) {
        event.preventDefault(); // 防止表單自動提交

        let ticketOrders = [];
        $(".ticket-type").each(function () {
            let eventNo = $(this).find("input[name='eventNo']").val();
            let ticketTypeNo = $(this).find("input[name='ticketTypeNo']").val();
            let ticketTypeQty = $(this).find("input[name='ticketTypeQty']").val();
            if (ticketTypeQty > 0) {
                ticketOrders.push({
                    eventNo: eventNo,
                    ticketTypeNo: ticketTypeNo,
                    ticketTypeQty: ticketTypeQty
                });
            }
        });

        $.ajax({
            url: "/events/order",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(ticketOrders),
            success: function (response) {
                // 清空
                let div_container = $("div.event-container");
                div_container.empty();

                // 產生class==form的div，並放入報名表單
                let form = $("<form id='registrationForm' class='form' ></form>");
                let object = JSON.parse(response);
                let eventNo = object.eventNo;
                let ticketOrderNo = object.ticketOrderNo;
                let ticketTypes = object.ticketOrderRegistrationForms;
                form.append("<input type='hidden' name='eventNo' value='" + eventNo + "'>");
                form.append("<input type='hidden' name='ticketOrderNo' value='" + ticketOrderNo + "'>");
                form.append("<h2>報名表單</h2>");
                form.append("<h3>每個報名的人都需要填寫一份資料，請填寫完畢後再送出。</h3>");

                let nameQuestionCount = 0;
                let textInputQuestionCount = 0;
                let textAreaQuestionCount = 0;
                let emailQuestionCount = 0;
                let phoneQuestionCount = 0;
                let dateQuestionCount = 0;
                let selectQuestionCount = 0;
                let checkboxQuestionCount = 0;
                let radioQuestionCount = 0;

                // 產生報名表單
                ticketTypes.forEach(function (ticketType) {
                    for (let i = 0; i < ticketType.ticketTypeQty; i++) {
                        for (let j = 0; j < ticketType.includedTickets; j++) {
                            let formElement = $("<div class='form-element'></div>");

                            let label = $("<h4></h4>").text("第" + (i + 1) + "張「" + ticketType.ticketTypeName + "」 - 人員" + (j + 1));
                            formElement.append(label);

                            let questions = ticketType.questions;

                            let question_div;
                            let question_label;
                            let input;

                            for (let question of questions) {
                                question_div = $("<div class='question'></div>");
                                question_label = $("<label></label>").text(question.label);
                                switch (question.component) {
                                    case "name":
                                        nameQuestionCount++;
                                        input = $("<input type='text' class='form-control' name='" + question.component + nameQuestionCount + "'>");
                                        break;
                                    case "textInput":
                                        textInputQuestionCount++;
                                        input = $("<input type='text' class='form-control' name='" + question.component + textInputQuestionCount + "'>");
                                        break;
                                    case "email":
                                        emailQuestionCount++;
                                        input = $("<input type='email' class='form-control' name='" + question.component + emailQuestionCount + "'>");
                                        break;
                                    case "phone":
                                        phoneQuestionCount++;
                                        input = $("<input type='tel' class='form-control' name='" + question.component + phoneQuestionCount + "'>");
                                        break;
                                    case "select":
                                        selectQuestionCount++;
                                        input = $("<select class='form-control' name='" + question.component + selectQuestionCount + "'>");
                                        question.options.forEach(function (option) {
                                            let optionElement = $("<option></option>").text(option);
                                            input.append(optionElement);
                                        });
                                        break;
                                    case "checkbox":
                                        checkboxQuestionCount++;
                                        input = $("<div></div>");
                                        question.options.forEach(function (option, index) {
                                            let checkbox = $("<input type='checkbox' name='" + question.component + checkboxQuestionCount + "' value='" + option + "'>");
                                            let optionLabel = $("<label></label>").text(option);
                                            optionLabel.prepend(checkbox);
                                            input.append(optionLabel);
                                            input.append("<br>")
                                        });
                                        break;
                                    case "radio":
                                        radioQuestionCount++;
                                        input = $("<div></div>");
                                        question.options.forEach(function (option, index) {
                                            let radio = $("<input type='radio' name='" + question.component + radioQuestionCount + "' value='" + option + "'>");
                                            let optionLabel = $("<label></label>").text(option);
                                            optionLabel.prepend(radio);
                                            input.append(optionLabel);
                                            input.append("<br>")
                                        });
                                        break;
                                    case "textArea":
                                        textAreaQuestionCount++;
                                        input = $("<textarea class='form-control' name='" + question.component + textAreaQuestionCount + "'></textarea>");
                                        break;
                                    case "date":
                                        dateQuestionCount++;
                                        input = $("<input type='date' class='form-control' name='" + question.component + dateQuestionCount + "'>");
                                        break;
                                }
                                // formElement.append("<br>")


                                question_div.append(question_label);
                                if (question.required === true) {
                                    question_div.append("<span style='color:red'>*</span>");
                                    input.attr("required", true);
                                }

                                question_div.append(input); // 將 input 添加到 div.question
                                formElement.append(question_div); // 將 div.question 添加到 formElement

                            }

                            form.append(formElement);
                        }
                    }
                });

                form.append("<button type='submit' id='saveRegistrationFormBtn' class='btn btn-primary btn-block'>下一步</button>")
                div_container.append(form);

            },
            error: function (error) {
                // 處理錯誤的回應
                alert("伺服器忙碌中，請稍後再試。")
            }
        });
    });


    // 當使用者點選下一步，送出報名資料
    $(document).on("submit", "#registrationForm", function (e) {
        e.preventDefault();

        var formData = {
            eventNo: $("input[name='eventNo']").val(),
            ticketOrderNo: $("input[name='ticketOrderNo']").val(),
            persons: []
        };

        $(".form-element").each(function (index) {
            var person = {
                name: null,
                email: null,
                phone: null,
                responses: []
            };

            $(this).find('.question').each(function () {
                var question = $(this).children('label').text();
                var response = {
                    question: question,
                    response: [],
                    component: null
                };

                $(this).find('input, select, textarea').each(function () {
                    var name = $(this).attr('name');
                    var value = $(this).val();
                    if (name.startsWith('name')) {
                        person.name = value;
                        response.response.push(value);

                    } else if (name.startsWith('email')) {
                        person.email = value;
                        response.response.push(value);

                    } else if (name.startsWith('phone')) {
                        person.phone = value;
                        response.response.push(value);

                    } else {
                        response.component = name.match(/[a-zA-Z]+/)[0]; // 去掉問題類型後面的數字
                        if ($(this).is(':checkbox') || $(this).is(':radio')) {
                            if ($(this).is(':checked')) {
                                response.response.push(value);
                            }
                        } else {
                            response.response.push(value);
                        }
                    }
                });

                person.responses.push(response);
            });

            formData.persons.push(person);
        });

        // 以Ajax送出報名資料
        $.ajax({
            url: "/events/registrations",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(formData),
            success: function (response) {
                alert("報名表送出成功！");
            },
            error: function (error) {
                // 處理錯誤的回應
                alert("伺服器忙碌中，請稍後再試。")
            }
        });
    });

});