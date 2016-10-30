package com.supinfo.supcrowdfunderandroid.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.supinfo.supcrowdfunderandroid.model.Category;

public class XMLCategoryHandler extends DefaultHandler {
	
	private final String catId = "catId";
	private final String cname = "cname";
	private final String cdesc = "cdesc";
	private final String category="category";
	
	private ArrayList<Category> categories;
	private boolean element;
	private Category currentCategory;
	private StringBuffer data;
	
	@Override
	public void processingInstruction(String target, String data) throws SAXException {
		super.processingInstruction(target, data);
	}

	public XMLCategoryHandler() {
		super();
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		categories = new ArrayList<Category>();
	}

	@Override
	public void startElement(String uri, String localName, String name,	Attributes attributes) throws SAXException {
		data = new StringBuffer();		
		//Catégorie créée à la rencontre de la balise <category>
		if (localName.equalsIgnoreCase(category)){
			this.currentCategory = new Category();
			element = true;
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {		

		//Catégorie ajoutée à la liste à la rencontre de la balise <category>
		if (localName.equalsIgnoreCase(category)){
			categories.add(this.currentCategory);
			element = false;
		}
		
		//Récupère tous les éléments d'une catégorie à la recontre des différentes balises
		if (localName.equalsIgnoreCase(catId)){
			if(element){
				this.currentCategory.setCatId((data.toString()));
				data = null;
			}
		}
		
		if (localName.equalsIgnoreCase(cname)){
			if(element){
				this.currentCategory.setCname(data.toString() );
				data = null;
			}
		}
		
		if (localName.equalsIgnoreCase(cdesc)){
			if(element){
				this.currentCategory.setCdesc(data.toString());
				data = null;
			}
		}
	}
	
	//Récupère les datas entre les balises
	public void characters(char[] ch,int start, int length)	throws SAXException{
		String datas = new String(ch,start,length);
		if(data != null) data.append(datas);
	}
	
	//Retourne la liste de catégories
	public ArrayList<Category> getData(){
		return categories;
	}
}