README file for secureGroupChat


Running the GUI client from the bin directory

Since this is a graphical user interface using Java Swing, you'll need to run
this from some sort of windowing environment (Xwindows, Windows, Mac, a Linux
workstation in the computer lab, etc) -- it doesn't run from a bash command
line unless you have up an xserver redirect of some sort. Failure to have an
adequate display configured will result in various AWT exceptions.

Compiling the code
cd secureGroupChat/groupChat/src
# compile the gui
javac -cp. GUI
# compile the server
javac -cp. groupChatServer
# move the files to the ../bin directory
mv *.class ../bin




From a command line in the secureGroupChat/groupChat/bin directory enter the
following command to run the client. Be aware that it was not integrated into
the chatserver, so beyond a pretty window that accepts text, it does nothing
of value yet.

java -cp . GUI


Running the server

java -cp . groupChatServer


As with the client because certain pieces were not finished or integrated by
the submission deadline, this program does very little of value.

FInally: all of the code is available on github at

https://github.com/gaver10/secureGroupChat

The design document was produced on GoogleDocs and then converted to a PDF
using Google Chrome's print to PDF facility which allows you to save a print
out as a PDF.

