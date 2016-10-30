package com.supinfo.supcrowdfunderandroid;

import java.util.ArrayList;

import com.supinfo.supcrowdfunderandroid.model.Project;

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

public class ProjectDetailsActivity extends Activity {
	private ArrayList<Project> projects = null;
	private int projectindex;
	private Project project; 
	private final int MY_CODE = 4;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_details);
		Bundle extras = getIntent().getExtras();

		if(extras != null) {
			 projectindex = extras.getInt("projectIndex");
			 projects= (ArrayList<Project>) extras.getSerializable("projectList");
		}
		
		project=projects.get(projectindex);
		
		final TextView name_project= (TextView) findViewById(R.id.name_project);
		final TextView content_project= (TextView) findViewById(R.id.project_details);
		final TextView creator_project= (TextView) findViewById(R.id.creator_project);
		final TextView creationDate_project= (TextView) findViewById(R.id.project_creation_date);
		final TextView completionDate_project= (TextView) findViewById(R.id.project_completion_date);
		final TextView budget_project= (TextView) findViewById(R.id.project_amount_needed);
		final TextView donation_project= (TextView) findViewById(R.id.project_donation_amount);
		
		name_project.setText(project.getName());
		content_project.setText(project.getDescription());
		creator_project.setText(project.getCreator());
		creationDate_project.setText(project.getCreationDate());
		completionDate_project.setText(project.getCompletionDate());
		budget_project.setText(Float.toString(project.getAmountNeeded()));
		donation_project.setText(Float.toString(project.getDonationAmount()));
		
        //Buttons
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
		getMenuInflater().inflate(R.menu.project_details, menu);
		return true;
	}
}