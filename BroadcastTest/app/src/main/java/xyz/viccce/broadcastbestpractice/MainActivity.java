package xyz.viccce.broadcastbestpractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
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

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button save;
    private Button load;
    private Button forceOffline;
    private EditText editText;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        save = (Button) findViewById(R.id.save);
        load = (Button) findViewById(R.id.load);
        forceOffline = (Button) findViewById(R.id.force_offline);
        editText = (EditText) findViewById(R.id.edit_text);
        webView = (WebView) findViewById(R.id.web_view);

        save.setOnClickListener(this);
        load.setOnClickListener(this);
        forceOffline.setOnClickListener(this);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        editText.setText(email);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://viccce.xyz");

    }

    @Override
    public void onClick(View v) {
        String text;
        switch (v.getId()) {
            case R.id.save:
                text = editText.getText().toString();
                saveToSDcard(text);
                break;
            case R.id.load:
                text = loadFromSDcard();
                editText.setText(text);
                editText.setSelection(text.length());
                break;
            case R.id.force_offline:
                Intent intent = new Intent("xyz.viccce.broadcastbestpractice.FORCE_OFFLINE");
                sendBroadcast(intent);
                break;
            default:
                ;
        }
    }

    public void saveToSDcard(String text) {
        try {
            FileOutputStream outputStream = openFileOutput("data", MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(text);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String loadFromSDcard() {
        String text = "";
        try {
            FileInputStream inputStream = openFileInput("data");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder content = new StringBuilder();
            text = reader.readLine();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
