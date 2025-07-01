package com.gimnasio.demo.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class PagoRequest {

    @NotBlank(message = "El documento del usuario es obligatorio")
    private String documento;

    @Min(value = 1, message = "El ID del plan debe ser mayor a 0")
    private int idPlan;

    @NotNull(message = "El monto no puede ser nulo")
    private BigDecimal monto;

    // Constructor vacío requerido por Spring
    public PagoRequest() {
    }

    // Constructor completo
    public PagoRequest(String documento, int idPlan, BigDecimal monto) {
        this.documento = documento;
        this.idPlan = idPlan;
        this.monto = monto;
    }

    // Getters y setters
    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    @Override
    public String toString() {
        return "PagoRequest{" +
                "documento='" + documento + '\'' +
                ", idPlan=" + idPlan +
                ", monto=" + monto +
                '}';
    }
}


