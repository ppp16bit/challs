package io.github.ppp16bit.Magalu.entity;

public enum StatusValues {
    PENDING(1L, "pending"),
    SUCESS(2L, "sucess"),
    ERROR(3L, "error"),
    CANCELED(4L, "canceled");

    private Long id;
    private String description;

    StatusValues(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Status toStatus() {
        return new Status(id, description);
    }
}