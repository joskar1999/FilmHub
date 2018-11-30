package main.java.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class Subscription {

    private Map<SubscriptionType, BigDecimal> priceMap = new HashMap<>();
    private Map<SubscriptionType, Integer> devicesAmountMap;
    private Map<SubscriptionType, Integer> resolutionMap;

    public Subscription() {
        BigDecimal tmp = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN);
        priceMap.put(SubscriptionType.NONE, tmp);
        tmp = new BigDecimal(19.99).setScale(2, RoundingMode.HALF_EVEN);
        priceMap.put(SubscriptionType.BASIC, tmp);
        tmp = new BigDecimal(49.99).setScale(2, RoundingMode.HALF_EVEN);
        priceMap.put(SubscriptionType.FAMILY, tmp);
        tmp = new BigDecimal(99.99).setScale(2, RoundingMode.HALF_EVEN);
        priceMap.put(SubscriptionType.PREMIUM, tmp);
    }

    public Map<SubscriptionType, BigDecimal> getPriceMap() {
        return priceMap;
    }

    public void setPriceMap(Map<SubscriptionType, BigDecimal> priceMap) {
        this.priceMap = priceMap;
    }

    public Map<SubscriptionType, Integer> getDevicesAmountMap() {
        return devicesAmountMap;
    }

    public void setDevicesAmountMap(Map<SubscriptionType, Integer> devicesAmountMap) {
        this.devicesAmountMap = devicesAmountMap;
    }

    public Map<SubscriptionType, Integer> getResolutionMap() {
        return resolutionMap;
    }

    public void setResolutionMap(Map<SubscriptionType, Integer> resolutionMap) {
        this.resolutionMap = resolutionMap;
    }
}
