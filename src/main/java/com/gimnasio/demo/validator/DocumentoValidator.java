package com.gimnasio.demo.validator;

public class DocumentoValidator {

    public static boolean esDNIValido(String dni) {
        return dni != null && dni.matches("\\d{8}");
    }
}
