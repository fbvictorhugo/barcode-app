package net.fbvictorhugo.barcode.ui.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import net.fbvictorhugo.barcode.model.MyBarcode;
import net.fbvictorhugo.barcode.R;
import net.fbvictorhugo.barcode.model.ReadingSource;
import net.fbvictorhugo.barcode.util.ActionUtils;
import net.fbvictorhugo.barcode.util.DialogUtils;

import java.io.IOException;
import java.util.Date;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * By fbvictorhugo on 08/03/17.
 */

public class CameraFragment extends Fragment {

    private static final int MY_PERMISSIONS_REQUEST_USE_CAMERA = 999;

    private SurfaceView mSurfaceCameraView;
    private CameraSource mCameraSource;
    private BarcodeDetector mBarcodeDetector;
    private LinearLayout mLayoutPermision;
    private Button mButtonPermision;
    private boolean mDetectorLocked;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.fragment_camera, parent, false);
        findViews(baseView);

        mBarcodeDetector = new BarcodeDetector.Builder(getContext())
                .setBarcodeFormats(Barcode.QR_CODE | Barcode.AZTEC | Barcode.DATA_MATRIX)
                .build();

        mCameraSource = new CameraSource.Builder(getContext(), mBarcodeDetector)
                .setAutoFocusEnabled(true)
                .build();

        mSurfaceCameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                checkPermissionAndStartCamera(false);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                mCameraSource.stop();
            }
        });
        mBarcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (!mDetectorLocked && barcodes.size() != 0) {
                    mDetectorLocked = true;
                    try {
                        MyBarcode barcode = new MyBarcode(barcodes.valueAt(0));
                        barcode.setReadingSource(ReadingSource.CAMERA);
                        barcode.setReadingDate(new Date());
                        showBarcodeDialog(barcode);
                    } catch (Exception e) {
                        mDetectorLocked = false;
                    }
                }
            }
        });

        configureClickListeners();

        return baseView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_USE_CAMERA: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissionAndStartCamera(false);
                } else {
                    showMessageNeedPermission(true);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        mBarcodeDetector.release();
        mCameraSource.release();
        super.onDestroy();
    }

    private void findViews(View baseView) {
        mSurfaceCameraView = (SurfaceView) baseView.findViewById(R.id.fragment_camera_surfaceview);
        mLayoutPermision = (LinearLayout) baseView.findViewById(R.id.fragment_camera_layout_permision);
        mButtonPermision = (Button) baseView.findViewById(R.id.fragment_camera_button_permision);
    }

    private void configureClickListeners() {
        mButtonPermision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissionAndStartCamera(true);
            }
        });
    }

    private void checkPermissionAndStartCamera(final boolean userRequest) {
        try {
            if (checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                showMessageNeedPermission(false);
                mCameraSource.start(mSurfaceCameraView.getHolder());

            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    showMessageNeedPermission(true);
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_USE_CAMERA);

                } else {
                    if (userRequest) {
                        ActionUtils.startApplicationDetailsSettings(getActivity());
                    } else {
                        showMessageNeedPermission(true);
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_USE_CAMERA);
                    }
                }
            }
        } catch (IOException e) {
            Log.e("CAMERA SOURCE", e.getMessage());
        }
    }

    private void showBarcodeDialog(MyBarcode barcode) {

        BarcodeDialogFragment dialogCode = DialogUtils.createBarcodeDialog(barcode);
        dialogCode.setDissmissListener(new BarcodeDialogFragment.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mDetectorLocked = false;
            }
        });
        dialogCode.show(getFragmentManager(), BarcodeDialogFragment.TAG);
    }

    private void showMessageNeedPermission(boolean show) {
        if (show) {
            mSurfaceCameraView.setVisibility(View.INVISIBLE);
            mLayoutPermision.setVisibility(View.VISIBLE);
        } else {
            mLayoutPermision.setVisibility(View.GONE);
            mSurfaceCameraView.setVisibility(View.VISIBLE);
        }
    }

}
