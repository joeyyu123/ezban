$(document).ready(function () {
    let totalPrice = 0;
    let totalQty = 0;
    let updateTotalPriceAndQty = function() {
        totalPrice = 0;
        totalQty = 0;
        $(".ticket-type").each(function() {
            let qty = parseInt($(this).find("input[name='ticketTypeQty']").val());
            let price = parseFloat($(this).find("#ticketTypePrice").val());
            totalQty += qty;
            totalPrice += qty * price;
        });
        $(".total-price").text('共 ' + totalQty + ' 張，'+'NT$ ' + totalPrice);

        if(totalQty === 0){
            $("#buyTicketBtn").prop("disabled", true);
        } else {
            $("#buyTicketBtn").prop("disabled", false);
        }
    }

    $("button.btn-plus").click(function() {
        let ticketType = $(this).closest(".ticket-type");
        $(ticketType).find(".quantity-info").removeClass("hidden");
        let qty = parseInt($(ticketType).find("input[name='ticketTypeQty']").val())
        qty++;
        $(ticketType).find("input[name='ticketTypeQty']").val(qty);
        $(ticketType).find("#ticketTypeQtyLabel").text(qty);

        updateTotalPriceAndQty();
    });


    $("button.btn-minus").click(function() {
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


});