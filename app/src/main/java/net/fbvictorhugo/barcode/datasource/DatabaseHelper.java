package net.fbvictorhugo.barcode.datasource;

import android.content.Context;
import android.content.SharedPreferences;

import net.fbvictorhugo.barcode.model.MyBarcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * By fbvictorhugo on 30/05/17.
 */

public class DatabaseHelper {
    private SharedPreferences mPreferences = null;

    public DatabaseHelper(Context context) {
        mPreferences = context.getSharedPreferences("BARC0DE", Context.MODE_PRIVATE);
    }

    private boolean saveBarcode(final ArrayList<MyBarcode> barcodes) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.clear();
        for (MyBarcode barcode : barcodes) {
            editor.putString(String.valueOf(barcode.getReadingDate().getTime()), barcode.toString());
        }
        return editor.commit();
    }

    public ArrayList<MyBarcode> loadBarcodes() {
        Map<String, String> mapBarcode = (Map<String, String>) mPreferences.getAll();
        ArrayList<MyBarcode> barcodes = new ArrayList<>();

        if (!mapBarcode.isEmpty()) {
            Set<String> keySet = mapBarcode.keySet();

            Object[] keys = keySet.toArray();
            Arrays.sort(keys, Collections.reverseOrder());

            for (Object key : keys) {
                barcodes.add(MyBarcode.getInstance(mapBarcode.get(key)));
            }
        }
        return barcodes;
    }

    public boolean saveBarcode(MyBarcode barcode) {
        ArrayList<MyBarcode> barcodes = loadBarcodes();
        barcodes.add(0, barcode);
        return saveBarcode(barcodes);
    }

    public boolean cleanBarcodes() {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.clear();
        return editor.commit();
    }

}