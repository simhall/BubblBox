package shared;

public class ContentSpecificationTypeHelper {

	public static ContentSpecificationType stringToType (String type)
	{
		switch(type.toLowerCase())
		{
		case "string":
			return ContentSpecificationType.STRING;
			
		case "boolean":
			return ContentSpecificationType.BOOLEAN;
			
		case "double":
			return ContentSpecificationType.DOUBLE;
			
		case "integer":
			return ContentSpecificationType.INT;
		
		default:
			return ContentSpecificationType.UNDEFINED;
		}
	}
}
