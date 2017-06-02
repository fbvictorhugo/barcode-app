package net.fbvictorhugo.barcode.util;

import com.google.android.gms.vision.barcode.Barcode;

/**
 * By fbvictorhugo on 01/04/17.
 */

public class Constants {

    public static final String KEY_BARCODE = "barcode";

    public static final int[] SUPPORTED_FORMATS_ARR = {Barcode.QR_CODE, Barcode.DATA_MATRIX, Barcode.AZTEC};

    public static final int SUPPORTED_FORMATS = intoBitmask();

    private static int intoBitmask() {
        int bit = 0;
        for (int format : SUPPORTED_FORMATS_ARR) {
            bit = bit + format;
        }
        return bit;
    }
}
