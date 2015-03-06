JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	    $(JC) $(JFLAGS) $*.java

CLASSES = ParseSCV.java window.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	     $(RM) *.class

run:
		java ParseSCV
