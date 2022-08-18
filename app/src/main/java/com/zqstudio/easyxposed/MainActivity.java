package com.zqstudio.easyxposed;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 修改主页提示语
		String msg = "EasyLSP已启用，点击关闭。\n并手动选择生效应用。\n开始你的 HOOK 吧！";
		TextView textView = findViewById(R.id.tips);
		textView.setText(msg);
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
