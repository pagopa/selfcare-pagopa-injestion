package it.pagopa.selfcare.pagopa.injestion.core;

import feign.FeignException;
import it.pagopa.selfcare.pagopa.injestion.api.mongo.ECPTRelationshipConnector;
import it.pagopa.selfcare.pagopa.injestion.api.rest.ExternalApiConnector;
import it.pagopa.selfcare.pagopa.injestion.constant.WorkStatus;
import it.pagopa.selfcare.pagopa.injestion.mapper.ECPTRelationshipMapper;
import it.pagopa.selfcare.pagopa.injestion.model.csv.ECPTRelationshipModel;
import it.pagopa.selfcare.pagopa.injestion.model.dto.Delegation;
import it.pagopa.selfcare.pagopa.injestion.model.dto.ECPTRelationship;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static it.pagopa.selfcare.pagopa.injestion.core.util.MigrationUtil.createDelegation;

@Service
@Slf4j
class DelegationServiceImpl implements DelegationService {

    private final MigrationService migrationService;
    private final ECPTRelationshipConnector ecptRelationshipConnector;
    private final ExternalApiConnector externalApiConnector;

    @Value("${app.local.ecptrelationship}")
    private String csvPath;

    @Value("${app.pageSize}")
    private int pageSize;

    public DelegationServiceImpl(
            MigrationService migrationService,
            ECPTRelationshipConnector ecptRelationshipConnector,
            ExternalApiConnector externalApiConnector) {
        this.migrationService = migrationService;
        this.ecptRelationshipConnector = ecptRelationshipConnector;
        this.externalApiConnector = externalApiConnector;
    }
    @Override
    public void persistECPTRelationship() {
        migrationService.migrateEntities(ECPTRelationshipModel.class, csvPath, ecptRelationshipConnector::save, ECPTRelationshipMapper::convertModelToDto);
    }


    @Override
    public void migrateECPTRelationship() {
        log.info("Starting migration of ECPTRelationship");
        int page = 0;
        boolean hasNext = true;
        do {
            List<ECPTRelationship> ecptRelationships = ecptRelationshipConnector.findAllByStatus(page, pageSize, WorkStatus.NOT_WORKED.name());
            if (!CollectionUtils.isEmpty(ecptRelationships)) {
                ecptRelationships.forEach(this::migrateECPTRelationship);
            } else {
                hasNext = false;
            }
        } while (Boolean.TRUE.equals(hasNext));
        log.info("Completed migration of ECPTRelationship");
    }

    private void migrateECPTRelationship(ECPTRelationship ecptRelationship) {
        Delegation delegation = createDelegation(ecptRelationship);
        try {
            externalApiConnector.createDelegation(delegation);
            ecptRelationship.setWorkStatus(WorkStatus.DONE);
        }catch (FeignException e){
            ecptRelationship.setWorkStatus(WorkStatus.ERROR);
            ecptRelationship.setCreateHttpStatus(e.status());
        }
        ecptRelationshipConnector.save(ecptRelationship);
    }
}
