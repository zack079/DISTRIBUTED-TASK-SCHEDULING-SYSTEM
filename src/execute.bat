TIMEOUT 2
start cmd.exe /c "java Slave.GreenFilter 3030 & TIMEOUT 2"
start cmd.exe /c "java Slave.GreenFilter 3040 & TIMEOUT 2"
start cmd.exe /c "java Slave.GreenFilter 3050 & TIMEOUT 2"
start cmd.exe /c "java Slave.GreenFilter 3060 & TIMEOUT 2"
start cmd.exe /c "java Server.Main"
TIMEOUT 2
java Client.TaskSchedulerClient

