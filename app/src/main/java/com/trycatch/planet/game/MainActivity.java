package com.trycatch.planet.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.trycatch.planet.R;
import com.trycatch.planet.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        View v = this.getWindow().getDecorView();
        v.setSystemUiVisibility(View.GONE);

        databaseHelper = new DatabaseHelper(this);
    }

    public void Start(View view) {
        try {
            if (databaseHelper.getName() != null) {
                Intent intent = new Intent(MainActivity.this, GameLevels.class);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
            Intent intent = new Intent(MainActivity.this, Registration.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Click again to exit the game", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
