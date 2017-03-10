package net.fbvictorhugo.barcode;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements BarcodeListener {

    private TextView mBarcodeTextInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        mBarcodeTextInfo = (TextView) findViewById(R.id.main_barcode_info);
    }

    @Override
    public void barcodeReaded(final String code) {

        mBarcodeTextInfo.post(new Runnable() {
            public void run() {
                mBarcodeTextInfo.setText(code);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Result of the activity will be delivered to the Fragment
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
