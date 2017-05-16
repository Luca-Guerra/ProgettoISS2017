/* Generated by AN DISI Unibo */ 
/*
This code is generated only ONCE
*/
package it.unibo.console;
import alice.tuprolog.NoSolutionException;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.QActorContext;

public class Console extends AbstractConsole { 
	public Console(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
		super(actorId, myCtx, outEnvView);
	}
	
	public int memoSonarEvent(int ID, int D, int A){
		outEnvView.addOutput("memo");
		solveGoal("assign("+ID+","+D+")");
		solveGoal("assign(dist,"+D+")");
		solveGoal("assign(angle,"+A+")");
		return 1;
	}
	
	public int inAreaA(){
		int area_a_dist;
		int area_a_angle;
		int dist; 
		int angle;
		try {
			dist = getFromKB("dist");
			angle = getFromKB("angle");
			area_a_dist = getFromKB("area_a_dist");
			area_a_angle = getFromKB("area_a_angle");
			return ((dist < area_a_dist) && (angle < area_a_angle)) ? 1 : 0;
		} catch (NoSolutionException e) {
			return 0;
		}
	}
	
	public int inAreaB(){
		int area_b_dist;
		int area_b_angle;
		int dist; 
		int angle;
		try {
			dist = getFromKB("dist");
			angle = getFromKB("angle");
			area_b_dist = getFromKB("area_b_dist");
			area_b_angle = getFromKB("area_b_angle");
			return ((dist > area_b_dist) && (angle > area_b_angle)) ? 1 : 0;
		} catch (NoSolutionException e) {
			return 0;
		}
	}
	
	public int sonarReached(){
		int angle;
		try {
			angle = getFromKB("angle");
			return (angle > 85 && angle < 95) ? 1 : 0;
		} catch (NoSolutionException e) {
			outEnvView.addOutput("eccezione:" + e.getMessage());
		}
		return 0;
	}
	
	public int expLessThanDMIN(){
		int dmin;
		int nextsonar;
		int nsonars;
		int sum = 0;
		// vado a leggere i vari valori e calcolo la soglia
		try {
			
			dmin = getFromKB("dmin");
			nextsonar = getFromKB("nextsonar");
			nsonars = getFromKB("nsonars");
			for(int i=nextsonar;i<=nsonars;i++){
				sum+=getFromKB(i+"");
			}
			double exp_res = sum/(nsonars-nextsonar+1);
			outEnvView.addOutput("exp: " +  exp_res);
			return exp_res < dmin ? 1 : 0;
		} catch (NoSolutionException e) {
			return 0;
		} 
	}
	
	private int getFromKB(String variable) throws NoSolutionException{
		return Integer.parseInt(solveGoal("value(" + variable + ",X)").getVarValue("X").toString());
	}

}
