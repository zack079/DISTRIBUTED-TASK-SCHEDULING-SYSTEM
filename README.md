# DISTRIBUTED-TASK-SCHEDULING-SYSTEM
The purpose of this project is to create a distributed task scheduling system
that allows multiple clients to connect to a server and submit tasks to be executed.
This system uses Java RMI to allow the client to submit tasks to the server
and retrieve the results. The server uses a thread pool to manage the worker threads 
and a queue to store the tasks that are waiting to be executed, then the tasks
are devided to multiple sub-tasks and distributed among other servers through sockets to be executed,
then the results are returned to the client when they are finished


Class Diagram of the Master Server:
![classDiagram](https://github.com/zack079/DISTRIBUTED-TASK-SCHEDULING-SYSTEM/assets/94627382/56c6ab48-4db5-4ad4-811e-cb1faee2ba4b)

Architecture:
![architecture](https://github.com/zack079/DISTRIBUTED-TASK-SCHEDULING-SYSTEM/assets/94627382/2a9edc07-25ac-4e36-829a-348c255d545a)
