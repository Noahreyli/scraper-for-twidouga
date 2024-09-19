package commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;


public class Run {
    public String[] run() throws Exception {
        List<String> results = new ArrayList<String>();
        ProcessBuilder p = new ProcessBuilder(".\\commands\\script.bat");
        Process process = p.start();
        InputStreamReader isr = new InputStreamReader(process.getInputStream(),"Cp943");
        try (BufferedReader r = new BufferedReader(isr)) {
            String line;
            while ((line = r.readLine()) != null) {
                results.add(line.replace('¥', '\\'));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results.toArray(new String[results.size()]);
    }
}
