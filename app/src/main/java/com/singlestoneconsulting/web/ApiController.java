package com.singlestoneconsulting.web;

import com.singlestoneconsulting.participant.Participant;
import com.singlestoneconsulting.participant.ParticipantRepository;
import com.singlestoneconsulting.sms.SmsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Set;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    private static final String MSG_REPLY = "Thanks %s! Check it out: %s";
    private static final String MSG_DEPLOY = "Hey %s, check out the new version: %s";
    private static final String URL = "http://singlestonedemo.com";

    private final ParticipantRepository participantRepository;
    private final SmsService smsService;

    @Autowired
    public ApiController(ParticipantRepository participantRepository, SmsService smsService) {
        this.participantRepository = participantRepository;
        this.smsService = smsService;
    }

    @RequestMapping(value = "/participants", method = GET, produces = APPLICATION_JSON_VALUE)
    public Set<Participant> participants() {
        return participantRepository.all();
    }

    @RequestMapping(value = "/broadcast", method = POST)
    public ResponseEntity<String> broadcast(String message) {
        for (Participant p : participantRepository.all()) {
            smsService.sendText(p.getPhoneNumber(), message);
        }
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @RequestMapping(value = "/twilio", method = RequestMethod.POST)
    public ResponseEntity<String> register(@RequestParam("From") String from,
                                           @RequestParam(value = "Body", required = false) String body,
                                           @RequestParam(value = "MediaUrl0", required = false) String selfieUrl) {
        Participant participant = participantRepository.get(from);
        if (participant == null) {
            participant = new Participant(from);
        }

        String name = StringUtils.isNotBlank(body) ? body
                    : StringUtils.isNotBlank(participant.getName()) ? participant.getName()
                    : from;
        participant.setName(name);

        if (StringUtils.isNotBlank(selfieUrl)) {
            participant.setSelfieUrl(selfieUrl);
        }

        participantRepository.save(participant);

        return sendXml(smsService.getResponse(String.format(MSG_REPLY, participant.getName(), URL)));
    }

    @PostConstruct
    public void postConstruct() {
        for (Participant p : participantRepository.all()) {
            smsService.sendText(p.getPhoneNumber(), String.format(MSG_DEPLOY, p.getName(), URL));
        }
    }

    private ResponseEntity<String> sendXml(String xml) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        return new ResponseEntity<>(xml, headers, HttpStatus.OK);
    }
}
