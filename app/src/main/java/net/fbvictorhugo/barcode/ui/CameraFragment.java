package net.fbvictorhugo.barcode.ui;

import android.Manifest;
import android.content.Context;
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

import net.fbvictorhugo.barcode.BarcodeListener;
import net.fbvictorhugo.barcode.R;

import java.io.IOException;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * By fbvictorhugo on 08/03/17.
 */

public class CameraFragment extends Fragment {

    private static final int MY_PERMISSIONS_REQUEST_USE_CAMERA = 999;

    private BarcodeListener mBarcodeCallback;
    private SurfaceView mSurfaceCameraView;
    private CameraSource mCameraSource;
    private BarcodeDetector mBarcodeDetector;
    private LinearLayout mLayoutPermision;
    private Button mButtonPermision;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mBarcodeCallback = (BarcodeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement BarcodeListener");
        }
    }

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

                checkPermissionAndStartCamera();
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
                if (barcodes.size() != 0) {
                    mBarcodeCallback.barcodeReaded(barcodes.valueAt(0).displayValue);
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
                    checkPermissionAndStartCamera();
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
                checkPermissionAndStartCamera();
            }
        });
    }

    private void checkPermissionAndStartCamera() {
        try {
            if (checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                showMessageNeedPermission(false);
                mCameraSource.start(mSurfaceCameraView.getHolder());

            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {

                    showMessageNeedPermission(true);
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_USE_CAMERA);

                } else {

                    showMessageNeedPermission(true);
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_USE_CAMERA);
                }
            }
        } catch (IOException e) {
            Log.e("CAMERA SOURCE", e.getMessage());
        }
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
