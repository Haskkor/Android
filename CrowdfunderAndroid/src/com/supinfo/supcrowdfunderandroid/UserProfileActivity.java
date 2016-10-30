package com.supinfo.supcrowdfunderandroid;

import com.supinfo.supcrowdfunderandroid.model.User;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserProfileActivity extends Activity {
	
	private User user = null;
	private final int MY_CODE = 5;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		Bundle extras = getIntent().getExtras();
		
		if(extras != null) {
			 user = (User) extras.getSerializable("userLog");
		}
		
		final TextView userName = (TextView) findViewById(R.id.user_name);
		final TextView userLastName = (TextView) findViewById(R.id.user_last_name);
		final TextView userEmail = (TextView) findViewById(R.id.user_email);
		
		userName.setText("Name : "+user.getName());
		userLastName.setText("Last Name : "+user.getLastName());
		userEmail.setText("Email : "+user.getMail());
		
        // Buttons
        final Button buttonIndex = (Button)findViewById(R.id.submit_index);
        final Button buttonLogin = (Button)findViewById(R.id.submit_login);
        final Button buttonProjects = (Button)findViewById(R.id.submit_projects);

        buttonIndex.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
	            Intent intent = new Intent(getApplicationContext(), Index.class);
                startActivityForResult(intent,MY_CODE); 
			}
		});
        buttonLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
	            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivityForResult(intent,MY_CODE); 
			}
		});
        buttonProjects.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
	            Intent intent = new Intent(getApplicationContext(), ProjectsByCategory.class);
                startActivityForResult(intent,MY_CODE); 
			}
		});
        
        //Test if a user is logged, if true change background color of login button 
        SharedPreferences prefs = getSharedPreferences("test",Context.MODE_PRIVATE);
		// If there is no value for “userLog”, return null
		String username = prefs.getString("userLog", null);
		if (username==null){
			buttonLogin.setBackgroundColor(Color.RED);
		}else{buttonLogin.setBackgroundColor(Color.GREEN);}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_profile, menu);
		return true;
	}
}