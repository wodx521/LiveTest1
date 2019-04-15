package com.lairui.livetest1.entity.bean;

import java.util.Objects;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class CategoryBean {

    /**
     * id : 4
     * name : 微商之家
     */
    @Id
    public long categoryMainId;
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryBean that = (CategoryBean) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }
}
