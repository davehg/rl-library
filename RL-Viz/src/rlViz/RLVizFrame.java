package rlViz;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import messaging.environmentShell.EnvShellListRequest;
import messaging.environmentShell.EnvShellListResponse;


public class RLVizFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	//Components
	EnvironmentPanel ePanel=null;
	EnvironmentPanel aPanel=null;

	RLGlueLogic theGlueConnection=null;
	public RLVizFrame(){
		super();
		
		theGlueConnection=new RLGlueLogic();
		
		
		

		
		ePanel= new EnvironmentPanel(new Dimension(200,200));
//		EnvironmentPanel aPanel= new EnvironmentPanel(new Dimension(200,200),theAVisualizer);
//		
		JPanel controlPanel=new RLControlPanel(theGlueConnection);
		
		JSplitPane agentEnvSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,ePanel,new JLabel("Agent viz goes here"));
		agentEnvSplitPane.setOneTouchExpandable(true);
		agentEnvSplitPane.setDividerLocation(150);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				agentEnvSplitPane, controlPanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(150);


		getContentPane().add(splitPane);
		setSize(800,600);
		setVisible(true);
	
	}

	public void setEnvVisualizer(MountainCarVisualizer theEVisualizer) {
		ePanel.setVisualizer(theEVisualizer);
		theGlueConnection.setVisualizer(theEVisualizer);
	}

}
