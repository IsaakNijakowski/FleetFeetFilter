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
        File file = new File("./SKUs.txt");

        Scanner maxCount = new Scanner(file);
        int max = 0;
        while(maxCount.hasNextLine()) {
            max++;
            maxCount.nextLine();
        }
        JFrame frame = new JFrame("Fleet Feet Filter");
        JPanel panel = new JPanel();
        JProgressBar load = new JProgressBar(0, max);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(200, 80);
        panel.add(load);
        frame.add(panel);
        frame.setVisible(true);

        File output = new File("./Output.txt");
        PrintWriter write = new PrintWriter(output);
        Scanner scanner = new Scanner(file);
        write.write("");// Clear output contents
        int count = 0;
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
                System.err.println("Error on SKU "+SKU+" at line "+count+"\nAre you processing too many requests?");
                scanner.close();
                write.close();
                break;
            }
            count++;
            load.setValue(count);
            frame.revalidate();
            Thread.sleep(100);
        }
        scanner.close();
        write.close();
    }
}
