import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SqlParser2 { 
    
    

    public static void main(String[] arg){
        String[] keyword = {"SELECT", "FROM", "WHERE", "AND", "LIKE"};
        String[] Function = {"MAX", "SUM"};
        String[] strAry;
        String[] strAryFlag;
        String strFlag = "";
        boolean multiFlag = false;
        List<String> strlist = new ArrayList<>();
        String str = "SELECT MAX(EMPNO), SUM(SAL)\n"
                    + "FROM EMP\n"
                    + "WHERE DEPTNO = :in_deptno\n"
                    + "AND ENAME LIKE '$xxx$'\n"
                    + "-- AND ENAME LIKE ‘$’ || :in_name\n"
                    + "AND SAL > 8000\n"
                    + "/* 이것은 multi line 주석이다.\n"
                    + "여러 line 의 주석을 처리할 수 있다.\n"
                    + "주석안에 있는 모든 token(keyword, Binding 변수, 문자열등)은 무시되고 주석으로 간주된다.\n"
                    + "*/";

        strAry = str.split("\n");

        for(String s: strAry){
            if(s.startsWith("/*")){
                multiFlag=true;
                strFlag = "";
            }
            if(multiFlag==true)
                strFlag = strFlag + s + "\n";
            else if(s.startsWith("--"))
                strlist.add(s);
            else{
                s = s.replace("(", " ( ");
                s = s.replace(")", " ) ");
                strAryFlag = s.split(" ");
                for(String r: strAryFlag)
                    strlist.add(r);
            }
            if(s.endsWith("*/")){
                multiFlag=false;
                strFlag = strFlag.substring(0, strFlag.length() - 1);
                strlist.add(strFlag);
            }
            
            
        }
        
        for (String s : strlist){
            if(Arrays.asList(keyword).contains(s))
                System.out.println(s + "-> keyword");
            else if(Arrays.asList(Function).contains(s))
                System.out.println(s + "-> Function");
            else if(s.startsWith(":"))
                System.out.println(s + "-> Binding 변수");
            else if(s.startsWith("'"))
                System.out.println(s + "-> 문자열");
            else if(s.startsWith("--"))
                System.out.println(s + "-> 주석");
            else if(s.startsWith("/*"))
                System.out.println(s + "-> 주석");
            else
                System.out.println(s + "-> ETC");
        }

    }
}