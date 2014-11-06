CLASSPATH = acm.jar
CFLAGS = -cp $(CLASSPATH)

JAVA_FILES = Pyramid.java Rainbow.java GraphicsHierarchy.java \
             Quadratic.java FindRange.java Hailstone.java
CLASS_FILES = $(JAVA_FILES:.java=.class)


all: pyramid rainbow hierarchy quadratic range hailstone

pyramid: Pyramid.class

rainbow: Rainbow.class

hierarchy: GraphicsHierarchy.class

quadratic: Quadratic.class

range: FindRange.class

hailstone: Hailstone.class

%.class: %.java
	javac $(CFLAGS) $<


submit: pset2.zip

pset2.zip: $(JAVA_FILES)
	zip $@ $^


clean:
	rm -f *.class
