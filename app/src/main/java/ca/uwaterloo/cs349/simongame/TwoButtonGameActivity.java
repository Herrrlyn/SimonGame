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

public class TwoButtonGameActivity extends AppCompatActivity implements Observer, View.OnClickListener {

    simonModel Simon;
    Button Two_1, Two_2, two_begin, two_Submit;
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
        if(butt == Two_1){
            retval = 0;
        } else if(butt == Two_2){
            retval = 1;
        }
        return retval;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_button_game);

        Simon = simonModel.getInstance();
        Simon.setNumButtons(2);


        Two_1 = findViewById(R.id.TwoButt_1);
        Two_2 = findViewById(R.id.TwoButt_2);
        two_begin = findViewById(R.id.twoBegin);
        two_Submit = findViewById(R.id.twoSubmit);
        scoreNum = findViewById(R.id.twoScore);
        message = findViewById(R.id.twoMessage);

        Simon.addObserver(this);



        two_begin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                correct = true;
                delay = 1;
                //message = (TextView) v.getRootView().findViewById(R.id.twoMessage);
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
                                            flash(Two_1);
                                        } else if (next == 1) {
                                            flash(Two_2);
                                        }
                                    }
                                }, delay*difficulty*250);
                                delay++;
                            }else {

                                if (next == 0) {
                                    flash(Two_1);
                                } else if (next == 1) {
                                    flash(Two_2);
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
        Two_1.setOnClickListener(this);
        Two_2.setOnClickListener(this);


        two_Submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //message = (TextView) v.getRootView().findViewById(R.id.twoMessage);
                if (correct) {
                    message.setText("You Win!");
                    scoreNum.setText(String.valueOf(Simon.getScore()));
                }

            }
        });

    }


    @Override
    public void onClick(View view){

        switch(view.getId()) {
            case R.id.TwoButt_1:
                if (!Simon.verifyButton(buttToInteger(Two_1))) {
                    message.setText("You Lose!");
                    scoreNum.setText("0");

                    correct = false;
                }
                break;
            case R.id.TwoButt_2:
                if (!Simon.verifyButton(buttToInteger(Two_2))) {
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
