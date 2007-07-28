package rlVizLib.messaging.environmentShell;

import rlVizLib.messaging.AbstractMessage;
import rlVizLib.messaging.GenericMessage;
import rlVizLib.messaging.MessageUser;
import rlVizLib.messaging.MessageValueType;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlglue.RLGlue;

public class EnvShellLoadRequest extends EnvironmentShellMessages{
String envName;

	public EnvShellLoadRequest(GenericMessage theMessageObject) {
		super(theMessageObject);
		
		this.envName=super.getPayLoad();
	}
	
	

	//This is intended for debugging but works well to be just called to save code duplication
	public static String getRequestMessage(String envName){
		return AbstractMessage.makeMessage(
				MessageUser.kEnvShell.id(),
				MessageUser.kBenchmark.id(),
				EnvShellMessageType.kEnvShellLoad.id(),
				MessageValueType.kString.id(),
				envName);
		
	}
	public static EnvShellLoadResponse Execute(String envName){
		String theRequest=getRequestMessage(envName);

		String responseMessage=RLGlue.RL_env_message(theRequest);

		EnvShellLoadResponse theResponse;
		try {
			theResponse = new EnvShellLoadResponse(responseMessage);
		} catch (NotAnRLVizMessageException e) {
			System.err.println("In EnvShellLoadRequest: response was not an RLViz Message");
			return null;
		}		return theResponse;


	}



	public String getEnvName() {
		return envName;
	}
	
}
