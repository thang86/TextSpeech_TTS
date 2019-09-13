package io.github.thang86.textspeech_tts;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class TextSpeechActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, View.OnClickListener {
    private static final String TAG = "TTS";
    private EditText mTextToSpeak;
    private Button mBtnSpeak;
    private TextToSpeech mTextToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_speech);
        initControl();
    }


    private void initControl() {
        mTextToSpeak = findViewById(R.id.textToSpeak);
        mBtnSpeak = findViewById(R.id.btnSpeak);
        mBtnSpeak.setEnabled(false);
        mTextToSpeech = new TextToSpeech(this, this);
        mBtnSpeak.setOnClickListener(this);
    }


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mTextToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(TAG, "language is not support");
            } else {
                mBtnSpeak.setEnabled(true);
                speakOut();
            }
        } else {
            Log.e(TAG, "Initilization failed!");
        }
    }

    private void speakOut() {
        String text = mTextToSpeak.getText().toString();
        if (text == null || text.isEmpty()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String utteranceId = this.hashCode() + "";
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
        } else {
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    protected void onDestroy() {
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        speakOut();
    }


}
