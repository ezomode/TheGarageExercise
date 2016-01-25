package com.wreck.model;

import java.util.UUID;

public abstract class Vehicle {

    private final UUID uuid = UUID.randomUUID();

    public UUID getUuid() {
        return uuid;
    }
}