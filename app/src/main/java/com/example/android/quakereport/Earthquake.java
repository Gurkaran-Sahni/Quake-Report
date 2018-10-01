package com.example.android.quakereport;

public class Earthquake {
    private double mMagnitude;
    private String mLocation;
    private Long mTimeInMilliseconds;
    private String mURL;

    public Earthquake(double Magnitude, String Location, Long TimeInMilliseconds, String URL){
        mMagnitude = Magnitude;
        mLocation = Location;
        mTimeInMilliseconds = TimeInMilliseconds;
        mURL = URL;
    }

    public String getmLocation() {
        return mLocation;
    }

    public double getmMagnitude() {
        return mMagnitude;
    }
    public Long getmTimeInMilliseconds(){
        return mTimeInMilliseconds;
    }

    public String getmURL() {
        return mURL;
    }
}
