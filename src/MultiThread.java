import java.util.ArrayList;
import java.util.List;
/*
Objective is to use two threads for stundent list and each thread will responsible for doing the needful based on Gender

In below example I have just used to print the name of male and fémale student with the help of different thread.

Another important aspect is both thread are communicating with standard wait and notify mechanism and the lock is on same signal object
 */

public class MultiThread {

    static List<Student> studentList = new ArrayList<>();
    private boolean isThreadWaiting = false;
    private boolean signalRaised = false;

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

        //SignalHolder signalObject = new SignalHolder(); // OK for two threaded environment
        SignalCounter signalObject = new SignalCounter(); // Ok for multithreaded environment
        Runnable runnable=() -> {
            try {
                //Thread.sleep(2000); //uncomment to see other ways execution
                signalObject.doWait();
                studentList.stream().sorted().filter(student -> student.getGender().equals("male")).forEach(student -> System.out.println(student));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        };

        Runnable runnable2=() -> {
            studentList.stream().sorted().filter(student -> student.getGender().equals("female")).forEach(student -> System.out.println(student));
            signalObject.doNotify();
        };
        startThreads(runnable, runnable2);
    }

    private static void startThreads(Runnable runnable, Runnable runnable2) {
        Thread t1= new Thread(runnable, "Male Thread 1");
        Thread t2= new Thread(runnable2, "Female Thread 1");
        t2.start();
        t1.start();
        Thread t3= new Thread(runnable, "Male Thread 2");
        Thread t4= new Thread(runnable2, "Female Thread 2");
        t4.start();
        t3.start();

    }

}

