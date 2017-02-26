package configurationValidator;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import bbox_server.Neo4jAdapter;
import shared.JTableExcelExporter;
import shared.ConfigurationArtifacts.ConfigurationArtifact;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;

public class ValidationReportUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1435155870137884172L;
	private JPanel contentPane;
	private ValidationTable table;
	private JPanel panel;
	private JButton btnValidateAll;
	private JButton btnSaveReport;
	private Collection<ValidationError> validationErrors = new ArrayList<ValidationError>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ValidationReportUI frame = new ValidationReportUI();
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
	public ValidationReportUI() {
		setTitle("Configuration Validation");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1200, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		table = new ValidationTable();
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane);
		
		panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel);
		
		btnValidateAll = new JButton("Validate All");
		btnValidateAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Load all items from Neo4j database
				Collection<ConfigurationArtifact> artifacts = Neo4jAdapter.getInstance().getAllConfigurationArtifacts();
				
				// Validate all items using the configuration validator
				validationErrors = ConfigurationValidator.validateArtifact(artifacts);
				
				// Clear list of current items
				table.clearTable();
			    
				// Populate list with issues found
				for(ValidationError entry: validationErrors)
				{
					table.addRow(entry);
				}
				
				// Enable Save Report Button
				btnSaveReport.setEnabled(true);
			}
		});
		panel.add(btnValidateAll);
		
		btnSaveReport = new JButton("Save Report");
		btnSaveReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser saveFile = new JFileChooser();
				String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
				saveFile.setSelectedFile(new File("ConfigValidationReport-" + timeStamp + ".csv"));
				saveFile.setFileFilter(new FileNameExtensionFilter("Comma Separated Value File","csv"));
                int userSelection = saveFile.showSaveDialog(ValidationReportUI.this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = saveFile.getSelectedFile();
                    fileToSave = addFileExtIfNecessary(fileToSave,"csv");
                    try {
						JTableExcelExporter.exportTable(table, fileToSave);
					} catch (IOException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(ValidationReportUI.this, "Could not save file", "Error", JOptionPane.ERROR_MESSAGE);
					}
                    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
                }
			}
		});
		btnSaveReport.setEnabled(false);
		panel.add(btnSaveReport);
	}

	/**
	 * Add extension to a file that doesn't have yet an extension
	 * this method is useful to automatically add an extension in the savefileDialog control
	 * @param file file to check
	 * @param ext extension to add
	 * @return file with extension (e.g. 'test.doc')
	 */
	private File addFileExtIfNecessary(File file,String ext) {
		ext = ext.replace(".", "");
		/*if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase(ext)) {
		    // filename is OK as-is
		} else {
		    file = new File(file.toString() + "." + ext);  // append .ext if "foo.jpg.ext" is OK
		    //file = new File(file.getParentFile(), FilenameUtils.getBaseName(file.getName()) + "." + ext); // ALTERNATIVELY: remove the extension (if any) and replace it with ".xml"
		}*/
		return file;
	}
}
