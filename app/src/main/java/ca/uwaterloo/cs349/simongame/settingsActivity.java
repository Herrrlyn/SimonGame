package ca.uwaterloo.cs349.simongame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class settingsActivity extends AppCompatActivity {
    private int difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button easyButton = (Button) (findViewById(R.id.button1));
        final Intent easyIntent = new Intent(this, buttonNumberActivity.class);
        easyButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                difficulty = 4;
                easyIntent.putExtra("difficulty", difficulty);
                startActivity(easyIntent);
            }});

        Button normalButton = (Button) (findViewById(R.id.button2));
        final Intent normalIntent = new Intent(this, buttonNumberActivity.class);
        normalButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                difficulty = 3;
                normalIntent.putExtra("difficulty", difficulty);
                startActivity(normalIntent);
            }});

        Button hardButton = (Button) (findViewById(R.id.button3));
        final Intent hardIntent = new Intent(this, buttonNumberActivity.class);
        hardButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                difficulty = 2;
                hardIntent.putExtra("difficulty", difficulty);
                startActivity(hardIntent);

            }});
    }
}
