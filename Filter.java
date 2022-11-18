
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
import javax.swing.plaf.ColorUIResource;

class Filter {
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        File file = new File("./SKUs.txt");

        Scanner maxCount = new Scanner(file);
        int max = 0;
        while(maxCount.hasNextLine()) {
            max++;
            maxCount.nextLine();
        }
        maxCount.close();
        JFrame frame = new JFrame("Fleet Feet Filter");
        JPanel panel = new JPanel();
        JProgressBar load = new JProgressBar(0, max);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(200, 80);
        load.setStringPainted(true);
        load.setBackground(new ColorUIResource(249, 249, 249));
        load.setForeground(new ColorUIResource(104, 131, 186));
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
            while (true) {
                try {
                    Document site = Jsoup.connect("https://www.fleetfeet.com/browse?q="+SKU).get();
                    elements.addAll(site.getElementsByClass("no-results"));
                    if (elements.size()!=0) {
                        write.append(SKU+"\n");
                    }
                    break;
                } catch(HttpStatusException e) {
                    load.setForeground(new ColorUIResource(224, 114, 74));
                    System.err.println("Error on SKU "+SKU+" at line "+count+"\nAre you processing too many requests?\nWaiting 5 mins and trying again.");
                    Thread.sleep(300000);
                }
            }
            count++;
            load.setForeground(new ColorUIResource(104, 131, 186));
            load.setValue(count);
            load.setString(count+"/"+max);
        }
        load.setForeground(new ColorUIResource(176, 226, 152));
        scanner.close();
        write.close();
    }
}
