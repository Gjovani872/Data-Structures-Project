package projekat;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Graph graph = new Graph();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Program");//metoda a
            System.out.println("2. Remove Program");//metoda a
            System.out.println("3. Import from file");
            System.out.println("4. Display Most Similar Programs");//metoda b
            System.out.println("5. Display the graph");//bonus
            System.out.println("6. Display subjects of a certain program");//bonus
            System.out.println("7. Modify a program name");//metoda a
            System.out.println("8. Look up the above average for a certain program with subjects from other programs");//metoda C
            System.out.println("9.Find which subjects are most common beetwen all programs");//bonus
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");
            System.out.println("---------------------------------------------------------------------------------------");
            try {
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1) {

                    System.out.println("Enter the name of the program:");
                    String name = sc.nextLine();

                    System.out.println("Enter the subjects of the program separated by comma (,) (also you need 3 or more subjects for program to be inserted):");
                    String[] subjectsArray = sc.nextLine().split(",");
                    List<String> subjects = Arrays.asList(subjectsArray);

                    for (int i = 0; i < subjects.size(); i++) {

                        subjects.set(i, subjects.get(i).toLowerCase().trim());
                    }

                    Set<String> subjectSet = new HashSet<>(subjects); //da se ne unesu duplikati

                    subjects = new ArrayList<>(subjectSet);

                    if (subjects.size() < 3) {
                        System.out.println("A program must have 3 or more subjects. Please enter a valid program.");
                    } else {
                        Program prog = new Program(name, subjects);
                        graph.addProgram(prog);
                    }

                    System.out.println("\n");


                } else if (choice == 2) {
                    System.out.print("Enter program name: ");
                    String programName = sc.nextLine();
                    graph.removeProgram(programName);
                    System.out.println("\n");

                } else if (choice == 3) {
                    System.out.println("Importing files");
                    graph.importFromFile();
                    System.out.println("\n");

                } else if (choice == 4) {
                    graph.showMostSimiliarPrograms();
                    System.out.println("\n");

                } else if (choice == 5) {
                    graph.graphDisplay();
                    System.out.println("\n");

                } else if (choice == 6) {
                    System.out.println("Which program are you looking for");
                    String name = sc.nextLine();
                    graph.displaySubjectsOfProgram(name);
                    System.out.println("--------------------");
                    System.out.println("\n");

                }else if (choice == 7){
                    System.out.println("Type in the name of the program");
                    String name = sc.nextLine();
                    System.out.println("Type in the new name of the program");
                    String newName = sc.nextLine();
                    graph.modifyProgramName(name,newName);
                    System.out.println("\n");
                }else if (choice == 8){
                    System.out.println("Enter the name of the program you wish to look up");
                    String name = sc.nextLine();
                    graph.moreThanHalf(name);
                    System.out.println("\n");
                }else if (choice == 9){
                    graph.mostCommonSubjects();
                    System.out.println("\n");
                }
                else if (choice == 10) {
                    System.out.println("***************MAIN MENU TERMINATED!***************");
                    System.out.println("\n");
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }

        }
        sc.close();
    }

    }


