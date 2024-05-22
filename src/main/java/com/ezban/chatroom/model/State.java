package com.ezban.chatroom.model;

import java.util.Set;

public class State {
    private String type;
    // the member changing the state
    private String member;
    // total members
    private Set<String> members;

    public State(String type, String member, Set<String> members) {
        super();
        this.type = type;
        this.member = member;
        this.members = members;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public Set<String> getMembers() {
        return members;
    }

    public void setMembers(Set<String> members) {
        this.members = members;
    }

}
