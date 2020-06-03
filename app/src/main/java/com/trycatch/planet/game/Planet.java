package com.trycatch.planet.game;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.trycatch.planet.R;
import com.trycatch.planet.database.DatabaseHelper;
import com.trycatch.planet.fragments.FragmentMissions;
import com.trycatch.planet.fragments.FragmentShop;

public class Planet extends AppCompatActivity {
    Intent intent;
    ImageView imageView_first;
    TextView textLevel, countGold, countBlackMatter;
    Button btnShop, btnBack, btnMissions;

    DatabaseHelper databaseHelper;
    Thread addGold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planet);

        databaseHelper = new DatabaseHelper(this);

        imageView_first = findViewById(R.id.planetFirst);
        textLevel = findViewById(R.id.textLevel);
        countGold = findViewById(R.id.textCountGold);
        countBlackMatter = findViewById(R.id.textCountBlackMatter);

        textLevel.setText("" + databaseHelper.getLevel());
        countBlackMatter.setText(String.valueOf(databaseHelper.getMatter()));
        countGold.setText(String.valueOf(databaseHelper.getGold()));

        btnShop = findViewById(R.id.btnShop);
        btnBack = findViewById(R.id.btnBack);
        btnMissions = findViewById(R.id.btnMissions);

        getCountGoldInSecond();
        addCoinInSecond();

        imageView_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    databaseHelper.setGold(databaseHelper.getGold() + 50);
                    setGoldText();
                } catch (Exception e) {
                }
            }
        });

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void addCoinInSecond() {
        addGold = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!addGold.isInterrupted()) {
                    databaseHelper.setGold(databaseHelper.getGold() + 1);
                    try {
                        Thread.sleep((long) databaseHelper.getSecond());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        addGold.start();
    }

    public void getCountGoldInSecond() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        public void run() {
                            setGoldText();
                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }

    public void setGoldText() {
        countGold.setText(databaseHelper.getGold() + "");
    }

    public void showMissions(View v) {
        btnShop.setVisibility(View.GONE);
        btnBack.setVisibility(View.GONE);
        btnMissions.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                new FragmentMissions(), null).commit();
    }

    public void btnShopFromPlanet(View view) {
        btnShop.setVisibility(View.GONE);
        btnBack.setVisibility(View.GONE);
        btnMissions.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                new FragmentShop(), null).commit();
    }

    public void btnBackFromPlanet(View view) {
        try {
            addGold.isInterrupted();
            addGold.isInterrupted();
            intent = new Intent(Planet.this, GameLevels.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        try {
            addGold.isInterrupted();
            intent = new Intent(Planet.this, GameLevels.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
        }
    }
}