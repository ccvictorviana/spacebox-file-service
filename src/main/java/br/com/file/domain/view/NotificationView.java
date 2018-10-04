package br.com.file.domain.view;

import br.com.file.domain.enums.ENotificationType;

import java.util.Date;

public class NotificationView {
    private Long id;
    private ENotificationType type;
    private String nFileName;
    private Long nFileId;
    private Long userActionId;
    private Long userOwnerId;
    private Date created;
    private FileView file;

    public NotificationView(Long id, int type, String fileName, Long userOwnerId, Long userActionId, Date created,
                            Long fileId, String name, String fileType, Long size, Long fileParentId, Date fileCreated, Date updated) {
        setId(id);
        setType(type);
        setnFileId(fileId);
        setnFileName(fileName);
        setUserOwnerId(userOwnerId);
        setUserActionId(userActionId);
        setCreated(created);
        file = new FileView(fileId, name, fileType, size, fileParentId, fileCreated, updated);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ENotificationType getType() {
        return type;
    }

    public void setType(int type) {
        this.type = ENotificationType.toEnum(type);
    }

    public Long getUserActionId() {
        return userActionId;
    }

    public void setUserActionId(Long userActionId) {
        this.userActionId = userActionId;
    }

    public Long getUserOwnerId() {
        return userOwnerId;
    }

    public void setUserOwnerId(Long userOwnerId) {
        this.userOwnerId = userOwnerId;
    }

    public String getnFileName() {
        return nFileName;
    }

    public void setnFileName(String fileName) {
        this.nFileName = fileName;
    }

    public Long getnFileId() {
        return nFileId;
    }

    public void setnFileId(Long nFileId) {
        this.nFileId = nFileId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public FileView getFile() {
        return file;
    }

    public void setFile(FileView file) {
        this.file = file;
    }

    public static class Builder {
        private Long nId;
        private int nType;
        private String nFileName;
        private Long userOwnerId;
        private Long userActionId;
        private Date nCreated;
        private Long fileId;
        private String fName;
        private String fType;
        private Long fSize;
        private Long fileParentId;
        private Date fCreated;
        private Date fUpdated;

        public Builder() {

        }

        public Builder withNId(Long id) {
            this.nId = id;
            return this;
        }

        public Builder withNType(int nType) {
            this.nType = nType;
            return this;
        }

        public Builder withNFileName(String nFileName) {
            this.nFileName = nFileName;
            return this;
        }

        public Builder withUserOwnerId(Long userOwnerId) {
            this.userOwnerId = userOwnerId;
            return this;
        }

        public Builder withUserActionId(Long userActionId) {
            this.userActionId = userActionId;
            return this;
        }

        public Builder withNCreated(Date nCreated) {
            this.nCreated = nCreated;
            return this;
        }

        public Builder withFileId(Long fileId) {
            this.fileId = fileId;
            return this;
        }

        public Builder withFName(String fName) {
            this.fName = fName;
            return this;
        }

        public Builder withFType(String fType) {
            this.fType = fType;
            return this;
        }

        public Builder withFSize(Long fSize) {
            this.fSize = fSize;
            return this;
        }

        public Builder withFileParentId(Long fileParentId) {
            this.fileParentId = fileParentId;
            return this;
        }

        public Builder withFCreated(Date fCreated) {
            this.fCreated = fCreated;
            return this;
        }

        public Builder withFUpdated(Date fUpdated) {
            this.fUpdated = fUpdated;
            return this;
        }

        public NotificationView build() {
            return new NotificationView(nId, nType, nFileName, userOwnerId, userActionId, nCreated,
                    fileId, fName, fType, fSize, fileParentId, fCreated, fUpdated);
        }
    }
}
