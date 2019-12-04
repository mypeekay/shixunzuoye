package com.peekay.shixun.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.peekay.shixun.R;

public class LoadingDialog {
    Context context;
    AlertDialog dialog;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    public AlertDialog ShowDialog(){
        View view1 = LayoutInflater.from(context).inflate(R.layout.load_dialog, null);
        ImageView imageView = view1.findViewById(R.id.img_dia);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.loading);
        imageView.setAnimation(animation);
        dialog = new AlertDialog.Builder(context).setView(view1).
                setCancelable(false).create();
        return dialog;
    }
}
