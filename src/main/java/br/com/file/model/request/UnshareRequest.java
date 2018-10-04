package br.com.file.model.request;

public class UnshareRequest {
    private Long[] UsersId;
    private Long fileId;

    public Long[] getUsersId() {
        return UsersId;
    }

    public void setUsersId(Long[] usersId) {
        UsersId = usersId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
}
