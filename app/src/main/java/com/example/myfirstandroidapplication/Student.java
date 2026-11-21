package com.example.myfirstandroidapplication;

public class Student {

    private final String username;
    private final String password;
    private final String studentNumber;
    private final String lastName;
    private final String firstName;
    private final String middleName;
    private final String program;
    private final String specialization;
    private final String yearLevel;

    public Student(String username, String password, String studentNumber,
                   String lastName, String firstName, String middleName,
                   String program, String specialization, String yearLevel) {
        this.username = username;
        this.password = password;
        this.studentNumber = studentNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.program = program;
        this.specialization = specialization;
        this.yearLevel = yearLevel;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getStudentNumber() { return studentNumber; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getProgram() { return program; }
    public String getSpecialization() { return specialization; }
    public String getYearLevel() { return yearLevel; }
}
