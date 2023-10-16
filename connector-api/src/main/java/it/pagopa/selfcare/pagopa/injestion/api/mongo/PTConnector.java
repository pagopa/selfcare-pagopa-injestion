package it.pagopa.selfcare.pagopa.injestion.api.mongo;

import it.pagopa.selfcare.pagopa.injestion.model.dto.PT;

import java.util.List;

public interface PTConnector {

    List<PT> findAll(int page, int pageSize);

    List<PT> findAll();

    PT findById(String id);

    PT save(PT pt);

    List<PT> saveAll(List<PT> ptList);

    void deleteById(String id);

    void delete(PT pt);

}
