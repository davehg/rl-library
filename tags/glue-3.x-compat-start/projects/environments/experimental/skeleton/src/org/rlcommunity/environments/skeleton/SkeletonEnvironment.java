package org.rlcommunity.environments.skeleton;

import rlVizLib.Environments.EnvironmentBase;
import rlVizLib.general.ParameterHolder;
import rlVizLib.general.hasVersionDetails;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvironmentMessageParser;
import rlVizLib.messaging.environment.EnvironmentMessages;
import rlVizLib.messaging.interfaces.HasAVisualizerInterface;
import rlglue.types.Action;
import rlglue.types.Observation;
import rlglue.types.Random_seed_key;
import rlglue.types.Reward_observation;
import rlglue.types.State_key;



/**
 * This is a skeleton environment project that can be used as a starting point
 * for other projects.
 * 
 * @author btanner
 */
public class SkeletonEnvironment extends EnvironmentBase implements HasAVisualizerInterface {


    private int numStates=10;
    
    
    //State variables
   private int currentState=0;
    
    public SkeletonEnvironment() {
        this(getDefaultParameters());
    }

    public SkeletonEnvironment(ParameterHolder p) {
        super();
        if (p != null) {
            if (!p.isNull()) {
                numStates=p.getIntegerParam("numStates");
                assert(numStates>0);
            }
        }
    }

    public static ParameterHolder getDefaultParameters() {
        ParameterHolder p = new ParameterHolder();
        rlVizLib.utilities.UtilityShop.setVersionDetails(p, new DetailsProvider());
        p.addIntegerParam("numStates",10);
        return p;
    }


    /*RL GLUE METHODS*/
    public String env_init() {
        currentState=0;

        String taskSpec = "2:e:1_[i]_";
        taskSpec += "[" + 0 + "," + (numStates-1) + "]_";
        taskSpec += ":1_[i]_[0,1]:[-1,1]";

        return taskSpec;
    }

    public Observation env_start() {
        currentState=0;

        return makeObservation();
    }

    public Reward_observation env_step(Action action) {
        int theAction=action.intArray[0];
        assert(theAction>=0);
        assert(theAction<2);
        
        if(theAction==0)currentState--;
        if(theAction==1)currentState++;
        
        if(currentState>=numStates)currentState=0;
        if(currentState<0)currentState=numStates-1;
        
        int terminalState=0;
        if(currentState==numStates-1)terminalState=1;

        return new Reward_observation(-1.0d,makeObservation(),terminalState);
    }

    public void env_cleanup() {
    }

    public Random_seed_key env_get_random_seed() {
        return null;
    }

    public State_key env_get_state() {
        return null;
    }

    public String env_message(String theMessage) {
        EnvironmentMessages theMessageObject;
        try {
            theMessageObject = EnvironmentMessageParser.parseMessage(theMessage);
        } catch (NotAnRLVizMessageException e) {
            System.err.println("Someone sent "+getClass()+" a message that wasn't RL-Viz compatible");
            return "I only respond to RL-Viz messages!";
        }

        if (theMessageObject.canHandleAutomatically(this)) {
            return theMessageObject.handleAutomatically(this);
        }
        
        System.err.println("We need some code written in Env Message for "+getClass()+" :: unknown request received: " + theMessage);
        Thread.dumpStack();
        return null;
    }

    public void env_set_random_seed(Random_seed_key key) {
    }

    public void env_set_state(State_key key) {
    }

    /*END OF RL_GLUE FUNCTIONS*/

    /*RL-VIZ Requirements*/
    @Override
    protected Observation makeObservation() {
        //1 Integer, 0 doubles
        Observation returnObs = new Observation(1, 0);
        returnObs.intArray[0] = currentState;

        return returnObs;
    }

    /*END OF RL-VIZ REQUIREMENTS*/

    
    public String getVisualizerClassName() {
        return "org.rlcommunity.environments.skeleton.visualizer.SkeletonVisualizer";
    }
}
/**
 * This is a little helper class that fills in the details about this environment
 * for the fancy print outs in the visualizer application.
 * @author btanner
 */
class DetailsProvider implements hasVersionDetails {

    public String getName() {
        return "Skeleton Environment 1.0 Beta";
    }

    public String getShortName() {
        return "Skeleton";
    }

    public String getAuthors() {
        return "Brian Tanner";
    }

    public String getInfoUrl() {
        return "http://code.google.com/p/rl-library/wiki/Skeleton";
    }

    public String getDescription() {
        return "RL-Library Sample Environment.";
    }
}

