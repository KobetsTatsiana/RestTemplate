package org.example.model;

import java.util.Objects;

public class UserSite {
    private long id;
    private long userId;
    private String nameSite;

    public UserSite() {
    }

    public UserSite(String nameSite) {
        this.nameSite = nameSite;
    }

    public UserSite(long id, String nameSite) {
        this.id = id;
        this.nameSite = nameSite;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNameSite() {
        return nameSite;
    }

    public void setNameSite(String nameSite) {
        this.nameSite = nameSite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSite userSite = (UserSite) o;
        return id == userSite.id && userId == userSite.userId && Objects.equals(nameSite, userSite.nameSite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, nameSite);
    }

    @Override
    public String toString() {
        return "UserSite{" +
                "id=" + id +
                ", userId=" + userId +
                ", nameSite='" + nameSite + '\'' +
                '}';
    }
}