/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.environments.continuousgridworld.messages;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.apache.commons.codec.binary.Base64;
import org.rlcommunity.environments.continuousgridworld.State;
import rlVizLib.messaging.GenericMessage;
import rlVizLib.messaging.MessageUser;
import rlVizLib.messaging.MessageValueType;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvMessageType;

/**
 *
 * @author btanner
 */
public class StateResponse {
private State theState;

    public StateResponse(State theState) {
        this.theState=theState;
    }

    public StateResponse(String responseMessage) throws NotAnRLVizMessageException {
            GenericMessage theGenericResponse = new GenericMessage(responseMessage);
            String thePayLoadString = theGenericResponse.getPayLoad();
            setVarsFromEncodedPayloadString(thePayLoadString);
    }


    public String makeStringResponse() {
        ObjectOutputStream OOS = null;
        try {
            StringBuffer theResponseBuffer = new StringBuffer();
            theResponseBuffer.append("TO=");
            theResponseBuffer.append(MessageUser.kBenchmark.id());
            theResponseBuffer.append(" FROM=");
            theResponseBuffer.append(MessageUser.kEnv.id());
            theResponseBuffer.append(" CMD=");
            theResponseBuffer.append(EnvMessageType.kEnvResponse.id());
            theResponseBuffer.append(" VALTYPE=");
            theResponseBuffer.append(MessageValueType.kString.id());
            theResponseBuffer.append(" VALS=");
            ByteArrayOutputStream BOS = new ByteArrayOutputStream();
            OOS = new ObjectOutputStream(new BufferedOutputStream(BOS));
            OOS.writeObject(theState);
            OOS.close();
            byte[] theStringBytes = BOS.toByteArray();
            byte[] b64encoded=Base64.encodeBase64(theStringBytes);
            String theBytesAsString = new String(b64encoded);
            theResponseBuffer.append(theBytesAsString);
            return theResponseBuffer.toString();
        } catch (IOException ex) {
            System.err.println("Problem encoding message: "+ex);
        } finally {
            try {
                OOS.close();
            } catch (IOException ex) {
            System.err.println("Problem closing stream: "+ex);
            }
        }
        return null;

    }
//
//    public Set<Target> getTargets() {
//        return theTargets;
//    }
//
    private void setVarsFromEncodedPayloadString(String thePayLoadString) {
        ObjectInputStream OIS = null;
        try {
            byte[] encodedPayload=thePayLoadString.getBytes();
            byte[] payLoadInBytes = Base64.decodeBase64(encodedPayload);
            ByteArrayInputStream BIS = new ByteArrayInputStream(payLoadInBytes);
            OIS = new ObjectInputStream(BIS);
            theState = (State) OIS.readObject();
            OIS.close();
        } catch (Exception ex) {
            System.err.println("Problem decoding message: "+ex);
        } finally {
            try {
                OIS.close();
            } catch (IOException ex) {
            System.err.println("Problem closing stream: "+ex);
            }
        }
    }

    public State getState() {
        return theState;
    }
}
