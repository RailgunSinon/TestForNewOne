package FileSearcher;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    private static int linesInOnePage = 500;

    public static List<String> readPage(File file,int page) throws Exception{

        if(page < 0)
        {
            throw new Exception("Page number should be 0 or higher");
        }
        else{

            List<String> lines = new ArrayList<>();
            BufferedReader in = new BufferedReader(new java.io.FileReader(file));
            String line = in.readLine();

            int lineNumber = 0;
            int lineNumberFrom = page * linesInOnePage;
            int lineNumberTill = (page + 1) * linesInOnePage;

            while (line != null && lineNumber < lineNumberTill){

                if(lineNumber >= lineNumberFrom){
                    lines.add(line);
                }

                lineNumber++;
                line = in.readLine();
            }

            return lines;
        }
    }
}
