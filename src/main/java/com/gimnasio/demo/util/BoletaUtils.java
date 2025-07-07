package com.gimnasio.demo.util;

import com.gimnasio.demo.model.Boleta;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BoletaUtils {

    public static ListMultimap<String, Boleta> agruparPorUsuario(List<Boleta> boletas) {
        ListMultimap<String, Boleta> agrupadas = ArrayListMultimap.create();

        for (Boleta boleta : boletas) {
            agrupadas.put(boleta.getDocumentoUsuario(), boleta);
        }

        return agrupadas;
    }

    public static Date calcularFechaVencimiento(Date fechaInicio, int meses) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaInicio);
        cal.add(Calendar.MONTH, meses);
        return cal.getTime();
    }
}


