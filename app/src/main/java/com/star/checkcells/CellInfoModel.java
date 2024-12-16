package com.star.checkcells;

public class CellInfoModel {
    private String cellId = "";
    private String signalStrength = "";
    private String networkType = "";

    public CellInfoModel(long cellId, long signalStrength, String networkType) {
        this.cellId = String.valueOf(cellId);
        this.signalStrength = signalStrength + " dBm";
        this.networkType = networkType;
    }

    public String getCellId() {
        return cellId;
    }

    public String getSignalStrength() {
        return signalStrength;
    }

    public String getNetworkType() {
        return networkType;
    }
}
