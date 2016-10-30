package com.supinfo.supcrowdfunderandroid;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.supinfo.supcrowdfunderandroid.model.Category;
import com.supinfo.supcrowdfunderandroid.model.Project;
import com.supinfo.supcrowdfunderandroid.parser.XMLCategoryHandler;
import com.supinfo.supcrowdfunderandroid.parser.XMLProjectHandler;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class ProjectsByCategory extends Activity implements OnItemSelectedListener{
	
	private final int MY_CODE = 2;
	private ArrayList<Category> categories = null;
	CategoryListAdapter<Category> adapter = null;	
	private ArrayList<Project> projects = null;
	ProjectListAdapter<Project> adapterProj = null;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_projects);
		categories = new ArrayList<Category>();
		projects = new ArrayList<Project>();
		
        //Requête background vers serveur REST
        try {
        	categories = new BddRequest().execute("categories").get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        
      //Requête background vers serveur REST
        try {
        	String projCatId = categories.get(0).getCatId();
        	projects = new BddRequestProj().execute(projCatId).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
	    //Buttons
	    final Button buttonIndex = (Button)findViewById(R.id.submit_index);
	    final Button buttonLogin = (Button)findViewById(R.id.submit_login);
	    
	    //Listener sur les bouttons des activités ProjectDetailsActivity et Login
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
	    
        //Test if a user is logged, if true change background color of login button 
        SharedPreferences prefs = getSharedPreferences("test",Context.MODE_PRIVATE);
		// If there is no value for “userLog”, return null
		String username = prefs.getString("userLog", null);
		if (username==null){
			buttonLogin.setBackgroundColor(Color.RED);
		}else{buttonLogin.setBackgroundColor(Color.GREEN);}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
        final Spinner spinner =  (Spinner)findViewById(R.id.category_menu);
        final ListView listView =  (ListView)findViewById(R.id.list_view_project_by_category);

        adapter = new CategoryListAdapter<Category>(this, android.R.layout.simple_list_item_1, categories);	
        adapterProj = new ProjectListAdapter<Project>(this, android.R.layout.simple_list_item_1, projects);	

        spinner.setAdapter(adapter);			
        listView.setAdapter(adapterProj);

        spinner.setOnItemSelectedListener(this);
        listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long myLng) {
				Intent intent = new Intent(getApplicationContext(), ProjectDetailsActivity.class);	
				intent.putExtra("projectList", projects);	
				intent.putExtra("projectIndex", myItemInt);		
				startActivityForResult(intent, MY_CODE);								
			}
		});
		adapter.notifyDataSetChanged();
		adapterProj.notifyDataSetChanged();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		categories = (ArrayList<Category>) savedInstanceState.getSerializable("categories");
		projects = (ArrayList<Project>) savedInstanceState.getSerializable("projects");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("categories", categories);
		outState.putSerializable("projects", projects);
		super.onSaveInstanceState(outState);
	}
	
	private class BddRequest extends AsyncTask<String,Void,ArrayList<Category>> {
	
		@Override
		protected ArrayList<Category> doInBackground(String... params) {

			SAXParserFactory parserfact = SAXParserFactory.newInstance();
			SAXParser parser = null;
			ArrayList<Category> categories = null;
			try {
				parser = parserfact.newSAXParser();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}

			URL url = null;
			try {
				url = new URL("http://192.168.1.44:8080/SupCrowdfunder/resources/"+params[0]);
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			
			DefaultHandler categHandler = new XMLCategoryHandler();
			try {
				InputStream input = url.openStream();
				if(input==null)
					Log.e("Erreur android","null");
				else{
					parser.parse(input, categHandler);
					categories = ((XMLCategoryHandler) categHandler).getData();
				}
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return categories;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.projects_by_category, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		//Requête background vers serveur REST
        try {
        	String projCatId = categories.get(pos).getCatId();
        	projects = new BddRequestProj().execute(projCatId).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        final ListView listView =  (ListView)findViewById(R.id.list_view_project_by_category);
        adapterProj = new ProjectListAdapter<Project>(this, android.R.layout.simple_list_item_1, projects);
        listView.setAdapter(adapterProj);
	}
	
	private class BddRequestProj extends AsyncTask<String,Void,ArrayList<Project>> {
		
		@Override
		protected ArrayList<Project> doInBackground(String... params) {
			SAXParserFactory parserfact = SAXParserFactory.newInstance();
			SAXParser parser = null;
			ArrayList<Project> projects = null;
			try {
				parser = parserfact.newSAXParser();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}

			URL url = null;
			try {
				url = new URL("http://192.168.1.44:8080/SupCrowdfunder/resources/projects/"+params[0]);
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			
			DefaultHandler projHandler = new XMLProjectHandler();
			try {
				InputStream input = url.openStream();
				if(input==null)
					Log.e("Erreur android","null");
				else{
					parser.parse(input, projHandler);
					projects = ((XMLProjectHandler) projHandler).getData();
				}
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return projects;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {}
}