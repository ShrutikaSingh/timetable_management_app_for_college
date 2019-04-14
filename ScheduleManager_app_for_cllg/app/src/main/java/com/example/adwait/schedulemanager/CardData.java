package com.example.adwait.schedulemanager;

public class CardData {
    String slot, session, faculty;

    public CardData(String sl,String ses,String fac)
    {
        slot = sl;
        session = ses;
        faculty = fac;
    }

    public String getSlot() {
        return slot;
    }

    public String getSession() {
        return session;
    }

    public String getFaculty() {
        return faculty;
    }

    public CardData()
    {

    }

}
