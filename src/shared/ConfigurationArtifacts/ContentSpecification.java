package shared.ConfigurationArtifacts;

import org.neo4j.ogm.annotation.*;

import shared.ComparisonHelper;
import shared.ComparisonOperator;
import shared.ContentSpecificationType;

// TODO: Fix this class and make it nice
@NodeEntity
public class ContentSpecification {
	// This is required to be in every class.
	@GraphId
    private Long id;
	
	private String path = "";
	private ComparisonOperator operator = ComparisonOperator.EQUAL;
	private boolean isMandatory = true;
	private ContentSpecificationType type = ContentSpecificationType.STRING;
	private String specifierOne = "";
	private String specifierTwo = ""; // Only used for comparisons such as "between"
	
	public ContentSpecification() {};
	
	public ContentSpecification(String path, ContentSpecificationType type, ComparisonOperator operator, boolean isMandatory, String specifierOne, String specifierTwo)
	{
		this.path = path;
		this.operator = operator;
		this.isMandatory = isMandatory;
		this.specifierOne = specifierOne;
		this.specifierTwo = specifierTwo;
		this.type = type;
	}
	
	public boolean compare(Object comparisonValue)
	{
		switch(type)
		{
		case STRING:
			return ComparisonHelper.<String>compare((String)comparisonValue, specifierOne, specifierTwo, operator);
		case INT:
			return ComparisonHelper.<Integer>compare((Integer)comparisonValue, Integer.parseInt(specifierOne), Integer.parseInt(specifierTwo), operator);
		case DOUBLE:
			return ComparisonHelper.<Double>compare((Double)comparisonValue, Double.parseDouble(specifierOne), Double.parseDouble(specifierTwo), operator);
		default:
			return false;
		}
		
	}
	
	//public String comparisonString(Object comparisonValue)
	//{
	//	return ComparisonHelper.comparisonString(comparisonValue, specifierOne, specifierTwo, operator);
	//}
	
	//================================================================================
    // Getters
    //================================================================================
	
	public String getXPath() { return path; }
	public ComparisonOperator getComparisonOperator() { return operator; }
	public boolean getIsManatory() { return isMandatory; }
	public String getSpecifierOne() { return specifierOne; }
	public String getSpecifierTwo() { return specifierTwo; }
	public ContentSpecificationType getType() { return type; }
	//public Class<T> getType() { return persistentClass; }
	
	
	//================================================================================
    // Setters
    //================================================================================
	
	public void setXPath(String xpath) { this.path = xpath; }
	public void getComparisonOperator(ComparisonOperator operator) { this.operator = operator; }
	public void getIsManatory(boolean isMandatory) { this.isMandatory = isMandatory; }
	public void getSpecifierOne(String specifierOne) { this.specifierOne = specifierOne; }
	public void getSpecifierTwo(String specifierTwo) { this.specifierTwo = specifierTwo; }
}
