package arch1;

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

public class TestSonarReached {

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
		 * 1) segnale sonar che indica la presenza nell'area A
		 * 2) controllo accensione e gostraight del Robot
		 * 3) segnale sonar che indica il Robot davanti al sonar
		 * 4) controllo il Robot faccia l'operazione
		 * 5) controllo il Roboto continui dritto (gostraight)
		 * 6) segnale area B raggiunta
		 * 7) controllo il Robot sia fermo (stop)
		 * */
		try {
			assertTrue("execTest console", console != null );
			assertTrue("execTest sonar", sonar != null );
			assertTrue("execTest robot", robot != null);
			Thread.sleep(1000);
			// area A event
			sonar.emit("sonar", "p(1,58,59)");
			Thread.sleep(2000);
			// robot gostraight
			assertTrue("execTest", robot.solveGoal("value(state,X)").getVarValue("X").toString().equals("gostraight"));
			Thread.sleep(1000);
			// sonarreached event
			sonar.emit("sonar", "p(1,50,90)");
			Thread.sleep(2000);
			assertTrue("execTest", robot.solveGoal("value(state,X)").getVarValue("X").toString().equals("sonarreached"));
			Thread.sleep(4000); // attendo il termine dell'operazione
			// robot gostraight
			assertTrue("execTest", robot.solveGoal("value(state,X)").getVarValue("X").toString().equals("gostraight"));
			Thread.sleep(1000);
			// area B event
			sonar.emit("sonar", "p(1,158,162)");
			Thread.sleep(2000);
			assertTrue("execTest", robot.solveGoal("value(state,X)").getVarValue("X").toString().equals("stop"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail("execTest " + e.getMessage());
		}
	}
}