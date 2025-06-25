package com.gimnasio.demo.validator;

import org.apache.commons.validator.GenericValidator;

public class DocumentoValidator {

    public static boolean esDNIValido(String dni) {
        return dni != null && dni.matches("\\d{8}");
    }
}
