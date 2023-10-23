package it.pagopa.selfcare.pagopa.injestion.core.util;

import it.pagopa.selfcare.commons.base.security.PartyRole;
import it.pagopa.selfcare.commons.base.utils.InstitutionType;
import it.pagopa.selfcare.pagopa.injestion.model.dto.*;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MigrationUtil {

    private static BillingData fillBillingDataFromInstitutionAndEC(String digitalAddress, String zipCode, EC ec) {
        BillingData billingData = new BillingData();
        billingData.setBusinessName(ec.getBusinessName());
        billingData.setDigitalAddress(digitalAddress);
        billingData.setRecipientCode(ec.getRecipientCode());
        billingData.setRegisteredOffice(ec.getRegisteredOffice());
        billingData.setTaxCode(ec.getTaxCode());
        billingData.setZipCode(zipCode);
        billingData.setVatNumber(ec.getVatNumber());
        return billingData;
    }

    public static AutoApprovalOnboarding constructOnboardingDto(EC ec, String origin, List<User> users) {
        AutoApprovalOnboarding onboarding = new AutoApprovalOnboarding();
        List<UserToOnboard> userToOnboards = new ArrayList<>();
        onboarding.setBillingData(fillBillingDataFromInstitutionAndEC(ec.getDigitalAddress(), ec.getZipCode(), ec));
        onboarding.setInstitutionType(InstitutionType.PA);
        onboarding.setGeographicTaxonomies(List.of());
        onboarding.setOrigin(origin);
        PspData pspData = new PspData();
        pspData.setDpoData(new DpoData());
        onboarding.setPspData(pspData);
        onboarding.setCompanyInformations(new CompanyInformations());
        onboarding.setAssistanceContacts(new AssistanceContacts());
        if(!CollectionUtils.isEmpty(users)) {
            userToOnboards = users.stream()
                    .map(user -> {
                        UserToOnboard userToSend = new UserToOnboard();
                        userToSend.setTaxCode(user.getTaxCode());
                        userToSend.setName(user.getName());
                        userToSend.setSurname(user.getSurname());
                        userToSend.setEmail(user.getEmail());
                        userToSend.setRole(user.getRole() == Role.RP ? PartyRole.MANAGER : PartyRole.OPERATOR);
                        return userToSend;
                    }).collect(Collectors.toList());
        }

        onboarding.setUsers(userToOnboards);
        onboarding.setAssistanceContacts(new AssistanceContacts());
        return onboarding;
    }

}
