package com.singlestoneconsulting.participant;

import java.util.Set;

public interface ParticipantRepository {

    Participant get(String phoneNumber);

    void save(Participant participant);

    Set<Participant> all();
}
