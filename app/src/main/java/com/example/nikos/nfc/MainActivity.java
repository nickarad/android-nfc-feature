package com.example.nikos.nfc;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

//    Declare instances of NFCAdapter an textview
    private NfcAdapter nfcAdapter;
    TextView textViewInfo, textViewTagInfo;



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
            finish();
        }else if(!nfcAdapter.isEnabled()){
            Toast.makeText(this,
                    "NFC NOT Enabled!",
                    Toast.LENGTH_LONG).show();
            finish();
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
                String tagInfo = "\n";

//                tagInfo += "\nTag Id: \n";
                byte[] tagId = tag.getId();
//                tagInfo += "length = " + tagId.length +"\n";
            //----------Display Tag ID --------------------
                for(int i=0; i<tagId.length; i++){
                    tagInfo += Integer.toHexString(tagId[i] & 0xFF) + " ";
                }
                tagInfo += "\n";
//
//                String[] techList = tag.getTechList();
//                tagInfo += "\nTech List\n";
//                tagInfo += "length = " + techList.length +"\n";
//                for(int i=0; i<techList.length; i++){
//                    tagInfo += techList[i] + "\n ";
//                }

                textViewInfo.setText(tagInfo);

                //Only android.nfc.tech.MifareClassic specified in nfc_tech_filter.xml,
                //so must be MifareClassic
                readMifareClassic(tag);
            }
        }else{
            Toast.makeText(this,
                    "onResume() : " + action,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void readMifareClassic(Tag tag){
        MifareClassic mifareClassicTag = MifareClassic.get(tag);

        String typeInfoString = "--- MifareClassic tag ---\n";
        int type = mifareClassicTag.getType();
//        switch(type){
//            case MifareClassic.TYPE_PLUS:
//                typeInfoString += "MifareClassic.TYPE_PLUS\n";
//                break;
//            case MifareClassic.TYPE_PRO:
//                typeInfoString += "MifareClassic.TYPE_PRO\n";
//                break;
//            case MifareClassic.TYPE_CLASSIC:
//                typeInfoString += "MifareClassic.TYPE_CLASSIC\n";
//                break;
//            case MifareClassic.TYPE_UNKNOWN:
//                typeInfoString += "MifareClassic.TYPE_UNKNOWN\n";
//                break;
//            default:
//                typeInfoString += "unknown...!\n";
//        }

//        int size = mifareClassicTag.getSize();
//        switch(size){
//            case MifareClassic.SIZE_1K:
//                typeInfoString += "MifareClassic.SIZE_1K\n";
//                break;
//            case MifareClassic.SIZE_2K:
//                typeInfoString += "MifareClassic.SIZE_2K\n";
//                break;
//            case MifareClassic.SIZE_4K:
//                typeInfoString += "MifareClassic.SIZE_4K\n";
//                break;
//            case MifareClassic.SIZE_MINI:
//                typeInfoString += "MifareClassic.SIZE_MINI\n";
//                break;
//            default:
//                typeInfoString += "unknown size...!\n";
//        }
//
//        int blockCount = mifareClassicTag.getBlockCount();
//        typeInfoString += "BlockCount \t= " + blockCount + "\n";
//        int sectorCount = mifareClassicTag.getSectorCount();
//        typeInfoString += "SectorCount \t= " + sectorCount + "\n";
//
//        textViewTagInfo.setText(typeInfoString);
    }


}
