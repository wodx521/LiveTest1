package com.lairui.livetest1.entity.bean;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class LoginInfoBean {
    @Id
    public long mainId;
    private String userName;
    private String password;

    public void updateData(LoginInfoBean loginInfoBean) {
        setUserName(loginInfoBean.getUserName());
        setPassword(loginInfoBean.getPassword());
    }

    public String getUserName() {
        return userName == null ? "" : userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password == null ? "" : password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
