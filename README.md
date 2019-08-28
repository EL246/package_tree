# package_tree

package_tree is a package indexer that keeps track of package dependencies.
The server opens a TCP connection on port 8080. Clients can connect and specify
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

To start the server, you can either compile and run the source code. A faster
option is to run the provided jar file with the following command:

````
java -jar ./<path>/package_tree.jar
````

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

To run the tests simply run the following commands in the project directory. Maven
should be [installed](https://maven.apache.org/download.cgi) for this to work.

````
mvn clean
mvn test
````

The provided `./do-package-tree_platform` test was also used as functional testing
during the development process and to verify the solution. The program has been
tested and runs successfully with a concurrency factor of over 100.