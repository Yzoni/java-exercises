CLASSPATH = karel.jar
CFLAGS = -cp $(CLASSPATH)

JAVA_FILES = CheckerboardKarel.java CollectNewspaperKarel.java MazeSolvingKarel.java \
             MidpointFindingKarel.java StoneMasonKarel.java
CLASS_FILES = $(JAVA_FILES:.java=.class)


all: checkerboard collectnewspaper mazesolving midpointfinding stonemason

checkerboard: CheckerboardKarel.class

collectnewspaper: CollectNewspaperKarel.class

mazesolving: MazeSolvingKarel.class

midpointfinding: MidpointFindingKarel.class

stonemason: StoneMasonKarel.class

%.class: %.java
	javac $(CFLAGS) $<


submit: pset1.zip

pset1.zip: $(JAVA_FILES)
	zip $@ $^


clean:
	rm -f *.class
