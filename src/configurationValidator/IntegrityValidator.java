package configurationValidator;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Collection;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import java.io.StringReader;

import shared.ConfigurationArtifacts.ConfigurationArtifact;

public class IntegrityValidator {

	public static Collection<ValidationError> validateIntegrity(ConfigurationArtifact artifact)
	{
		Collection<ValidationError> errors = new ArrayList<ValidationError>();
		switch(artifact.getArtifactType())
		{
		case XML:
			try
			{
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				String xmlData = artifact.getArtifactDataText();
				InputSource is = new InputSource(new StringReader(xmlData));
				Document doc = dBuilder.parse(is);
				//optional, but recommended
				//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
				doc.getDocumentElement().normalize();
				
				// No errors encountered. Add info message
				errors.add(new ValidationError(
						ValidationErrorType.INTEGRITY,
						ValidationErrorLevel.INFO,
						artifact,
						"XML integrity validation passed."));
			}
			catch(Exception e)
			{
				// Error encountered add error message.
				errors.add(new ValidationError(
						ValidationErrorType.INTEGRITY,
						ValidationErrorLevel.ERROR,
						artifact,
						e.toString()));
			}
			break;
			
		case JSON:
			String jsonData = artifact.getArtifactDataText();
			try
			{
				@SuppressWarnings("unused")
				JSONObject jsonObject = new JSONObject(jsonData);
				errors.add(new ValidationError(
						ValidationErrorType.INTEGRITY,
						ValidationErrorLevel.INFO,
						artifact,
						"JSON integrity validation passed."));
			}
			catch(JSONException e)
			{
				errors.add(new ValidationError(
						ValidationErrorType.INTEGRITY,
						ValidationErrorLevel.ERROR,
						artifact,
						e.toString()));
			}
			
			break;
			
		case TEXT:
			errors.add(new ValidationError(
					ValidationErrorType.INTEGRITY,
					ValidationErrorLevel.INFO,
					artifact,
					"Unstructured text can not be validated."));
			break;
			
		case UNDEFINED:
			errors.add(new ValidationError(
					ValidationErrorType.INTEGRITY,
					ValidationErrorLevel.INFO,
					artifact,
					"Undefined artifacs can not be validated for integrity."));
			break;
			
		default:
			errors.add(new ValidationError(
					ValidationErrorType.INTEGRITY,
					ValidationErrorLevel.WARNING,
					artifact,
					"Artifact type not recognized. Not validated."));
			break;	
			
		}
		return errors;
	}
}
