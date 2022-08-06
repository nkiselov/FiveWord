import java.util.*;

public class Anagrams {
    public static List<String> removeAnagrams(List<String> list){
        List<String> ans = new ArrayList<>();
        Set<String> charsets = new HashSet<>();
        for(String s:list){
            char[] ar = s.toCharArray();
            Arrays.sort(ar);
            String sorted = String.valueOf(ar);
            if(charsets.contains(sorted)) continue;
            charsets.add(sorted);
            ans.add(s);
        }
        return ans;
    }
}
