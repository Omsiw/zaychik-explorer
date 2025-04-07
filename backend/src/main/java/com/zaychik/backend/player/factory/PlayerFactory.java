package com.zaychik.backend.player.factory;

import com.zaychik.backend.player.entity.Player;
import com.zaychik.backend.player.model.PlayerCreationParams;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PlayerFactory {
    public static Player creatPlayer(PlayerCreationParams params) {
        return new Player(params.username(), params.password(), params.email());
    }
}
