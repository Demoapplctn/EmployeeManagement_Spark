package com.perficient.empmanagementsystem.exception;

public class DuplicateEntryException extends CignaBaseException{
    private static final long serialVersionUID = 590803464812880611L;
    public DuplicateEntryException(String message){
        super(message);
    }

}
