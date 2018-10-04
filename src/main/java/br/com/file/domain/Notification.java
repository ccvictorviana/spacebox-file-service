package br.com.file.domain;

import br.com.file.domain.enums.ENotificationType;
import br.com.file.repository.converter.ENotificationTypeConverter;
import br.com.spacebox.common.domain.AEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_notification")
public class Notification extends AEntity {

    @Column(nullable = false)
    @Convert(converter = ENotificationTypeConverter.class)
    private ENotificationType type;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private Long fileId;

    @Column(nullable = false)
    private Long userOwnerId;

    @Column(nullable = false)
    private Long userActionId;

    @Column(nullable = false)
    private Date created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "long", name = "fileId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none"))
    private File file;

    public ENotificationType getType() {
        return type;
    }

    public void setType(ENotificationType type) {
        this.type = type;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Long getUserOwnerId() {
        return userOwnerId;
    }

    public void setUserOwnerId(Long userOwnerId) {
        this.userOwnerId = userOwnerId;
    }

    public Long getUserActionId() {
        return userActionId;
    }

    public void setUserActionId(Long userActionId) {
        this.userActionId = userActionId;
    }
}