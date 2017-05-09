%====================================================================================
% Context ctxBase  SYSTEM-configuration: file it.unibo.ctxBase.a0.pl 
%====================================================================================
context(ctxbase, "localhost",  "TCP", "8079" ).  		 
%%% -------------------------------------------
qactor( sensorsonar , ctxbase, "it.unibo.sensorsonar.MsgHandle_Sensorsonar"   ). %%store msgs 
qactor( sensorsonar_ctrl , ctxbase, "it.unibo.sensorsonar.Sensorsonar"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

