package net.hlw5a.VidPicLib.Database;

import java.awt.Desktop;
import java.awt.Image;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.hlw5a.VidPicLib.*;
import net.hlw5a.VidPicLib.Model.Built;
import net.hlw5a.VidPicLib.Model.Cup;
import net.hlw5a.VidPicLib.Model.Race;
import net.hlw5a.VidPicLib.Pass.State;

public class XmlDatabase extends Database {

	private static final String RACES_FILE = "races.xml";
	private static final String BUILTS_FILE = "builts.xml";
	private static final String CUPS_FILE = "cups.xml";
    private static final String MODELS_FILE = "models.xml";
    private static final String SITES_FILE = "sites.xml";
    private static final String SETS_FILE = "sets.xml";
    private static final String STATES_FILE = "states.xml";
    private static final String PASSES_FILE = "passes.xml";
    private static final String MAPPINGS_FILE = "mappings.xml";
    private static final String SETTINGS_FILE = "settings.xml";

    private String databaseRoot;

    public XmlDatabase() {
    	databaseRoot = String.format("%s%sLibrary%sApplication Support%snet.hlw5a.VidPicLib%s", System.getProperty("user.home"), File.separator, File.separator, File.separator, File.separator);
        //databaseRoot = String.format("%s%sDatabase%s", System.getProperty("user.dir"), File.separator, File.separator);
    	//databaseRoot = String.format("C:\\Users\\Adrian\\Documents\\Source Code\\VidPicLib2\\VidPicLibGUI\\Database\\");
        try {
        	LoadSettings();
        	LoadRaces();
        	LoadBuilts();
        	LoadCups();
			LoadModels();
	        LoadSites();
	        LoadSets();
	        LoadStates();
	        LoadPasses();
	        LoadMappings();
        }
        catch (IOException e) { e.printStackTrace(); }
        catch (ParserConfigurationException e) { e.printStackTrace(); }
        catch (SAXException e) { e.printStackTrace(); }
        catch (ParseException e) { e.printStackTrace(); }
        catch (XPathExpressionException e) { e.printStackTrace(); }
    }
    
    public void saveDatabase() {
        try {
        	SaveModels();
            SaveSites();
            SaveSets();
			SavePasses();
			SaveMappings();
		}
        catch (IOException e) { e.printStackTrace(); }
    }
    
    public void openDatabase() {
        try { Desktop.getDesktop().open(new File(databaseRoot)); }
		catch (IOException e) { e.printStackTrace(); }
    }
    
    public Image getImage(String imageName) throws IOException {
    	return ImageIO.read(new File(databaseRoot + imageName));
    }
    
    private void LoadRaces() throws ParserConfigurationException, SAXException, IOException {
    	File xmlFile = new File(databaseRoot + RACES_FILE);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xmlFile);
		
