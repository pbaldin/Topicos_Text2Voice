package baldin.pedro.topicos_text2voice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;


public class Text2Voice extends ActionBarActivity {
    /* Context Variable */
    final Context context = this;

    /* Definition of the classes used by the Project */
    class Speech2TextButton extends Button {
        OnClickListener clicker = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Speech2Text.class);
                startActivity(intent);
            }
        };

        public Speech2TextButton(Context context) {
            super(context);
            setOnClickListener(clicker);
        }
    }

    class Text2SpeechButton extends Button {
        OnClickListener clicker = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Text2Speech.class);
                startActivity(intent);
            }
        };

        public Text2SpeechButton(Context context) {
            super(context);
            setOnClickListener(clicker);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        /* Gets the size of the Display to position the layout components */
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenHeight = (int) (0.95 * size.y);
        int screenWidth = size.x;

        /* Create the Settings Screen Layout */
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(1);
        gridLayout.setRowCount(2);

        /* Creates the detect colors button to be used in the layout */
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(GridLayout.spec(0), GridLayout.spec(0));
        layoutParams.width = screenWidth;
        layoutParams.height = screenHeight / 2;

        Speech2TextButton speech2TextButton = new Speech2TextButton(this);
        speech2TextButton.setBackgroundResource(R.drawable.s2t);
        speech2TextButton.setLayoutParams(layoutParams);
        gridLayout.addView(speech2TextButton);

        layoutParams = new GridLayout.LayoutParams(GridLayout.spec(1), GridLayout.spec(0));
        layoutParams.width = screenWidth;
        layoutParams.height = screenHeight / 2;

        Text2SpeechButton text2SpeechButton = new Text2SpeechButton(this);
        text2SpeechButton.setBackgroundResource(R.drawable.t2s);
        text2SpeechButton.setLayoutParams(layoutParams);
        gridLayout.addView(text2SpeechButton);

        setContentView(gridLayout);
    }
}