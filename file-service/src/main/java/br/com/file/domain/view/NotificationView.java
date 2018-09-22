package br.com.file.domain.view;

import java.util.Date;

public class NotificationView {

    private Long id;

    private int type;

    private String nFileName;

    private Long nFileId;

    private Long userId;

    private Date created;

    private FileView file;

    public NotificationView(Long id, int type, String fileName, Long userId, Date created,
                            Long fileId, String name, String fileType, Long size, Long fileParentId, Date fileCreated, Date updated) {
        this.id = id;
        this.type = type;
        this.nFileName = fileName;
        this.nFileId = fileId;
        this.userId = userId;
        this.created = created;
        file = new FileView(fileId, name, fileType, size, fileParentId, fileCreated, updated);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        private Long nUserId;
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

        public Builder withNUserId(Long nUserId) {
            this.nUserId = nUserId;
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
            return new NotificationView(
                    nId, nType, nFileName, nUserId, nCreated,
                    fileId, fName, fType, fSize, fileParentId, fCreated, fUpdated);
        }
    }
}
