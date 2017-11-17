package net.fbvictorhugo.barcode.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import net.fbvictorhugo.barcode.R;
import net.fbvictorhugo.barcode.ui.BarcodeModelView;
import net.fbvictorhugo.barcode.ui.fragment.CameraFragment;
import net.fbvictorhugo.barcode.ui.fragment.FileFragment;
import net.fbvictorhugo.barcode.ui.fragment.HistoryFragment;
import net.fbvictorhugo.barcode.util.Constants;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private CameraFragment mCameraFragment;
    private FileFragment mFilesFragment;
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

        if (getSupportActionBar() != null) {
            int[] formats = Constants.SUPPORTED_FORMATS_ARR;
            String subtitle = "";

            for (int i = 0; i < formats.length; i++) {
                subtitle += getBarcodeTypeLabel(formats[i]);
                if (i < formats.length - 1) {
                    subtitle += " | ";
                }
            }
            getSupportActionBar().setSubtitle(subtitle);
        }
    }

    @SuppressWarnings("EmptyMethod")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Result of the activity will be delivered to the Fragment
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void findViews() {
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.main_bottom_navigation);
    }

    private void instanceFragments() {

        mCameraFragment = new CameraFragment();
        mFilesFragment = new FileFragment();
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

    String getBarcodeTypeLabel(final int barcodeType) {
        return getString(BarcodeModelView.getBarcodeTypeResValue(barcodeType));
    }

}
