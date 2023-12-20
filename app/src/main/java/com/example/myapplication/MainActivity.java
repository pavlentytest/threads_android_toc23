package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // view binding
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    private int counter = 0;
                    @Override
                    public void run() {
                        while(true) {
                            counter++;
                            doSlow();
                            // нельзя обращаться из другого потока напрямую в поток UI
                            //binding.textView.setText("counter+"+counter);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.textView.setText("Step "+counter);
                                }
                            });
                        }
                    }
                }).start();

            }
        });
    }
    public void doSlow() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}