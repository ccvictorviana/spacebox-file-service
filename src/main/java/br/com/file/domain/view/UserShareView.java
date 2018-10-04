package br.com.file.domain.view;

public class UserShareView {

    private Long userId;
    private String name;

    public UserShareView(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public Long getUserId(Long userId) {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
