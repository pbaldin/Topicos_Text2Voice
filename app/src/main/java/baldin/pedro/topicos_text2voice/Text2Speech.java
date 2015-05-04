package baldin.pedro.topicos_text2voice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import java.util.Locale;


public class Text2Speech extends ActionBarActivity  implements TextToSpeech.OnInitListener {

    /* Definition of the classes used by the Project */
    class TranslateButton extends Button {
        OnClickListener clicker = new OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut();
            }
        };

        public TranslateButton(Context context) {
            super(context);
            setOnClickListener(clicker);
        }
    }

    /* Definition of the Variables used by the Project */
    private TranslateButton translateButton = null;
    private EditText editText = null;
    private TextToSpeech tts = null;

    protected void speakOut() {
        String text = editText.getText().toString();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tts = new TextToSpeech(this, this);

        /* Gets the size of the Display to position the layout components */
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenHeight = (int) (0.95 * size.y);
        int screenWidth = size.x;

        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(1);
        gridLayout.setRowCount(2);

        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(GridLayout.spec(0), GridLayout.spec(0));
        layoutParams.width = screenWidth;
        layoutParams.height = (2 * screenHeight / 3);

        editText = new EditText(this);
        editText.setHint("Please type the desired text...");
        editText.setGravity(Gravity.CENTER);
        editText.setLayoutParams(layoutParams);
        gridLayout.addView(editText);

        layoutParams = new GridLayout.LayoutParams(GridLayout.spec(1), GridLayout.spec(0));
        layoutParams.width = screenWidth;
        layoutParams.height = screenHeight / 3;

        translateButton = new TranslateButton(this);
        translateButton.setText("Click here to transform your text into voice");
        translateButton.setBackgroundColor(Color.BLACK);
        translateButton.setTextColor(Color.WHITE);
        translateButton.setLayoutParams(layoutParams);
        gridLayout.addView(translateButton);

        gridLayout.setBackgroundColor(Color.LTGRAY);

        setContentView(gridLayout);
        checkTTS();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);

            if (result != TextToSpeech.LANG_MISSING_DATA
                    && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                speakOut();
            } else {
                Log.e("TTS", "Initilization Failed!");
            }
        }
    }

    /* Definition of the functions used by the Project */
    private void checkTTS(){
        Intent check = new Intent();
        check.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(check, 0x1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.stop();
        tts.shutdown();
    }
}
