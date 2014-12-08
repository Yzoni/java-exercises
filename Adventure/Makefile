CLASSPATH = .:acm.jar:AdventureMagic.jar
CFLAGS = -cp $(CLASSPATH)

all: adventure

adventure: Adventure.class

Adventure.class: Adventure.java AdvRoom.class
	javac $(CFLAGS) Adventure.java

AdvRoom.class: AdvRoom.java AdvObject.class AdvMotionTableEntry.class
	javac $(CFLAGS) AdvRoom.java 

%.class: %.java
	javac $(CFLAGS) $<

clean:
	rm -f *.class

submit: pset5.zip

pset5.zip: *.java acm.jar AdventureMagic.jar *.txt Makefile
	zip $@ $^
