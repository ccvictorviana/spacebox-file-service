package br.com.file.domain;

import br.com.spacebox.common.domain.AEntity;
import br.com.spacebox.common.domain.User;

import javax.persistence.*;

@Entity
@Table(name = "tb_file_share")
public class FileShare extends AEntity {
    private Long id;
    private Long userId;
    private Long fileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "long", name = "fileId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none"))
    private File file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "long", name = "fileId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none"))
    private User user;

    public FileShare() {

    }

    public FileShare(Long fileId, Long userId) {
        this.fileId = fileId;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
