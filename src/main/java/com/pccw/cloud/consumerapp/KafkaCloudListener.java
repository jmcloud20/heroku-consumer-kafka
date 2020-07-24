package com.pccw.cloud.consumerapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pccw.cloud.consumerapp.model.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@EnableKafka
@Slf4j
@Component
public class KafkaCloudListener {

    //    @KafkaListener(topics="CUST_optOut_optIn, CUST_update_email, PROD_offer", groupId = "")
//    public void readMessage(String message){
//        log.info("Message Retrieved: "+ message);
//    }
    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "CUST_optOut_optIn", groupId = "customerOpt", containerFactory = "optContainerFactory")
    public void readOpt(String message) throws JsonProcessingException {
        this.computeTransportTime(message);
        log.info("Opt In-Out message: " + message);
    }

    @KafkaListener(topics = "CUST_update_email", groupId = "updateEmail", containerFactory = "updateEmailFactory")
    public void readUpdateEmail(String message) throws JsonProcessingException {
        this.computeTransportTime(message);
        log.info("Update email message: " + message);
    }

    @KafkaListener(topics = "PROD_offer", groupId = "productOffer", containerFactory = "prodOfferFactory")
    public void readProdffer(String message) throws JsonProcessingException {
        this.computeTransportTime(message);
        log.info("Product offer message: " + message);
    }

    private void computeTransportTime(String brokerMessage) throws JsonProcessingException {
        MessageDto messageDto = objectMapper.readValue(brokerMessage, MessageDto.class);
        Map<String, Object> message = (Map<String, Object>) messageDto.getMessage();
        Long publishTime = (Long) message.get("publishTime");
        Long transportTime = new Date().getTime() - publishTime;
        log.info("Transport Time: " + transportTime.toString() + " milli seconds");
    }


}
