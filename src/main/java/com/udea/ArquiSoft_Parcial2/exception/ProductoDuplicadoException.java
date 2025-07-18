package com.udea.ArquiSoft_Parcial2.exception;

public class ProductoDuplicadoException extends RuntimeException {
    
    public ProductoDuplicadoException(String mensaje) {
        super(mensaje);
    }
    
    public ProductoDuplicadoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
