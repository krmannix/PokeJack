package com.example.secondattempt;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.view.View.OnClickListener;

public class LoseScreen extends Activity {
	 
	Button backbuttonLose;
	ImageView lose;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.losescreen);
		addListenerOnButton();
	}
 
	public void addListenerOnButton() {
 
		final Context context = this;
 
		backbuttonLose = (Button) findViewById(R.id.backbut2);
		
 
		backbuttonLose.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, ThirdScreen.class);
			    intent.putExtra("MoneyValue", 1000);
                            startActivity(intent);   
 
			}
 
		});
		
			
 
	}
 
}
