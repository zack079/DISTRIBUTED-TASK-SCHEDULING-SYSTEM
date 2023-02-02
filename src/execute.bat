TIMEOUT 2
start cmd.exe /c "java Slave.GreenFilter & pause"
start cmd.exe /c "java Server.Main"
TIMEOUT 2
java Client.TaskSchedulerClient

