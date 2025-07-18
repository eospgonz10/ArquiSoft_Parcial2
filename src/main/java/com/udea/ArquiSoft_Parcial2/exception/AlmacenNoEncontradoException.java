package com.udea.ArquiSoft_Parcial2.exception;

public class AlmacenNoEncontradoException extends RuntimeException {
    
    public AlmacenNoEncontradoException(String mensaje) {
        super(mensaje);
    }
    
    public AlmacenNoEncontradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
