package com.example.nikos.nfc;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";// we need this to create a new intend

//    Declare instances of NFCAdapter and textview
    private NfcAdapter nfcAdapter;
    TextView textViewInfo, textViewTagInfo;

//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewInfo = (TextView)findViewById(R.id.info);
//        textViewTagInfo = (TextView)findViewById(R.id.taginfo);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null){
            Toast.makeText(this,
                    "NFC NOT supported on this devices!",
                    Toast.LENGTH_LONG).show();
//            finish(); With this function the app terminates
        }else if(!nfcAdapter.isEnabled()){
            Toast.makeText(this,
                    "NFC NOT Enabled!",
                    Toast.LENGTH_LONG).show();
            //finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String action = intent.getAction();

        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            Toast.makeText(this,
                    "MifareClassic Detected",
                    Toast.LENGTH_SHORT).show();

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if(tag == null){
                textViewInfo.setText("tag == null");
            }else{
                String tagInfo="";

//                tagInfo += "\nTag Id: \n";
                byte[] tagId = tag.getId();
//                tagInfo += "length = " + tagId.length +"\n";
            //----------Display Tag ID --------------------
                for(int i=0; i<tagId.length; i++){
                    tagInfo += Integer.toHexString(tagId[i] & 0xff);
                }
//                tagInfo += "\n";

                textViewInfo.setText(tagInfo.toUpperCase()); // toUpperCase(): converts to capital letters

                //Only android.nfc.tech.MifareClassic specified in nfc_tech_filter.xml,
                //so must be MifareClassic
                readMifareClassic(tag);
            }
        }else{
//            Toast.makeText(this, "onResume() : " + action, Toast.LENGTH_SHORT).show(); // On resume action
        }
    }

    public void readMifareClassic(Tag tag){
        MifareClassic mifareClassicTag = MifareClassic.get(tag);

        String typeInfoString = "--- MifareClassic tag ---\n";
        int type = mifareClassicTag.getType();

    }


    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        TextView textViewInfo = (TextView)findViewById(R.id.info);
        String message = textViewInfo.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}
