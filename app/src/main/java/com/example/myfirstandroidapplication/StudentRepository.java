package com.example.myfirstandroidapplication;

import java.util.ArrayList;

public class StudentRepository {

    // Temporary in-memory storage. "static" means all screens share the same list.
    private static final ArrayList<Student> students = new ArrayList<>();

    static {
        // Pre-populate demo student matching initial portal data
        students.add(new Student(
                "sophia",
                "password123",
                "2024-1964571",
                "Terol",
                "Sophia Diane",
                "C",
                "BS Computer Science",
                "Digital Forensics",
                "4th Year"
        ));
    }

    public static void addStudent(Student student) {
        students.add(student);
    }

    public static boolean isUsernameTaken(String username) {
        for (Student s : students) {
            if (s.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isStudentNumberTaken(String studentNumber) {
        for (Student s : students) {
            if (s.getStudentNumber().equalsIgnoreCase(studentNumber)) {
                return true;
            }
        }
        return false;
    }

    // Returns the student with this username, or null if none exists.
    public static Student findByUsername(String username) {
        for (Student s : students) {
            if (s.getUsername().equalsIgnoreCase(username)) {
                return s;
            }
        }
        return null;
    }
}
