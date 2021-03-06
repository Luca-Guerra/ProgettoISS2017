package arch3;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import alice.tuprolog.SolveInfo;
import it.unibo.ctxBase.MainCtxBase;
import it.unibo.qactors.QActorContext;
import it.unibo.qactors.QActorUtils;
import it.unibo.qactors.akka.QActor;

import org.junit.Test;

public class TestPhotoWall {

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
		 * NOME: SonarReached
		 * NOTE: Controlliamo se il Robot una volta raggiunto un sonar 
		 *       reagisce correttamente al segnale dalla console
		 * - invio lo userstart per inizializzare il sistema
		 * - controllo accensione e gostraight del Robot
		 * - controllo il valore di sonarreach sia a 0
		 * - segnale sonar che indica il Robot davanti al sonar
		 * - controllo il Robot faccia l'operazione
		 * - controllo il Roboto continui dritto (gostraight)
		 * - controllo il valore di sonarreach sia a 1
		 * - controllo il Robot sia fermo (stop)
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
			assertTrue("execTest", console.solveGoal("value(sonarreached,X)").getVarValue("X").toString().equals("0"));
			Thread.sleep(1000);
			// sonarreached event
			sonar.emit("sonar", "p(1,50,90)");
			Thread.sleep(1000);
			assertTrue("execTest", robot.solveGoal("value(state,X)").getVarValue("X").toString().equals("sonarreached"));
			Thread.sleep(1000);
			assertTrue("execTest", console.solveGoal("value(state,X)").getVarValue("X").toString().equals("photofromrobot"));
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			fail("execTest " + e.getStackTrace());
		}
	}
}