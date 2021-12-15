JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
		  Main.java \
		  Rook.java \
		  Bishop.java\
		  Knight.java\
		  Pawn.java\
		  King.java\
		  Queen.java\
		  Board.java

default: classes

build: default

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
	
run: build
	java Main
