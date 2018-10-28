package br.com.file.model.respose;

public class FileSharedResponse {
    private Long id;
    private String userName;
    private Long userId;

    public FileSharedResponse(Long id, String userName, Long userId) {
        this.id = id;
        this.userName = userName;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
