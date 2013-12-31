package net.hlw5a.VidPicLib.Database;

import java.awt.Desktop;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.imageio.ImageIO;

import net.hlw5a.VidPicLib.*;
import net.hlw5a.VidPicLib.Model.Built;
import net.hlw5a.VidPicLib.Model.Cup;
import net.hlw5a.VidPicLib.Model.Race;
import net.hlw5a.VidPicLib.Pass.State;

public class MysqlDatabase extends Database {

	private static final String DB_URL = "localhost";
	private static final String DB_NAME = "vidpiclib";
    private static final String DB_USER = "vidpiclibuser";
    private static final String DB_PASSWORD = "DBMfEfphASQPZE5u";
    
    private Vector<Integer> existingModels = new Vector<Integer>();
    private Vector<Integer> existingSites = new Vector<Integer>();
    private Vector<Integer> existingSets = new Vector<Integer>();
    private Vector<Integer> existingPasses = new Vector<Integer>();

    public MysqlDatabase() {
    	Connection dbConnection = null;
    	try {
    		dbConnection = DriverManager.getConnection(String.format("jdbc:mysql://%s/%s", DB_URL, DB_NAME), DB_USER, DB_PASSWORD);
			LoadModels(dbConnection);
	        LoadSites(dbConnection);
	        LoadSets(dbConnection);
	        LoadStates(dbConnection);
	        LoadPasses(dbConnection);
		}
        catch (IOException e) { e.printStackTrace(); }
    	catch (SQLException e) { e.printStackTrace(); }
    	finally { if (dbConnection != null) try { dbConnection.close(); } catch (SQLException e) { } }
    }
    
    public void saveDatabase() {
    	Connection dbConnection = null;
        try {
        	dbConnection = DriverManager.getConnection(String.format("jdbc:mysql://%s/%s", DB_URL, DB_NAME), DB_USER, DB_PASSWORD);
        	SaveModels(dbConnection);
            SaveSites(dbConnection);
            SaveSets(dbConnection);
			SavePasses(dbConnection);
        }
        catch (IOException e) { e.printStackTrace(); }
    	catch (SQLException e) { e.printStackTrace(); }
        finally { if (dbConnection != null) try { dbConnection.close(); } catch (SQLException e) { e.printStackTrace(); } }
    }

    public void openDatabase() {
    	try { Desktop.getDesktop().browse(new URI(String.format("http://%s/phpmyadmin/index.php?db=%s", DB_URL, DB_NAME))); }
    	catch (IOException e) { e.printStackTrace(); }
    	catch (URISyntaxException e) { e.printStackTrace(); }
    }
    
    public Image getImage(String imageName) throws IOException {
    	return ImageIO.read(new File(imageName));
    }

    private void LoadModels(Connection dbConnection) throws SQLException, IOException {
		Statement modelsStatement = dbConnection.createStatement();
		ResultSet modelsResult = modelsStatement.executeQuery("SELECT * FROM models");
    	while (modelsResult.next()) {
    		existingModels.add(modelsResult.getInt("id"));
    		models.put(modelsResult.getInt("id"), new Model(modelsResult.getInt("id"), modelsResult.getString("name"), null, ImageIO.read(modelsResult.getBlob("image").getBinaryStream()), Race.unknown, Built.unknown, new GregorianCalendar(1980, 1, 1).getTime(), "XX-XX-XX", Cup.unknown));
    	}
    }

    private void LoadSites(Connection dbConnection) throws SQLException, IOException {
		Statement sitesStatement = dbConnection.createStatement();
		ResultSet sitesResult = sitesStatement.executeQuery("SELECT * FROM sites");
    	while (sitesResult.next()) {
    		existingSites.add(sitesResult.getInt("id"));
    		sites.put(sitesResult.getInt("id"), new Site(sitesResult.getInt("id"), sitesResult.getString("name"), sitesResult.getURL("url"), null, ImageIO.read(sitesResult.getBlob("image").getBinaryStream())));
    	}
    }
    
    private void LoadSets(Connection dbConnection) throws SQLException, IOException {
    	Statement setsStatement = dbConnection.createStatement();
    	ResultSet setsResult = setsStatement.executeQuery("SELECT * FROM sets");
    	while (setsResult.next()) {
    		Vector<Model> models = new Vector<Model>();
        	Statement modelsStatement = dbConnection.createStatement();
         	ResultSet modelsResult = modelsStatement.executeQuery("SELECT * FROM setmodels WHERE setid = " + setsResult.getInt("id"));
        	while (modelsResult.next()) {
        		models.add(getModel(modelsResult.getInt("modelid")));
        	}
        	existingSets.add(setsResult.getInt("id"));
    		sets.put(setsResult.getInt("id"), new Set(setsResult.getInt("id"), setsResult.getString("name"), setsResult.getString("number"), setsResult.getDate("date"), null, ImageIO.read(setsResult.getBlob("image").getBinaryStream()), getModel(setsResult.getInt("mainmodel")), models, getSite(setsResult.getInt("site"))));
    	}
    }

