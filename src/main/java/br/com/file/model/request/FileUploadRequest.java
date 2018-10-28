package br.com.file.model.request;

import io.swagger.annotations.ApiModelProperty;

public class FileUploadRequest {
    @ApiModelProperty(position = 0)
    private String File;
    private String type;
    private String name;

    public String getFile() {
        return File;
    }

    public void setFile(String file) {
        File = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
