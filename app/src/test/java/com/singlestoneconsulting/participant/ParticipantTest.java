package com.singlestoneconsulting.participant;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

import java.util.Date;

public class ParticipantTest {

    @Test
    public void testParticipant() {
        Date date = new Date();
        Participant p = new Participant();
        p.setName("Foo");
        p.setPhoneNumber("5551212");
        p.setRegistered(date);

        assertThat(p.getName(), is("Foo"));
        assertThat(p.getPhoneNumber(), is("5551212"));
        assertThat(p.getRegistered(), is(date));
    }

    @Test
    public void testEquals() {
        Participant p1 = new Participant("1234");
        Participant p2 = new Participant("1234");

        assertThat(p1, is(p2));
    }
}
