import java.util.ArrayList;
import java.util.List;

public class Solution {
    ans[] answer;
    int[] dp;
    int[] words;
    int dpfill;

    List<List<Integer>> compute(int[] words){
        dp = new int[1 << 26];
        answer = new ans[1 << 26];
        dp[0]=2;
        this.words = words;
        int mask = (1 << 26) - 1;
        for(int i=0; i<26; i++) {
            dfs(mask^(1<<i), 0);
        }
        List<List<Integer>> groups = new ArrayList<>();
        List<Integer> stack = new ArrayList<>();
        for(int i=0; i<26; i++) {
            buildGroups(mask^(1<<i), stack, groups);
        }
        return groups;
    }

    boolean dfs(int mask, int st){
        if(dp[mask] != 0) return dp[mask]==2;
        if(st == words.length) return false;
        boolean found = false;
        int nst = -1;
        int bit = mask&(-mask);
        for(int i=st; i<words.length; i++){
            if((mask|words[i]) != mask) continue;
            if(nst==-1) nst=i;
            if((bit&words[i])!=bit) continue;
            if(dfs(mask^words[i],nst)){
                if(!found) {
                    answer[mask] = new ans();
                    found = true;
                }
                answer[mask].list.add(i);
            }
        }
        dpfill++;
        dp[mask]=1;
        if(found) dp[mask]=2;
        return found;
    }

    void buildGroups(int mask, List<Integer> stack, List<List<Integer>> groups){
        if(dp[mask]!=2) return;
        if(answer[mask]==null){
            groups.add(new ArrayList<>(stack));
            return;
        }
        for(int nxt:answer[mask].list){
            stack.add(nxt);
            buildGroups(mask^words[nxt],stack,groups);
            stack.remove(stack.size()-1);
        }
    }

    class ans{
        ArrayList<Integer> list;

        public ans() {
            list = new ArrayList<>();
        }
    }
}
