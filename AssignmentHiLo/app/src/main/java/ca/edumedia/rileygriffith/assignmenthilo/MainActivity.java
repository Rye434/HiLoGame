package ca.edumedia.rileygriffith.assignmenthilo;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity {
    private static final String ABOUT_DIALOG_TAG = "About Dialog";
    protected int theNumber;
    public int guessCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        theNumber = createRandomNumber();
        final Button reset = findViewById(R.id.Reset);
        reset.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                theNumber = createRandomNumber();
                guessCount = 0;
                EditText userGuess = findViewById(R.id.userGuess);
                userGuess.setText("");

                TextView remaining = findViewById(R.id.remainingGuesses);
                remaining.setText("10");

                Context c2 = getApplicationContext();
                String message2 = "Game Reset";
                int duration2 = Toast.LENGTH_LONG;
                Toast t2 = Toast.makeText(c2,message2,duration2);
                t2.show();
            }
        });
        reset.setOnLongClickListener(new View.OnLongClickListener(){
            public boolean onLongClick(View v) {
                Context c = getApplicationContext();
                String stringInt = Integer.toString(theNumber);
                String message = stringInt;
                int duration = Toast.LENGTH_LONG;
                Toast t = Toast.makeText(c,message,duration);
                t.show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialog();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected int createRandomNumber(){
        Random rand = new Random();
        int theNumber = rand.nextInt(1001);
        return theNumber;
    }

    public void onGuess(View EditText) {
        EditText userGuess = findViewById(R.id.userGuess);
        String guess = userGuess.getText().toString();
        Pattern p = Pattern.compile ("^[1-9][0-9]?[0-9]?");
        Matcher m = p.matcher(guess);
        boolean b = m.matches();
        if (b == true) {
            int result = Integer.parseInt(guess);
            checkNumber(result);
        } else {
            userGuess.setError("Invalid Guess");
            userGuess.requestFocus();
        }
    }

    public void checkNumber(int userGuess) {
        TextView remaining = findViewById(R.id.remainingGuesses);
        guessCount++;
        int holder = (10 - guessCount);
        if (holder >= 0) {
            String setText = Integer.toString(holder);
            remaining.setText(setText);
        } else {
            remaining.setText("0");
        }
        if (guessCount >= 11) {
            Context c = getApplicationContext();
            String message = getString(R.string.pleaseReset);
            int duration = Toast.LENGTH_LONG;
            Toast t = Toast.makeText(c,message,duration);
            t.show();
        } else if (guessCount < 5 & userGuess == theNumber) {
            Context c = getApplicationContext();
            String message = getString(R.string.superiorWin);
            int duration = Toast.LENGTH_LONG;
            Toast t = Toast.makeText(c,message,duration);
            t.show();
        } else if (guessCount < 10 & userGuess == theNumber) {
            Context c = getApplicationContext();
            String message = getString(R.string.ExcellentWin);
            int duration = Toast.LENGTH_LONG;
            Toast t = Toast.makeText(c,message,duration);
            t.show();
        } else if (userGuess > theNumber) {
            Context c = getApplicationContext();
            String message = getString(R.string.tooHigh);
            int duration = Toast.LENGTH_LONG;
            Toast t = Toast.makeText(c,message,duration);
            t.show();
        } else if (userGuess < theNumber){
            Context c = getApplicationContext();
            String message = getString(R.string.tooLow);
            int duration = Toast.LENGTH_LONG;
            Toast t = Toast.makeText(c,message,duration);
            t.show();
        }

    }

}
