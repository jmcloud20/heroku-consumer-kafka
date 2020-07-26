package com.pccw.cloud.consumerapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pccw.cloud.consumerapp.model.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Slf4j
public class MessageService {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private ObjectMapper objectMapper;

    private List<MessageDto> messages;

    public void save(String source, MessageDto messageDto) {
        log.info("Retrieve list from context.");
        List<MessageDto> messages = (List<MessageDto>) servletContext.getAttribute(source);
        messages = messages == null ? new LinkedList() : messages;

        log.info("Saving message to list.");
        messages.add(messageDto);

        servletContext.setAttribute(source, messages);

    }

    public MessageDto createMessageDto(String brokerMessage) throws JsonProcessingException {
        MessageDto messageDto = objectMapper.readValue(brokerMessage, MessageDto.class);
        Map<String, Object> message = (Map<String, Object>) messageDto.getMessage();
        Long publishTime = (Long) message.get("publishTime");
        Long transportTime = new Date().getTime() - publishTime;
        log.info("Transport Time: " + transportTime.toString() + " milli seconds");
        messageDto.setTransportTime(transportTime);
        messageDto.setDateReceived(formatHKTDate());
        return messageDto;
    }

    public String formatHKTDate() {
        log.info("Set format for date.");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz YYYY");

        log.info("Set timezone to Asia/Hong Kong");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Hong_Kong"));

        log.info("Generate formatted date.");
        return sdf.format(Calendar.getInstance().getTime());
    }

}
