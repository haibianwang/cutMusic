package com.fine.cutmusic.exception;

public class MusicException extends RuntimeException{

    private String errorCode;
    private String[] params;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }
    public MusicException(String message,String errorCode,String[] params){
        super(message);
        this.errorCode=errorCode;
        this.params=params;
    }

    public MusicException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    public MusicException(String message) {
        super(message);
    }
}
