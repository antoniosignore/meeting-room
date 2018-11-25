# AKQA Test 

## Installation

This project requires JDK 8 and maven for building and testing it. 

Install Java on linux using this command:

    sudo apt-get install openjdk-8-jdk
    
## Install maven    
    
    sudo apt-get install maven

## Build
    
    mvn clean package

## Design

The application is composed of 2 components :

    1. MeetingRequestCollector: 
        a. single threaded Observable processes the meeting file as a stream of lines, cleans them, 
        b. builds the MeetingRequest objects added to a TreeMap in order
           to order them by request date.
        c. drops the meetings requests that fall outside of the office hours
        d. notify the Observers    
            
    2. MeetingScheduler Observer/Observable class which processes the MeetingRequests and 
        a. check for overlaps of meetings
        b. create a Calendar and add to meetings to it
        c. notifies the Observers (Users...not implemented in this exercise)


The MeetingCollector uses a TreeSet to store the meeting requests that are compared by 
request date

The MeetingScheduler uses a matrix defined as a TreeSet of TreeSet where each row has as a key
the meeting day and as columns just the meetings scheduled for that row day.

This way each time we need to check for overlaps we do not need scan the whole set of meetings but
just the meetings for the day. 

## Adopted patterns
Factory, Builder, Observer Pattern. 

## Reference Implementation
A reference implementation with a command line interface (CLI) is provided .

### Running the Command Line Client
The CLI can be started by typing at terminal executing a self contained jar executable (built by maven):
 
    java -jar target/meeting-0.1.0.jar
    
At the prompt digit:

    src/test/resources/meetings.txt

If successfull you should read at terminal

Enter meetings reservation filename : src/test/resources/meetings.txt

~~~
Meeting requested by user :EMP002
	Date :2011-03-21
	Time :09:00
	Duration :2
Meeting requested by user :EMP003
	Date :2011-03-22
	Time :14:00
	Duration :2
Meeting requested by user :EMP004
	Date :2011-03-22
	Time :16:00
	Duration :1
~~~

which matches the expected results as per test requirement

### unit test code coverage

Coverage is execute by Cobertura. The code coverage is not too bad.

### Maven site (summary information)
Execute the :

    mvn site
    
The resulting web site is under target/site/index.html

