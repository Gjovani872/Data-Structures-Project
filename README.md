# Data-Structures-Project
This project consists of classes: Program, ProgramConn, Graph and Main. The application does saving the program in the matrix, and this project represents an undirected weight graph which is performed using a matrix

A graph representing the similarity between study programs. The name and sequence are known for each program
subject. For each link, the number of identical items is known.
a) Adding, modifying and deleting data (analyze and implement all possibilities)
b) A method that displays pairs of the most similar programs in a graph (for each program, print one
which is most similar to it).
c) The method that receives the name of the study program as an input argument and displays the total number
programs where the number of the same subjects is more than half.



3. Description of the application
In this application, methods have been created for: entering programs with and without files, deleting
programs, methods for displaying the matrix, methods for displaying the most similar programs, methods for
item view for a program, method for modifying the name of a program, method
which calculates the number of subjects with other programs, and which will find how many programs there are
similar to him if the number of subjects between them is greater than the average length of the program
we enter the name. We have a method that displays the items that are most used with
sides of the program.


3.1 Graph implementation
The implementation of the graph is done in the Graph class, the graph is undirected and weighted, which is done via
matrices. Within the matrix, weights are placed between each program based on the same
subject.
