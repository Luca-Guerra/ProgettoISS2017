package arch3;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unibo.ctxBase.MainCtxBase;
import it.unibo.qactors.QActorContext;
import it.unibo.qactors.QActorUtils;
import it.unibo.qactors.akka.QActor;

public class TestObstacle {
	private QActorContext ctx;
	private QActor console;
	private QActor sonar;
	private QActor robot;
	
	@Before
   	public void setUp() throws Exception  {
	   	ctx = MainCtxBase.initTheContext();
   		System.out.println("====== setUp  " + ctx );
		console	= QActorUtils.getQActor("console_ctrl");
		sonar   = QActorUtils.getQActor("sensorsonar_ctrl");
		robot 	= QActorUtils.getQActor("robot_ctrl");
	}
	
	@After
    public void terminate(){
	    System.out.println("====== terminate  " + ctx );
    }
	
	@Test
	public void execTest() {
		System.out.println("====== execTest ===============");
		/*
		 * PIANO DI TEST:
		 * NOME: Test Obstacle
		 * NOTE: Controlliamo se lo stop dell'utente ferma effettivamente il Robot
		 * - invio lo userstart per inizializzare il sistema
		 * - controllo accensione e gostraight del Robot
		 * - invio evento obstacle
		 * - controllo che il Robot sia fermo
		 * - controllo che la console abbia rilevato l'ostacolo dal Robot
		 * */
		try {
			assertTrue("execTest console", console != null );
			assertTrue("execTest sonar", sonar != null );
			assertTrue("execTest robot", robot != null);
			Thread.sleep(1000);
			// area A event
			console.emit("usercmd", "usercmd(robotgui(userstart))");
			Thread.sleep(2000);
			// robot gostraight
			assertTrue("execTest", robot.solveGoal("value(state,X)").getVarValue("X").toString().equals("gostraight"));
			Thread.sleep(2000);
			console.emit("obstacle","obstacle");
			Thread.sleep(2000);
			assertTrue("execTest", robot.solveGoal("value(state,X)").getVarValue("X").toString().equals("stop"));
			assertTrue("execTest", console.solveGoal("value(state,X)").getVarValue("X").toString().equals("obstacle"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail("execTest " + e.getMessage());
		}
	}

}
