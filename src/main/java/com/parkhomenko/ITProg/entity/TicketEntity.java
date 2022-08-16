package com.parkhomenko.ITProg.entity;

import java.sql.Blob;

public class TicketEntity {

    private long ticketId = -1;
    private long columnId = -1;
    private String name;
    private String description;
    private String status;
    private Blob answer;
    private String markers;
    private String stages;

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public long getColumnId() {
        return columnId;
    }

    public void setColumnId(long columnId) {
        this.columnId = columnId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Blob getAnswer() {
        return answer;
    }

    public void setAnswer(Blob answer) {
        this.answer = answer;
    }

    public String getMarkers() {
        return markers;
    }

    public void setMarkers(String markers) {
        this.markers = markers;
    }

    public String getStages() {
        return stages;
    }

    public void setStages(String stages) {
        this.stages = stages;
    }
}
