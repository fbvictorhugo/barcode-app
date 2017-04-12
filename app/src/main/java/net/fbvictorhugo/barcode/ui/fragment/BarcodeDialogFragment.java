package net.fbvictorhugo.barcode.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import net.fbvictorhugo.barcode.model.MyBarcode;
import net.fbvictorhugo.barcode.R;
import net.fbvictorhugo.barcode.ui.BarcodeModelView;
import net.fbvictorhugo.barcode.util.ActionUtils;
import net.fbvictorhugo.barcode.util.Constants;

/**
 * By fbvictorhugo on 30/03/17.
 */

public class BarcodeDialogFragment extends DialogFragment {

    public static final String TAG = "code_dialog_fragment";
    private OnDismissListener mOnDismissListener;
    private BarcodeModelView mBarcodeModelView;
    private ImageButton mButtonCustomAction;
    private ImageButton mButtonCopyAction;
    private ImageButton mButtonShareAction;
    private Button mButtonOk;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);

        MyBarcode barcode = args.getParcelable(Constants.KEY_BARCODE);
        mBarcodeModelView = new BarcodeModelView(barcode);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog dialogBuider = new AlertDialog.Builder(getActivity())
                .setTitle(mBarcodeModelView.getBarcodeFormatName())
                .setMessage(mBarcodeModelView.getBarcodeValue())
                .create();
        setCancelable(false);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_barcode, null);
        findViews(view);

        configureButtons();

        dialogBuider.setView(view);
        return dialogBuider;
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss(dialog);
        }
    }

    private void findViews(View view) {
        mButtonCopyAction = (ImageButton) view.findViewById(R.id.dialog_code_button_copy);
        mButtonShareAction = (ImageButton) view.findViewById(R.id.dialog_code_button_share);
        mButtonCustomAction = (ImageButton) view.findViewById(R.id.dialog_code_button_custom_action);
        mButtonOk = (Button) view.findViewById(R.id.dialog_code_button_ok);
    }

    private void configureButtons() {

        // Button copy
        mButtonCopyAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("barcode", mBarcodeModelView.getBarcodeValue());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), R.string.copied_to_clipboard, Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        // Button share
        mButtonShareAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionUtils.startIntentForSharing(getContext(), mBarcodeModelView.getBarcodeValue());
                dismiss();
            }
        });

        // Button Custom Action
        if (mBarcodeModelView.hasCustomAction()) {
            mButtonCustomAction.setImageResource(mBarcodeModelView.getImageForCustomAction());
            mButtonCustomAction.setVisibility(View.VISIBLE);
            mButtonCustomAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBarcodeModelView.excecuteCustomAction(getContext());
                    dismiss();
                }
            });
        }

        // Button OK
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    interface OnDismissListener {
        void onDismiss(final DialogInterface dialog);
    }

    public void setDissmissListener(OnDismissListener dissmissListener) {
        this.mOnDismissListener = dissmissListener;
    }

}

