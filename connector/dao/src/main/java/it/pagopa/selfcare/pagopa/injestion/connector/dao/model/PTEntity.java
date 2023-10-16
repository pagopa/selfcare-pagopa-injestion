package it.pagopa.selfcare.pagopa.injestion.connector.dao.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Sharded;

@Data
@NoArgsConstructor
@Document("PT")
@Sharded(shardKey = {"id"})
@FieldNameConstants(asEnum = true)
public class PTEntity {

    @Id
    private String id;
    private String businessName;
    private String taxCode;
    private String registeredOffice;
    private String zipCode;
    private String digitalAddress;
    private String vatNumber;

}
