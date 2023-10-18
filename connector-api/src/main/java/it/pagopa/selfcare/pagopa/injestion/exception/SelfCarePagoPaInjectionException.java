package it.pagopa.selfcare.pagopa.injestion.exception;

import lombok.Getter;

@Getter
public class SelfCarePagoPaInjectionException extends RuntimeException {

    private final int code;

    public SelfCarePagoPaInjectionException(String message, int code){
        super(message);
        this.code = code;
    }

}
