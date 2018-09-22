package br.com.file.model.request;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class FileFilterRequest {

    @ApiModelProperty(position = 0)
    private Date beginUpdateDate;

    @ApiModelProperty(position = 1)
    private Long fileParentId;

    public Date getBeginUpdateDate() {
        return beginUpdateDate;
    }

    public void setBeginUpdateDate(Date beginUpdateDate) {
        this.beginUpdateDate = beginUpdateDate;
    }

    public Long getFileParentId() {
        return fileParentId;
    }

    public void setFileParentId(Long fileParentId) {
        this.fileParentId = fileParentId;
    }
}
