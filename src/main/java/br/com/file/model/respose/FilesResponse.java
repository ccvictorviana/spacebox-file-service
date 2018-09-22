package br.com.file.model.respose;

import br.com.file.model.request.FileFilterRequest;
import io.swagger.annotations.ApiModelProperty;

public class FilesResponse {

    @ApiModelProperty(position = 0)
    private FileFilterRequest filter;

    @ApiModelProperty(position = 1)
    private FileSummaryResponse[] files;

    public FileFilterRequest getFilter() {
        return filter;
    }

    public void setFilter(FileFilterRequest filter) {
        this.filter = filter;
    }

    public FileSummaryResponse[] getFiles() {
        return files;
    }

    public void setFiles(FileSummaryResponse[] files) {
        this.files = files;
    }
}
