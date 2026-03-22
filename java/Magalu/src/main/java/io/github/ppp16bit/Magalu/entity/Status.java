package io.github.ppp16bit.Magalu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "table_name")
public class Status {
    @Id
    private Long statusID;

    private String description;

    public Status() {}

    public Status(Long statusID, String description) {
        this.statusID = statusID;
        this.description = description;
    }

    public Long getStatusID() {
        return statusID;
    }

    public void setStatusID(Long statusID) {
        this.statusID = statusID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}