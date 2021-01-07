package com.fire.sx1gd1243;

public class MemoAttributes {
    private String id, memo, memoTitle, memDT;

    public MemoAttributes(String id, String memo, String memoTitle, String memDT) {
        this.id = id;
        this.memo = memo;
        this.memoTitle = memoTitle;
        this.memDT = memDT;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMemoTitle() {
        return memoTitle;
    }

    public void setMemoTitle(String memoTitle) {
        this.memoTitle = memoTitle;
    }

    public String getMemDT() {
        return memDT;
    }

    public void setMemDT(String memDT) {
        this.memDT = memDT;
    }
}
