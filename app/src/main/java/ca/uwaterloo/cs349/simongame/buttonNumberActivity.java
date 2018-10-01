package ca.uwaterloo.cs349.simongame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class buttonNumberActivity extends AppCompatActivity {
    simonModel Simon;
    private int difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_number);

        difficulty = getIntent().getIntExtra("difficulty", 0);

        Button oneButton = (Button) (findViewById(R.id.button_1));
        final Intent oneIntent = new Intent(this, OneButtonGameActivity.class);
        oneButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Simon = simonModel.getInstance();
                Simon.setNumButtons(1);
                oneIntent.putExtra("difficulty", difficulty);
                startActivity(oneIntent);

            }});

        Button twoButton = (Button) (findViewById(R.id.button_2));
        final Intent twoIntent = new Intent(this, TwoButtonGameActivity.class);
        twoButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Simon = simonModel.getInstance();
                Simon.setNumButtons(2);
                twoIntent.putExtra("difficulty", difficulty);
                startActivity(twoIntent);

            }});

        Button threeButton = (Button) (findViewById(R.id.button_3));
        final Intent threeIntent = new Intent(this, ThreeButtonGameActivity.class);
        threeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Simon = simonModel.getInstance();
                Simon.setNumButtons(3);
                threeIntent.putExtra("difficulty", difficulty);
                startActivity(threeIntent);

            }});

        Button fourButton = (Button) (findViewById(R.id.button_4));
        final Intent fourIntent = new Intent(this, FourButtonGameActivity.class);
        fourButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Simon = simonModel.getInstance();
                Simon.setNumButtons(4);
                fourIntent.putExtra("difficulty", difficulty);
                startActivity(fourIntent);

            }});

        Button fiveButton = (Button) (findViewById(R.id.button_5));
        final Intent fiveIntent = new Intent(this, FiveButtonGameActivity.class);
        fiveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Simon = simonModel.getInstance();
                Simon.setNumButtons(5);
                fiveIntent.putExtra("difficulty", difficulty);
                startActivity(fiveIntent);

            }});

        Button sixButton = (Button) (findViewById(R.id.button_6));
        final Intent sixIntent = new Intent(this, SixButtonGameActivity.class);
        sixButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Simon = simonModel.getInstance();
                Simon.setNumButtons(6);
                sixIntent.putExtra("difficulty", difficulty);
                startActivity(sixIntent);

            }});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view1, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle interaction based on item selection
        switch (item.getItemId())
        {
            case R.id.menu1_GoToSettings:
                // Create Intent to launch second activity
                Intent intent = new Intent(this, settingsActivity.class);

                // Start activity
                startActivity(intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
