package com.ezban.cart.model;

import org.springframework.data.annotation.Id;

import java.util.List;

//@RedisHash("Cart")
public class GetCartItemsResponse {

    @Id
    private Integer memberNo;
    private List<CartItems> items;

    public GetCartItemsResponse() {
    }

    public GetCartItemsResponse(Integer memberNo, List<CartItems> items) {
        this.memberNo = memberNo;
        this.items = items;
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public List<CartItems> getItems() {
        return items;
    }

    public void setItems(List<CartItems> items) {
        this.items = items;
    }
}