    private void LoadStates(Connection dbConnection) throws SQLException {
    	Statement statesStatement = dbConnection.createStatement();
 	    ResultSet statesResult = statesStatement.executeQuery("SELECT * FROM states");
		while (statesResult.next()) {
			states.put(statesResult.getInt("id"), State.valueOf(statesResult.getString("state")));  
		}
    }

    private void LoadPasses(Connection dbConnection) throws SQLException {
    	Statement passesStatement = dbConnection.createStatement();
        ResultSet passesResult = passesStatement.executeQuery("SELECT * FROM passes");
    	while (passesResult.next()) {
    		existingPasses.add(passesResult.getInt("id"));
    		passes.put(passesResult.getInt("id"), new Pass(passesResult.getInt("id"), passesResult.getString("username"), passesResult.getString("password"), passesResult.getDate("date"), getState(passesResult.getInt("state")), getSite(passesResult.getInt("site"))));
    	}
    }
    
    private void SaveModels(Connection dbConnection) throws SQLException, FileNotFoundException {
    	for (Model model : getModels()) {
    		if (!existingModels.contains(model.getId())) {
    			PreparedStatement ps = dbConnection.prepareStatement("INSERT INTO models (id, name, image) VALUES (?, ?, ?)");
    			ps.setInt(1, model.getId());
    			ps.setString(2, model.getName());
    			ps.setBlob(3, (new FileInputStream(model.getImageName())));
    			ps.executeUpdate();
    		}
    	}
    }
    
    private void SaveSites(Connection dbConnection) throws SQLException, FileNotFoundException {
    	for (Site site : sites.values()) {
    		if (!existingSites.contains(site.getId())) {
    			PreparedStatement ps = dbConnection.prepareStatement("INSERT INTO sites (id, name, url, image) VALUES (?, ?, ?, ?)");
    			ps.setInt(1, site.getId());
    			ps.setString(2, site.getName());
    			ps.setURL(3, site.getUrl());
    			ps.setBlob(4, (new FileInputStream(site.getImageName())));
    			ps.executeUpdate();
    		}
    	}
    }
    
    private void SaveSets(Connection dbConnection) throws SQLException, FileNotFoundException {
    	for (Set set : sets.values()) {
    		if (!existingSets.contains(set.getId())) {
    			Statement dbStatement;
    			PreparedStatement ps;
    			dbStatement = dbConnection.createStatement();
    			ps = dbConnection.prepareStatement("INSERT INTO sets (id, site, name, date, mainmodel, image) VALUES (?, ?, ?, ?, ?, ?)");
    			ps.setInt(1, set.getId());
    			ps.setInt(2, set.getSite().getId());
    			ps.setString(3, set.getName());
    			ps.setDate(4, new Date(set.getDate().getTime()));
    			ps.setInt(5, set.getMainModel().getId());
    			ps.setBlob(6, (new FileInputStream(set.getImageName())));
    			ps.executeUpdate();
    	    	dbStatement.close();
    	    	for (Model model : set.getModels()) {
	    	    	dbStatement = dbConnection.createStatement();
	    	    	ps = dbConnection.prepareStatement("INSERT INTO setmodels (setid, modelid) VALUES (?, ?)");
	    	    	ps.setInt(1, set.getId());
	    	    	ps.setInt(2, model.getId());
	    	    	ps.executeUpdate();
	    	    	dbStatement.close();
    	    	}
    		}
    	}
    }
    
    private void SavePasses(Connection dbConnection) throws SQLException {
    	for (Pass pass: passes.values()) {
    		if (!existingPasses.contains(pass.getId())) {
    			PreparedStatement ps = dbConnection.prepareStatement("INSERT INTO passes (id, site, state, username, password, date) VALUES (?, ?, ?, ?, ?, ?)");
    			ps.setInt(1, pass.getId());
    			ps.setInt(2, pass.getSite().getId());
    			ps.setInt(3, pass.getState().ordinal() + 1);
    			ps.setString(4, pass.getUsername());
    			ps.setString(5, pass.getPassword());
    			ps.setDate(6, new java.sql.Date(pass.getDate().getTime()));
    			ps.executeUpdate();
    		}
    		else {
    			PreparedStatement ps = dbConnection.prepareStatement("UPDATE passes SET state=?, date=? WHERE id=?");
    			ps.setInt(1, pass.getState().ordinal() + 1);
    			ps.setDate(2, new java.sql.Date(pass.getDate().getTime()));
    			ps.setInt(3,  pass.getId());
    			ps.executeUpdate();
    	    	existingPasses.remove(pass.getId());
    		}
    	}
    	for (Integer existingPass : existingPasses) {
			PreparedStatement ps = dbConnection.prepareStatement("DELETE FROM passes WHERE id=?");
			ps.setInt(1, existingPass);
			ps.executeUpdate();
    	}
    }
}
