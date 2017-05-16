%====================================================================================
% Context ctxBase  SYSTEM-configuration: file it.unibo.ctxBase.a1.pl 
%====================================================================================
context(ctxbase, "localhost",  "TCP", "8079" ).  		 
%%% -------------------------------------------
qactor( robot , ctxbase, "it.unibo.robot.MsgHandle_Robot"   ). %%store msgs 
qactor( robot_ctrl , ctxbase, "it.unibo.robot.Robot"   ). %%control-driven 
qactor( console , ctxbase, "it.unibo.console.MsgHandle_Console"   ). %%store msgs 
qactor( console_ctrl , ctxbase, "it.unibo.console.Console"   ). %%control-driven 
qactor( sensorsonar , ctxbase, "it.unibo.sensorsonar.MsgHandle_Sensorsonar"   ). %%store msgs 
qactor( sensorsonar_ctrl , ctxbase, "it.unibo.sensorsonar.Sensorsonar"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evh_sonar,ctxbase,"it.unibo.ctxBase.Evh_sonar","sonar").  
eventhandler(evh_user,ctxbase,"it.unibo.ctxBase.Evh_user","usercmd").  
%%% -------------------------------------------

