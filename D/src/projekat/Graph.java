package projekat;

import java.io.*;
import java.util.*;

public class Graph { 
    
    private List<Program> programs;
    private ProgramConn[][] adjMatrix;

    public Graph() {
        super();
        adjMatrix = new ProgramConn[0][0];
        programs = new ArrayList<>();
    }


    public void addProgram(Program prog) { 
        if (programs.contains(prog)) {
            System.out.println("Program : " + prog.getName() + " already exists");
            return;
        }

        programs.add(prog);


        ProgramConn[][] newMatrix = new ProgramConn[programs.size()][programs.size()];

        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix.length; j++) {
                newMatrix[i][j] = adjMatrix[i][j];
            }
        }

        // Zamjenjujemo staru matricu novom
        adjMatrix = newMatrix;
        System.out.println("Succesfully added the program");
        lengthenMatrix(prog);
    }

    private void lengthenMatrix(Program p) { //h

        int index = programs.indexOf(p);

        for (int i = 0; i < programs.size(); i++) {
            if (i == index) {
                adjMatrix[i][i] = new ProgramConn(0); 
                continue;
            }

            int sameSubjects = countSimilarSubjects(p.getSubjects(), programs.get(i).getSubjects());

            ProgramConn pc = new ProgramConn(sameSubjects);
            adjMatrix[index][i] = pc;
            adjMatrix[i][index] = pc;
        }
    }



    private int countSimilarSubjects(List<String> subjects1, List<String> subjects2) {//h
        if (subjects1 == null || subjects2 == null) {
            System.out.println("You must add subjects for this program!");
            return 0;
        }
        int commonSubjects = 0;
        for (String sub1 : subjects1) {
            if (subjects2.contains(sub1)) {
                commonSubjects++;
            }
        }
        return commonSubjects;
    }



    public void removeProgram(String program) { // metoda a
        Program remove = new Program(program);
        int removeIndex = programs.indexOf(remove);

        if (removeIndex == -1) {
            System.out.println("Program called (" + program + ") doesnt exist");
            return;
        }

        ProgramConn[][] newMatrix = new ProgramConn[programs.size() - 1][programs.size() - 1];

        for (int i = 0, row = 0; i < adjMatrix.length; i++) {
            if (i == removeIndex) {
                continue;
            }
            for (int j = 0, col = 0; j < adjMatrix[i].length; j++) {
                if (j == removeIndex) {
                    continue;
                }
                newMatrix[row][col] = adjMatrix[i][j];
                col++;
            }
            row++;
        }

        adjMatrix = newMatrix;
        System.out.println("Program ("+programs.get(removeIndex).getName()+") succesfully deleted!");
        programs.remove(removeIndex);
    }






    public void importFromFile() { 
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the file name to import from: ");
        String fileName = sc.nextLine();
        File file = new File(fileName);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] programDetails = line.split(",");
                String name = programDetails[0];
                List<String> subjects = new ArrayList<>();
                for (int i = 1; i < programDetails.length; i++) {
                    String subject = programDetails[i].trim();
                    if (!subject.isEmpty() && !subjects.contains(subject)) {
                        subjects.add(subject.toLowerCase());
                    }
                }
                if (subjects.size() < 3) {
                    System.out.println("Program " + name + " has less than 3 subjects, it will not be added to the graph.");
                    continue;
                }
                Program prog = new Program(name, subjects);
                addProgram(prog);
            }
            System.out.println("Import successful!");
        } catch (FileNotFoundException e) {
            System.out.println("File not found, please try again.");
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file, please try again.");
        }
    }





    public void showMostSimiliarPrograms() { //metoda B
        if (programs.size() == 0){
            System.out.println("No values in graph");
            return;
        }

        int maxSubjects = 0;
        StringBuilder sb = new StringBuilder("***** Most Similar Programs *****\n");
        for (int i = 0; i < adjMatrix.length; i++) {
            Program prog = programs.get(i);
            List<Program> similarPrograms = new ArrayList<>();
            for (int j = 0; j < adjMatrix.length; j++) {
                if (i != j) {
                    int numOfSubjects = adjMatrix[i][j].getNoOfSubjects();
                    if (numOfSubjects > maxSubjects) {
                        maxSubjects = numOfSubjects;
                        similarPrograms.clear();
                        Program progJ = programs.get(j);
                        similarPrograms.add(progJ);
                    } else if (numOfSubjects == maxSubjects) {
                        Program progJ = programs.get(j);
                        similarPrograms.add(progJ);
                    }
                }
            }
            sb.append(prog.getName() + " has the most number of similar subjects with [ ");
            if (maxSubjects > 0) {
                for (Program similarProgram : similarPrograms) {
                    sb.append("("+similarProgram.getName() + "),");
                }
                sb.append("\nNumber of similar programs: " + similarPrograms.size());
            } else {
                sb.append("->(0)(No similar programs found)");
            }
            sb.append("\n--------------\n");
            maxSubjects = 0;
        }
        System.out.println(sb.toString());
    }





    public void graphDisplay() {
        StringBuilder sb = new StringBuilder();
        int columnWidth = 15; // sirina izmedju kolone
        sb.append(String.format("%" + columnWidth + "s", "")); // unosi prazno polje za prvu kolonu
        for (Program prog : programs) {
            sb.append(String.format("%" + columnWidth + "s", prog.getName()));
        }
        sb.append("\n");
        for (int i = 0; i < adjMatrix.length; i++) {
            sb.append(String.format("%" + columnWidth + "s", programs.get(i).getName()));
            for (int j = 0; j < adjMatrix[i].length; j++) {
                if (i == j) {
                    sb.append(String.format("%" + columnWidth + "s", "x"));
                } else if (adjMatrix[i][j] != null) {
                    sb.append(String.format("%" + columnWidth + "s", adjMatrix[i][j].getNoOfSubjects()));
                } else {
                    sb.append(String.format("%" + columnWidth + "s", "-"));
                }
            }
            sb.append("\n");
        }
        if (adjMatrix.length == 0) {
            sb.append("************Prazan Graf***********");
            for (int i = 0; i < 5; i++) {
                sb.append("\n");
            }
        }
        System.out.println(sb.toString());
    }





    public void displaySubjectsOfProgram(String programName) {//bonus
        Program program = findProgram(programName);
        if (program == null) {
            System.out.println("Program not found.");
            return;
        }
        System.out.println("Subjects for program (" + programName + "):");
        for (String subject : program.getSubjects()) {
            System.out.println(subject);
        }
    }

    private Program findProgram(String name) {
        for (Program program : programs) {
            if (program.getName().equals(name)) {
                return program;
            }
        }
        return null;
    }




    private boolean checkIfProgramExists(String newName) {//h
        for (Program p : programs) {
            if (p.getName().equals(newName)) {
                return true;
            }
        }
        return false;
    }

    public void modifyProgramName(String currentName, String newName) {//metoda a
        if (checkIfProgramExists(newName)) {
            System.out.println("Program with name " + newName + " already exists in the graph. Name change failed.");
            return;
        }

        int index = -1;
        for (int i = 0; i < programs.size(); i++) {
            if (programs.get(i).getName().equals(currentName)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Program with name " + currentName + " doesn't exist in the graph. Name change failed.");
            return;
        }

        programs.get(index).setName(newName);
        System.out.println("Program name successfully changed from " + currentName + " to " + newName);
    }





    public void moreThanHalf(String name) {//metoda c
        Program targetProgram = null;
        for (Program program : programs) {
            if (program.getName().equals(name)) {
                targetProgram = program;
                break;
            }
        }
        if (targetProgram == null) {
            System.out.println("Program with name '" + name + "' not found");
            return;
        }
        List<String> commonPrograms = new ArrayList<>();
        List<String> targetProgramSubjects = targetProgram.getSubjects();
        for (Program program : programs) {
            if (program == targetProgram){ 	
            	continue;
            }//
            List<String> programSubjects = program.getSubjects();
            int commonSubjects = 0;
            for (String subject : targetProgramSubjects) {
                if (programSubjects.contains(subject)) {
                    commonSubjects++;
                }
            }
            if (commonSubjects > (targetProgramSubjects.size() / 2)) {
                commonPrograms.add(program.getName());
            }
        }
        if (commonPrograms.size() > 0) {
            int counter = 0;
            System.out.println("Programs with more than half common subjects with " + targetProgram.getName() + ":");
            for (String commonProgram : commonPrograms) {
                System.out.println(commonProgram);
                counter++;
            }
            System.out.println("No. programs:"+counter);
        } else {
            System.out.println("No programs with more than half common subjects with " + targetProgram.getName());
        }
    }




    public void mostCommonSubjects() { //bonus metoda
        List<String> allSubjects = new ArrayList<>();
        for (Program program : programs) {
            List<String> programSubjects = program.getSubjects();
            for (String subject : programSubjects) {
                if (!allSubjects.contains(subject)) {
                    allSubjects.add(subject);
                }
            }
        }

        if (allSubjects.isEmpty()) {
            System.out.println("The programs have no subjects in common.");
            return;
        }
        if (countIfNoSubjectsInCommon() == 0){
            System.out.println("No subjects in common beetwen programs");
            return;
        }

        int[] subjectCounts = new int[allSubjects.size()];
        for (Program program : programs) {
            List<String> programSubjects = program.getSubjects();
            for (int i = 0; i < allSubjects.size(); i++) {
                if (programSubjects.contains(allSubjects.get(i))) {
                    subjectCounts[i]++;
                }
            }
        }

        List<String> mostCommonSubjects = new ArrayList<>();
        int maxCount = 0;
        
        for (int i = 0; i < allSubjects.size(); i++) {
            if (subjectCounts[i] > maxCount) {
            	
                maxCount = subjectCounts[i];
                mostCommonSubjects.clear();
                mostCommonSubjects.add(allSubjects.get(i));
                
            } else if (subjectCounts[i] == maxCount) {
                mostCommonSubjects.add(allSubjects.get(i));
            }
        }

        System.out.println("Most common subjects: " + mostCommonSubjects);
    }


    private int countIfNoSubjectsInCommon(){//helper
        int counter  =0 ;
        for (int i = 0; i < adjMatrix.length; i++) {
            if (adjMatrix[0][i].getNoOfSubjects() != 0){
                counter++;
            }
        }
        return counter;
    }


    public ProgramConn[][] getAdjMatrix() {
        return adjMatrix;
    }

    public void setAdjMatrix(ProgramConn[][] adjMatrix) {
        this.adjMatrix = adjMatrix;
    }

    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }
}
