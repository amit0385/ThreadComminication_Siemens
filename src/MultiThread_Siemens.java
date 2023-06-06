import java.util.ArrayList;
import java.util.List;
/*
Objective is to use two threads for stundent list and each thread will responsible for doing the needful based on Gender

In below example I have just used to print the name of male and fémale student with the help of different thread.

Another important aspect is both thread are communicating with standard wait and notify mechanism and the lock is on same signal object
 */
public class MultiThread_Siemens {
    static List<Student> studentList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        Student s1= new Student("Amit", "male");
        Student s2= new Student("Amita", "female");
        Student s3= new Student("Chandan", "male");
        Student s4= new Student("Amrita", "female");
        Student s5= new Student("Ajay", "male");
        Student s6= new Student("Chandana", "female");
        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);
        studentList.add(s4);
        studentList.add(s5);
        studentList.add(s6);

        Object signalObject = new Object();

        Runnable runnable=() -> {
            synchronized (signalObject) {
                try {
                    System.out.println("Calling "+Thread.currentThread());
                    signalObject.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                studentList.stream().sorted().filter(student -> student.getGender().equals("male")).forEach(student -> System.out.println(student));
            }

        };

        Runnable runnable2=() -> {
            synchronized (signalObject) {
                System.out.println("Calling "+Thread.currentThread());
                studentList.stream().sorted().filter(student -> student.getGender().equals("female")).forEach(student -> System.out.println(student));
                signalObject.notify();
            }
        };
        startThreads(runnable, runnable2);




    }

    private static void startThreads(Runnable runnable, Runnable runnable2) {
        Thread t1= new Thread(runnable, "Male Thread");
        t1.start();
        Thread t2= new Thread(runnable2, "Female Thread");
        t2.start();
    }

}
class Student implements Comparable<Student >{
    String name;
    String gender;

    Student() {

    }
    Student(String name, String gender) {
        this.name=name;
        this.gender=gender;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public int compareTo(Student std) {
        return this.name.compareTo(std.getName());
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
