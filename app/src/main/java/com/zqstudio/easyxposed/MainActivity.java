package com.zqstudio.easyxposed;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 修改主页提示语
		String msg = "EasyXposed 帮你免重启了，\n开始你的 HOOK 吧！";
		TextView textView = findViewById(R.id.tips);
		textView.setText(msg);
	}
}
