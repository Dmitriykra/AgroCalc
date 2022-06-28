package com.dimaster.agrocalc.model;

import androidx.annotation.NonNull;

import java.util.StringJoiner;

public class Crop {
    private final String cropName;
    private final double n;
    private final double p;
    private final double k;

    public Crop(String cropName, double n, double p, double k) {
        this.cropName = cropName;
        this.n = n;
        this.p = p;
        this.k = k;
    }

    public String getCropName() {
        return cropName;
    }

    public double getN() {
        return n;
    }

    public double getP() {
        return p;
    }

    public double getK() {
        return k;
    }

    @NonNull
    @Override
    public String toString() {
        return new StringJoiner(", ", Crop.class.getSimpleName() + "[", "]")
                .add("cropName='" + cropName + "'")
                .add("n=" + n)
                .add("p=" + p)
                .add("k=" + k)
                .toString();
    }
}
