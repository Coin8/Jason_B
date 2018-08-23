package com.coin.b8.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.coin.b8.R;

/**
 * Created by zhangyi on 2018/8/22.
 */
public class CoinStoreInputDialog {
    private AlertDialog mAlertDialog;
    private Context mContext;
    private TextView txtDialogTitle;
    private EditText txtInput;
    private TextView btnSelectNegative;
    private TextView btnSelectPositive;
    private String mTitle= "标题";
    private InputDialogOkClickListener mInputDialogOkClickListener;
    private TextView mCountWordView;

    public interface InputDialogOkClickListener {
        void onClick(Dialog dialog, String inputText);
    }

    public CoinStoreInputDialog(Context context,String title,InputDialogOkClickListener listener) {
        mContext = context;
        mTitle = title;
        mInputDialogOkClickListener = listener;
    }

    public void showDialog() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(mContext, R.style.inputDialoStyle);
        builder.setCancelable(false);
        mAlertDialog = builder.create();
        mAlertDialog.setView(new EditText(mContext));
        mAlertDialog.setCanceledOnTouchOutside(true);
        mAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mAlertDialog != null){
                    mAlertDialog.dismiss();
                }
            }
        });

        mAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mAlertDialog != null){
                    mAlertDialog.dismiss();
                }
            }
        });

        Window window = mAlertDialog.getWindow();
        window.setWindowAnimations(R.style.inputDialogAnimStyle);
        mAlertDialog.show();
        window.setContentView(R.layout.coin_store_input_dialog);
        mCountWordView = window.findViewById(R.id.count_word_view);
        txtDialogTitle = window.findViewById(R.id.txt_dialog_title);
        txtInput = window.findViewById(R.id.txt_input);
        btnSelectNegative = window.findViewById(R.id.btn_selectNegative);
        btnSelectPositive = window.findViewById(R.id.btn_selectPositive);
        txtInput = window.findViewById(R.id.txt_input);
        txtInput.setVisibility(View.VISIBLE);
        txtDialogTitle.setText(mTitle);
        txtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String tex = txtInput.getText().toString();
                if(TextUtils.isEmpty(tex)){
                    mCountWordView.setText("0/6");
                }else {
                    mCountWordView.setText(tex.length() + "/6");
                }
            }
        });
        btnSelectPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInputDialogOkClickListener != null){
                    mInputDialogOkClickListener.onClick(mAlertDialog,txtInput.getText().toString());
                }
                mAlertDialog.dismiss();
            }
        });
        btnSelectNegative.setVisibility(View.VISIBLE);
        btnSelectNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
    }

}
