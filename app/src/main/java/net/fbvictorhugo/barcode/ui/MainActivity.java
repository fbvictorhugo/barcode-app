package net.fbvictorhugo.barcode.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import net.fbvictorhugo.barcode.BarcodeListener;
import net.fbvictorhugo.barcode.R;
import net.fbvictorhugo.barcode.model.MyBarcode;

public class MainActivity extends AppCompatActivity implements BarcodeListener {

    private TextView mBarcodeTextInfo;
    private BottomNavigationView mBottomNavigationView;

    private CameraFragment mCameraFragment;
    private Fragment mFilesFragment;
    private HistoryFragment mHistoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        instanceFragments();
        configureBottomNavigation();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_fragment_container, mCameraFragment).commit();

    }

    @Override
    public void barcodeReaded(final MyBarcode barcode) {

        mBarcodeTextInfo.post(new Runnable() {
            public void run() {
                mBarcodeTextInfo.setText(barcode.getCode());
            }
        });

    }

    @SuppressWarnings("EmptyMethod")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Result of the activity will be delivered to the Fragment
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void findViews() {

        mBarcodeTextInfo = (TextView) findViewById(R.id.main_barcode_info);
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.main_bottom_navigation);

    }

    private void instanceFragments() {

        mCameraFragment = new CameraFragment();
        mFilesFragment = new Fragment();
        mHistoryFragment = new HistoryFragment();

    }

    private void configureBottomNavigation() {

        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        changeFragmentOnContainer(item);
                        return true;
                    }
                });
    }

    private void changeFragmentOnContainer(MenuItem item) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()) {
            case R.id.action_nav_camera:
                transaction.replace(R.id.main_fragment_container, mCameraFragment);
                transaction.commit();
                break;

            case R.id.action_nav_file:
                transaction.replace(R.id.main_fragment_container, mFilesFragment);
                transaction.commit();
                break;

            case R.id.action_nav_history:
                transaction.replace(R.id.main_fragment_container, mHistoryFragment);
                transaction.commit();
                break;
        }
    }

}
