CLASSPATH = .:acm.jar
CFLAGS = -cp $(CLASSPATH)

JAVA_FILES = Breakout.java Brick.java Subtractor.java
CLASS_FILES = $(JAVA_FILES:.java=.class)


all: breakout

breakout: Breakout.class

Breakout.class: Breakout.java Brick.class Subtractor.java
	javac $(CFLAGS) Breakout.java


%.class: %.java
	javac $(CFLAGS) $<


submit: pset3.zip

pset3.zip: $(JAVA_FILES)
	zip $@ $^


clean:
	rm -f *.class