		NodeList xmlRaces = doc.getElementsByTagName("races").item(0).getChildNodes();
        for (int i = 0; i < xmlRaces.getLength(); i++) {
        	if (xmlRaces.item(i).getNodeType() == Node.ELEMENT_NODE) {
            	Element stateElement = (Element) xmlRaces.item(i);
            	Integer id = Integer.parseInt(getAttributeText(stateElement,"id"));
            	races.put(id, Race.valueOf(stateElement.getNodeName()));
        	}
        }
    }
    
    private void LoadBuilts() throws ParserConfigurationException, SAXException, IOException {
    	File xmlFile = new File(databaseRoot + BUILTS_FILE);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xmlFile);
		
		NodeList xmlBuilts = doc.getElementsByTagName("builts").item(0).getChildNodes();
        for (int i = 0; i < xmlBuilts.getLength(); i++) {
        	if (xmlBuilts.item(i).getNodeType() == Node.ELEMENT_NODE) {
            	Element stateElement = (Element) xmlBuilts.item(i);
            	Integer id = Integer.parseInt(getAttributeText(stateElement,"id"));
            	builts.put(id, Built.valueOf(stateElement.getNodeName()));
        	}
        }
    }
    
    private void LoadCups() throws ParserConfigurationException, SAXException, IOException {
    	File xmlFile = new File(databaseRoot + CUPS_FILE);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xmlFile);
		
		NodeList xmlCups = doc.getElementsByTagName("cups").item(0).getChildNodes();
        for (int i = 0; i < xmlCups.getLength(); i++) {
        	if (xmlCups.item(i).getNodeType() == Node.ELEMENT_NODE) {
            	Element stateElement = (Element) xmlCups.item(i);
            	Integer id = Integer.parseInt(getAttributeText(stateElement,"id"));
            	cups.put(id, Cup.valueOf(stateElement.getNodeName()));
        	}
        }
    }

    private void LoadModels() throws IOException, ParserConfigurationException, SAXException, ParseException {
    	File xmlFile = new File(databaseRoot + MODELS_FILE);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xmlFile);

		NodeList xmlModels = doc.getElementsByTagName("model");
        for (int i = 0; i < xmlModels.getLength(); i++)
        {
        	Element modelElement = (Element) xmlModels.item(i);
            Integer id = Integer.parseInt(getAttributeText(modelElement, "id"));
            String name = getChildText(modelElement, "name").firstElement();
            Race race = getRace(Integer.parseInt(getChildText(modelElement, "race").firstElement()));
            Built built = getBuilt(Integer.parseInt(getChildText(modelElement, "built").firstElement()));
            Date birthdate = (new SimpleDateFormat("yyyy-MM-dd")).parse(getChildText(modelElement, "birthdate").firstElement());
            String measurements = getChildText(modelElement, "measurements").firstElement();
            Cup cup = getCup(Integer.parseInt(getChildText(modelElement, "cup").firstElement()));
            String imageName = getChildText(modelElement, "image").firstElement();
            Image image = ImageIO.read(new File(databaseRoot + imageName));
            models.put(id, new Model(id, name, imageName, image, race, built, birthdate, measurements, cup));
        }
    }

    private void LoadSites() throws IOException, ParserConfigurationException, SAXException {
    	File xmlFile = new File(databaseRoot + SITES_FILE);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xmlFile);

		NodeList xmlSites = doc.getElementsByTagName("site");
        for (int i = 0; i < xmlSites.getLength(); i++)
        {
        	Element siteElement = (Element) xmlSites.item(i);
            Integer id = Integer.parseInt(getAttributeText(siteElement, "id"));
            String name = getChildText(siteElement, "name").firstElement();
            URL url = new URL(getChildText(siteElement, "url").firstElement());
            String imageName = getChildText(siteElement, "image").firstElement();
            Image image = ImageIO.read(new File(databaseRoot + imageName));
            sites.put(id, new Site(id, name, url, imageName, image));
        }
    }
    
    private void LoadSets() throws IOException, ParserConfigurationException, SAXException, ParseException {
    	File xmlFile = new File(databaseRoot + SETS_FILE);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xmlFile);

		NodeList xmlSets = doc.getElementsByTagName("set");
        for (int i = 0; i < xmlSets.getLength(); i++)
        {
        	Element setElement = (Element) xmlSets.item(i);
            Integer id = Integer.parseInt(getAttributeText(setElement, "id"));
            String name = getChildText(setElement, "name").firstElement();
            String number = getChildText(setElement, "number").firstElement();
            Date date = (new SimpleDateFormat("yyyy-MM-dd")).parse(getChildText(setElement, "date").firstElement());
            String imageName = getChildText(setElement, "image").firstElement();
            Image image = ImageIO.read(new File(databaseRoot + imageName));
            Model mainModel = getModel(Integer.parseInt(getChildText(setElement, "mainmodel").firstElement()));
            Vector<Model> models = new Vector<Model>();
            for (String str : getChildText(setElement, "model")) {
            	models.add(getModel(Integer.parseInt(str)));
            }
            sets.put(id, new Set(id, name, number, date, imageName, image, mainModel, models, getSite(Integer.parseInt(getAttributeText(setElement, "site")))));
        }
    }

    private void LoadStates() throws ParserConfigurationException, SAXException, IOException {
    	File xmlFile = new File(databaseRoot + STATES_FILE);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xmlFile);
		
		NodeList xmlStates = doc.getElementsByTagName("states").item(0).getChildNodes();
        for (int i = 0; i < xmlStates.getLength(); i++) {
        	if (xmlStates.item(i).getNodeType() == Node.ELEMENT_NODE) {
            	Element stateElement = (Element) xmlStates.item(i);
            	Integer id = Integer.parseInt(getAttributeText(stateElement,"id"));
            	states.put(id, State.valueOf(stateElement.getNodeName()));
        	}
        }
    }

    private void LoadPasses() throws IOException, ParserConfigurationException, SAXException, ParseException {
    	File xmlFile = new File(databaseRoot + PASSES_FILE);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xmlFile);

		NodeList xmlPasses = doc.getElementsByTagName("pass");
        for (int i = 0; i < xmlPasses.getLength(); i++)
        {
        	Element passElement = (Element) xmlPasses.item(i);
            Integer id = Integer.parseInt(getAttributeText(passElement, "id"));
            String username = getChildText(passElement, "username").firstElement();
            String password = getChildText(passElement, "password").firstElement();
            Date date = (new SimpleDateFormat("yyyy-MM-dd")).parse(getChildText(passElement, "date").firstElement());
            State state = getState(Integer.parseInt(getAttributeText(passElement, "state")));
            passes.put(id, new Pass(id, username, password, date, state, getSite(Integer.parseInt(getAttributeText(passElement, "site")))));
        }
    }
    
    private void LoadSettings() throws ParserConfigurationException, SAXException, IOException
    {
    	File xmlFile = new File(databaseRoot + SETTINGS_FILE);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xmlFile);

		settings.put("contentfolder", getAttributeText((Element) doc.getElementsByTagName("contentfolder").item(0), "value"));		
    }
    
    private void LoadMappings() throws IOException, ParserConfigurationException, SAXException, ParseException, XPathExpressionException {
    	File xmlFile = new File(databaseRoot + MAPPINGS_FILE);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xmlFile);

		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		NodeList xmlCompleted = (NodeList) xpath.evaluate("mappings/completed/boolean", doc, XPathConstants.NODESET);
        for (int i = 0; i < xmlCompleted.getLength(); i++)
        {
        	Element passElement = (Element) xmlCompleted.item(i);
            Integer model = Integer.parseInt(getAttributeText(passElement, "model"));
            Integer site = Integer.parseInt(getAttributeText(passElement, "site"));
            mappingsModelSite.put(models.get(model), sites.get(site), true);
        }
    }
    
    private void SaveModels() throws IOException {
        StringBuilder xmlString = new StringBuilder(String.format("<?xml version=\"1.0\" encoding=\"utf-8\" ?>%n"));
        xmlString.append(String.format("<models>%n"));
        for (Model model : getModels()) {
        	xmlString.append(String.format("  <model id=\"%d\">%n", model.getId()));
        	xmlString.append(String.format("    <name>%s</name>%n", model.getName()));
        	xmlString.append(String.format("    <race>%s</race>%n", model.getRace().ordinal()));
        	xmlString.append(String.format("    <built>%s</built>%n", model.getBuilt().ordinal()));
        	xmlString.append(String.format("    <birthdate>%s</birthdate>%n", (new SimpleDateFormat("yyyy-MM-dd")).format(model.getBirthdate())));
        	xmlString.append(String.format("    <measurements>%s</measurements>%n", model.getMeasurements()));
        	xmlString.append(String.format("    <cup>%s</cup>%n", model.getCup().ordinal()));
        	xmlString.append(String.format("    <image>%s</image>%n", model.getImageName()));
        	xmlString.append(String.format("  </model>%n"));
        }
        xmlString.append(String.format("</models>%n"));
        (new File(databaseRoot + MODELS_FILE)).renameTo(new File(databaseRoot + MODELS_FILE + ".backup"));
        BufferedWriter xmlFile = new BufferedWriter(new FileWriter(databaseRoot + MODELS_FILE));
        xmlFile.write(xmlString.toString());
        xmlFile.close();
    }
    
    private void SaveSites() throws IOException {
        StringBuilder xmlString = new StringBuilder(String.format("<?xml version=\"1.0\" encoding=\"utf-8\" ?>%n"));
        xmlString.append(String.format("<sites>%n"));
        for (Site site : sites.values()) {
        	xmlString.append(String.format("  <site id=\"%d\">%n", site.getId()));
        	xmlString.append(String.format("    <name>%s</name>%n", site.getName()));
        	xmlString.append(String.format("    <url>%s</url>%n", site.getUrl()));
        	xmlString.append(String.format("    <image>%s</image>%n", site.getImageName()));
        	xmlString.append(String.format("  </site>%n"));
        }
        xmlString.append(String.format("</sites>%n"));
        (new File(databaseRoot + SITES_FILE)).renameTo(new File(databaseRoot + SITES_FILE + ".backup"));
        BufferedWriter xmlFile = new BufferedWriter(new FileWriter(databaseRoot + SITES_FILE));
        xmlFile.write(xmlString.toString());
        xmlFile.close();
    }
    
    private void SaveSets() throws IOException {
    	StringBuilder xmlString = new StringBuilder(String.format("<?xml version=\"1.0\" encoding=\"utf-8\" ?>%n"));
        xmlString.append(String.format("<sets>%n"));
        for (Set set : sets.values()) {
        	xmlString.append(String.format("  <set id=\"%d\" site=\"%d\">%n", set.getId(), set.getSite().getId()));
        	xmlString.append(String.format("    <name>%s</name>%n", set.getName()));
        	xmlString.append(String.format("    <number>%s</number>%n", set.getNumber()));
        	xmlString.append(String.format("    <date>%s</date>%n", (new SimpleDateFormat("yyyy-MM-dd")).format(set.getDate())));
        	xmlString.append(String.format("    <mainmodel>%d</mainmodel>%n", set.getMainModel().getId()));
        	for (Model model : set.getModels()) {
        		xmlString.append(String.format("    <model>%d</model>%n", model.getId()));
        	}
        	xmlString.append(String.format("    <image>%s</image>%n", set.getImageName()));
        	xmlString.append(String.format("  </set>%n"));
        }
        xmlString.append(String.format("</sets>%n"));
        (new File(databaseRoot + SETS_FILE)).renameTo(new File(databaseRoot + SETS_FILE + ".backup"));
        BufferedWriter xmlFile = new BufferedWriter(new FileWriter(databaseRoot + SETS_FILE));
        xmlFile.write(xmlString.toString());
        xmlFile.close();
    }
    
    private void SavePasses() throws IOException {
        StringBuilder xmlString = new StringBuilder(String.format("<?xml version=\"1.0\" encoding=\"utf-8\" ?>%n"));
        xmlString.append(String.format("<passes>%n"));
        for (Pass pass : passes.values()) {
        	xmlString.append(String.format("  <pass id=\"%d\" site=\"%d\" state=\"%d\">%n", pass.getId(), pass.getSite().getId(), pass.getState().ordinal() + 1));
        	xmlString.append(String.format("    <username>%s</username>%n", pass.getUsername()));
        	xmlString.append(String.format("    <password>%s</password>%n", pass.getPassword()));
        	xmlString.append(String.format("    <date>%s</date>%n", (new SimpleDateFormat("yyyy-MM-dd")).format(pass.getDate())));
        	xmlString.append(String.format("  </pass>%n"));
        }
        xmlString.append(String.format("</passes>%n"));
        (new File(databaseRoot + PASSES_FILE)).renameTo(new File(databaseRoot + PASSES_FILE + ".backup"));
        BufferedWriter xmlFile = new BufferedWriter(new FileWriter(databaseRoot + PASSES_FILE));
        xmlFile.write(xmlString.toString());
        xmlFile.close();
    }
    
    private void SaveMappings() throws IOException {
        StringBuilder xmlString = new StringBuilder(String.format("<?xml version=\"1.0\" encoding=\"utf-8\" ?>%n"));
        xmlString.append(String.format("<mappings>%n"));
        xmlString.append(String.format("  <completed>%n"));
        for (Key2<Model, Site> keys : mappingsModelSite.keySet()) {       	
        	xmlString.append(String.format("    <boolean model=\"%d\" site=\"%d\" value=\"1\" />%n", keys.key1.getId(), keys.key2.getId()));
        }
        xmlString.append(String.format("  </completed>%n"));
        xmlString.append(String.format("</mappings>%n"));
        (new File(databaseRoot + MAPPINGS_FILE)).renameTo(new File(databaseRoot + MAPPINGS_FILE + ".backup"));
        BufferedWriter xmlFile = new BufferedWriter(new FileWriter(databaseRoot + MAPPINGS_FILE));
        xmlFile.write(xmlString.toString());
        xmlFile.close();
    }
    
    private String getAttributeText(Element element, String attributeName) {
        return element.getAttributes().getNamedItem(attributeName).getNodeValue();
    }
    
    private Vector<String> getChildText(Element element, String childName) {
    	Vector<String> retVal = new Vector<String>();
    	NodeList nodes = element.getElementsByTagName(childName);
    	for (int i = 0; i < nodes.getLength(); i++) {
    		NodeList nodeList = nodes.item(i).getChildNodes();
    		retVal.add(nodeList.item(0).getNodeValue());
    	}
        return retVal;
    }
}
