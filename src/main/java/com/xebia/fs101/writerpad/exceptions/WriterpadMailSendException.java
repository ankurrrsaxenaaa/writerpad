package com.xebia.fs101.writerpad.exceptions;

public class WriterpadMailSendException extends RuntimeException {
    private Object context;
    private Exception ex;

    public WriterpadMailSendException(Object context, Exception ex) {
        this.context = context;
        this.ex = ex;
    }

    public Object getContext() {
        return context;
    }
}
