package net.fbvictorhugo.barcode;

import com.google.android.gms.vision.barcode.Barcode;

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

    private Date dateReading;

    public Date getDateReading() {
        return dateReading;
    }

    public void setDateReading(Date dateReading) {
        this.dateReading = dateReading;
    }

}
