package configurationHandlerUI;

import static org.junit.Assert.fail;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bbox_server.Neo4jAdapter;
import shared.ConfigurationArtifacts.ConfigurationArtifact;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import java.awt.List;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JOptionPaneCreateDependency extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtArtifactName;
	private JTextField txtDependencyName;
	private ConfigurationArtifact result = new ConfigurationArtifact();
	private ConfigurationArtifact fromArtifact = new ConfigurationArtifact();
	private java.util.List<ConfigurationArtifact> artifactsList = new ArrayList<ConfigurationArtifact>();
	private List artifactListView = new List();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			JOptionPaneCreateDependency dialog = new JOptionPaneCreateDependency();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ConfigurationArtifact showDialog(ConfigurationArtifact a, java.util.List<ConfigurationArtifact> listArtifacts)
	{
		artifactsList = listArtifacts;
		for(ConfigurationArtifact artifact : artifactsList)
		{ 
			if(artifact.getArtifactName() != a.getArtifactName())
				artifactListView.add(artifact.getArtifactName());
		}
		contentPanel.add(artifactListView);
		this.fromArtifact = a;
		txtArtifactName.setText(a.getArtifactName());
		setModal(true);
		setVisible(true);
		
		/*// Populate list
		for(ConfigurationArtifact artifact : listArtifacts) 
		{
			if(artifact != a)
			{
				this.artifactsList.add(artifact);
				this.artifactListView.add(artifact.getArtifactName());				
			}
		}*/
		return result;
	}

	/**
	 * Create the dialog.
	 */
	public JOptionPaneCreateDependency() {
		setTitle("Create Artifact Dependency");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblArtifactName = new JLabel("Artifact Name");
		lblArtifactName.setBounds(10, 11, 81, 23);
		contentPanel.add(lblArtifactName);
		
		txtArtifactName = new JTextField();
		txtArtifactName.setEditable(false);
		txtArtifactName.setColumns(1);
		txtArtifactName.setBounds(123, 12, 292, 23);
		contentPanel.add(txtArtifactName);
		
		JLabel lblDependencyName = new JLabel("Dependency Name");
		lblDependencyName.setBounds(10, 45, 100, 23);
		contentPanel.add(lblDependencyName);
		
		txtDependencyName = new JTextField();
		txtDependencyName.setText("Dependency Name");
		txtDependencyName.setColumns(1);
		txtDependencyName.setBounds(123, 46, 292, 33);
		contentPanel.add(txtDependencyName);
		
		
		
		artifactListView.setBounds(121, 85, 292, 120);
		for(ConfigurationArtifact artifact : artifactsList)
		{ 
			artifactListView.add(artifact.getArtifactName());
		}
		contentPanel.add(artifactListView);
		
		JLabel lblArtifactDependent = new JLabel("Dependent Artifact(s)");
		lblArtifactDependent.setBounds(10, 79, 105, 23);
		contentPanel.add(lblArtifactDependent);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Ok");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						artifactListView.getSelectedItem();
						for(ConfigurationArtifact artifact : artifactsList)
						{ 
							if(artifact.getArtifactName() == artifactListView.getSelectedItem())
							{
								if(Neo4jAdapter.getInstance().getConfigurationArtifact(artifact.getArtifactName()) == null)
								{
									JOptionPane.showMessageDialog(getContentPane(), "Something went wrong when creating the dependency.");
								}
								fromArtifact.createDependency("dependentOn", fromArtifact, artifact);
								
								if(!Neo4jAdapter.getInstance().createConfigurationArtifact(fromArtifact))
								{
									JOptionPane.showMessageDialog(getContentPane(), "Artifact dependency could not be created");
								}
								
								setVisible(false);
								dispose();
								
							}
						}
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
