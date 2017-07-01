/* Generated by AN DISI Unibo */ 
package it.unibo.robot;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import it.unibo.iot.models.sensorData.ISensorData;
import it.unibo.iot.sensors.ISensorObserver;
import it.unibo.is.interfaces.IOutputView;
import it.unibo.qactors.QActorUtils;
import it.unibo.qactors.akka.QActor;
import it.unibo.system.SituatedPlainObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class SensorObserver<T extends ISensorData> extends SituatedPlainObject implements ISensorObserver<T>{
protected QActor actor;
public int counterForObstacle=0;
public int lastDistance=0;
public Timer timer;
	public SensorObserver(QActor actor, IOutputView outView) { 
		super(outView);
		this.actor = actor;		
		timer= new Timer(3000,new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(counterForObstacle>10){
					println("DEBUG OBSTACLE");
					try {
						QActorUtils.raiseEvent(actor.getQActorContext(),"sensor", "obstacle", "obstacle(" + lastDistance + ")" );
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				counterForObstacle=0;
			}
		});
		timer.setRepeats(true);
		timer.start();
 	}
	@Override
 	/*
  		SENSORDATA raw
  			{"p":"b","t":"l","d":{"detection":0}},"tm":16124773}
 			{"p":"t","t":"m","d":{"raw3axes":{"x":308, "y":-649, "z":120}}}
 			{"p":"b","t":"l","d":{"detection":1}},"tm":520200}
 		SENSORDATA high level
 			distance( VALUE, DIRECTION, POSITION )
 			magnetometer(x(VX),y(VY),z(VZ),POSITION)
  	*/
	public void notify(T data) {
 		//println("SensorObserver: " + data.getClass().getName() );
 		//println("SensorObserver: " + data.getDefStringRep() );
   		try {
			handleData(data);
		} catch (Exception e) {
 			e.printStackTrace();
		}
	}
	/*
	* -----------------------------------------------
	* DATA HANDLING APPLICATION LOGIC
	* -----------------------------------------------
	*/
	protected void handleData(T data) throws Exception{
		Struct t = (Struct) Term.createTerm(data.getDefStringRep());
 		//QActorUtils.raiseEvent(actor.getQActorContext(),"sensor", "sensordata", "sensordata("+data.getDefStringRep()+")" );
		println("SensorObserver data=" + data.getDefStringRep() +"  "+counterForObstacle);
		if( t.getName().equals("distance")){
			int d = Integer.parseInt(t.getArg(0).toString());
			//if( d > 5 && d < 120 ) println("SensorObserver: " + data.getDefStringRep() + " json:" + data.getJsonStringRep());
			if( d < 10 ){
				counterForObstacle++;
				lastDistance=d;
				/*if(counterForObstacle>10){
					println("DEBUG OBSTACLE");
					QActorUtils.raiseEvent(actor.getQActorContext(),"sensor", "obstacle", "obstacle("+d+")" );
					counterForObstacle=0;
				}*/
 			}
		}
	}	
	/* 
	 * Delegate to sensor/1 of sensorTheory the policy to handle the sensor DATA
 	 * including raising of events like sensordata:sensordata(DATA)
	 * WARNING: If the prolog machine is already running, the following solveGoal blocks
	 * This happens for example when we use a robot interpreter based on a talkTehory
	*/
	protected void handleDataViaProlog(T data){
		try {
			String goal = "sensor( DATA )".replace("DATA", data.getDefStringRep());
 			QActorUtils.solveGoal( actor.getPrologEngine(), goal  );
		} catch (Exception e) {
 			e.printStackTrace();
		}		
	}
}