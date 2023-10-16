package it.pagopa.selfcare.pagopa.injestion.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Institution{

    @JsonProperty("id")
    private String id;

    @JsonProperty("originId")
    private String originId;

    @JsonProperty("o")
    private String o;

    @JsonProperty("ou")
    private String ou;

    @JsonProperty("aoo")
    private String aoo;

    @JsonProperty("taxCode")
    private String taxCode;

    @JsonProperty("category")
    private String category;

    @JsonProperty("description")
    private String description;

    @JsonProperty("digitalAddress")
    private String digitalAddress;

    @JsonProperty("address")
    private String address;

    @JsonProperty("zipCode")
    private String zipCode;

    @JsonProperty("origin")
    private Origin origin;

    @JsonProperty("istatCode")
    private String istatCode;

}
