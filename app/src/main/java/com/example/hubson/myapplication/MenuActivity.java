package com.example.hubson.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MenuActivity extends Activity {
    private ImageButton startGameBtn;
    private ImageButton optionsBtn;
    private ImageButton statisticsBtn;
    private ImageButton helpBtn;
    private ImageButton exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViewsById();
        startGame();
        goToOption();
        goToStatistics();
        goToHelp();
        exitGame();
    }

    private void findViewsById() {
        startGameBtn = (ImageButton) findViewById(R.id.startGameBtn);
        optionsBtn = (ImageButton) findViewById(R.id.optionsBtn);
        statisticsBtn = (ImageButton) findViewById(R.id.statisticsBtn);
        helpBtn = (ImageButton) findViewById(R.id.helpBtn);
        exitBtn = (ImageButton) findViewById(R.id.exitBtn);
    }

    public void startGame() {
        startGameBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    public void goToOption() {
        optionsBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(getResources().getString(R.string.option_message));
            }
        });
    }


    public void goToStatistics() {
        statisticsBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(getResources().getString(R.string.statistics_message));
            }
        });
    }

    public void goToHelp() {
        helpBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(getResources().getString(R.string.help_message));
            }
        });
    }


    public void exitGame() {
        exitBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
            }
        });

    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
