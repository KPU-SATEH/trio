package com.example.yeo.practice.Common_braille_data;



import java.util.ArrayList;

/**
 * Created by yoonc on 2016-07-29.
 */

/*
 * 알파벳 연습에서 사용되는 점자의 배열정보 및 글자정보를 관리하는 클래스
 */
public class dot_abbreviation_word {
    static public int abbreviation_word_count = 61; // 알파벳 연습의 갯수
    int but[][] ={{1,0}, {1,0}, {0,0}}; //but
    int can[][] = {{1,1},{0,0}, {0,0}}; // can
    int do_[][] = {{1,1}, {0,1}, {0,0}}; // do
    int every[][] = {{1,0}, {0,1}, {0,0}}; // every
    int from[][] = {{1,1},{1,0},{0,0}}; // from
    int go[][] = {{1,1}, {1,1}, {0,0}}; // go
    int have[][] = {{1,0}, {1,1}, {0,0}}; // have
    int just[][] = {{0,1}, {1,1}, {0,0}}; //just
    int knowledge[][] = {{1,0}, {0,0}, {1,0}}; //knowledge
    int like[][] = {{1,0}, {1,0}, {1,0}}; //like
    int more[][] = {{1,1}, {0,0}, {1,0}}; //more
    int not[][] = {{1,1}, {0,1}, {1,0}}; //not
    int people[][] = {{1,1}, {1,0}, {1,0}}; //people
    int quite[][] = {{1,1}, {1,1}, {1,0}};
    int rather[][] = {{1,0}, {1,1}, {1,0}};
    int so[][] = {{0,1}, {1,0}, {1,0}};
    int that[][] = {{0,1}, {1,1}, {1,0}};
    int us[][] = {{1,0}, {0,0}, {1,1}};
    int very[][] = {{1,0}, {1,0}, {1,1}};
    int will[][] = {{0,1}, {1,1}, {0,1}};
    int it[][] = {{1,1}, {0,0}, {1,1}};
    int you[][] = {{1,1}, {0,1}, {1,1}};
    int as[][] = {{1,0}, {0,1}, {1,1}};
    int and[][] = {{1,1}, {1,0}, {1,1}};
    int for_[][] = {{1,1}, {1,1}, {1,1}};
    int of[][] = {{1,0}, {1,1}, {1,1}};
    int the[][] = {{0,1}, {1,0}, {1,1}};
    int with[][] = {{0,1}, {1,1}, {1,1}};
    int child[][] = {{1,0}, {0,0}, {0,1}};
    int shall[][] = {{1,1}, {0,0}, {0,1}};
    int this_[][] = {{1,1}, {0,1}, {0,1}};
    int which[][] = {{1,0}, {0,1}, {0,1}};
    int out[][] = {{1,0}, {1,1}, {0,1}};
    int still[][] = {{0,1}, {0,0}, {1,0}};

    int ch[][] = {{1,0}, {0,0}, {0,1}};
    int gh[][] = {{1,0}, {1,0}, {0,1}};
    int sh[][] = {{1,1}, {0,0}, {0,1}};
    int th[][] = {{1,1}, {0,1}, {0,1}};
    int wh[][] = {{1,0}, {0,1}, {0,1}};
    int ed[][] = {{1,1}, {1,0}, {0,1}};
    int er[][] = {{1,1}, {1,1}, {0,1}};
    int ou[][] = {{1,0}, {1,1}, {0,1}};
    int ow[][] = {{0,1}, {1,0}, {0,1}};
    int st[][] = {{0,1}, {0,0}, {1,0}};
    int ar[][] = {{0,1}, {0,1}, {1,0}};
    int ble[][] = {{0,1}, {0,1}, {1,1}};
    int ing[][] = {{0,1}, {0,0}, {1,1}};
    int en[][] = {{0,0}, {1,0}, {0,1}};
    int in[][] = {{0,0}, {0,1}, {1,0}};

    int ea[][] = {{0,0}, {1,0}, {0,0}};
    int be_bb[][] = {{0,0}, {1,0}, {1,0}};
    int con_cc[][] = {{0,0}, {1,1}, {0,0}};
    int dis_dd[][] = {{0,0}, {1,1}, {0,1}};
    int en_enough[][] = {{0,0}, {1,0}, {0,1}};
    int to_ff[][] = {{0,0}, {1,1}, {1,0}};
    int were_gg[][] = {{0,0}, {1,1}, {1,1}};
    int his[][] = {{0,0}, {1,0}, {1,1}};
    int in2[][] = {{0,0}, {0,1}, {1,0}};
    int into[][] = {{0,0,0,0}, {0,1,1,1}, {1,0,1,0}};
    int was_by[][] = {{0,0}, {0,1}, {1,1}};
    int com_[][] = {{0,0}, {1,0}, {0,1}};

