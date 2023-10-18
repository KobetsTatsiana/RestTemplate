package org.example.servlet.dto.user;

import org.example.model.UserEntity;

import java.util.List;

public class EntityAllOutGoingDTO {

    private List<UserEntity> userEntityList;

    public List<UserEntity> getUserEntityList() {
        return userEntityList;
    }

    public void setUserEntityList(List<UserEntity> userEntityList) {
        this.userEntityList = userEntityList;
    }

}
