package com.supinfo.supcrowdfunderandroid.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.supinfo.supcrowdfunderandroid.model.Project;

public class XMLProjectHandler extends DefaultHandler {

	private final String project = "project";
	private final String id = "projectId";
	private final String creationDate = "creationDate";
	private final String completionDate ="completionDate";
	private final String nameProj = "name";
	private final String description = "description";
	private final String creator = "creator";
	private final String amountNeeded = "amountNeeded";
	private final String donationAmount="donationAmount";
	
	private ArrayList<Project> projects;
	private boolean inItem;
	private Project currentProject;
	private StringBuffer data;
		 
	@Override
	public void processingInstruction(String target, String data) throws SAXException {
		super.processingInstruction(target, data);
	}

	public XMLProjectHandler() {
		super();
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		projects = new ArrayList<Project>();
	}

	@Override
	public void startElement(String uri, String localName, String name,	Attributes attributes) throws SAXException {
		data = new StringBuffer();		
		//Projet créé à la rencontre de la balise <project>
		if (localName.equalsIgnoreCase(project)){
			this.currentProject = new Project();
			inItem = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {	
		
		//Projet ajouté à la liste à la rencontre de la balise <project>
		if (localName.equalsIgnoreCase(project)){
			projects.add(this.currentProject);
			inItem = false;
		}
			
		//Récupère tous les éléments d'un projet à la recontre des différentes balises
		if (localName.equalsIgnoreCase(creationDate)){
			if(inItem){
				this.currentProject.setCreationDate((data.toString()));
				data = null;
			}
		}
			
		if (localName.equalsIgnoreCase(completionDate)){
			if(inItem){
				this.currentProject.setCompletionDate(data.toString() );
				data = null;
			}
		}
		
		if (localName.equalsIgnoreCase(id)){
			if(inItem){
				this.currentProject.setProjectId(data.toString());
				data = null;
			}
		}
		
		if (localName.equalsIgnoreCase(description)){
			if(inItem){
				this.currentProject.setDescription(data.toString());
				data = null;
			}
		}
		
		if (localName.equalsIgnoreCase(nameProj)){
			if(inItem){
				this.currentProject.setName(data.toString());
				data = null;
			}
		}
		
		if (localName.equalsIgnoreCase(creator)){
			if(inItem){
				this.currentProject.setCreator(data.toString());
				data = null;
			}
		}
		
		if (localName.equalsIgnoreCase(donationAmount)){
			if(inItem){
				this.currentProject.setDonationAmount(Float.parseFloat(data.toString()));
				data = null;
			}
		}
		
		if (localName.equalsIgnoreCase(amountNeeded)){
			if(inItem){
				this.currentProject.setAmountNeeded(Float.parseFloat(data.toString()));
				data = null;
			}
		}
	}
	
	//Récupère les datas entre les balises
	public void characters(char[] ch,int start, int length)	throws SAXException{
		String lecture = new String(ch,start,length);
		if(data != null) data.append(lecture);
	}
	
	//Retourne la liste de projets
	public ArrayList<Project> getData(){
		return projects;
	}
}