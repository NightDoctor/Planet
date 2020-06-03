package com.trycatch.planet.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.trycatch.planet.R;
import com.trycatch.planet.database.DatabaseHelper;
import com.trycatch.planet.fragments.FragmentMissions;
import com.trycatch.planet.fragments.FragmentShop;

public class GameLevels extends AppCompatActivity {
    Intent intent;
    ImageView imageView_first, imageView_second;
    TextView textLevel, countGold, countBlackMatter;
    Button btnShop, btnBack, btnMissions;

    DatabaseHelper databaseHelper;

    FragmentMissions fragmentMissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_levels);

        databaseHelper = new DatabaseHelper(this);

        imageView_first = findViewById(R.id.planetFirst);
        imageView_second = findViewById(R.id.planetSecond);
        textLevel = findViewById(R.id.textLevel);
        countGold = findViewById(R.id.textCountGold);
        countBlackMatter = findViewById(R.id.textCountBlackMatter);
        btnShop = findViewById(R.id.btnShop);
        btnBack = findViewById(R.id.btnBack);
        btnMissions = findViewById(R.id.btnMissions);

        textLevel.setText("" + databaseHelper.getLevel());
        countGold.setText("" + databaseHelper.getGold());
        countBlackMatter.setText("" + databaseHelper.getMatter());

        databaseHelper.checkMissions();

        fragmentMissions = new FragmentMissions();

        imageView_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    intent = new Intent(GameLevels.this, Planet.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                }
            }
        });
    }

    public void showMissions(View v) {
        btnShop.setVisibility(View.GONE);
        btnBack.setVisibility(View.GONE);
        btnMissions.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                new FragmentMissions(), null).commit();
    }

    public void toShopFromGL(View view) {
        btnShop.setVisibility(View.GONE);
        btnBack.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                new FragmentShop(), null).commit();
    }

    public void toBackFromGL(View view) {
        try {
            intent = new Intent(GameLevels.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        try {
            intent = new Intent(GameLevels.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
        }
    }
}