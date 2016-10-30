package com.supinfo.supcrowdfunderandroid;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.supinfo.supcrowdfunderandroid.model.User;
import com.supinfo.supcrowdfunderandroid.parser.XMLUserHandler;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	private User authUser;
	private final int MY_CODE = 3;
	private String logmail=null;
	private String logpassw=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_login);
        
        //Buttons
        final Button buttonIndex = (Button)findViewById(R.id.submit_index);
        final Button buttonProjects = (Button)findViewById(R.id.submit_projects);
        
        buttonIndex.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
	            Intent intent = new Intent(getApplicationContext(), Index.class);
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	private class BddRequest extends AsyncTask<String,Void,User> {
		
		@Override
		protected User doInBackground(String... params) {
				SAXParserFactory parserfact = SAXParserFactory.newInstance();
				SAXParser parser = null;
				try {
					parser = parserfact.newSAXParser();
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				}

				URL url = null;
				try {
					url = new URL("http://192.168.1.44:8080/SupCrowdfunder/resources/users/"+params[0]);
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
				
				DefaultHandler userHandler = new XMLUserHandler();
				try {
					InputStream input = url.openStream();
					if(input==null)
						Log.e("Erreur android","null");
					else{
						parser.parse(input, userHandler);
						authUser = ((XMLUserHandler) userHandler).getData();
					}
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return authUser;
			}
		}

		@Override
		protected void onResume() {
			super.onResume();
			final Button connect = (Button)findViewById(R.id.connect);
			final EditText mail = (EditText) findViewById(R.id.email_login);
            final EditText passw = (EditText) findViewById(R.id.password_login);
			
			connect.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
		            logmail=mail.getText().toString();
		            logpassw=passw.getText().toString();  
		            String param=logmail+"&"+logpassw;
		            
		            try {
		            	authUser = new BddRequest().execute(param).get();
		    		} catch (InterruptedException e) {
		    			e.printStackTrace();
		    		} catch (ExecutionException e) {
		    			e.printStackTrace();
		    		}
		            
		            if (authUser==null){
		            	Toast.makeText(getApplicationContext(), "User do not exist or wrong password!", Toast.LENGTH_SHORT).show();
		            }else{
		            	SharedPreferences prefs = getSharedPreferences("test",Context.MODE_PRIVATE);
		            	SharedPreferences.Editor editor = prefs.edit();
		            	editor.putString("userLog", authUser.getLastName()).commit(); 

						Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);	
						intent.putExtra("userLog", authUser);		
						startActivityForResult(intent, 5);
		            }
				}
			});
		}
}