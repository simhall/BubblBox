package configurationHandlerUI;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.border.EmptyBorder;

//import shared.Neo4jSessionFactory;
import shared.ConfigurationArtifacts.*;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import configurationValidator.ConfigurationValidator;
import configurationValidator.ValidationConfirmationDialog;
import configurationValidator.ValidationError;
import configurationValidator.ValidationReportUI;
import databaseAdapter.Neo4jAdapter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import shared.Environment;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;

public class ConfigurationHandlerUI extends JFrame {

	/**
	 * 
	 */

	//Create application elements
	private static final long serialVersionUID = -1259470314416895788L;

	// Create UI elements
	private JPanel contentPane;
	//private DefaultListModel<ConfigurationArtifact> model;
	private JTextArea textArea;
	//private JList<ConfigurationArtifact> artifactList;
	private JButton btnLoadData;
	private JScrollPane scrollPane;
	private JPanel buttonPanel;
	private JButton btnSave;
	private JButton btnDeleteArtifact;
	private JButton btnAddArtifact;
	private JScrollPane scrollPane_1;
	private JButton btnAddDependency;
	private JButton btnLoadSampleData;
	private JTabbedPane tabbedPane;
	private JLabel lblNewLabel;
	private JTextField artifactNameField;
	private JLabel lblNewLabel_1;
	private JComboBox<Environment> environmentComboBox;
	private JLabel lblNewLabel_2;
	private JComboBox<ArtifactType> artifactTypeComboBox;
	private JLabel lblArtifactPath;
	private JTextField textDataPath;
	private JPanel panel;
	private JButton btnOpen;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenuItem mntmExit;
	private JMenu mnHelp;
	private JMenuItem mntmAbout;
	private JMenuItem mntmLoadItems;
	private JMenuItem mntmSaveItem;
	private JMenuItem mntmValidationReport;
	private JMenuItem mntmLoadSampleItems;
	private JLabel lblDocumentationURL;
	private JTextField textDocumentationURL;
	private ContentSpecificationPanel contentSpecificationPanel;
	private JPanel contentSpecificationButtons;
	private JButton btnDeleteRow;
	private JButton btnAddRow;
	private JTree tree;
	private ConfigurationArtifact lastSelected = null;
	private JLabel lblSourceCompName;
	private JTextField textField;
	private JTextField txtSourceCompName;
	private JButton btnLoadSampleDataChef;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Windows".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            try {
						UIManager.setLookAndFeel(info.getClassName());
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// Could not set look and feel to either Windows or Nimbus. Use default.
					}
		            break;
		        }
		    }
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigurationHandlerUI frame = new ConfigurationHandlerUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConfigurationHandlerUI() {
		setTitle("BuBBL Box Configuration Tool");

		//================================================================================
		// Initialize UI
		//================================================================================
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		buttonPanel = new JPanel();
		btnDeleteArtifact = new JButton("Delete Artifact");
		btnAddArtifact = new JButton("Add Artifact");
		btnSave = new JButton("Save Artifact");
		btnLoadData = new JButton("Load Data");
		
		menuBar = new JMenuBar();
		contentPane.add(menuBar, BorderLayout.NORTH);
		
		mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfigurationHandlerUI.this.dispatchEvent(new WindowEvent(ConfigurationHandlerUI.this, WindowEvent.WINDOW_CLOSING));
			}
		});
		
		mntmSaveItem = new JMenuItem("Save Item");
		mntmSaveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveArtifact();
			}
		});
		mnNewMenu.add(mntmSaveItem);
		
		mntmLoadItems = new JMenuItem("Load Items");
		mntmLoadItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadArtifacts();
			}
		});
		mnNewMenu.add(mntmLoadItems);
		
		mntmLoadSampleItems = new JMenuItem("Load Sample Items");
		mntmLoadSampleItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadSampleData();
			}
		});
		mnNewMenu.add(mntmLoadSampleItems);
		mnNewMenu.add(mntmExit);
		
		JMenu mnValidate = new JMenu("Validate");
		menuBar.add(mnValidate);
		
		mntmValidationReport = new JMenuItem("ValidationReport");
		mntmValidationReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(panel, "To be implemented.", "Error", JOptionPane.ERROR_MESSAGE);
				ValidationReportUI reportUI = new ValidationReportUI();
				reportUI.setVisible(true);
			}
		});
		mnValidate.add(mntmValidationReport);
		
		mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(ConfigurationHandlerUI.this, "BuBBL BOX Alpha");
			}
		});
		mnHelp.add(mntmAbout);
		
		tree = new JTree();
		scrollPane_1 = new JScrollPane(tree);
		Dimension d = new Dimension();
		d.width = 200;
		scrollPane_1.setPreferredSize(d);
		contentPane.add(scrollPane_1, BorderLayout.WEST);
		buttonPanel.add(btnLoadData);
		buttonPanel.add(btnDeleteArtifact);
		buttonPanel.add(btnAddArtifact);
		buttonPanel.add(btnSave);
		
		tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		textArea = new JTextArea(5, 30);
		scrollPane = new JScrollPane(textArea);
		tabbedPane.addTab("Content", null, scrollPane, null);
		scrollPane.setViewportView(textArea);
		
		JPanel artifactMetadataPanel = new JPanel();
		tabbedPane.addTab("Artifact Metadata", null, artifactMetadataPanel, null);
		tabbedPane.setEnabledAt(1, true);
		GridBagLayout gbl_artifactMetadataPanel = new GridBagLayout();
		gbl_artifactMetadataPanel.columnWidths = new int[] {200, 800, 0};
		gbl_artifactMetadataPanel.rowHeights = new int[] {25, 25, 25, 25, 25, 25, 185, 0, 0, 0};
		gbl_artifactMetadataPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_artifactMetadataPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		artifactMetadataPanel.setLayout(gbl_artifactMetadataPanel);
		
		lblNewLabel = new JLabel("Artifact Name");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		artifactMetadataPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		artifactNameField = new JTextField();
		GridBagConstraints gbc_artifactNameField = new GridBagConstraints();
		gbc_artifactNameField.fill = GridBagConstraints.BOTH;
		gbc_artifactNameField.insets = new Insets(0, 0, 5, 0);
		gbc_artifactNameField.gridx = 1;
		gbc_artifactNameField.gridy = 0;
		artifactMetadataPanel.add(artifactNameField, gbc_artifactNameField);
		artifactNameField.setColumns(1);
		
		txtSourceCompName = new JTextField();
		txtSourceCompName.setText("Select item to load text");
		GridBagConstraints gbc_txtSourceCompName = new GridBagConstraints();
		gbc_txtSourceCompName.insets = new Insets(0, 0, 5, 0);
		gbc_txtSourceCompName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSourceCompName.gridx = 1;
		gbc_txtSourceCompName.gridy = 1;
		artifactMetadataPanel.add(txtSourceCompName, gbc_txtSourceCompName);
		txtSourceCompName.setColumns(10);
		
		lblArtifactPath = new JLabel("Artifact Path");
		GridBagConstraints gbc_lblArtifactPath = new GridBagConstraints();
		gbc_lblArtifactPath.anchor = GridBagConstraints.WEST;
		gbc_lblArtifactPath.fill = GridBagConstraints.BOTH;
		gbc_lblArtifactPath.insets = new Insets(0, 0, 5, 5);
		gbc_lblArtifactPath.gridx = 0;
		gbc_lblArtifactPath.gridy = 2;
		artifactMetadataPanel.add(lblArtifactPath, gbc_lblArtifactPath);
		
		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		artifactMetadataPanel.add(panel, gbc_panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		textDataPath = new JTextField();
		textDataPath.setText("Select item to load text");
		panel.add(textDataPath);
		textDataPath.setColumns(10);
		
		btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(ConfigurationHandlerUI.this);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            //This is where a real application would open the file.
					textDataPath.setText(file.getAbsolutePath().toString());
					
		            System.err.println("Opening: " + file.getName());
		            //log.append("Opening: " + file.getName() + "." + newline);
		        } else {
		        	System.err.println("Open command cancelled by user.");
		            //log.append("Open command cancelled by user." + newline);
		        }
			}
		});
		panel.add(btnOpen);
		
		lblNewLabel_1 = new JLabel("Environment");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.weightx = 0.4;
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 3;
		artifactMetadataPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		textDocumentationURL = new JTextField();
		textDocumentationURL.setCursor(new Cursor(Cursor.HAND_CURSOR));
		textDocumentationURL.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(Desktop.isDesktopSupported())
				{
				  try {
					Desktop.getDesktop().browse(new URI(textDocumentationURL.getText()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
			}
		});
		
		environmentComboBox = new JComboBox<Environment>();
		environmentComboBox.setModel(new DefaultComboBoxModel<Environment>(Environment.values()));
		GridBagConstraints gbc_environmentComboBox = new GridBagConstraints();
		gbc_environmentComboBox.fill = GridBagConstraints.BOTH;
		gbc_environmentComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_environmentComboBox.gridx = 1;
		gbc_environmentComboBox.gridy = 3;
		artifactMetadataPanel.add(environmentComboBox, gbc_environmentComboBox);
		
		lblNewLabel_2 = new JLabel("Artifact Type");
		//lblNewLabel_2.setPreferredSize(new Dimension(150,23));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.weightx = 0.4;
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 4;
		artifactMetadataPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		artifactTypeComboBox = new JComboBox<ArtifactType>();
		artifactTypeComboBox.setModel(new DefaultComboBoxModel<ArtifactType>(ArtifactType.values()));
		GridBagConstraints gbc_artifactTypeComboBox = new GridBagConstraints();
		gbc_artifactTypeComboBox.weightx = 0.6;
		gbc_artifactTypeComboBox.fill = GridBagConstraints.BOTH;
		gbc_artifactTypeComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_artifactTypeComboBox.gridx = 1;
		gbc_artifactTypeComboBox.gridy = 4;
		artifactMetadataPanel.add(artifactTypeComboBox, gbc_artifactTypeComboBox);
		
		lblDocumentationURL = new JLabel("Documentation");
		GridBagConstraints gbc_lblDocumentationURL = new GridBagConstraints();
		gbc_lblDocumentationURL.fill = GridBagConstraints.BOTH;
		gbc_lblDocumentationURL.weightx = 0.4;
		gbc_lblDocumentationURL.anchor = GridBagConstraints.WEST;
		gbc_lblDocumentationURL.insets = new Insets(0, 0, 5, 5);
		gbc_lblDocumentationURL.gridx = 0;
		gbc_lblDocumentationURL.gridy = 5;
		artifactMetadataPanel.add(lblDocumentationURL, gbc_lblDocumentationURL);
		textDocumentationURL.setForeground(Color.BLUE);
		textDocumentationURL.setFont(new Font("Tahoma", Font.ITALIC, 11));
		GridBagConstraints gbc_textDocumentationURL = new GridBagConstraints();
		gbc_textDocumentationURL.weightx = 0.6;
		gbc_textDocumentationURL.fill = GridBagConstraints.HORIZONTAL;
		gbc_textDocumentationURL.insets = new Insets(0, 0, 5, 0);
		gbc_textDocumentationURL.gridx = 1;
		gbc_textDocumentationURL.gridy = 5;
		artifactMetadataPanel.add(textDocumentationURL, gbc_textDocumentationURL);
		textDocumentationURL.setColumns(10);
		
		contentSpecificationPanel = new ContentSpecificationPanel();
		GridBagConstraints gbc_contentSpecificationPanel = new GridBagConstraints();
		gbc_contentSpecificationPanel.anchor = GridBagConstraints.WEST;
		gbc_contentSpecificationPanel.insets = new Insets(0, 0, 5, 0);
		gbc_contentSpecificationPanel.fill = GridBagConstraints.BOTH;
		gbc_contentSpecificationPanel.gridwidth = 2;
		gbc_contentSpecificationPanel.gridy = 6;
		gbc_contentSpecificationPanel.gridx = 0;
		artifactMetadataPanel.add(contentSpecificationPanel, gbc_contentSpecificationPanel);
		
		contentSpecificationButtons = new JPanel();
		FlowLayout flowLayout = (FlowLayout) contentSpecificationButtons.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		GridBagConstraints gbc_contentSpecificationButtons = new GridBagConstraints();
		gbc_contentSpecificationButtons.gridwidth = 2;
		gbc_contentSpecificationButtons.fill = GridBagConstraints.BOTH;
		gbc_contentSpecificationButtons.gridx = 0;
		gbc_contentSpecificationButtons.gridy = 7;
		artifactMetadataPanel.add(contentSpecificationButtons, gbc_contentSpecificationButtons);
		
		btnDeleteRow = new JButton("Delete Row");
		btnDeleteRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Get selected row
				String path = contentSpecificationPanel.getSelectedRowPath();
				
				if(lastSelected != null)
				{
					// Delete row
					lastSelected.deleteSpecificationWithPath(path);
					
					// Update list
					updateUIWithArtifact(lastSelected);
					
					// Persist changes
					Neo4jAdapter.getInstance().updateConfigurationArtifact(lastSelected);
				}
			}
		});
		contentSpecificationButtons.add(btnDeleteRow);
		
		btnAddRow = new JButton("Add Row");
		btnAddRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentSpecificationPanel.addRowToTable(new ContentSpecification());
			}
		});
		contentSpecificationButtons.add(btnAddRow);
		
		lblSourceCompName = new JLabel("Source Component");
		GridBagConstraints gbc_lblSourceCompName = new GridBagConstraints();
		gbc_lblSourceCompName.anchor = GridBagConstraints.WEST;
		gbc_lblSourceCompName.insets = new Insets(0, 0, 5, 5);
		gbc_lblSourceCompName.gridx = 0;
		gbc_lblSourceCompName.gridy = 1;
		artifactMetadataPanel.add(lblSourceCompName, gbc_lblSourceCompName);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 7;
		artifactMetadataPanel.add(textField, gbc_textField);
		textField.setColumns(10);
		contentPane.add(buttonPanel, BorderLayout.EAST);
		buttonPanel.setLayout(new GridLayout(7, 1, 0, 0));
		
		btnAddDependency = new JButton("Create Depdendency");
		btnAddDependency.setEnabled(false);
		btnAddDependency.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Load the artifact from the list
				/*ConfigurationArtifact a = artifactList.getSelectedValue();
				
				List<ConfigurationArtifact> listArtifacts = new ArrayList<ConfigurationArtifact>();
				for(int i = 0; i < artifactList.getModel().getSize(); i++)
				{
					listArtifacts.add(artifactList.getModel().getElementAt(i));
				}
				//TODO gets one artifact and not complete list FIX
				 */
				
				// Note: Changed JList -> JTree for readability. Get artifacts from Neo4j instead for now.
				Collection<ConfigurationArtifact> collectionArtifacts = Neo4jAdapter.getInstance().getAllConfigurationArtifacts();
				List<ConfigurationArtifact> listArtifacts = new ArrayList<ConfigurationArtifact> (collectionArtifacts);
				
				JOptionPaneCreateDependency dlgDependency = new JOptionPaneCreateDependency();
				if(lastSelected != null)
				{
					dlgDependency.showDialog(lastSelected,listArtifacts);
				}
			}
		});
		buttonPanel.add(btnAddDependency);
		
		btnLoadSampleData = new JButton("Load Sample Data");
		buttonPanel.add(btnLoadSampleData);
		
		btnLoadSampleDataChef = new JButton("Load Chef Sample Data");
		btnLoadSampleDataChef.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Delete all existing artifacts
				Collection<ConfigurationArtifact> artifacts = Neo4jAdapter.getInstance().getAllConfigurationArtifacts();
				for(ConfigurationArtifact art: artifacts)
				{
					Neo4jAdapter.getInstance().deleteConfigurationArtifact(art);
				}
				
				// Load sample data
				Collection<ConfigurationArtifact> sampleArtifacts = SampleDataLoader.getChefSampleData();
				for(ConfigurationArtifact art: sampleArtifacts)
				{
					Neo4jAdapter.getInstance().createConfigurationArtifact(art);
				}
				
				// Populate artifacts in list to ensure sync
				loadArtifacts();
			}
		});
		buttonPanel.add(btnLoadSampleDataChef);
		
		//================================================================================
		// Initialize
		//================================================================================
		
		loadArtifacts();
		
		//================================================================================
		// Add listeners
		//================================================================================
		
		// -------------- Tree item changed		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                        tree.getLastSelectedPathComponent();
				
				if(node != null && node.isLeaf())
				{
					lastSelected = (ConfigurationArtifact) node.getUserObject();
					btnAddDependency.setEnabled(true);
					updateUIWithArtifact(lastSelected);
				}
				else
				{
					// We have selected 
				}
				
			}
		});
		
		//////////////////////////////////////////////////////////////
		// -------------- Load data
		btnLoadData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadArtifacts();
			}
		});

		//////////////////////////////////////////////////////////////
		// -------------- Delete Artifact
		btnDeleteArtifact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteArtifact();
			}
		});
		
		//////////////////////////////////////////////////////////////
		// -------------- Add/Create Artifact
		btnAddArtifact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//String response = JOptionPane.showInputDialog
				//	      ( "What is the name of the artifact?" );
				
				JOptionPaneCreateArtifact dlg = new JOptionPaneCreateArtifact();
				ConfigurationArtifact a = dlg.showDialog();
				if(a != null && a.getArtifactName() != "DefaultName")
				{
					if(!Neo4jAdapter.getInstance().createConfigurationArtifact(a))
					{
						JOptionPane.showMessageDialog(contentPane, "Something went wrong when creating the artifact.");
					}
					loadArtifacts();
				}
				else
				{
					// We failed to create a configuration artifact. Do nothing.
				}
			}
		});
			
		//////////////////////////////////////////////////////////////
		// -------------- Update Artifact Content
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveArtifact();
			}
		});
		
		//////////////////////////////////////////////////////////////
		// -------------- Load sample data (from file)
		btnLoadSampleData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadSampleData();
			}
		});
	}
	
	private void updateUIWithArtifact(ConfigurationArtifact artifact)
	{
		if(artifact != null)
		{
			// Set up content data display
			textArea.setText((String) artifact.getArtifactDataText());
			
			// Set up metadata display
			artifactNameField.setText(artifact.getArtifactName());
			environmentComboBox.setSelectedItem(artifact.getEnvironment());
			artifactTypeComboBox.setSelectedItem(artifact.getArtifactType());
			txtSourceCompName.setText(artifact.getSourceComponentName());
			textDataPath.setText(artifact.getContentPath().toString());
			textDocumentationURL.setText(artifact.getDocumentationURL().toString());
			
			// Set populate artifact specification
			if(artifact.getArtifactType() == ArtifactType.XML || artifact.getArtifactType() == ArtifactType.JSON)
			{
				contentSpecificationPanel.setVisible(true);
				contentSpecificationButtons.setVisible(true);
				contentSpecificationPanel.populateTable(artifact);
			}
			else
			{
				contentSpecificationPanel.clearTable();
				contentSpecificationPanel.setVisible(false);
				contentSpecificationButtons.setVisible(false);
			}
		}
		else
		{
			textArea.setText("");
		}
	}
	
	private void loadSampleData()
	{
		// Delete all existing artifacts
		Collection<ConfigurationArtifact> artifacts = Neo4jAdapter.getInstance().getAllConfigurationArtifacts();
		for(ConfigurationArtifact art: artifacts)
		{
			Neo4jAdapter.getInstance().deleteConfigurationArtifact(art);
		}
		
		// Load sample data
		Collection<ConfigurationArtifact> sampleArtifacts = SampleDataLoader.getSampleData();
		for(ConfigurationArtifact art: sampleArtifacts)
		{
			Neo4jAdapter.getInstance().createConfigurationArtifact(art);
		}
		
		// Populate artifacts in list to ensure sync
		loadArtifacts();
	}
	
	private void deleteArtifact()
	{
		if(lastSelected != null)
		{
			if(!Neo4jAdapter.getInstance().deleteConfigurationArtifact(lastSelected))
			{
				JOptionPane.showMessageDialog(contentPane, "Something went wrong when deleting the artifact.");
			}
			loadArtifacts();
		}
	}
	
	private boolean saveArtifact()
	{
		// Load the last selected configuration artifact from the JTree
		ConfigurationArtifact a = lastSelected;
		
		if(a == null) return false;
		
		// update content
		a.setArtifactData(textArea.getText());
		a.setArtifactType((ArtifactType) artifactTypeComboBox.getSelectedItem());
		a.setArtifactEnvironment((Environment) environmentComboBox.getSelectedItem());
		a.setSourceComponentName(txtSourceCompName.getText());
		
		// Update Content Specification
		a.setContentSpecification(contentSpecificationPanel.getContentSpecificationFromTable());
		
		
		// See if there are any issues with the artifact by validating it
		boolean proceedWithSave = false;
		Collection<ValidationError> validationErrors = ConfigurationValidator.validateArtifact(a);
		if(ConfigurationValidator.countErrors(validationErrors) + ConfigurationValidator.countWarnings(validationErrors) == 0)
		{
			proceedWithSave = true;
		}
		else
		{
			// Show warnings and errors to user. Give option to override.
			ValidationConfirmationDialog dlg = new ValidationConfirmationDialog(a);
			proceedWithSave = dlg.showDialog();
		}
		
		if(proceedWithSave == true)
		{
			// update in graph DB
			if(!Neo4jAdapter.getInstance().updateConfigurationArtifact(a))
			{
				JOptionPane.showMessageDialog(contentPane, "Something went wrong when updating the artifact.");
			}
		}
		else
		{
			// Operation aborted by user. Do nothing.
		}
					
		loadArtifacts();
		return true;
	}
	
	private void loadArtifacts()
	{
		// Load the last selected configuration artifact from the JTree. Can be null;
		ConfigurationArtifact selectedArtifact = lastSelected;
		
		// Load artifacts and re-populate the list
		Collection<ConfigurationArtifact> artifacts = Neo4jAdapter.getInstance().getAllConfigurationArtifacts();
		if(artifacts != null)
		{
			buildArtifactTree(artifacts);
			
			// Try to restore the selection
			if(selectedArtifact != null)
			{
				DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
				DefaultMutableTreeNode top = (DefaultMutableTreeNode) model.getRoot();
				for(int i = 0; i < top.getChildCount(); ++i)
				{
					DefaultMutableTreeNode sourceCompNode = (DefaultMutableTreeNode) top.getChildAt(i);
					for(int j = 0; j < sourceCompNode.getChildCount(); ++j)
					{
						DefaultMutableTreeNode configArtifactNode = (DefaultMutableTreeNode) sourceCompNode.getChildAt(j);
						ConfigurationArtifact artifact = (ConfigurationArtifact) configArtifactNode.getUserObject();
						
						if(artifact.getArtifactID() == selectedArtifact.getArtifactID())
						{
							//artifactList.setSelectedIndex(i);
							TreeNode[] nodes = model.getPathToRoot(configArtifactNode);
							tree.setSelectionPath(new TreePath(nodes));
						}
					}
				}
				
			}
		}
	}
	
	private void buildArtifactTree(Collection<ConfigurationArtifact> artifacts)
	{
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		DefaultMutableTreeNode top = (DefaultMutableTreeNode) model.getRoot();
		top.setUserObject("Configuration Artifacts");
		top.removeAllChildren();
		
		for(ConfigurationArtifact artifact: artifacts)
		{
			DefaultMutableTreeNode sourceComponent = null;
			
			// See if source component is already in tree
			for(int i = 0; i < top.getChildCount(); ++i)
			{
				if(top.getChildAt(i).toString().equalsIgnoreCase(artifact.getSourceComponentName()))
				{
					sourceComponent = (DefaultMutableTreeNode) top.getChildAt(i);
				}
			}
			
			if(sourceComponent != null)
			{
				// If in tree -> add to that node
				sourceComponent.add(new DefaultMutableTreeNode(artifact));
			}
			else
			{
				// If not in tree -> Create new node and add
				sourceComponent = new DefaultMutableTreeNode(artifact.getSourceComponentName());
				sourceComponent.add(new DefaultMutableTreeNode(artifact));
				top.add(sourceComponent);
			}
		}
		
		// Replace old tree
		model.reload(top);
		tree.setShowsRootHandles(false);
	}
}
