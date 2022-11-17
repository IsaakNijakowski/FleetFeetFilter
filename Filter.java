import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import Jsoup

class Filter {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Hello World");
        File file = new File("./SKUs.txt");
        File output = new File("./Output.txt");
        PrintWriter write = new PrintWriter(output);
        Scanner scanner = new Scanner(file);
        write.write("");// Clear output contents
        while(scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
        write.append("this is the real test");

        scanner.close();
        write.close();
    }
}
