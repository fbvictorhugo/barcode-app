package net.fbvictorhugo.barcode.model;

import com.google.android.gms.vision.barcode.Barcode;

/**
 * By fbvictorhugo on 20/03/17.
 */

public class MyBarcode {

    private String mCode;

    public MyBarcode() {

    }

    public MyBarcode(Barcode barcode) {
        mCode = barcode.displayValue;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        this.mCode = code;
    }

}
