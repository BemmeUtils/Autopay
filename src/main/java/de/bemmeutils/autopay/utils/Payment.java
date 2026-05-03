package de.bemmeutils.autopay.utils;

import lombok.Getter;
import lombok.Setter;

public class Payment {
    @Getter
    @Setter
    private String uuid;
    @Getter
    @Setter
    private String playerName;
    @Getter
    @Setter
    private double sum;

    public Payment(String uuid, String playerName, double sum) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.sum = sum;
    }
}