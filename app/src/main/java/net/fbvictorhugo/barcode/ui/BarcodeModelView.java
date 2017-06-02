package net.fbvictorhugo.barcode.ui;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.google.android.gms.vision.barcode.Barcode;

import net.fbvictorhugo.barcode.model.MyBarcode;
import net.fbvictorhugo.barcode.R;
import net.fbvictorhugo.barcode.model.ReadingSource;
import net.fbvictorhugo.barcode.util.ActionUtils;

import java.text.DateFormat;

/**
 * By fbvictorhugo on 31/03/17.
 */

public class BarcodeModelView {

    private Barcode.Email email;
    private Barcode.Sms sms;
    private Barcode.GeoPoint geoPoint;
    private Barcode.Phone phone;
    private String barcodeValue;

    @StringRes
    private int barcodeFormatName;
    private int barcodeContentType;
    @DrawableRes
    private int imageCustomAction;

    private boolean hasCustomAction;
    private String readingDate;
    private ReadingSource readingSource;

    public BarcodeModelView(final MyBarcode barcode) {
        if (barcode != null) {
            sms = barcode.sms;
            email = barcode.email;
            geoPoint = barcode.geoPoint;
            phone = barcode.phone;

            barcodeValue = barcode.displayValue;
            barcodeFormatName = getBarcodeTypeResValue(barcode.format);
            barcodeContentType = barcode.valueFormat;
            readingDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(barcode.getReadingDate());
            readingSource = barcode.getReadingSource();
            configureForCustomAction();
        }
    }

    private void configureForCustomAction() {

        switch (barcodeContentType) {
            case Barcode.EMAIL:
                hasCustomAction = true;
                imageCustomAction = R.drawable.ic_mail_black_24dp;
                break;

            case Barcode.URL:
                hasCustomAction = true;
                imageCustomAction = R.drawable.ic_web_black_24dp;
                break;

            case Barcode.PHONE:
                hasCustomAction = true;
                imageCustomAction = R.drawable.ic_phone_black_24dp;
                break;

            case Barcode.SMS:
                hasCustomAction = true;
                imageCustomAction = R.drawable.ic_textsms_black_24dp;
                break;

            case Barcode.GEO:
                hasCustomAction = true;
                imageCustomAction = R.drawable.ic_location_on_black_24dp;
                break;

            default:
                hasCustomAction = false;
                imageCustomAction = 0;
        }
    }

    public static int getBarcodeTypeResValue(int format) {

        switch (format) {
            case Barcode.QR_CODE:
                return R.string.barcode_qr_code;

            case Barcode.AZTEC:
                return R.string.barcode_aztex;

            case Barcode.DATA_MATRIX:
                return R.string.barcode_data_matrix;

            default:
                return R.string.barcode_undefined;
        }
    }

    public int getImageForCustomAction() {
        return imageCustomAction;
    }

    public void excecuteCustomAction(final Context context) {

        switch (barcodeContentType) {

            case Barcode.EMAIL:
                ActionUtils.startIntentForEmail(context, email.address, email.subject, email.body);
                break;

            case Barcode.URL:
                ActionUtils.startIntentForWebsite(context, barcodeValue);
                break;

            case Barcode.PHONE:
                ActionUtils.startIntentForPhone(context, phone.number);
                break;

            case Barcode.SMS:
                ActionUtils.startIntentForSMS(context, sms.phoneNumber, sms.message);
                break;

            case Barcode.GEO:
                ActionUtils.startIntentForGeolocation(context, geoPoint.lat, geoPoint.lng);
                break;
        }
    }

    public String getBarcodeValue() {
        return barcodeValue;
    }

    public int getBarcodeFormatName() {
        return barcodeFormatName;
    }

    public boolean hasCustomAction() {
        return hasCustomAction;
    }

    public String getReadingDate() {
        return readingDate;
    }

    public int ReadingSourceWas() {
        switch (readingSource) {
            case CAMERA:
                return R.string.camera_source;

            case IMAGE:
                return R.string.image_source;

            default:
                return 0;
        }
    }
}
