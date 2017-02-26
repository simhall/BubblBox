package configurationValidator;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Collection;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import java.io.StringReader;

import shared.ConfigurationArtifacts.ConfigurationArtifact;
import shared.ConfigurationArtifacts.ContentSpecification;

public class CompletenessValidator {

	public static Collection<ValidationError> validateCompleteness(ConfigurationArtifact artifact)
	{

		Collection<ValidationError> errors = new ArrayList<ValidationError>();

		if(artifact.getContentSpecification().size() == 0)
		{
			return errors;
		}


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

				// Loop through all content validations
				for(ContentSpecification spec: artifact.getContentSpecification())
				{
					// Locate xpath in document
					XPathFactory xpf = XPathFactory.newInstance();
			        XPath xp = xpf.newXPath();
			        String text = xp.evaluate(spec.getXPath(),
			                doc.getDocumentElement());
			        System.err.println("######################################################################");
			        System.err.println(spec.getXPath());
			        System.err.println("Output: " + text);
			        
			        //Check for completeness
			        if(text.isEmpty())
			        {
			        	//Not found. Should it have been?
			        	if(spec.getIsManatory())
			        	{
			        		// Send error
			        		errors.add(new ValidationError(
									ValidationErrorType.INTEGRITY,
									ValidationErrorLevel.ERROR,
									artifact,
									"Mandatory field not found: " + spec.getXPath()));
			        	}
			        	else
			        	{
			        		// Send warning
			        		errors.add(new ValidationError(
									ValidationErrorType.INTEGRITY,
									ValidationErrorLevel.WARNING,
									artifact,
									"Optional field not found: " + spec.getXPath()));
			        	}
			        }
			        else
			        {
			        	//We found it. Do nothing
			        }
				}

				if(errors.size() == 0)
				{
					// No errors encountered. Add info message
					errors.add(new ValidationError(
							ValidationErrorType.INTEGRITY,
							ValidationErrorLevel.INFO,
							artifact,
							"XML completeness validation passed."));
				}
			}
			catch(Exception e)
			{
				// This is an integrity error. We already have a separate check for it. Do nothing.
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
				// This is an integrity error. We already have a separate check for it. Do nothing.
			}

			break;

		case TEXT:
			errors.add(new ValidationError(
					ValidationErrorType.INTEGRITY,
					ValidationErrorLevel.INFO,
					artifact,
					"Unstructured text can not be validated for completeness."));
			break;

		case UNDEFINED:
			errors.add(new ValidationError(
					ValidationErrorType.INTEGRITY,
					ValidationErrorLevel.INFO,
					artifact,
					"Undefined artifacs can not be validated for completeness."));
			break;

		default:
			errors.add(new ValidationError(
					ValidationErrorType.INTEGRITY,
					ValidationErrorLevel.WARNING,
					artifact,
					"Artifact type not recognized. Not validated for completeness."));
			break;	

		}
		return errors;
	}
}
