package com.perficient.empmanagementsystem.exception;

public class CignaBaseException extends RuntimeException{
    private static final long serialVersionUID = 8805230401942234462L;
    CignaBaseException(){
        super();
    }
    CignaBaseException(String message){
        super(message);
    }


}
