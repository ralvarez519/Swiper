package ralvarez519.swiper;

import android.os.CountDownTimer;
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
    private boolean timeOver;

    private static final int RIGHT_SWIPE = 150;
    private static final int LEFT_SWIPE = -150;
    private int currScore;
    public int counter ;
    private float Lx1, Lx2, Rx1, Rx2;


    private Button LBtn;
    private Button RBtn;
    private TextView Score;
    private TextView Timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_dual_buttons);

        LBtn = (Button) findViewById(R.id.left_btn_2mode);
        LBtn.setBackground(getDrawable( R.mipmap.button_icon) );
        RBtn = (Button) findViewById(R.id.right_btn_2mode);
        RBtn.setBackground(getDrawable( R.mipmap.button_icon) );
        Score = (TextView) findViewById(R.id.score_dualmode);
        Timer = (TextView) findViewById(R.id.timer_dualmode);
        currScore = 0;
        timeOver = false;

        new CountDownTimer(30000, 1000){
            public void onTick(long millisUntilFinished){
                Timer.setText(String.valueOf(30 - counter));
                counter++;
            }
            public  void onFinish(){
                timeOver = true;
                Timer.setText("FINISH!!");
            }
        }.start();

        initButtons();
    }

    private void initButtons() {
        leftAction = "press";
        rightAction = "press";
        Score.setText("0");

        LBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (timeOver) return false;
                int action = MotionEventCompat.getActionMasked(event);
                String currAction = "none";

                switch (action){
                    case (MotionEvent.ACTION_DOWN):
                        Lx1 = event.getX();
                        break;
                    case (MotionEvent.ACTION_UP) :
                        Lx2 = event.getX();
                        float deltaX = Lx2 - Lx1;
                        if (deltaX > RIGHT_SWIPE) {
                            currAction = "right swipe";
                        }
                        else if (deltaX < LEFT_SWIPE){
                            currAction = "left swipe";
                        }
                        else {
                            currAction = "press";
                        }
                        break;
                    default:
                        break;
                }
                /*Upon successful press, increase score and randomize next instruction*/
                if (currAction.equals(leftAction)) {
                    currScore += 100000;
                    Score.setText(String.valueOf(currScore));
                    leftAction = randAction();
                    LBtn.setText(leftAction);
                    if (leftAction.equals("press"))
                        LBtn.setBackground(getDrawable( R.mipmap.button_icon) );
                    else if (leftAction.equals("left swipe"))
                        LBtn.setBackground(getDrawable( R.mipmap.l_swipe) );
                    else if (leftAction.equals("right swipe"))
                        LBtn.setBackground(getDrawable( R.mipmap.r_swipe) );

                }
                else Log.d("FAIL", "/Attempt " + currAction + " Actual: " + leftAction);
                return true;

            }
        });
        RBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (timeOver) return false;
                int action = MotionEventCompat.getActionMasked(event);
                String currAction = "none";

                switch (action){
                    case (MotionEvent.ACTION_DOWN):
                        Rx1 = event.getX();
                        break;
                    case (MotionEvent.ACTION_UP) :
                        Rx2 = event.getX();
                        float deltaX = Rx2 - Rx1;
                        if (deltaX > RIGHT_SWIPE) {
                            currAction = "right swipe";
                        }
                        else if (deltaX < LEFT_SWIPE){
                            currAction = "left swipe";
                        }
                        else {
                            currAction = "press";
                        }
                    default:
                        break;
                }
                if (currAction.equals(rightAction)){
                    currScore += 100000;
                    Score.setText(String.valueOf(currScore));
                    rightAction = randAction();
                    RBtn.setText(rightAction);
                    if (rightAction.equals("press"))
                        RBtn.setBackground(getDrawable( R.mipmap.button_icon) );
                    else if (rightAction.equals("left swipe"))
                        RBtn.setBackground(getDrawable( R.mipmap.l_swipe) );
                    else if (rightAction.equals("right swipe"))
                        RBtn.setBackground(getDrawable( R.mipmap.r_swipe) );

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
