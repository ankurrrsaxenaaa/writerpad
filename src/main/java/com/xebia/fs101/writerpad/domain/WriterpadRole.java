package com.xebia.fs101.writerpad.domain;

public enum WriterpadRole {
    WRITER, EDITOR, ADMIN;


    public String getRoleName(){
        return "ROLE_"+this.name();
    }
}
