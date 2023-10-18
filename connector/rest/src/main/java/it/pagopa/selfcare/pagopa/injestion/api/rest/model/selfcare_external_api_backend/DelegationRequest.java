package it.pagopa.selfcare.pagopa.injestion.api.rest.model.selfcare_external_api_backend;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.pagopa.selfcare.pagopa.injestion.model.dto.DelegationType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DelegationRequest {

    @NotBlank
    @JsonProperty("from")
    private String from;

    @NotBlank
    @JsonProperty("to")
    private String to;

    @NotBlank
    @JsonProperty("institutionFromName")
    private String institutionFromName;

    @NotBlank
    @JsonProperty("institutionToName")
    private String institutionToName;

    @NotBlank
    @JsonProperty("productId")
    private String productId;

    @NotNull
    @JsonProperty("type")
    private DelegationType type;

}
