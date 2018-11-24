package main.java.model;

import java.util.Map;

public class Subscription {

    private Map<SubscriptionType, Double> priceMap;
    private Map<SubscriptionType, Integer> devicesAmountMap;
    private Map<SubscriptionType, Integer> resolutionMap;

    public Map<SubscriptionType, Double> getPriceMap() {
        return priceMap;
    }

    public void setPriceMap(Map<SubscriptionType, Double> priceMap) {
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
