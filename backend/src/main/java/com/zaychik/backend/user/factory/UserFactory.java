package com.zaychik.backend.user.factory;

import com.zaychik.backend.user.entity.User;
import com.zaychik.backend.user.model.UserCreationParams;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserFactory {
    public static User createUser(UserCreationParams params) {
        return new User(params.username(), params.password(), params.email());
    }
}
