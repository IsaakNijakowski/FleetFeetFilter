import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import java.util.ArrayList;
import java.io.IOException;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import javax.swing.*;

class Filter {
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
        File file = new File("./SKUs.txt");
        File output = new File("./Output.txt");
        PrintWriter write = new PrintWriter(output);
        Scanner scanner = new Scanner(file);
        write.write("");// Clear output contents
        while(scanner.hasNextLine()) {
            ArrayList<Element> elements = new ArrayList<>();
            String SKU = scanner.nextLine();
            try {
                Document site = Jsoup.connect("https://www.fleetfeet.com/browse?q="+SKU).get();
                elements.addAll(site.getElementsByClass("no-results"));
                if (elements.size()!=0) {
                    write.append(SKU+"\n");
                }
            } catch(HttpStatusException e) {
                System.err.println("Error on SKU "+SKU);
                scanner.close();
                write.close();
                break;
            }
            Thread.sleep(100);
        }
        scanner.close();
        write.close();
    }
}
