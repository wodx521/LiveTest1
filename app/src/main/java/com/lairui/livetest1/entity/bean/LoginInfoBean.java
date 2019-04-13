package com.lairui.livetest1.entity.bean;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginInfoBean that = (LoginInfoBean) o;
        return Objects.equals(userName, that.userName) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password);
    }
}
