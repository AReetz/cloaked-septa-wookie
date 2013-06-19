package net.hlw5a.VidPicLib.Ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;

public class VPLTabPanel extends JPanel {
	private static final long serialVersionUID = -8068522243459777772L;
	
	private JPanel m_LeftContent;
	private JPanel m_RightContent;
	
	public VPLTabPanel() {
		m_LeftContent = new JPanel();
		m_LeftContent.setLayout(new VPLWrapLayout(FlowLayout.LEFT));
		m_LeftContent.setBackground(VPLStyles.PANEL_BACKGROUND);
		JScrollPane leftScroll = new JScrollPane(m_LeftContent, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		leftScroll.setPreferredSize(new Dimension(450,10));
		leftScroll.getVerticalScrollBar().setUnitIncrement(VPLStyles.SCROLL_SPEED);
		
		m_RightContent = new JPanel();
		m_RightContent.setLayout(new VPLWrapLayout(FlowLayout.LEFT));
		m_RightContent.setBackground(VPLStyles.PANEL_BACKGROUND);
		JScrollPane rightScroll = new JScrollPane(m_RightContent, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		rightScroll.getVerticalScrollBar().setUnitIncrement(VPLStyles.SCROLL_SPEED);
		
		SpringLayout layout = new SpringLayout();
		layout.putConstraint(SpringLayout.WEST, leftScroll, VPLStyles.BORDER_SIZE, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, leftScroll, VPLStyles.BORDER_SIZE, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, leftScroll, -VPLStyles.BORDER_SIZE, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, rightScroll, VPLStyles.BORDER_SIZE, SpringLayout.EAST, leftScroll);
		layout.putConstraint(SpringLayout.EAST, rightScroll, -VPLStyles.BORDER_SIZE, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, rightScroll, VPLStyles.BORDER_SIZE, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, rightScroll, -VPLStyles.BORDER_SIZE, SpringLayout.SOUTH, this);
		
		this.setLayout(layout);
		
		this.add(leftScroll);
		this.add(rightScroll);
	}
	
	public Component add(Component comp, int index) {
		switch (index) {
		case LEFT:
			m_LeftContent.add(comp);
			break;
		case RIGHT:
			m_RightContent.add(comp);
			break;
		}
		return comp;
	}
	
	public void removeAll(int index) {
		switch (index) {
		case LEFT:
			m_LeftContent.removeAll();
			break;
		case RIGHT:
			m_RightContent.removeAll();
			break;
		}
	}
	
	public final static int LEFT = 1;
	public final static int RIGHT = 2;
}
