package br.com.file.model.respose;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class NotificationResponse {

    private Long id;

    @ApiModelProperty(position = 1)
    private int type;

    @ApiModelProperty(position = 2)
    private String nFileName;

    @ApiModelProperty(position = 3)
    private Long nFileId;

    @ApiModelProperty(position = 4)
    private Long userId;

    @ApiModelProperty(position = 5)
    private Date created;

    @ApiModelProperty(position = 6)
    private FileSummaryResponse file;

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

    public void setnFileName(String nFileName) {
        this.nFileName = nFileName;
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

    public FileSummaryResponse getFile() {
        return file;
    }

    public void setFile(FileSummaryResponse file) {
        this.file = file;
    }
}