package configurationHandlerUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import shared.Environment;
import shared.ConfigurationArtifacts.ArtifactType;
import shared.ConfigurationArtifacts.ConfigurationArtifact;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class JOptionPaneCreateArtifact extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 336149705078254440L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtNameHere;
	private JTextArea txtDataHere;
	private JComboBox<Environment> environmentComboBox;
	private ConfigurationArtifact result = new ConfigurationArtifact();
	private JTextField txtDataPath;
	private JTextField textDocumentation;
	private JTextField textSourceComponent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			JOptionPaneCreateArtifact dialog = new JOptionPaneCreateArtifact();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ConfigurationArtifact showDialog()
	{
		setModal(true);
		setVisible(true);
		return result;
	}

	/**
	 * Create the dialog.
	 */
	public JOptionPaneCreateArtifact() {
		setTitle("Create new configuration artifact");
		setBounds(100, 100, 450, 400);
		getContentPane().setLayout(new BorderLayout(0, 0));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] {122, 302, 0};
		gbl_contentPanel.rowHeights = new int[] {23, 30, 23, 23, 23, 23, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblArtifactName = new JLabel("Artifact Name");
			GridBagConstraints gbc_lblArtifactName = new GridBagConstraints();
			gbc_lblArtifactName.fill = GridBagConstraints.BOTH;
			gbc_lblArtifactName.insets = new Insets(0, 0, 5, 5);
			gbc_lblArtifactName.gridx = 0;
			gbc_lblArtifactName.gridy = 0;
			contentPanel.add(lblArtifactName, gbc_lblArtifactName);
		}
		{
			txtNameHere = new JTextField();
			txtNameHere.setText("Name here");
			GridBagConstraints gbc_txtNameHere = new GridBagConstraints();
			gbc_txtNameHere.fill = GridBagConstraints.BOTH;
			gbc_txtNameHere.insets = new Insets(0, 0, 5, 0);
			gbc_txtNameHere.gridx = 1;
			gbc_txtNameHere.gridy = 0;
			contentPanel.add(txtNameHere, gbc_txtNameHere);
			txtNameHere.setColumns(1);
		}
		{
			JLabel lblSourceComponent = new JLabel("Source Component");
			GridBagConstraints gbc_lblSourceComponent = new GridBagConstraints();
			gbc_lblSourceComponent.anchor = GridBagConstraints.WEST;
			gbc_lblSourceComponent.insets = new Insets(0, 0, 5, 5);
			gbc_lblSourceComponent.gridx = 0;
			gbc_lblSourceComponent.gridy = 1;
			contentPanel.add(lblSourceComponent, gbc_lblSourceComponent);
		}
		{
			textSourceComponent = new JTextField();
			GridBagConstraints gbc_textSourceComponent = new GridBagConstraints();
			gbc_textSourceComponent.insets = new Insets(0, 0, 5, 0);
			gbc_textSourceComponent.fill = GridBagConstraints.HORIZONTAL;
			gbc_textSourceComponent.gridx = 1;
			gbc_textSourceComponent.gridy = 1;
			contentPanel.add(textSourceComponent, gbc_textSourceComponent);
			textSourceComponent.setColumns(10);
		}
		{
			JLabel lblArtifactEnvironment = new JLabel("Environment");
			GridBagConstraints gbc_lblArtifactEnvironment = new GridBagConstraints();
			gbc_lblArtifactEnvironment.fill = GridBagConstraints.BOTH;
			gbc_lblArtifactEnvironment.insets = new Insets(0, 0, 5, 5);
			gbc_lblArtifactEnvironment.gridx = 0;
			gbc_lblArtifactEnvironment.gridy = 2;
			contentPanel.add(lblArtifactEnvironment, gbc_lblArtifactEnvironment);
		}
		{
			environmentComboBox = new JComboBox<Environment>();
			environmentComboBox.setModel(new DefaultComboBoxModel<Environment>(Environment.values()));
			GridBagConstraints gbc_environmentComboBox = new GridBagConstraints();
			gbc_environmentComboBox.weightx = 1.0;
			gbc_environmentComboBox.fill = GridBagConstraints.BOTH;
			gbc_environmentComboBox.insets = new Insets(0, 0, 5, 0);
			gbc_environmentComboBox.gridx = 1;
			gbc_environmentComboBox.gridy = 2;
			contentPanel.add(environmentComboBox, gbc_environmentComboBox);
		}
		{
			JLabel lblArtifactType = new JLabel("Artifact Type");
			GridBagConstraints gbc_lblArtifactType = new GridBagConstraints();
			gbc_lblArtifactType.fill = GridBagConstraints.BOTH;
			gbc_lblArtifactType.insets = new Insets(0, 0, 5, 5);
			gbc_lblArtifactType.gridx = 0;
			gbc_lblArtifactType.gridy = 3;
			contentPanel.add(lblArtifactType, gbc_lblArtifactType);
		}
		{
			JComboBox artifactTypeComboBox = new JComboBox();
			artifactTypeComboBox.setModel(new DefaultComboBoxModel(ArtifactType.values()));
			GridBagConstraints gbc_artifactTypeComboBox = new GridBagConstraints();
			gbc_artifactTypeComboBox.fill = GridBagConstraints.BOTH;
			gbc_artifactTypeComboBox.insets = new Insets(0, 0, 5, 0);
			gbc_artifactTypeComboBox.gridx = 1;
			gbc_artifactTypeComboBox.gridy = 3;
			contentPanel.add(artifactTypeComboBox, gbc_artifactTypeComboBox);
		}
		{
			JLabel lblDataPath = new JLabel("Data Path");
			GridBagConstraints gbc_lblDataPath = new GridBagConstraints();
			gbc_lblDataPath.fill = GridBagConstraints.BOTH;
			gbc_lblDataPath.insets = new Insets(0, 0, 5, 5);
			gbc_lblDataPath.gridx = 0;
			gbc_lblDataPath.gridy = 4;
			contentPanel.add(lblDataPath, gbc_lblDataPath);
		}
		{
			JPanel panel = new JPanel();
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.insets = new Insets(0, 0, 5, 0);
			gbc_panel.gridx = 1;
			gbc_panel.gridy = 4;
			contentPanel.add(panel, gbc_panel);
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			{
				txtDataPath = new JTextField();
				panel.add(txtDataPath);
				txtDataPath.setText("C:\\Your\\Path\\Here");
				txtDataPath.setColumns(10);
			}
			{
				JButton btnLoadFile = new JButton("Open");
				btnLoadFile.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						final JFileChooser fc = new JFileChooser();
						int returnVal = fc.showOpenDialog(JOptionPaneCreateArtifact.this);

						if (returnVal == JFileChooser.APPROVE_OPTION) {
							File file = fc.getSelectedFile();
							//This is where a real application would open the file.
							txtDataPath.setText(file.getAbsolutePath().toString());

							System.err.println("Opening: " + file.getName());
							//log.append("Opening: " + file.getName() + "." + newline);
						} else {
							System.err.println("Open command cancelled by user.");
							//log.append("Open command cancelled by user." + newline);
						}
					}
				});
				panel.add(btnLoadFile);
			}
		}
		{
			JLabel lblData = new JLabel("Data");
			GridBagConstraints gbc_lblData = new GridBagConstraints();
			gbc_lblData.fill = GridBagConstraints.BOTH;
			gbc_lblData.insets = new Insets(0, 0, 5, 5);
			gbc_lblData.gridx = 0;
			gbc_lblData.gridy = 5;
			contentPanel.add(lblData, gbc_lblData);
		}
		{
			JButton btnLoadData = new JButton("Load Data");
			btnLoadData.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					File file = new File(txtDataPath.getText());
					try {
						Scanner sc = new Scanner(file);
						txtDataHere.setText("");
						while (sc.hasNextLine()) {
							txtDataHere.append(sc.nextLine() + "\n");
						}
					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(JOptionPaneCreateArtifact.this,
								"Error opening file.",
								"Error",
								JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}

				}
			});
			GridBagConstraints gbc_btnLoadData = new GridBagConstraints();
			gbc_btnLoadData.fill = GridBagConstraints.BOTH;
			gbc_btnLoadData.insets = new Insets(0, 0, 5, 0);
			gbc_btnLoadData.gridx = 1;
			gbc_btnLoadData.gridy = 5;
			contentPanel.add(btnLoadData, gbc_btnLoadData);
		}
		{
			JLabel lblDocumentationURL = new JLabel("Documentation URL");
			GridBagConstraints gbc_lblDocumentationURL = new GridBagConstraints();
			gbc_lblDocumentationURL.fill = GridBagConstraints.BOTH;
			gbc_lblDocumentationURL.insets = new Insets(0, 0, 0, 5);
			gbc_lblDocumentationURL.gridx = 0;
			gbc_lblDocumentationURL.gridy = 6;
			contentPanel.add(lblDocumentationURL, gbc_lblDocumentationURL);
		}
		{
			textDocumentation = new JTextField();
			textDocumentation.setText("http://nourl");
			GridBagConstraints gbc_textDocumentation = new GridBagConstraints();
			gbc_textDocumentation.fill = GridBagConstraints.BOTH;
			gbc_textDocumentation.gridx = 1;
			gbc_textDocumentation.gridy = 6;
			contentPanel.add(textDocumentation, gbc_textDocumentation);
			textDocumentation.setColumns(10);
		}
		{
			txtDataHere = new JTextArea();
			txtDataHere.setTabSize(4);
			//getContentPane().add(txtDataHere);
			txtDataHere.setText("Data Here");
			txtDataHere.setColumns(1);
			JScrollPane scrollPane = new JScrollPane(txtDataHere);
			getContentPane().add(scrollPane);
			scrollPane.setViewportView(txtDataHere);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(!txtNameHere.getText().equals(""))
						{
							////////////////////////////////////////////////////////////////////////////////////
							// NOTE: This is where the result is summarized. Consider making this clearer.
							////////////////////////////////////////////////////////////////////////////////////

							result.setArtifactName(txtNameHere.getText());
							result.setArtifactType(ArtifactType.TEXT);
							result.setArtifactData(txtDataHere.getText());
							result.setArtifactEnvironment(environmentComboBox.getItemAt(environmentComboBox.getSelectedIndex()));
							result.setContentPath(txtDataPath.getText());
							result.setSourceComponentName(textSourceComponent.getText());
							
							try {
								result.setDocumentationURL(new URL(textDocumentation.getText()).toString());
							} catch (MalformedURLException e1) {
								// We failed to parse the URL. Ask user if he wants to use default value instead
								Object[] options = {"Use Default URL", "Abort"};
								int n = JOptionPane.showOptionDialog(JOptionPaneCreateArtifact.this,
										"Could not parse documentation URL. Use default value instead?",
										"Documentation URL Error",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.ERROR_MESSAGE,
										null,     //do not use a custom Icon
										options,  //the titles of buttons
										options[0]); //default button title
								if(n == 0)
								{
									// Use default URL
									try
									{
										result.setDocumentationURL("http://nourl");
									}
									catch(Exception ex)
									{
										// We really messed up, abort.
										return;
									}
								}
								else
								{
									// User pressed abort. Cancel artifact creation
									return;
								}
							}
						}
						else
						{
							// We failed to create a proper artifact
							result = null;
						}
						setVisible(false);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						result = null;
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
