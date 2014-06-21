package com.example.secondattempt;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
	 
	Button button1;
	Button button2;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addListenerOnButton();
	}
 
	public void addListenerOnButton() {
 
		final Context context = this;
 
		button1 = (Button) findViewById(R.id.button_one);
		button2 = (Button) findViewById(R.id.button_two);
 
		button1.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, SecondScreen.class);
                            startActivity(intent);   
 
			}
 
		});
		
		button2.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, ThirdScreen.class);
			    intent.putExtra("MoneyValue", 1000);
                            startActivity(intent);   
 
			}
 
		});
		
		
		
		
		
 
	}
 
}
