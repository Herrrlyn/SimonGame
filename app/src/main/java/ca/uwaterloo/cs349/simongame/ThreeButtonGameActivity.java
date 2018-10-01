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

public class ThreeButtonGameActivity extends AppCompatActivity implements Observer, View.OnClickListener {

    simonModel Simon;
    Button Three_1, Three_2, Three_3, three_begin, three_Submit;
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
        if(butt == Three_1){
            retval = 0;
        } else if(butt == Three_2){
            retval = 1;
        } else if(butt == Three_3){
            retval = 2;
        }
        return retval;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_button_game);

        Simon = simonModel.getInstance();
        Simon.setNumButtons(3);


        Three_1 = findViewById(R.id.ThreeButt_1);
        Three_2 = findViewById(R.id.ThreeButt_2);
        Three_3 = findViewById(R.id.ThreeButt_3);

        three_begin = findViewById(R.id.threeBegin);
        three_Submit = findViewById(R.id.threeSubmit);
        scoreNum = findViewById(R.id.threeScore);
        message = findViewById(R.id.threeMessage);

        Simon.addObserver(this);



        three_begin.setOnClickListener(new View.OnClickListener() {
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
                                            flash(Three_1);
                                        } else if (next == 1) {
                                            flash(Three_2);
                                        } else if (next == 2) {
                                            flash(Three_3);
                                        }
                                    }
                                }, delay*difficulty*1000);
                                delay++;
                            }else {

                                if (next == 0) {
                                    flash(Three_1);
                                } else if (next == 1) {
                                    flash(Three_2);
                                } else if (next == 2) {
                                    flash(Three_3);
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
        Three_1.setOnClickListener(this);
        Three_2.setOnClickListener(this);
        Three_3.setOnClickListener(this);

        //}


        three_Submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //message = (TextView) v.getRootView().findViewById(R.id.ThreeMessage);
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
            case R.id.ThreeButt_1:
                if(!Simon.verifyButton(buttToInteger(Three_1))){
                    message.setText("You Lose!");
                    scoreNum.setText("0");

                    correct = false;
                }
                break;
            case R.id.ThreeButt_2:
                if(!Simon.verifyButton(buttToInteger(Three_2))){
                    message.setText("You Lose!");
                    scoreNum.setText("0");

                    correct = false;
                }
                break;
            case R.id.ThreeButt_3:
                if(!Simon.verifyButton(buttToInteger(Three_3))){
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
