package ca.uwaterloo.cs349.simongame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class SixButtonGameActivity extends AppCompatActivity implements Observer, View.OnClickListener {

    simonModel Simon;
    Button Six_1, Six_2, Six_3, Six_4, Six_5, Six_6, six_begin, six_Submit;
    int delay = 1, difficulty;
    boolean correct = true;


    TextView scoreNum, message;

    public void flash(final Button butt){
        difficulty = getIntent().getIntExtra("difficulty", 0);
        butt.setPressed(true);
        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                butt.setPressed(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {

                    @Override
                    public void run() {

                    }
                }, (difficulty-1)*250);
            }
        }, (difficulty-1)*250);

    }

    public Integer buttToInteger(final Button butt){
        Integer retval = null;
        if(butt == Six_1){
            retval = 0;
        } else if(butt == Six_2){
            retval = 1;
        } else if(butt == Six_3){
            retval = 2;
        } else if(butt == Six_4){
            retval = 3;
        } else if(butt == Six_5){
            retval = 4;
        }else if(butt == Six_6){
            retval = 5;
        }
        return retval;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six_button_game);

        Simon = simonModel.getInstance();
        Simon.setNumButtons(6);


        Six_1 = findViewById(R.id.SixButt_1);
        Six_2 = findViewById(R.id.SixButt_2);
        Six_3 = findViewById(R.id.SixButt_3);
        Six_4 = findViewById(R.id.SixButt_4);
        Six_5 = findViewById(R.id.SixButt_5);
        Six_6 = findViewById(R.id.SixButt_6);
        six_begin = findViewById(R.id.sixBegin);
        six_Submit = findViewById(R.id.sixSubmit);
        scoreNum = findViewById(R.id.sixScore);
        message = findViewById(R.id.sixMessage);

        Simon.addObserver(this);



        six_begin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                correct = true;
                delay = 1;
                //message = (TextView) v.getRootView().findViewById(R.id.sixMessage);
                message.setText("Watch what I do...");
                Simon.newRound();
                final int[] index = {0};
                while (Simon.getState() == simonModel.State.COMPUTER) {
                    final Integer next = Simon.nextButton();
                    Handler init = new Handler();
                    init.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            if (index[0] != 0) {
                                Handler space = new Handler();
                                space.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (next == 0) {
                                            flash(Six_1);
                                        } else if (next == 1) {
                                            flash(Six_2);
                                        } else if (next == 2) {
                                            flash(Six_3);
                                        } else if (next == 3) {
                                            flash(Six_4);
                                        } else if (next ==4) {
                                            flash(Six_5);
                                        } else if (next ==5 ){
                                            flash(Six_6);
                                        }
                                    }
                                }, delay*difficulty*250);
                                delay++;
                            }else {

                                if (next == 0) {
                                    flash(Six_1);
                                } else if (next == 1) {
                                    flash(Six_2);
                                } else if (next == 2) {
                                    flash(Six_3);
                                } else if (next == 3) {
                                    flash(Six_4);
                                } else if (next ==4) {
                                    flash(Six_5);
                                } else if (next ==5) {
                                    flash(Six_6);
                                }
                                    index[0]++;
                                }
                            }
                        }, 500);

                }

                Handler changeTurn = new Handler();
                changeTurn.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        message.setText("It is your turn!");
                    }
                }, 1000*delay);

            }
        });

        //while (Simon.getState() == simonModel.State.COMPUTER) {
        Six_1.setOnClickListener(this);
        Six_2.setOnClickListener(this);
        Six_3.setOnClickListener(this);
        Six_4.setOnClickListener(this);
        Six_5.setOnClickListener(this);
        Six_6.setOnClickListener(this);
        //}


        six_Submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //message = (TextView) v.getRootView().findViewById(R.id.sixMessage);
                if (correct) {
                    message.setText("You Win");
                    scoreNum.setText(String.valueOf(Simon.getScore()));
                }

            }
        });

    }

    /*public void computer(){

    }

    public void human(){

    }*/



    @Override
    public void onClick(View view){

        switch(view.getId()) {
            case R.id.SixButt_1:
                if(!Simon.verifyButton(buttToInteger(Six_1))){
                    message.setText("You Lose!");
                    scoreNum.setText("0");

                    correct = false;
                }
                break;
            case R.id.SixButt_2:
                if(!Simon.verifyButton(buttToInteger(Six_2))){
                    message.setText("You Lose!");
                    scoreNum.setText("0");

                    correct = false;
                }
                break;
            case R.id.SixButt_3:
                if(!Simon.verifyButton(buttToInteger(Six_3))){
                    message.setText("You Lose!");
                    scoreNum.setText("0");

                    correct = false;
                }
                break;
            case R.id.SixButt_4:
                if(!Simon.verifyButton(buttToInteger(Six_4))){
                    message.setText("You Lose!");
                    scoreNum.setText("0");

                    correct = false;
                }
                break;
            case R.id.SixButt_5:
                if(!Simon.verifyButton(buttToInteger(Six_5))){
                    message.setText("You Lose!");
                    scoreNum.setText("0");

                    correct = false;
                }
                break;
            case R.id.SixButt_6:
                if(!Simon.verifyButton(buttToInteger(Six_6))){
                    message.setText("You Lose!");
                    scoreNum.setText("0");

                    correct = false;
                }
                break;
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //Log.d(String.valueOf(R.string.DEBUG_MVC_ID), "MainActivity: onDestory");

        // Remove observer when activity is destroyed.
        Simon.deleteObserver(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view1, menu);
        inflater.inflate(R.menu.view2, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle interaction based on item selection
        Intent intent;
        switch (item.getItemId())
        {
            case R.id.menu1_GoToSettings:
                // Create Intent to launch second activity
                intent = new Intent(this, settingsActivity.class);

                startActivity(intent);
                finish();
                return true;
            case R.id.menu1_GoToButtonNumber:
                // Create Intent to launch second activity
                intent = new Intent(this, buttonNumberActivity.class);
                intent.putExtra("difficulty", difficulty);
                startActivity(intent);
                finish();
                return true;

            // Start activity

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(Observable o, Object arg)
    {


    }
}