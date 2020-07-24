package com.pccw.cloud.consumerapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOfferDto {
    private String offerId;
    private String brand;
    private String type;
    private String source;
    private Long publishTime;
}
