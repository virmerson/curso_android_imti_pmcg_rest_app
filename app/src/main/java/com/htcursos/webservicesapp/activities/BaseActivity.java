package com.htcursos.webservicesapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.htcursos.webservicesapp.R;

import butterknife.Bind;


public class BaseActivity extends AppCompatActivity {
    //Barra de progresso, view de carregamento
    private ProgressDialog loading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       //Comportamento ao girar a tela
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    public void startLoading() {
        //Pega a view da activity
        View view = this.getCurrentFocus();
        if (view != null) {

            //Esconde o teclado
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        if (loading == null) {
            //Carrega o Loading da Activity
            loading = new ProgressDialog(this);
            loading.setCancelable(false);
            loading.setMessage("Carregando");
        }
        //Exibe quando não estiver visivel
        if (!loading.isShowing()) {
            loading.show();
        }
    }

    public void finishLoading() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        if (loading != null && loading.isShowing()) {
            loading.cancel();
        }
    }

}
