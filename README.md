# DISTRIBUTED-TASK-SCHEDULING-SYSTEM
The purpose of this project is to create a distributed task scheduling system
that allows multiple clients to connect to a server and submit tasks to be executed.
This system uses Java RMI to allow the client to submit tasks to the server
and retrieve the results. The server uses a thread pool to manage the worker threads 
and a queue to store the tasks that are waiting to be executed, then the tasks
are devided to multiple sub-tasks and distributed among other servers through sockets to be executed.
