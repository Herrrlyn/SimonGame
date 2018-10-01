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

public class FourButtonGameActivity extends AppCompatActivity implements Observer, View.OnClickListener {

    simonModel Simon;
    Button Four_1, Four_2, Four_3, Four_4, Four_begin, Four_Submit;
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
        Integer retval;
        if(butt == Four_1){
            retval = 0;
        } else if(butt == Four_2){
            retval = 1;
        } else if(butt == Four_3){
            retval = 2;
        } else {
            retval = 3;
        }
        return retval;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_button_game);

        Simon = simonModel.getInstance();
        Simon.setNumButtons(4);


        Four_1 = findViewById(R.id.FourButt_1);
        Four_2 = findViewById(R.id.FourButt_2);
        Four_3 = findViewById(R.id.FourButt_3);
        Four_4 = findViewById(R.id.FourButt_4);
        Four_begin = findViewById(R.id.fourBegin);
        Four_Submit = findViewById(R.id.fourSubmit);
        scoreNum = findViewById(R.id.fourScore);
        message = findViewById(R.id.fourMessage);

        Simon.addObserver(this);



        Four_begin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                correct = true;
                delay = 1;
                //message = (TextView) v.getRootView().findViewById(R.id.fourMessage);
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
                                            flash(Four_1);
                                        } else if (next == 1) {
                                            flash(Four_2);
                                        } else if (next == 2) {
                                            flash(Four_3);
                                        } else if (next == 3) {
                                            flash(Four_4);
                                        }
                                    }
                                }, delay*difficulty*250);
                                delay++;
                            }else {

                                if (next == 0) {
                                    flash(Four_1);
                                } else if (next == 1) {
                                    flash(Four_2);
                                } else if (next == 2) {
                                    flash(Four_3);
                                } else if (next == 3) {
                                    flash(Four_4);
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
            Four_1.setOnClickListener(this);
            Four_2.setOnClickListener(this);
            Four_3.setOnClickListener(this);
            Four_4.setOnClickListener(this);
            //}


            Four_Submit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //message = (TextView) v.getRootView().findViewById(R.id.fourMessage);
                    if (correct) {
                        message.setText("You Win!");
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
            case R.id.FourButt_1:
                if(!Simon.verifyButton(buttToInteger(Four_1))){
                    message.setText("You Lose!");
                    scoreNum.setText("0");

                    correct = false;
                }
                break;
            case R.id.FourButt_2:
                if(!Simon.verifyButton(buttToInteger(Four_2))){
                    message.setText("You Lose!");
                    scoreNum.setText("0");

                    correct = false;
                }
                break;
            case R.id.FourButt_3:
                if(!Simon.verifyButton(buttToInteger(Four_3))){
                    message.setText("You Lose!");
                    scoreNum.setText("0");

                    correct = false;
                }
                break;
            case R.id.FourButt_4:
                if(!Simon.verifyButton(buttToInteger(Four_4))){
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
                Simon.deleteObserver(this);
                finish();
                return true;
            case R.id.menu1_GoToButtonNumber:
                // Create Intent to launch second activity
                intent = new Intent(this, buttonNumberActivity.class);
                intent.putExtra("difficulty", difficulty);
                startActivity(intent);
                Simon.deleteObserver(this);
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
