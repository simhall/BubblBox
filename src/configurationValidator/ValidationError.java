package configurationValidator;

import shared.ConfigurationArtifacts.ConfigurationArtifact;

public class ValidationError {

	private ValidationErrorType errorType = ValidationErrorType.UNDEFINED;
	private ValidationErrorLevel errorLevel = ValidationErrorLevel.ERROR;
	private ConfigurationArtifact triggerArtifact;
	private String errorData = "No error data specified";
	
	public ValidationError(ValidationErrorType errorType, ValidationErrorLevel errorLevel, ConfigurationArtifact artifact, String errorData)
	{
		this.errorType = errorType;
		this.errorLevel = errorLevel;
		this.triggerArtifact = artifact;
		this.errorData = errorData;
	}
	
	
	
	//================================================================================
    // Getters
    //================================================================================
	
	public ValidationErrorType getValiationErrorType() { return errorType; }
	public ValidationErrorLevel getValidationErrorLevel() { return errorLevel; }
	public ConfigurationArtifact getTriggeringArtifact() { return triggerArtifact; }
	public String getErrorData() { return errorData; }
	
	//================================================================================
    // Setters
    //================================================================================
	
	public void getValiationErrorType(ValidationErrorType newErrorType) { this.errorType = newErrorType; }
	public void setErrorData(String errorData) { this.errorData = errorData; } 
}
