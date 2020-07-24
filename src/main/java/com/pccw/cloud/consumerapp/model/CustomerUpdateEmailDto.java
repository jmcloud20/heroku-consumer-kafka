package com.pccw.cloud.consumerapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerUpdateEmailDto {
    private String userId;
    private String oldEmail;
    private String newEmail;
    private String source;
    private Long publishTime;

}
