package net.hlw5a.VidPicLib.Ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import net.hlw5a.VidPicLib.Model;
import net.hlw5a.VidPicLib.Model.Built;
import net.hlw5a.VidPicLib.Model.Cup;
import net.hlw5a.VidPicLib.Model.Race;
import net.hlw5a.VidPicLib.Database.Database;

public class ModelNew extends JPanel {
	
	private static final long serialVersionUID = 5922297647696898018L;

	public ModelNew() {
		JLabel id = new JLabel("X", JLabel.CENTER);
		id.setFont(VPLStyles.SMALL);
		id.setPreferredSize(new Dimension(VPLStyles.TEXTFIELD_HEIGHT, VPLStyles.TEXTFIELD_HEIGHT));
		id.setOpaque(true);
		id.setBackground(VPLStyles.TEXT_BACKGROUND);
		
		final JTextField imageName = new JTextField("model picture name");
		imageName.setFont(VPLStyles.ITALIC);
		imageName.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, VPLStyles.TEXTFIELD_HEIGHT));
		imageName.setOpaque(true);
		imageName.setBackground(VPLStyles.TEXT_BACKGROUND);

		final JTextField modelName = new JTextField("model name");
		modelName.setFont(VPLStyles.ITALIC);
		modelName.setPreferredSize(new Dimension(165, VPLStyles.TEXTFIELD_HEIGHT));
		modelName.setOpaque(true);
		modelName.setBackground(VPLStyles.TEXT_BACKGROUND);
		modelName.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent arg0) { if (modelName.getText().equals("")) modelName.setText("model name"); }
			public void focusGained(FocusEvent arg0) { if (modelName.getText().equals("model name")) modelName.setText(""); }
		});
		modelName.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent de) { }
			public void insertUpdate(DocumentEvent de) {
				try { imageName.setText(de.getDocument().getText(0, de.getDocument().getLength()).replace(' ', '_').toLowerCase() + "_model_X.jpg"); }
				catch (BadLocationException e) { }
			}
			public void changedUpdate(DocumentEvent de) { }
		});
		
		final JComboBox modelRace = new JComboBox(Database.getInstance().getRaces());
		modelRace.setSelectedItem(Database.getInstance().getRace(0));
		modelRace.setFont(VPLStyles.REGULAR);
		modelRace.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH / 3 + 14, VPLStyles.COMBOBOX_HEIGHT));
		modelRace.setOpaque(true);
		modelRace.setBackground(VPLStyles.TEXT_BACKGROUND);
		modelRace.setRenderer(new ListCellRenderer() {
			protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
			public Component getListCellRendererComponent(JList arg0, Object arg1, int arg2, boolean arg3, boolean arg4) {
				JLabel renderer = (JLabel)defaultRenderer.getListCellRendererComponent(arg0, arg1, arg2, arg3, arg4);
				renderer.setText(((Race)arg1).name());
			    return renderer;
			}
		});
		
		final JComboBox modelBuilt = new JComboBox(Database.getInstance().getBuilts());
		modelBuilt.setSelectedItem(Database.getInstance().getBuilt(0));
		modelBuilt.setFont(VPLStyles.REGULAR);
		modelBuilt.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH / 3 + 14, VPLStyles.COMBOBOX_HEIGHT));
		modelBuilt.setOpaque(true);
		modelBuilt.setBackground(VPLStyles.TEXT_BACKGROUND);
		modelBuilt.setRenderer(new ListCellRenderer() {
			protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
			public Component getListCellRendererComponent(JList arg0, Object arg1, int arg2, boolean arg3, boolean arg4) {
				JLabel renderer = (JLabel)defaultRenderer.getListCellRendererComponent(arg0, arg1, arg2, arg3, arg4);
				renderer.setText(((Built)arg1).name());
			    return renderer;
			}
		});
		
		final JComboBox modelCup = new JComboBox(Database.getInstance().getCups());
		modelCup.setSelectedItem(Database.getInstance().getCup(0));
		modelCup.setFont(VPLStyles.REGULAR);
		modelCup.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH / 3 + 14, VPLStyles.COMBOBOX_HEIGHT));
		modelCup.setOpaque(true);
		modelCup.setBackground(VPLStyles.TEXT_BACKGROUND);
		modelCup.setRenderer(new ListCellRenderer() {
			protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
			public Component getListCellRendererComponent(JList arg0, Object arg1, int arg2, boolean arg3, boolean arg4) {
				JLabel renderer = (JLabel)defaultRenderer.getListCellRendererComponent(arg0, arg1, arg2, arg3, arg4);
				renderer.setText(((Cup)arg1).name());
			    return renderer;
			}
		});
		
		JButton save = new JButton("save model");
		save.setFont(VPLStyles.ITALIC);
		save.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, VPLStyles.TEXTFIELD_HEIGHT));
		save.setOpaque(true);
		save.setBackground(VPLStyles.TEXT_BACKGROUND);
		save.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) { }
			public void mousePressed(MouseEvent arg0) { }
			public void mouseExited(MouseEvent arg0) { }
			public void mouseEntered(MouseEvent arg0) { }
			public void mouseClicked(MouseEvent arg0) { try {
				Database.getInstance().createModel(modelName.getText(), imageName.getText(), (Race)modelRace.getSelectedItem(), (Built)modelBuilt.getSelectedItem(), (Cup)modelCup.getSelectedItem());
			} catch (IOException e) {
				e.printStackTrace();
			} }
		});
		
		this.add(id);
		this.add(modelName);
		this.add(modelRace);
		this.add(modelBuilt);
		this.add(modelCup);
		this.add(imageName);
		this.add(save);
		this.setBackground(VPLStyles.CONTAINER_BACKGROUND);
		this.setPreferredSize(new Dimension(VPLStyles.CONTROL_WIDTH, 295));
	}
}
