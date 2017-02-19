package configurationValidator;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import shared.ConfigurationArtifacts.ConfigurationArtifact;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;

public class ValidationConfirmationDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8058024357466370739L;
	private ValidationTable table;
	private boolean result = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ValidationConfirmationDialog dialog = new ValidationConfirmationDialog(new ConfigurationArtifact());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean showDialog()
	{
		setModal(true);
		setVisible(true);
		return result;
	}
	
	/**
	 * Create the dialog.
	 */
	public ValidationConfirmationDialog(ConfigurationArtifact art) {
		setTitle("Artifact Issues Found");
		setBounds(100, 100, 800, 300);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		{
			table = new ValidationTable();
			
			JScrollPane scrollPane = new JScrollPane(table);
			getContentPane().add(scrollPane);
			scrollPane.setViewportView(table);
			
			table.addRows(ConfigurationValidator.validateArtifact(art));
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("Proceed");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// User choose to proceed despite the warnings. Return true.
						result = true;
						setVisible(false);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Abort");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// User choose to abort. Return false.
						result = false;
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
