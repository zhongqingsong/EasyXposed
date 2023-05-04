package com.zqstudio.easyxposed;

import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_UP){
			android.os.Process.killProcess(android.os.Process.myPid());
		}else{
			System.exit(0);
		}
		return super.dispatchTouchEvent(ev);
	}

}
