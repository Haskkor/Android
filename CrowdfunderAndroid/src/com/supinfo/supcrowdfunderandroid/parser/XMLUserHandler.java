package com.supinfo.supcrowdfunderandroid.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.supinfo.supcrowdfunderandroid.model.User;

public class XMLUserHandler extends DefaultHandler{
	
	private final String userName = "name";
	private final String userId = "userId";
	private final String lastName = "lastName";
	private final String mailAddress="mailAddress";

	private User user;
	private boolean inItem;
	private StringBuffer data;	
			
	@Override
	public void processingInstruction(String target, String data) throws SAXException {
		super.processingInstruction(target, data);
	}

	public XMLUserHandler() {
		super();
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}	
			
	@Override
	public void startElement(String uri, String localName, String name,	Attributes attributes) throws SAXException {
		data = new StringBuffer();		
		//User créé à la rencontre de la balise <user>
		if (localName.equalsIgnoreCase("USER")){
			this.user = new User();
			inItem = true;
		}
	}
			
	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {		
		
		if (localName.equalsIgnoreCase("USER")){					
			inItem = false;
		}
		//Récupère tous les éléments d'un user à la recontre des différentes balises
		if (localName.equalsIgnoreCase(userName)){
			if(inItem){
				this.user.setName((data.toString()));
				data = null;
			}
		}
		
		if (localName.equalsIgnoreCase(lastName)){
			if(inItem){
				this.user.setLastName(data.toString() );
				data = null;
			}
		}
		
		if (localName.equalsIgnoreCase(userId)){
			if(inItem){
				this.user.setUserId(data.toString());
				data = null;
			}
		}
		
		if (localName.equalsIgnoreCase(mailAddress)){
			if(inItem){
				this.user.setMail(data.toString());
				data = null;
			}
		}		
	}

	//Récupère les datas entre les balises
	public void characters(char[] ch,int start, int length)	throws SAXException{
		String lecture = new String(ch,start,length);
		if(data != null) data.append(lecture);
	}
	
	//Retourne le user
	public User getData(){
		return user;
	}		
}