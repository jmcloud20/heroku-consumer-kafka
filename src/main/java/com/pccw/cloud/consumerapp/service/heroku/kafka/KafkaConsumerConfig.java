package com.pccw.cloud.consumerapp.service.heroku.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@EnableKafka
@Configuration
@Component
public class KafkaConsumerConfig {

    private final DefaultConfig defaultConfig;

    public KafkaConsumerConfig(DefaultConfig defaultConfig) {
        this.defaultConfig = defaultConfig;
    }


    public ConsumerFactory<String, String> consumerFactory(String groupId) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, URISyntaxException {
        Map<String, Object> props = new HashMap<>();

        Properties properties = defaultConfig.getProperties();

        //consumer specific properties
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        properties.stringPropertyNames().forEach(name -> {
            props.put(name, properties.getProperty(name));
        });

        return new DefaultKafkaConsumerFactory<>(props);
    }


    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(String groupId) throws URISyntaxException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
        factory.setConsumerFactory(consumerFactory(groupId));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory optContainerFactory() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, URISyntaxException {
        return this.kafkaListenerContainerFactory("customerOpt");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory updateEmailFactory() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, URISyntaxException {
        return this.kafkaListenerContainerFactory("updateEmail");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory prodOfferFactory() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, URISyntaxException {
        return this.kafkaListenerContainerFactory("productOffer");
    }


}
