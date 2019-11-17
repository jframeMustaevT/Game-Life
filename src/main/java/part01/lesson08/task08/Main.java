
package part01.lesson08.task08;


public class Main {
    public static void main(String[] args) {
        try {
            if (args.length < 2) {
                String message = "The program needs two command line arguments: inputFile and maxGenerations.\n";
                message += "inputFile: txt file containing rows of ones and zeros. Each row must have the same amount of characters.\n";
                message += "maxGenerations: how many generations the simulation will run.\n";
                message += "Run example: java -jar \"gameoflife.jar\" map.txt 100\n";
                System.out.println(message);
            } else {
                LifeApp.getInstance().run(args[0], Integer.valueOf(args[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
