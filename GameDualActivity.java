package ralvarez519.swiper;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ThreadLocalRandom;


public class GameDualActivity extends AppCompatActivity {

    private String leftAction;
    private String rightAction;

    private static final int MIN_DISTANCE = 150;
    private int currScore;
    private float Lx1, Lx2, Rx1, Rx2;


    private Button LBtn;
    private Button RBtn;
    private TextView Score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_dual_buttons);

        LBtn = (Button) findViewById(R.id.left_btn_2mode);
        RBtn = (Button) findViewById(R.id.right_btn_2mode);
        Score = (TextView) findViewById(R.id.editText);
        currScore = 0;
        initButtons();
    }

    private void initButtons() {
        leftAction = "press";
        rightAction = "press";
        Score.setText("0");

        LBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);
                String currAction = "none";

                switch (action){
                    case (MotionEvent.ACTION_DOWN):
                        Lx1 = event.getX();
                        break;
                    case (MotionEvent.ACTION_UP) :
                        Lx2 = event.getX();
                        float deltaX = Lx2 - Lx1;
                        if (Math.abs (deltaX) > MIN_DISTANCE) {
                            currAction = "swipe";
                            Log.d("ACTION", "Swipe");
                        }
                        else {
                            currAction = "press";
                            Log.d("ACTION", "Press");
                        }
                        break;
                    default:
                        break;
                }
                /*Upon successful press, increase score and randomize next instruction*/
                if (currAction.equals(leftAction)) {
                    currScore += 100;
                    Score.setText(String.valueOf(currScore));
                    leftAction = randAction();
                    LBtn.setText(leftAction);
                }
                else Log.d("FAIL", "/Attempt " + currAction + " Actual: " + leftAction);
                return true;

            }
        });
        RBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);
                String currAction = "none";

                switch (action){
                    case (MotionEvent.ACTION_DOWN):
                        Rx1 = event.getX();
                        break;
                    case (MotionEvent.ACTION_UP) :
                        Rx2 = event.getX();
                        float deltaX = Rx2 - Rx1;
                        if (Math.abs (deltaX) > MIN_DISTANCE) {
                            currAction = "swipe";
                            Log.d("ACTION", "Swipe");
                        }
                        else {
                            currAction = "press";
                            Log.d("ACTION", "Press");
                        }
                    default:
                        break;
                }
                if (currAction.equals(rightAction)){
                    currScore += 100;
                    Score.setText(String.valueOf(currScore));
                    rightAction = randAction();
                    RBtn.setText(rightAction);
                }
                return true;
            }
        });
    }

    private String randAction() {
        String[] choices = getResources().getStringArray(R.array.Actions);
        int randomNum = ThreadLocalRandom.current().nextInt(0, choices.length);
        return choices[randomNum];
    }
}
