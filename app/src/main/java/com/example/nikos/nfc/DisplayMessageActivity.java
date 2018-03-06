package com.example.nikos.nfc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

//Import OkHttp Libraries
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class DisplayMessageActivity extends AppCompatActivity {

    private TextView mTextViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);


        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
        Log.d("DisplayMessageActivity",message);
//---------OkHttp-----------
        mTextViewResult = findViewById(R.id.text_view_result);

        OkHttpClient client = new OkHttpClient();

        String url = "http://192.168.1.7:8080/access";

//--------------Java OkHttp------------------
        MediaType mediaType = MediaType.parse("application/json");

//        RequestBody body = RequestBody.create(mediaType, "{\n    \"userId\": \"abc1234\",\n    \"nfcId\": \"1d 52 a6 a9\",\n    \"accessToken\": \"fssflklkl32l53l\"\n}");

        JSONObject postdata = new JSONObject();
        try {
            postdata.put("userId", "abc1234");
            postdata.put("nfcId", message); //1d 52 a6 a9
            postdata.put("accessToken", "fssflklkl32l53l");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(mediaType,
                postdata.toString());

        Request request = new Request.Builder()
                .url("http://192.168.1.7:8080/access")
                .post(body)
                .addHeader("Content-type", "application/json; charset=UTF-8")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Postman-Token", "13a0d8dc-3701-44d2-b542-0f68d3db6637")
                .build();

//        Response response = client.newCall(request).execute();
//-------------------------------------------
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    DisplayMessageActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextViewResult.setText(myResponse);
                        }
                    });
                }
            }
        });

    }
}