    public int dot_counter[]={1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1}; //몇개의 칸으로 구성되어 있는지를 나타내는 점자 배열 변수
    public static ArrayList<int[][]> abbreviation_word_Array = new ArrayList<>();  //점자의 배열정보를 저장하는 연결리스트
    public static ArrayList<Integer> abbreviation_word_dot_count = new ArrayList<>(); // 몇개의 칸으로 구성되어 있는지를 저장하는 연결리스트
    public static ArrayList<String> abbreviation_word_name = new ArrayList<>();//점자의 글자를 저장하는 연결리스트

    public static String name [] ={"But", "Can", "Do", "Every", "From", "Go", "Have", "Just", "Knowledge", "Like", "More", "Not", "People", "Quite", "Rather",
                                     "So", "That", "Us", "Very", "Will", "It", "You", "As", "And", "For", "Of", "The", "With", "Child", "Shall", "This", "Which", "Out", "Still", "ch", "gh", "sh", "th", "wh", "ed", "er", "ou", "ow", "st", "ar", "ble", "ing", "en", "in",
                                     "ea", "be_bb", "con_cc", "dis_dd", "en_enough", "to_ff", "were_gg", "his", "in", "into", "was_by", "com" };

    public dot_abbreviation_word(){
        abbreviation_word_Array.add(but);
        abbreviation_word_Array.add(can);
        abbreviation_word_Array.add(do_);
        abbreviation_word_Array.add(every);
        abbreviation_word_Array.add(from);
        abbreviation_word_Array.add(go);
        abbreviation_word_Array.add(have);
        abbreviation_word_Array.add(just);
        abbreviation_word_Array.add(knowledge);
        abbreviation_word_Array.add(like);
        abbreviation_word_Array.add(more);
        abbreviation_word_Array.add(not);
        abbreviation_word_Array.add(people);
        abbreviation_word_Array.add(quite);
        abbreviation_word_Array.add(rather);
        abbreviation_word_Array.add(so);
        abbreviation_word_Array.add(that);
        abbreviation_word_Array.add(us);
        abbreviation_word_Array.add(very);
        abbreviation_word_Array.add(will);
        abbreviation_word_Array.add(it);
        abbreviation_word_Array.add(you);
        abbreviation_word_Array.add(as);
        abbreviation_word_Array.add(and);
        abbreviation_word_Array.add(for_);
        abbreviation_word_Array.add(of);
        abbreviation_word_Array.add(the);
        abbreviation_word_Array.add(with);
        abbreviation_word_Array.add(child);
        abbreviation_word_Array.add(shall);
        abbreviation_word_Array.add(this_);
        abbreviation_word_Array.add(which);
        abbreviation_word_Array.add(out);
        abbreviation_word_Array.add(still);

        abbreviation_word_Array.add(ch);
        abbreviation_word_Array.add(gh);
        abbreviation_word_Array.add(sh);
        abbreviation_word_Array.add(th);
        abbreviation_word_Array.add(wh);
        abbreviation_word_Array.add(ed);
        abbreviation_word_Array.add(er);
        abbreviation_word_Array.add(ou);
        abbreviation_word_Array.add(ow);
        abbreviation_word_Array.add(st);
        abbreviation_word_Array.add(ar);
        abbreviation_word_Array.add(ble);
        abbreviation_word_Array.add(ing);
        abbreviation_word_Array.add(en);
        abbreviation_word_Array.add(in);

        abbreviation_word_Array.add(ea);
        abbreviation_word_Array.add(be_bb);
        abbreviation_word_Array.add(con_cc);
        abbreviation_word_Array.add(dis_dd);
        abbreviation_word_Array.add(en_enough);
        abbreviation_word_Array.add(to_ff);
        abbreviation_word_Array.add(were_gg);
        abbreviation_word_Array.add(his);
        abbreviation_word_Array.add(in2);
        abbreviation_word_Array.add(into);
        abbreviation_word_Array.add(was_by);
        abbreviation_word_Array.add(com_);



        for(int i = 0 ; i<abbreviation_word_count ; i++){
            abbreviation_word_name.add(name[i]);
            abbreviation_word_dot_count.add(dot_counter[i]);
        }
    }
}
