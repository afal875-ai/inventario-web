package co.edu.sena.inventario.service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ValidacionException extends RuntimeException {
    private final Map<String, String> errores;

    public ValidacionException(Map<String, String> errores) {
        super("Los datos del producto no son válidos");
        this.errores = Collections.unmodifiableMap(new LinkedHashMap<>(errores));
    }

    public Map<String, String> getErrores() {
        return errores;
    }
}
