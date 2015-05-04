package baldin.pedro.topicos_text2voice;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class Speech2Text extends ActionBarActivity {

    /* Definition of the classes used by the Project */
    class TranslateButton extends Button {
        OnClickListener clicker = new OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
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
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        editText.setHint("Your text will appear here soon....");
        editText.setGravity(Gravity.CENTER);
        editText.setLayoutParams(layoutParams);
        editText.setInputType(InputType.TYPE_NULL);
        gridLayout.addView(editText);

        layoutParams = new GridLayout.LayoutParams(GridLayout.spec(1), GridLayout.spec(0));
        layoutParams.width = screenWidth;
        layoutParams.height = screenHeight / 3;

        translateButton = new TranslateButton(this);
        translateButton.setText("Click here to speak...");
        translateButton.setBackgroundColor(Color.BLACK);
        translateButton.setTextColor(Color.WHITE);
        translateButton.setLayoutParams(layoutParams);
        gridLayout.addView(translateButton);

        gridLayout.setBackgroundColor(Color.LTGRAY);

        setContentView(gridLayout);
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something and wait for the Magic to happen :)");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support speech input",
                    Toast.LENGTH_SHORT).show();
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    editText.setText(result.get(0).toString());
                }
                break;
            }

        }
    }
}
