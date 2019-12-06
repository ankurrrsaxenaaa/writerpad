package com.xebia.fs101.writerpad.api.representations;

public class TagResponse {

    private String tag;
    private long occurence;

    public TagResponse() {
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getOccurence() {
        return occurence;
    }

    public void setOccurence(long occurence) {
        this.occurence = occurence;
    }

    public TagResponse(String tag, long occurence) {
        this.tag = tag;
        this.occurence = occurence;
    }
}
