import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader br = new BufferedReader(new FileReader(new File("words_alpha.txt")));
        List<String> words = new ArrayList<>();
        String line;
        main:
        while((line=br.readLine()) != null){
            String word = line.trim().toLowerCase();
            if(word.length()!=5) continue main;
            boolean[] filled = new boolean[26];
            for(int i=0; i<word.length(); i++){
                char c = word.charAt(i);
                if(c<'a' || word.charAt(i)>'z') continue main;
                if(filled[c-'a']) continue main;
                filled[c-'a'] = true;
            }
            words.add(word);
        }
        br.close();

        words = Anagrams.removeAnagrams(words);

        int[] iwords = new int[words.size()];
        for(int i=0; i<words.size(); i++){
            for(int j=0; j<words.get(i).length(); j++){
                iwords[i]|=1<<(words.get(i).charAt(j)-'a');
            }
        }

        System.out.println("Search started");
        Solution sol = new Solution();
        List<List<Integer>> igroups = sol.compute(iwords);
        System.out.println("Search finished");

        String[][] groups = new String[igroups.size()][];
        for(int i=0; i<groups.length; i++){
            groups[i] = new String[igroups.get(i).size()];
            for(int j=0; j<igroups.get(i).size(); j++){
                groups[i][j] = words.get(igroups.get(i).get(j));
            }
        }

        long time = System.currentTimeMillis()-start;
        System.out.println(groups.length+" results in "+String.format("%.2f",time/1000.0)+" s");

        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("result.txt")));
        for(String[] group:groups){
            for(int i=0; i<group.length; i++){
                bw.write(group[i]);
                if(i<group.length-1) bw.write(" ");
            }
            bw.write(System.lineSeparator());
        }
        bw.flush();
        bw.close();
    }
}
