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
        super(1, barcode.format, barcode.rawValue, barcode.displayValue, barcode.valueFormat, barcode.cornerPoints,
                barcode.email, barcode.phone, barcode.sms, barcode.wifi, barcode.url, barcode.geoPoint, barcode.calendarEvent,
                barcode.contactInfo, barcode.driverLicense);
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
