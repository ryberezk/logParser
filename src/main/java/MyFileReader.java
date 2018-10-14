import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyFileReader {

    static String line;
    static ArrayList<String> words = new ArrayList<String>();
    static String lineLC = null;
    static int currLine = 1;
    static int wordPos = 0;

    List<Integer> currLines = new ArrayList<Integer>();


    public List searchLineForWord(String path, String word) {
        FileReader fr = getFileReader(path);
        BufferedReader bR = getBufferedReader(fr);
        try {
            while ((line = bR.readLine()) != null) {
                lineLC = line.toLowerCase();
                currLine++;

                if (lineLC.contains(word)) {
                    wordPos = lineLC.lastIndexOf(word);
                    System.out.println("   ..." + word + "..." + currLine);
                    currLines.add(currLine);
                }
            }
            bR.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("Input/Output error: " + e.toString());
        }
        return currLines;
    }

    private BufferedReader getBufferedReader(FileReader fileReader) {
        BufferedReader br = null;
        br = new BufferedReader(fileReader);
        return br;
    }

    private FileReader getFileReader(String path) {
        FileReader fileReader = null;
        try {
            File f = new File(path);
            fileReader = new FileReader(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileReader;
    }
}

