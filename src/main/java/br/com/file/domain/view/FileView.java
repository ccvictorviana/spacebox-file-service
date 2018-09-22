package br.com.file.domain.view;

import br.com.spacebox.common.domain.AEntity;

import java.util.Date;

public class FileView extends AEntity {
    private String name;
    private String type;
    private Long size;
    private Long fileParentId;
    private Date created;
    private Date updated;

    public FileView(Long id, String name, String type, Long size, Long fileParentId, Date created, Date updated) {
        setId(id);
        this.name = name;
        this.type = type;
        this.size = size;
        this.fileParentId = fileParentId;
        this.created = created;
        this.updated = updated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getFileParentId() {
        return fileParentId;
    }

    public void setFileParentId(Long fileParentId) {
        this.fileParentId = fileParentId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
