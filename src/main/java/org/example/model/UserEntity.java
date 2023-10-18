package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserEntity {
    private long id;
    private String name;
    private String surname;
    private String address;

    private List<UserSite> userSiteList;

    public UserEntity() {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<UserSite> getUserSiteList() {
        if (this.userSiteList == null) {
            this.userSiteList = new ArrayList<>();
        }
        return userSiteList;
    }

    public void setUserSiteList(List<UserSite> userSiteList) {
        this.userSiteList = userSiteList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(address, that.address) && Objects.equals(userSiteList, that.userSiteList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, address, userSiteList);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", userSiteList=" + userSiteList +
                '}';
    }
}
