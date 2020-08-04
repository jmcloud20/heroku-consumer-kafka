package com.pccw.cloud.consumerapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pccw.cloud.consumerapp.model.MessageDto;
import com.pccw.cloud.consumerapp.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@EnableKafka
@Slf4j
@Component
public class KafkaCloudListener {

    public static final String CUSTOMER_OPT = "customerOpt";
    public static final String UPDATE_EMAIL = "updateEmail";
    public static final String PROD_OFFER = "productOffer";
    public static final String CUSTOMER_UPDATE = "customerUpdate";


    private final MessageService messageService;

    public KafkaCloudListener(MessageService messageService) {
        this.messageService = messageService;
    }

    @KafkaListener(topics = "CUST_optOut_optIn", groupId = "customerOpt", containerFactory = "optContainerFactory")
    public void readOpt(String message) throws JsonProcessingException {
        messageService.save(
                KafkaCloudListener.CUSTOMER_OPT,
                messageService.createMessageDto(message));
        log.info("Opt In-Out message: " + message);
    }

    @KafkaListener(topics = "CUST_update_email", groupId = "updateEmail", containerFactory = "updateEmailFactory")
    public void readUpdateEmail(String message) throws JsonProcessingException {
        messageService.save(
                KafkaCloudListener.UPDATE_EMAIL,
                messageService.createMessageDto(message));
        log.info("Update email message: " + message);
    }

    @KafkaListener(topics = "PROD_offer", groupId = "productOffer", containerFactory = "prodOfferFactory")
    public void readProdffer(String message) throws JsonProcessingException {
        messageService.save(
                KafkaCloudListener.PROD_OFFER,
                messageService.createMessageDto(message));
        log.info("Product offer message: " + message);
    }

    @KafkaListener(topics = "CustomerUpdate", groupId = "customerUpdate", containerFactory = "customerUpdateFactory")
    public void customerUpdate(String message) throws JsonProcessingException {
        MessageDto messageDto = new MessageDto();
        messageDto = messageService.createMessageDto(message);
        if(messageDto.getTopic().equals("CustomerUpdate")) {
            messageService.save(
                    KafkaCloudListener.CUSTOMER_UPDATE,
                    messageDto);
            log.info("Customer Update message: " + message);
        }
    }
}
