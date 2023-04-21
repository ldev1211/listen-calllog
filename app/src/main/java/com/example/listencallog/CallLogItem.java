package com.example.listencallog;

public class CallLogItem {
    private String phone;
    private String duration;
    private String type;
    private long date;
    private long timestamp;

    public CallLogItem() {
    }

    public CallLogItem(String phone, String duration, String type, long date, long timestamp) {
        this.phone = phone;
        this.duration = duration;
        this.type = type;
        this.date = date;
        this.timestamp = timestamp;
    }

    public long getTimestamp() { // tạo getter method cho biến timestamp
        return timestamp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

}