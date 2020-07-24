package com.pccw.cloud.consumerapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class MessageDto {
    private String topic;
    private String desc;
    private Object message;
}
