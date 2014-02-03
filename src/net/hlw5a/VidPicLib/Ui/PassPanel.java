package net.hlw5a.VidPicLib.Ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.hlw5a.VidPicLib.Pass;
import net.hlw5a.VidPicLib.Pass.State;
import net.hlw5a.VidPicLib.Database.Database;

public class PassPanel extends JPanel {

	private static final long serialVersionUID = 1880638996354120578L;
	private Pass pass;
	
	public int getId() {
		return pass.getId();
	}
	
	public PassPanel(Pass pass) {
		this.pass = pass;
		
		MouseAdapter copyToClipboard = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				StringSelection selection = new StringSelection(PassPanel.this.pass.getUsername() + ":" + PassPanel.this.pass.getPassword());
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
			}
		};
		
		JLabel username = new JLabel(this.pass.getUsername(), JLabel.CENTER);
		username.addMouseListener(copyToClipboard);
		username.setFont(VPLStyles.REGULAR);
		username.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, VPLStyles.TEXTFIELD_HEIGHT));
		username.setOpaque(true);
		username.setBackground(VPLStyles.TEXT_BACKGROUND);
		
		JLabel password = new JLabel(this.pass.getPassword(), JLabel.CENTER);
		password.addMouseListener(copyToClipboard);
		password.setFont(VPLStyles.REGULAR);
		password.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, VPLStyles.TEXTFIELD_HEIGHT));
		password.setOpaque(true);
		password.setBackground(VPLStyles.TEXT_BACKGROUND);
		
		final JLabel date = new JLabel(new SimpleDateFormat("yyyy-MM-dd").format(this.pass.getDate()), JLabel.CENTER);
		date.setFont(VPLStyles.REGULAR);
		date.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, VPLStyles.LABEL_HEIGHT));
		date.setOpaque(true);
		date.setBackground(VPLStyles.TEXT_BACKGROUND);
		date.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					Database.getInstance().getPass(PassPanel.this.pass.getId()).setDate(new Date());
					date.setText(new SimpleDateFormat("yyyy-MM-dd").format(PassPanel.this.pass.getDate()));
				}
			}
		});
		
		final JComboBox<State> state = new JComboBox<State>(Database.getInstance().getStates());
		state.setSelectedItem(this.pass.getState());
		state.setFont(VPLStyles.REGULAR);
		state.setPreferredSize(new Dimension(142, VPLStyles.COMBOBOX_HEIGHT));
		state.setOpaque(true);
		state.setBackground(VPLStyles.TEXT_BACKGROUND);
		state.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Database.getInstance().getPass(PassPanel.this.pass.getId()).setState((State)state.getItemAt(state.getSelectedIndex()));
				Database.getInstance().getPass(PassPanel.this.pass.getId()).setDate(new Date());
				date.setText(new SimpleDateFormat("yyyy-MM-dd").format(PassPanel.this.pass.getDate()));
			}
		});

		JButton remove = new JButton("-");
		remove.setFont(VPLStyles.BOLD);
		remove.setPreferredSize(new Dimension(44, VPLStyles.COMBOBOX_HEIGHT));
		remove.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) { Database.getInstance().deletePass(PassPanel.this.pass); }
		});
		
		this.add(username);
		this.add(password);
		this.add(date);
		this.add(state);
		this.add(remove);
		this.setBackground(VPLStyles.CONTAINER_BACKGROUND);
		this.setPreferredSize(new Dimension(VPLStyles.CONTROL_WIDTH, 106));
	}
}
