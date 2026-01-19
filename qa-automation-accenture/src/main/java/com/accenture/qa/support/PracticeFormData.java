package com.accenture.qa.support;

public class PracticeFormData {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String gender;
    private final String mobile;
    private final String dob; // "10 Oct 1995"
    private final String subject;
    private final String hobby;
    private final String uploadResourcePath; // "files/upload.txt"
    private final String address;
    private final String state;
    private final String city;

    public PracticeFormData(
            String firstName,
            String lastName,
            String email,
            String gender,
            String mobile,
            String dob,
            String subject,
            String hobby,
            String uploadResourcePath,
            String address,
            String state,
            String city
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.mobile = mobile;
        this.dob = dob;
        this.subject = subject;
        this.hobby = hobby;
        this.uploadResourcePath = uploadResourcePath;
        this.address = address;
        this.state = state;
        this.city = city;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getGender() { return gender; }
    public String getMobile() { return mobile; }
    public String getDob() { return dob; }
    public String getSubject() { return subject; }
    public String getHobby() { return hobby; }
    public String getUploadResourcePath() { return uploadResourcePath; }
    public String getAddress() { return address; }
    public String getState() { return state; }
    public String getCity() { return city; }
}
