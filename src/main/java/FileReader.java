import java.io.*;
import java.util.Scanner;

public class FileReader {

    public boolean foundMark = false;

    public void fileReader(String dir, String key) throws FileNotFoundException {
        File file = new File(dir);

        Scanner scanner = new Scanner(file);
        try {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] cols = line.split("><");

                for (int i = 0; i < cols.length; i++) {
                    if (cols[i].equals(key)) {
                        System.out.println(line);
                        foundMark = true;
                    } else {
                    }
                }
            }
        } finally {
            if (!foundMark) {
                System.out.println("Ничего не найдено");
            } scanner.close();
        }
    }
}