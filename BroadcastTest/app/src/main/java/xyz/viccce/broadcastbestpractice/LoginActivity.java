package xyz.viccce.broadcastbestpractice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    private EditText emailEdit;
    private EditText passwordEdit;
    private CheckBox isRemember;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEdit = (EditText) findViewById(R.id.email);
        passwordEdit = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.sign_in_button);
        isRemember = (CheckBox) findViewById(R.id.is_remember);


        try {
            FileInputStream inputStream = null;
            BufferedReader reader = null;
            inputStream = openFileInput("email");
            reader = new BufferedReader(new InputStreamReader(inputStream));
            if (reader != null && !("".equals(reader))) {
                String email = reader.readLine();
                emailEdit.setText(email);
                emailEdit.setSelection(email.length());
            }
            reader.close();
            inputStream = openFileInput("password");
            reader = new BufferedReader(new InputStreamReader(inputStream));
            if (reader != null && !("".equals(reader))) {
                String password = reader.readLine();
                passwordEdit.setText(password);
                passwordEdit.setSelection(password.length());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //实现保存帐号密码
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                if ("admin@test.com".equals(email) && "123456".equals(password)) {

                    try {
                        FileOutputStream outputStream = null;
                        BufferedWriter writer = null;
                        outputStream = openFileOutput("email", MODE_PRIVATE);
                        writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                        writer.write(email);
                        writer.close();
                        if (isRemember.isChecked()) {
                            outputStream = openFileOutput("password", MODE_PRIVATE);
                            writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                            writer.write(password);
                            writer.close();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "帐号或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
