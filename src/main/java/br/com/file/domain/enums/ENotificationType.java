package br.com.file.domain.enums;

public enum ENotificationType {
    CRAETE(1),
    UPDATE(2),
    DELETE(3);

    int type;

    ENotificationType(int type) {
        this.type = type;
    }


    public int getType() {
        return type;
    }

}
