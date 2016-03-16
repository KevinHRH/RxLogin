package com.myandroid.rxlogin;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.myandroid.model.InputValidation;

import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText confirmedPassword;
    private Button submitbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.password);
        confirmedPassword = (EditText) findViewById(R.id.confirmedPassword);
        submitbutton = (Button) findViewById(R.id.submit_button);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initRx();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initRx() {
        rx.Observable<String> userNameObservable = RxTextView.textChangeEvents(username)
                .map(InputValidation::stringOfTextChangEvent);
        rx.Observable<String> passwordObservable = RxTextView.textChangeEvents(password)
                .map(InputValidation::stringOfTextChangEvent);
        rx.Observable<String> confirmedPasswordObservable = RxTextView.textChangeEvents(confirmedPassword)
                .map(InputValidation::stringOfTextChangEvent);

        rx.Observable<InputValidation> inputValidationObservable = rx.Observable
                .zip(
                        userNameObservable,
                        passwordObservable,
                        confirmedPasswordObservable,
                        InputValidation::new)
                .map(inputValidation -> {
                    submitbutton.setEnabled(inputValidation.allowSubmit());
                    return inputValidation;
                })
                .observeOn(AndroidSchedulers.mainThread());

        RxView.clicks(submitbutton)
                .zipWith(inputValidationObservable,
                        (onClickEvent, inputValidation) -> inputValidation)
                .subscribe();
    }
}
