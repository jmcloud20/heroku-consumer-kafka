package com.pccw.cloud.consumerapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@EnableKafka
@Slf4j
@Component
public class KafkaCloudListener {

//    @KafkaListener(topics="CUST_optOut_optIn, CUST_update_email, PROD_offer", groupId = "")
//    public void readMessage(String message){
//        log.info("Message Retrieved: "+ message);
//    }

    @KafkaListener(topics="CUST_optOut_optIn", groupId = "customerOpt", containerFactory = "optContainerFactory")
    public void readOpt(String message){
        log.info("Opt In-Out message: "+message);
    }

    @KafkaListener(topics="CUST_update_email", groupId = "updateEmail", containerFactory = "updateEmailFactory")
    public void readUpdateEmail(String message){
        log.info("Update email message: "+message);
    }

    @KafkaListener(topics="PROD_offer", groupId = "productOffer", containerFactory = "prodOfferFactory")
    public void readProdffer(String message){
        log.info("Product offer message: "+message);
    }





}
