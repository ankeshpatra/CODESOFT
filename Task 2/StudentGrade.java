import java.util.ArrayList;
import java.util.Scanner;

public class StudentGrade {
    public static void main(String[] args) {
        int n, totalMarks = 0;
        double averagepercent = 0.0;
        ArrayList<Integer> marks = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of students: ");
        n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            int mark;
            while (true) {
                System.out.print("Enter the marks of the subject " + (i + 1) + " (0 to 100): ");
                mark = sc.nextInt();
                if (mark >= 0 && mark <= 100) {
                    marks.add(mark);
                    break;
                } else {
                    System.out.println("Invalid input. Please enter marks between 0 and 100.");
                }
            }
        }

        for (int mark : marks) {
            totalMarks += mark;
        }

        averagepercent = (double) totalMarks / n;

        char grade;
        if (averagepercent >= 90 && averagepercent <= 100) {
            grade = 'A';
        } else if (averagepercent >= 70) {
            grade = 'B';
        } else if (averagepercent >= 50) {
            grade = 'C';
        } else if (averagepercent >= 40) {
            grade = 'D';
        } else {
            grade = 'F';
        }

        System.out.println();
        System.out.println("Total marks of all subjects are: " + totalMarks);
        System.out.printf("Average Percent is: %.2f%%\n", averagepercent);
        System.out.println("Overall grade: " + grade);
        System.out.println();

        sc.close();
    }
}