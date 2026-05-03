package de.bemmeutils.autopay.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class RecurringPayment {
    @Getter
    @Setter
    private String uuid;
    @Getter
    @Setter
    private double sum;
    @Getter
    @Setter
    private String timeUnit; //daily = d, weekly = w, monthly = w
    @Getter
    @Setter
    private Date lastClaimed;

    public RecurringPayment(String uuid, double sum, int timeFrame, String timeUnit) {
        this.uuid = uuid;
        this.sum = sum;
        this.timeUnit = timeUnit;
        this.lastClaimed = null;
    }

    public RecurringPayment(String uuid, double sum, String timeUnit, Date lastClaimed) {
        this.uuid = uuid;
        this.sum = sum;
        this.timeUnit = timeUnit;
        this.lastClaimed = lastClaimed;
    }
}
