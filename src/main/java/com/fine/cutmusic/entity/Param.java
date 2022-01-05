package com.fine.cutmusic.entity;

public class Param {
    private String input;
    private String out;
    private String start;
    private String end;

    public Param(String input, String out, String start, String end) {
        this.input = input;
        this.out = out;
        this.start = start;
        this.end = end;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
