package shared;

public class ComparisonHelper {

	/*
	EQUAL,
	NOT_EQUAL,
	GREATER,
	SMALLER,
	BETWEEN
	 */
	public static <T extends Comparable<T>> boolean compare(T value, T specifierOne, T specifierTwo, ComparisonOperator operator)
	{
		switch(operator)
		{
		case NONE:
			return true;
		case EQUAL:
			return (value.compareTo(specifierOne) == 0);
		
		case NOT_EQUAL:
			return (value.compareTo(specifierOne) != 0);
			
		case GREATER:
			return (value.compareTo(specifierOne) > 0);
			
		case SMALLER:
			return (value.compareTo(specifierOne) < 0);
			
		case BETWEEN:
			return (value.compareTo(specifierOne) > 0 && value.compareTo(specifierTwo) < 0);
			
		default:
			return false;
		}
	}
	
	public static <T extends Comparable<T>> String comparisonString(T value, T specifierOne, T specifierTwo, ComparisonOperator operator)
	{
		switch(operator)
		{
		case NONE:
			return "no comparison made";
		case EQUAL:
			return value + " " + operatorToString(operator) + " " + specifierOne;
		
		case NOT_EQUAL:
			return value + " " + operatorToString(operator) + " " + specifierOne;
			
		case GREATER:
			return value + " " + operatorToString(operator) + " " + specifierOne;
			
		case SMALLER:
			return value + " " + operatorToString(operator) + " " + specifierOne;
			
		case BETWEEN:
			return value + " " + operatorToString(operator) + " (" + specifierOne + ", " + specifierTwo + ")";
			
		default:
			return "Error: Operator not found";
		}
	}
	
	public static String operatorToString(ComparisonOperator operator)
	{
		switch(operator)
		{
		case NONE:
			return "None";
			
		case EQUAL:
			return "=";
		
		case NOT_EQUAL:
			return "!=";
			
		case GREATER:
			return ">";
			
		case SMALLER:
			return "<";
			
		case BETWEEN:
			return "Between";
		
		default:
			return "?";
		}
	}
	
	public static ComparisonOperator stringToOperator (String operator)
	{
		switch(operator.toLowerCase())
		{
		case "none":
			return ComparisonOperator.NONE;
			
		case "=":
			return ComparisonOperator.EQUAL;
		
		case "!=":
			return ComparisonOperator.NOT_EQUAL;
			
		case ">":
			return ComparisonOperator.GREATER;
			
		case "<":
			return ComparisonOperator.SMALLER;
			
		case "between":
			return ComparisonOperator.BETWEEN;
		
		default:
			return ComparisonOperator.NONE;
		}
	}
}
