package com.pokemonreview.exception;

public class ResourceNotFoundException extends RuntimeException{
    //RuntimeException class use serialization
    private static final long serialVerisionUID = 1;

    public ResourceNotFoundException(String message){
        super(message);
    }

}
