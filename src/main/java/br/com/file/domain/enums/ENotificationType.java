package br.com.file.domain.enums;

public enum ENotificationType {
    CREATE(1),
    UPDATE(2),
    DELETE(3),
    SHARE(4),
    SHARE_CREATE(5),
    SHARE_UPDATE(6),
    SHARE_DELETE(7),
    SHARE_WITH(8),
    UNSHARE(9),
    UNSHARE_WITH(10);

    int type;

    ENotificationType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static ENotificationType toEnum(Integer value) {
        for (ENotificationType notificationType : ENotificationType.values()) {
            if (notificationType.getType() == value) {
                return notificationType;
            }
        }
        throw new IllegalArgumentException();
    }
}
