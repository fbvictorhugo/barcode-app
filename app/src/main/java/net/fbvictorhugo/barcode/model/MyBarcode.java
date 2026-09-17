package net.fbvictorhugo.barcode.model;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.fbvictorhugo.barcode.util.Constants;

import java.util.Date;

/**
 * By fbvictorhugo on 03/04/17.
 */

public class MyBarcode extends Barcode {

    public MyBarcode() {
        super();
    }

    public MyBarcode(Barcode barcode) {
        super();
        this.format = barcode.format;
        this.rawValue = barcode.rawValue;
        this.displayValue = barcode.displayValue;
        this.valueFormat = barcode.valueFormat;
        this.cornerPoints = barcode.cornerPoints;
        this.email = barcode.email;
        this.phone = barcode.phone;
        this.sms = barcode.sms;
        this.wifi = barcode.wifi;
        this.url = barcode.url;
        this.geoPoint = barcode.geoPoint;
        this.calendarEvent = barcode.calendarEvent;
        this.contactInfo = barcode.contactInfo;
        this.driverLicense = barcode.driverLicense;
    }

    public static MyBarcode getInstance(String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, MyBarcode.class);
    }

    private Date readingDate;
    private Constants.ReadingSource readingSource;

    public Date getReadingDate() {
        return readingDate;
    }

    public void setReadingDate(Date dateReading) {
        this.readingDate = dateReading;
    }

    public Constants.ReadingSource getReadingSource() {
        return readingSource;
    }

    public void setReadingSource(Constants.ReadingSource readingSource) {
        this.readingSource = readingSource;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
