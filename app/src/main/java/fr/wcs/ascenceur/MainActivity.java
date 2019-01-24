package fr.wcs.ascenceur;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static boolean isLiftMoving = false;
    private static int currentFloor = 0;
    private TextView floorDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floorDisplay = findViewById(R.id.stage);
    }

    public void onClick(View v){
        Button b = (Button) v;
        goToFloor(Integer.parseInt(b.getText().toString()));
    }
    private void goToFloor(int floor) {
        if (!isLiftMoving && floor != currentFloor) {
            isLiftMoving = true;
            new MoveLift().execute(floor);
        }
    }

    private void setDisplay(String floor) {
        floorDisplay.setText(floor);
    }

    private class MoveLift extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... integers) {
            int floor = currentFloor;
            while (floor != integers[0]) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                floor += (floor < integers[0] ? 1 : -1);
                publishProgress(floor);
            }
            return false;
        }

        @Override
        protected void onProgressUpdate(Integer... progress){
            currentFloor = progress[0];
            setDisplay(String.valueOf(currentFloor));
        }

        @Override
        protected void onPostExecute(Boolean result) {
            isLiftMoving = result;
        }

    }
}
