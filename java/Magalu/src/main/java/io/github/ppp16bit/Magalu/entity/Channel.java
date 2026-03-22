package io.github.ppp16bit.Magalu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tabel_channel")
public class Channel {
    @Id
    private Long channelID;

    private String description;

    public Channel() {}

    public Channel(Long channelID, String description) {
        this.channelID = channelID;
        this.description = description;
    }

    public Long getChannelID() {
        return channelID;
    }

    public void setChannelID(Long channelID) {
        this.channelID = channelID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
