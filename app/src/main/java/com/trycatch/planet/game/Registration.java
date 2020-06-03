package com.trycatch.planet.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.trycatch.planet.R;
import com.trycatch.planet.database.DatabaseHelper;

public class Registration extends AppCompatActivity {

    private EditText nickname;
    String login;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        nickname = findViewById(R.id.enterNickname);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void Submit(View view) {
        login = nickname.getText().toString();
        if (login.length() < 3) {
            Toast.makeText(getApplicationContext(), "Никнэйм не может быть короче 3 символов!", Toast.LENGTH_SHORT).show();
        } else {
            enterNickname(login);
        }
    }

    public void enterNickname(String login) {
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.addPlayer(login);
        databaseHelper.addMission("Сборщик", "Накопить 1000 золота", 1);
        databaseHelper.addMission("Супер сборщек", "Накопи 2000 золота", 1);
        databaseHelper.addMission("Что-то", "Сделай что-то", 2);
        databaseHelper.addMission("Что-то", "Сделай что-то", 2);
        databaseHelper.addMission("Что-то", "Сделай что-то", 2);
        databaseHelper.addMission("Что-то", "Сделай что-то", 2);

        Intent intent = new Intent(Registration.this, GameLevels.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(Registration.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
        }
    }
}