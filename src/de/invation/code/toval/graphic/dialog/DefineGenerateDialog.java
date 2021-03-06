package de.invation.code.toval.graphic.dialog;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.invation.code.toval.graphic.component.BoxLayoutPanel;
import de.invation.code.toval.graphic.renderer.AlternatingRowColorListCellRenderer;

public class DefineGenerateDialog extends AbstractDialog<List<String>> {

	private static final long serialVersionUID = -1396837102031308301L;
	
	private JList stringList;
	private JButton btnDefine;
	private JButton btnGenerate;
	private DefaultListModel stringListModel;
	
	protected DefineGenerateDialog(Window owner, String title) {
		super(owner, title);
		initialize();
	}

	protected void initialize() {
		stringListModel = new DefaultListModel();
	}

	@Override
	protected void addComponents() throws Exception {
		mainPanel().setLayout(new BorderLayout());
		
		JPanel buttonPanel = new BoxLayoutPanel();
		buttonPanel.add(getButtonDefine());
		buttonPanel.add(getButtonGenerate());
		mainPanel().add(buttonPanel, BorderLayout.PAGE_START);

		JScrollPane scrollPane = new JScrollPane(getActivityList());
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		mainPanel().add(scrollPane, BorderLayout.CENTER);
	}
	
	@Override
	protected void setTitle() {}
	
	private JButton getButtonDefine(){
		if(btnDefine == null){
			btnDefine = new JButton("Define...");
			btnDefine.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<String> definedStrings = null;
					try {
						definedStrings = StringListDefinitionDialog.showDialog(DefineGenerateDialog.this, "Define " + getTitle());
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(DefineGenerateDialog.this, "<html>Cannot launch string list definition dialog.<br>Reason: " + e1.getMessage() + "</html>", "Internal Exception", JOptionPane.ERROR_MESSAGE);
					}
					if(definedStrings != null){
						stringListModel.clear();
						for(String string: definedStrings){
							stringListModel.addElement(string);
						}
						
					}
				}
			});
		}
		return btnDefine;
	}
	
	private JButton getButtonGenerate(){
		if(btnGenerate == null){
			btnGenerate = new JButton("Generate...");
			btnGenerate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<String> generatedStrings = null;
					try {
						generatedStrings = StringListGeneratorDialog.showDialog(DefineGenerateDialog.this, "Generate " + getTitle());
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(DefineGenerateDialog.this, "<html>Cannot launch string list generator dialog.<br>Reason: " + e1.getMessage() + "</html>", "Internal Exception", JOptionPane.ERROR_MESSAGE);
					}
					if(generatedStrings != null){
						stringListModel.clear();
						for(String string: generatedStrings){
							stringListModel.addElement(string);
						}
						
					}
				}
			});
		}
		return btnGenerate;
	}
	
	@Override
	protected void okProcedure() {
		if(!stringListModel.isEmpty()){
			if(getDialogObject() == null)
				setDialogObject(new ArrayList<String>());
			for(int i=0; i<stringListModel.size(); i++)
				getDialogObject().add((String) stringListModel.getElementAt(i));
			super.okProcedure();
		} else {
			JOptionPane.showMessageDialog(DefineGenerateDialog.this, "Activity list is empty.", "Invalid Parameter", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private JList getActivityList(){
		if(stringList == null){
			stringList = new JList(stringListModel);
			stringList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			stringList.setCellRenderer(new AlternatingRowColorListCellRenderer());
			stringList.setFixedCellHeight(20);
			stringList.setVisibleRowCount(10);
			stringList.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			stringList.setBorder(null);
			
			stringList.addListSelectionListener(
	        		new ListSelectionListener(){
	        			public void valueChanged(ListSelectionEvent e) {
	        			    if ((e.getValueIsAdjusting() == false) && (stringList.getSelectedValue() != null)) {
	        			    	
	        			    }
	        			}
	        		}
	        );
		}
		return stringList;
	}
	
	public static List<String> showDialog(Window owner, String title) throws Exception{
		DefineGenerateDialog activityDialog = new DefineGenerateDialog(owner, title);
		activityDialog.setUpGUI();
		return activityDialog.getDialogObject();
	}
	
}
