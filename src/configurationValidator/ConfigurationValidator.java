package configurationValidator;

import java.util.ArrayList;
import java.util.Collection;

import shared.ConfigurationArtifacts.ConfigurationArtifact;

public class ConfigurationValidator {
	
	/**
	 * Validate a configuration artifact and return ALL issues/errors found
	 * @param artifact The artifact to validate
	 * @return Collection of errors found
	 */
	public static Collection<ValidationError> validateArtifact(ConfigurationArtifact artifact)
	{
		Collection<ValidationError> validationErrors = new ArrayList<ValidationError>();
		
		// Validate Integrity
		validationErrors.addAll(IntegrityValidator.validateIntegrity(artifact));
		
		// Validate Completeness
		validationErrors.addAll(CompletenessValidator.validateCompleteness(artifact));
		
		// Validate Correctness
		// TODO: Implement correctness validation
		
		// Validate Consistency
		// TODO: Implement consistency validation
		
		return validationErrors;
	}
	
	/**
	 * Validate all artifacts in a collection and return a collections of errors/issues found
	 * @param artifacts The artifacts to validate
	 * @return The identified errors
	 */
	public static Collection<ValidationError> validateArtifact(Collection<ConfigurationArtifact> artifacts)
	{
		Collection<ValidationError> validationErrors = new ArrayList<ValidationError>();
		for(ConfigurationArtifact a: artifacts)
		{
			validationErrors.addAll(validateArtifact(a));
		}
		return validationErrors;
	}
	
	public static int countErrors(Collection<ValidationError> errors)
	{
		int numErrors = 0;
		for(ValidationError err: errors)
		{
			if(err.getValidationErrorLevel() == ValidationErrorLevel.ERROR)
			{
				numErrors++;
			}
		}
		return numErrors;
	}
	
	public static int countWarnings(Collection<ValidationError> errors)
	{
		int numWarnings = 0;
		for(ValidationError err: errors)
		{
			if(err.getValidationErrorLevel() == ValidationErrorLevel.WARNING)
			{
				numWarnings++;
			}
		}
		return numWarnings;
	}
}
