package io.github.ppp16bit.Magalu.entity;

public enum ChannelValues {
    EMAIL(1L, "email"),
    SMS(2L, "sms"),
    PUSH(3L, "push"),
    WHATSAPP(4L, "whatsapp");

    private final Long id;
    private final String description;

    ChannelValues(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Channel toChannel() {
        return new Channel(id, description);
    }
}