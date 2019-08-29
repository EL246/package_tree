# package_tree

package_tree is a package indexer that keeps track of package dependencies.
The server opens a TCP socket and listens on port 8080. Clients can connect and specify
packages they would like to install, remove, or query. To maintain consistency,
packages cannot be indexed unless their dependencies are already indexed, and
packages cannot be removed if other packages depend on it. The server accepts
multiple clients at the same time. It can support over 100 connections at a time.

## Usage

Messages from clients follow this pattern:

```
<command>|<package>|<dependencies>\n
```

Where:
* `<command>` is mandatory, and is either `INDEX`, `REMOVE`, or `QUERY`
* `<package>` is mandatory, the name of the package referred to by the command, e.g. `mysql`, `openssl`, `pkg-config`, `postgresql`, etc.
* `<dependencies>` is optional, and if present it will be a comma-delimited list of packages that need to be present before `<package>` is installed. e.g. `cmake,sphinx-doc,xz`
* The message always ends with the character `\n`

Here are some sample messages:
```
INDEX|cloog|gmp,isl,pkg-config\n
INDEX|ceylon|\n
REMOVE|cloog|\n
QUERY|cloog|\n
```

Possible server response codes are `OK\n`, `FAIL\n`, or `ERROR\n`.

## Getting Started

To start the server, you can compile and run the source code. A faster
option is to run the provided jar file with the following command:

````
java -jar ./<path>/package_tree.jar
````
The jar is located
in out/artifacts/package_tree_jar/package_tree.jar.

A new jar file can also be compiled using the following maven command, if maven
is installed:

````
mvn package
````

Once the program is running you should see the following output in the terminal:

````
Starting server...
Listening on port 8080
````

## Running the tests

JUnit and PowerMock testing frameworks were used for this project. To run the tests simply run the commands below in the project directory.
Maven should be [installed](https://maven.apache.org/download.cgi) for this to work. 

````
mvn clean
mvn test
````

Additionally, the provided `./do-package-tree_platform` test was used as functional testing
during the development process and to verify the solution. The program has been
tested and runs successfully with a concurrency factor of over 100.


# Design Rationale

The system is composed of a server class which processes connections by creating a new thread for each client and adding it to a threadpool. 
The ClientHandler processes the messages sent by a client. The client messages are processed by the MessageHandler, which parses the
messages and returns a Command by passing the parsed message to a CommandCreator. Once the message is handled,
the Command.execute() method is called which updates the PackageIndexer and returns a boolean signifying whether the command succeeded.
PackageIndexer access is controlled by a read-write lock to ensure consistency and thread-safety. Finally, a response is returned to the client.

#### Server
The server opens a socket on port 8080 and accepts client connections. A new thread is created
for each client connection, and it is managed by a thread pool. A fixed thread pool was chosen. Although it is
not necessary for <100 threads, it saves resources in cases where the server becomes heavily loaded.
The thread pool limits the number of threads and submits excess requests to a queue. 
The pool size can be adjusted based on expected server demand.

#### ClientHandler
The ClientHandler reads messages received from the new connection socket on a dedicated thread. 
It calls the MessageHandler to handle each new line it receives. After handling the message, ClientHandler returns a response to the client.

#### MessageHandler
The MessageHandler calls the MessageParser to parse the message. If the parsing is unsuccessful, 
it catches the ParseException from the MessageParser and returns `ERROR`. If parsing is successful, 
a Command is created and executed, and an `OK` or `FAIL` response is returned.

#### MessageParser
The MessageParser parses the message string passed to it, and throws a ParseException if 
the message is invalid. It ensures the correct number of arguments and validates the package name. The first argument is 
checked against the CommandType enum to ensure its validity. If the message is formed correctly, the CommandCreator is called to create a Command 
of type IndexCommand, QueryCommand, or RemoveCommand.

#### Command and CommandCreator
The CommandCreator acts as a factory class to create the appropriate type of Command based
on the Command type (Index/Remove/Query). All of these classes (IndexCommand, RemoveCommand, 
and QueryCommand) are subclasses of Command and they all inherit the abstract execute() method.
This allows for future extensibility.
The execute() method is called by the MessageHandler and returns a boolean to signify whether the
command execution succeeded or failed. A lot of the logic of the execute() method currently
exists in the PackageIndexer. A possible optimization would be to move this logic into the
command class and only have basic insert/delete/get methods in the PackageIndexer.


#### PackageIndexer and Package
The PackageIndexer holds all of the indexed packages in a HashMap that maps between the name
of the package, and the Package object. The hashmap allows for constant time retrieval and insertion.
PackageIndexer maintains consistency of packages by enforcing the requirements that packages cannot be added if their dependencies do not exist
and cannot be removed if they are depended on. It provides the necessary checks prior
to adding or removing packages from the index.

The PackageIndexer was initially created with synchronized methods which lock the entire object. 
It was optimized to use a read-write lock instead. However, optimization is limited by the
additional overhead of the read-write lock. The Package class was initially created to hold
a Set of dependencies as well as children(packages that depend on it). This was optimized by using a counter int instead of a Set.
A ConcurrentHashMap was considered instead of a HashMap. However, reads from
a ConcurrentHashMap are not guaranteed to be accurate so it was not used.