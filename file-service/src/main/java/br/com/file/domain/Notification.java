package br.com.file.domain;

import br.com.spacebox.common.domain.AEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_notification")
public class Notification extends AEntity {

    @Column(nullable = false)
    private int type;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private Long fileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "long", name = "fileId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none"))
    private File file;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Date created;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
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
}