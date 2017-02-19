package configurationValidator;

import java.util.Collection;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ValidationTable extends JTable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2810514671742493990L;

	public ValidationTable()
	{
		
		this.setModel(ValidationTable.getTableModel());
		
		this.getColumnModel().getColumn(0).setPreferredWidth(100);
		this.getColumnModel().getColumn(0).setMaxWidth(100);
		this.getColumnModel().getColumn(1).setPreferredWidth(100);
		this.getColumnModel().getColumn(1).setMaxWidth(100);
		this.getColumnModel().getColumn(2).setPreferredWidth(150);
		this.getColumnModel().getColumn(2).setMaxWidth(150);
		this.getColumnModel().getColumn(3).setPreferredWidth(150);
		this.getColumnModel().getColumn(4).setPreferredWidth(200);
		this.getColumnModel().getColumn(4).setMaxWidth(200);
	}
	
	public static DefaultTableModel getTableModel()
	{
		return new DefaultTableModel(
				new Object[][] {},
				new String[] {
					"Error Type", "Error Level", "Artifact", "Message", "Date"
				}
			);
	}
	
	public void clearTable()
	{
		DefaultTableModel model = (DefaultTableModel) this.getModel();
	    model.setRowCount(0);
	}
	
	public void addRow(ValidationError entry)
	{
		DefaultTableModel model = (DefaultTableModel) this.getModel();
		model.addRow(new Object[]
				{
					entry.getValiationErrorType(),
					entry.getValidationErrorLevel(),
					entry.getTriggeringArtifact().getArtifactName(),
					entry.getErrorData(),
					entry.getTriggeringArtifact().getLastUpdate().toString()
				}
		);
	}
	
	public void addRows(Collection<ValidationError> entries)
	{
		for(ValidationError entry: entries)
		{
			addRow(entry);
		}
	}
}
