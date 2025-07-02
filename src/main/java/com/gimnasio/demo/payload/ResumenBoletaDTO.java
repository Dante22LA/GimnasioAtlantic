package com.gimnasio.demo.payload;

import java.math.BigDecimal;

public class ResumenBoletaDTO {
    private String documento;
    private BigDecimal totalGastado;
    private int cantidadPagos;

    public ResumenBoletaDTO(String documento, BigDecimal totalGastado, int cantidadPagos) {
        this.documento = documento;
        this.totalGastado = totalGastado;
        this.cantidadPagos = cantidadPagos;
    }

    public String getDocumento() {
        return documento;
    }

    public BigDecimal getTotalGastado() {
        return totalGastado;
    }

    public int getCantidadPagos() {
        return cantidadPagos;
    }
}

