package configurationHandlerUI;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.*;
import javax.swing.table.*;

import shared.ComparisonHelper;
import shared.ComparisonOperator;
import shared.ContentSpecificationType;
import shared.ContentSpecificationTypeHelper;
import shared.ConfigurationArtifacts.ConfigurationArtifact;
import shared.ConfigurationArtifacts.ContentSpecification;

public class ContentSpecificationPanel extends JComponent {

	private static final long serialVersionUID = 1L;
	private JTable table;
	DefaultTableModel model;

	public Dimension getPreferredSize() {
		return new Dimension(250,200);
	}

	public ContentSpecificationPanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		JPanel panel = new JPanel();
		add(panel);
		Object[] columnNames = {"ID", "XPath", "Type", "Operator", "Specifier 1", "Specifier 2", "Is Mandatory"};
		Object[][] data = {
				{new Integer(1),"/test/field1", "String", "==", "server name" , "n/a", true},
				{new Integer(2),"/test/field2", "Integer", "==", new Integer(80) , "n/a", true},
				//{"Sell", "MicroSoft", new Integer(2000), new Double(6.25), true,false},
				//{"Sell", "Apple", new Integer(3000), new Double(7.35), true,false},
				//{"Buy", "Nortel", new Integer(4000), new Double(20.00), false,false}
		};
		model = new DefaultTableModel(data, columnNames);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// Center rendering
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table = new JTable(model) {

			private static final long serialVersionUID = 1L;

			/*@Override
            public Class getColumnClass(int column) {
            return getValueAt(0, column).getClass();
            }*/
			@SuppressWarnings("unchecked")
			@Override
			public Class getColumnClass(int column) {
				switch (column) {
				case 0:
					return Integer.class; // ID
				case 1:
					return String.class; // XPath
				case 2:
					return String.class; // Field Type, needs custom editor
				case 3:
					return String.class; // Operator, needs custom editor
				case 4:
					return String.class; // Specifier 1
				case 5:
					return String.class; // Specifier 2
				case 6:
					return Boolean.class; // Is Mandatory
				default:
					return String.class;
				}
			}
		};
		table.setFillsViewportHeight(true);

		// Create type drop-down menu
		TableColumn column2 = table.getColumnModel().getColumn(2);

		// Create operator drop-down menu
		TableColumn column4 = table.getColumnModel().getColumn(3);

		column2.setCellEditor(new TypeCellEditor());
		column4.setCellEditor(new OperatorCellEditor());

		table.setPreferredScrollableViewportSize(table.getPreferredSize());

		// Set size of columns
		table.getColumnModel().getColumn(0).setPreferredWidth(25);
		table.getColumnModel().getColumn(0).setMinWidth(20);
		table.getColumnModel().getColumn(0).setMaxWidth(30);

		table.getColumnModel().getColumn(3).setPreferredWidth(80);
		table.getColumnModel().getColumn(3).setMinWidth(80);
		table.getColumnModel().getColumn(3).setMaxWidth(80);

		table.getColumnModel().getColumn(6).setPreferredWidth(80);
		table.getColumnModel().getColumn(6).setMinWidth(80);
		table.getColumnModel().getColumn(6).setMaxWidth(80);

		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );

		// Add columns to panels
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane);
	}

	public Collection<ContentSpecification> getContentSpecificationFromTable()
	{
		Collection<ContentSpecification> ret = new ArrayList<ContentSpecification>();
		
		// Loop through all rows in the table
		for(int row = 0; row < model.getRowCount(); ++row)
		{
			// Recreate ContentSpecification for each row
			ContentSpecification toAdd = new ContentSpecification
					(
							(String) model.getValueAt(row, 1), // Path
							(ContentSpecificationType) ContentSpecificationTypeHelper.stringToType((String)model.getValueAt(row, 2)), // Type
							(ComparisonOperator) ComparisonHelper.stringToOperator((String)model.getValueAt(row, 3)), // Operator
							(boolean)model.getValueAt(row, 6), // isMandatory
							(String) model.getValueAt(row, 4), // SpecifierOne
							(String) model.getValueAt(row, 5) // SpecifierTwo
					);
			
			// Add to collection
			ret.add(toAdd);
		}
		return ret;
	}
	public void populateTable(ConfigurationArtifact a)
	{
		clearTable();
		for(ContentSpecification spec: a.getContentSpecification())
		{
			addRowToTable(spec);
		}
		//addRowToTable(a.getContentSpecification());
	}

	public String getSelectedRowPath()
	{
		return (String) table.getModel().getValueAt(table.getSelectedRow(), 1);
	}
	
	public void clearTable()
	{
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
	}

	public void addRowToTable(ContentSpecification rowSpecification)
	{
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int newId = model.getRowCount();
		ComparisonOperator operator = rowSpecification.getComparisonOperator();
		String operatorString = ComparisonHelper.operatorToString(operator);
		Object[] rowData = 
			{
					new Integer(newId),
					rowSpecification.getXPath().toString(),
					rowSpecification.getType().toString(),
					operatorString,
					rowSpecification.getSpecifierOne().toString(),
					rowSpecification.getSpecifierTwo().toString(),
					rowSpecification.getIsManatory()
			};

		model.addRow(rowData);
	}

	public class OperatorCellEditor extends AbstractCellEditor implements TableCellEditor 
	{
		private JComboBox<String> editor;
		private String [] values ;


		public OperatorCellEditor()
		{
			ArrayList<String> operators = new ArrayList<String>();
			for(ComparisonOperator operator: ComparisonOperator.values())
			{
				//System.out.println(ComparisonHelper.operatorToString(operator));
				operators.add(ComparisonHelper.operatorToString(operator));
			}
			String[] values = new String[operators.size()];

			values = (String[]) operators.toArray(values);

			// Create a new Combobox with the array of values.
			editor = new JComboBox<String>(values);
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int colIndex) 
		{

			// Set the model data of the table
			if(isSelected)
			{
				editor.setSelectedItem(value);
				TableModel model = table.getModel();
				model.setValueAt(value, rowIndex, colIndex);
			}

			return editor;
		}

		@Override
		public Object getCellEditorValue() 
		{
			return editor.getSelectedItem();
		}
	}

	public class TypeCellEditor extends AbstractCellEditor implements TableCellEditor 
	{
		private JComboBox editor;
		private String [] values ;


		@SuppressWarnings("unchecked")
		public TypeCellEditor()
		{
			ArrayList<String> operators = new ArrayList<String>();
			for(ContentSpecificationType type: ContentSpecificationType.values())
			{
				//System.out.println(ComparisonHelper.operatorToString(operator));
				operators.add(type.toString());
			}
			String[] values = new String[operators.size()];

			values = (String[]) operators.toArray(values);

			// Create a new Combobox with the array of values.
			editor = new JComboBox(values);
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int colIndex) 
		{

			// Set the model data of the table
			if(isSelected)
			{
				editor.setSelectedItem(value);
				TableModel model = table.getModel();
				model.setValueAt(value, rowIndex, colIndex);
			}

			return editor;
		}

		@Override
		public Object getCellEditorValue() 
		{
			return editor.getSelectedItem();
		}
	}
}