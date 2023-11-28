# DISTRIBUTED-TASK-SCHEDULING-SYSTEM
The purpose of this project is to create a distributed task scheduling system
that allows multiple clients to connect to a server and submit tasks to be executed.
This system uses Java RMI to allow the client to submit tasks to the server
and retrieve the results. The server uses a thread pool to manage the worker threads 
and a queue to store the tasks that are waiting to be executed, then the tasks
are devided to multiple sub-tasks and distributed among other servers through sockets to be executed,
then the results are returned to the client when they are finished


Class Diagram of the Master Server:
![classDiagram](https://github.com/zack079/DISTRIBUTED-TASK-SCHEDULING-SYSTEM/assets/94627382/f3ed1bac-9351-49ca-9fd0-4011511231bd)

Architecture:
![architecture](https://github.com/zack079/DISTRIBUTED-TASK-SCHEDULING-SYSTEM/assets/94627382/36ccf331-b46a-4796-b5d3-7496b76c34ee)
