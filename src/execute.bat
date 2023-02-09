TIMEOUT 2
start cmd.exe /c "java Slave.GreenFilter 3030 & pause"
start cmd.exe /c "java Slave.GreenFilter 3040 & pause"
start cmd.exe /c "java Slave.GreenFilter 3050 & pause"
start cmd.exe /c "java Slave.GreenFilter 3060 & pause"

start cmd.exe /c "java Slave.ConvolutionFilter 4030 & pause"
start cmd.exe /c "java Slave.ConvolutionFilter 4040 & pause"
start cmd.exe /c "java Slave.ConvolutionFilter 4050 & pause"
start cmd.exe /c "java Slave.ConvolutionFilter 4060 & pause"


start cmd.exe /c "java Server.Main"
TIMEOUT 2
java Client.TaskSchedulerClient

