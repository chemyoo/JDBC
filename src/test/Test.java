package test;

public class Test {

    String a = "hello";

    public static void main(String args[]){

        Test test = new Test();
        test.changeValue(test.a);
        System.err.println(test.a);

    }

    public void changeValue(String a)
    {
        a = "word";
    }

}
