package arch1;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unibo.ctxBase.MainCtxBase;
import it.unibo.qactors.QActorContext;
import it.unibo.qactors.QActorUtils;
import it.unibo.qactors.akka.QActor;

public class TestUnderThreshold {
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
		 * NOME: UnderThreshould
		 * NOTE: Controlliamo se il viene stoppato se viene rilevato un valore sotto soglia
		 * - immetto il termine areaa 1, questo fa partire il sistema
		 * - controllo accensione e gostraight del Robot
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
			console.solveGoal("assign(area_a,1)");
			Thread.sleep(2000);
			// robot gostraight
			assertTrue("execTest", robot.solveGoal("value(state,X)").getVarValue("X").toString().equals("gostraight"));
			Thread.sleep(1000);
			// sonarreached event
			sonar.emit("sonar", "p(1,50,90)");
			Thread.sleep(1000);
			assertTrue("execTest", robot.solveGoal("value(state,X)").getVarValue("X").toString().equals("sonarreached"));
			Thread.sleep(4000); // attendo il termine dell'operazione
			// robot gostraight
			assertTrue("execTest", robot.solveGoal("value(state,X)").getVarValue("X").toString().equals("gostraight"));
			Thread.sleep(3000); // attendo il termine dell'operazione
			// sonarreached event
			sonar.emit("sonar", "p(3,50,90)");
			Thread.sleep(2000);
			assertTrue("execTest", console.solveGoal("value(state,X)").getVarValue("X").toString().equals("underthreshold"));
			Thread.sleep(4000);
			assertTrue("execTest", robot.solveGoal("value(state,X)").getVarValue("X").toString().equals("stop"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail("execTest " + e.getMessage());
		}
	}

}
