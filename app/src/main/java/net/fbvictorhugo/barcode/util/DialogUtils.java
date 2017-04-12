package net.fbvictorhugo.barcode.util;

import android.os.Bundle;

import net.fbvictorhugo.barcode.model.MyBarcode;
import net.fbvictorhugo.barcode.ui.fragment.BarcodeDialogFragment;

/**
 * By fbvictorhugo on 03/04/17.
 */

public class DialogUtils {

    public static BarcodeDialogFragment createBarcodeDialog(final MyBarcode barcode) {
        BarcodeDialogFragment barcodeDialogFragment = new BarcodeDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_BARCODE, barcode);
        barcodeDialogFragment.setArguments(bundle);

        return barcodeDialogFragment;
    }

}
