package br.com.file.domain.view;

public class UserShareView {
    private Long id;
    private String userName;

    public UserShareView(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public Long getId() {
        return this.id;
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

}
