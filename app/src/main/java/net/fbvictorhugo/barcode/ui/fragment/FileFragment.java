package net.fbvictorhugo.barcode.ui.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import net.fbvictorhugo.barcode.model.MyBarcode;
import net.fbvictorhugo.barcode.R;
import net.fbvictorhugo.barcode.model.ReadingSource;
import net.fbvictorhugo.barcode.util.DialogUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class FileFragment extends Fragment {

    private static final int SELECT_PHOTO = 1;
    private ImageView mImageView;
    private Button mButtonChoose;
    private BarcodeDetector mBarcodeDetector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View baseView = inflater.inflate(R.layout.fragment_file, container, false);
        findViews(baseView);

        configureClickListeners();

        mBarcodeDetector = new BarcodeDetector.Builder(getContext())
                .setBarcodeFormats(Barcode.QR_CODE | Barcode.AZTEC | Barcode.DATA_MATRIX)
                .build();

        return baseView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                        Frame frame = new Frame.Builder().setBitmap(selectedImage).build();
                        SparseArray<Barcode> barcodes = mBarcodeDetector.detect(frame);
                        mImageView.setImageBitmap(selectedImage);

                        if (barcodes.size() != 0) {
                            mImageView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.overlay));
                            final MyBarcode barcode = new MyBarcode(barcodes.valueAt(0));
                            barcode.setReadingSource(ReadingSource.IMAGE);
                            barcode.setReadingDate(new Date());

                            drawImageAndOverlay(barcode);

                            mImageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    showBarcodeDialog(barcode);
                                }
                            });

                        } else {
                            mImageView.setOnClickListener(null);
                            Toast.makeText(getContext(), getString(R.string.message_barcode_could_not_be_detected), Toast.LENGTH_LONG).show();
                            mImageView.setBackgroundColor(0);
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }


    private void findViews(View baseView) {
        mImageView = (ImageView) baseView.findViewById(R.id.fragment_file_image_placeholder);
        mButtonChoose = (Button) baseView.findViewById(R.id.fragment_file_button_choose);
    }

    private void drawImageAndOverlay(MyBarcode barcode) {
        final float stroke = 40;
        BitmapDrawable bmpDraw = (BitmapDrawable) mImageView.getDrawable();
        Bitmap bmp = bmpDraw.getBitmap().copy(Bitmap.Config.RGB_565, true);

        Canvas canvas = new Canvas(bmp);

        Rect barcodeRect = barcode.getBoundingBox();

        canvas.clipRect(barcodeRect.left - stroke, barcodeRect.top - stroke, barcodeRect.right + stroke, barcodeRect.bottom + stroke, Region.Op.DIFFERENCE);
        Rect rectFull = new Rect(bmpDraw.getBounds());

        Paint paintOverlay = new Paint();
        paintOverlay.setAntiAlias(true);
        paintOverlay.setColor(ContextCompat.getColor(getContext(), R.color.overlay));
        canvas.drawRect(rectFull, paintOverlay);

        mImageView.setImageBitmap(bmp);
    }

    private void configureClickListeners() {
        mButtonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                startActivityForResult(pickIntent, SELECT_PHOTO);
            }
        });
    }

    private void showBarcodeDialog(MyBarcode barcode) {
        BarcodeDialogFragment dialogCode = DialogUtils.createBarcodeDialog(barcode);
        dialogCode.show(getFragmentManager(), BarcodeDialogFragment.TAG);
    }
}
