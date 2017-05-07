package com.example.yeo.practice.Common_braille_translation;



import java.util.ArrayList;

/**
 * Created by yoonc on 2016-07-29.
 */

/*
 * 알파벳 연습에서 사용되는 점자의 배열정보 및 글자정보를 관리하는 클래스
 */

public class Trans_dot_alphabet {
    static boolean real_finish = false;
    boolean check = false;
    boolean finish = false;
    static int search_count=0;
    boolean position_check = false;
    boolean first = true;
    boolean samedata = false;
    int samedata_index = 0;

    int a[][] = {{1,0}, {0,0}, {0,0}};
    int b[][] = {{1,0}, {1,0}, {0,0}};
    int c[][] = {{1,1}, {0,0}, {0,0}};
    int d[][] = {{1,1}, {0,1}, {0,0}};
    int e[][] = {{1,0}, {0,1}, {0,0}};
    int f[][] = {{1,1}, {1,0}, {0,0}};
    int g[][] = {{1,1}, {1,1}, {0,0}};
    int h[][] = {{1,0}, {1,1}, {0,0}};
    int i[][] = {{0,1}, {1,0}, {0,0}};
    int j[][] = {{0,1}, {1,1}, {0,0}};
    int k[][] = {{1,0}, {0,0}, {1,0}};
    int l[][] = {{1,0}, {1,0}, {1,0}};
    int m[][] = {{1,1}, {0,0}, {1,0}};
    int n[][] = {{1,1}, {0,1}, {1,0}};
    int o[][] = {{1,0}, {0,1}, {1,0}};
    int p[][] = {{1,1}, {1,0}, {1,0}};
    int q[][] = {{1,1}, {1,1}, {1,0}};
    int r[][] = {{1,0}, {1,1}, {1,0}};
    int s[][] = {{0,1}, {1,0}, {1,0}};
    int t[][] = {{0,1}, {1,1}, {1,0}};
    int u[][] = {{1,0}, {0,0}, {1,1}};
    int v[][] = {{1,0}, {1,0}, {1,1}};
    int w[][] = {{0,1}, {1,1}, {0,1}};
    int x[][] = {{1,1}, {0,0}, {1,1}};
    int y[][] = {{1,1}, {0,1}, {1,1}};
    int z[][] = {{1,0}, {0,1}, {1,1}};
    int capital_a[][] = {{0,0,1,0},{0,0,0,0},{0,1,0,0}}; //A
    int capital_b[][] = {{0,0,1,0},{0,0,1,0},{0,1,0,0}};
    int capital_c[][] = {{0,0,1,1},{0,0,0,0},{0,1,0,0}};
    int capital_d[][] = {{0,0,1,1},{0,0,0,1},{0,1,0,0}};
    int capital_e[][] = {{0,0,1,0},{0,0,0,1},{0,1,0,0}};
    int capital_f[][] = {{0,0,1,1},{0,0,1,0},{0,1,0,0}};
    int capital_g[][] = {{0,0,1,1},{0,0,1,1},{0,1,0,0}};
    int capital_h[][] = {{0,0,1,0},{0,0,1,1},{0,1,0,0}};
    int capital_i[][] = {{0,0,0,1},{0,0,1,0},{0,1,0,0}};
    int capital_j[][] = {{0,0,0,1},{0,0,1,1},{0,1,0,0}};
    int capital_k[][] = {{0,0,1,0},{0,0,0,0},{0,1,1,0}};
    int capital_l[][] = {{0,0,1,0},{0,0,1,0},{0,1,1,0}};
    int capital_m[][] = {{0,0,1,1},{0,0,0,0},{0,1,1,0}};
    int capital_n[][] = {{0,0,1,1},{0,0,0,1},{0,1,1,0}};
    int capital_o[][] = {{0,0,1,0},{0,0,0,1},{0,1,1,0}};
    int capital_p[][] = {{0,0,1,1},{0,0,1,0},{0,1,1,0}};
    int capital_q[][] = {{0,0,1,1},{0,0,1,1},{0,1,1,0}};
    int capital_r[][] = {{0,0,1,0},{0,0,1,1},{0,1,1,0}};
    int capital_s[][] = {{0,0,0,1},{0,0,1,0},{0,1,1,0}};
    int capital_t[][] = {{0,0,0,1},{0,0,1,1},{0,1,1,0}};
    int capital_u[][] = {{0,0,1,0},{0,0,0,0},{0,1,1,1}};
    int capital_v[][] = {{0,0,1,0},{0,0,1,0},{0,1,1,1}};
    int capital_w[][] = {{0,0,0,1},{0,0,1,1},{0,1,0,1}};
    int capital_x[][] = {{0,0,1,1},{0,0,0,0},{0,1,1,1}};
    int capital_y[][] = {{0,0,1,1},{0,0,0,1},{0,1,1,1}};
    int capital_z[][] = {{0,0,1,0},{0,0,0,1},{0,1,1,1}};

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
    int ing[][] = {{0,1}, {0,0}, {1,1}};
    int en[][] = {{0,0}, {1,0}, {0,1}};


    int in[][] = {{0,0}, {0,1}, {1,0}};
    int ea[][] = {{0,0}, {1,0}, {0,0}};
    int be[][] = {{0,0}, {1,0}, {1,0}};
    int bb[][] = {{0,0}, {1,0}, {1,0}};
    int dd[][] = {{0,0},{1,1},{0,1}};
    int con[][] = {{0,0}, {1,1}, {0,0}};
    int cc[][] = {{0,0}, {1,1}, {0,0}};
    int enough[][] = {{0,0}, {1,0}, {0,1}};
    int were[][] = {{0,0}, {1,1}, {1,1}};
    int gg[][] = {{0,0}, {1,1}, {1,1}};
    int his[][] = {{0,0}, {1,0}, {1,1}};
    int was[][] = {{0,0}, {0,1}, {1,1}};

    int day[][] = {{0,0,1,1},{0,1,0,1},{0,0,0,0}};
    int ever[][] = {{0,0,1,0},{0,1,0,1},{0,0,0,0}};
    int father[][] = {{0,0,1,1},{0,1,1,0},{0,0,0,0}};
    int here[][] = {{0,0,1,0},{0,1,1,1},{0,0,0,0}};
    int know[][] = {{0,0,1,0},{0,1,0,0},{0,1,0,0}};
    int lord[][] = {{0,0,1,0},{0,1,1,0},{0,0,1,0}};
    int mother[][] = {{0,0,1,1},{0,1,0,0},{0,0,1,0}};
    int under[][] = {{0,0,1,0},{0,1,0,0},{0,0,1,1}};
    int name_[][] = {{0,0,1,1},{0,1,0,1},{0,0,1,0}};
    int one[][] = {{0,0,1,0},{0,1,0,1},{0,0,1,0}};
    int part[][] = {{0,0,1,1},{0,1,1,0},{0,0,1,0}};
    int question[][] = {{0,0,1,1},{0,1,1,1},{0,0,1,0}};
    int right[][] = {{0,0,1,0},{0,1,1,1},{0,0,1,0}};
    int some[][] = {{0,0,0,1},{0,1,1,0},{0,0,1,0}};
    int time[][] = {{0,0,0,1},{0,1,1,1},{0,0,1,0}};
    int work[][] = {{0,0,0,1},{0,1,1,1},{0,0,0,1}};
    int young[][] = {{0,0,1,1},{0,1,0,1},{0,0,1,1}};
    int there[][] = {{0,0,0,1},{0,1,1,0},{0,0,1,1}};
    int character[][] = {{0,0,1,0},{0,1,0,0},{0,0,0,1}};
    int through[][] = {{0,0,1,1},{0,1,0,1},{0,0,0,1}};
    int where[][] = {{0,0,1,0},{0,1,0,1},{0,0,0,1}};
    int ought[][] = {{0,0,1,0},{0,1,1,1},{0,0,0,1}};

    int upon[][] = {{0,1,1,0},{0,1,0,0},{0,0,1,1}};
    int word[][] = {{0,1,0,1},{0,1,1,1},{0,0,0,1}};
    int these[][] = {{0,1,0,1},{0,1,1,0},{0,0,1,1}};
    int those[][] = {{0,1,1,1},{0,1,0,1},{0,0,0,1}};
    int whose[][] = {{0,1,1,0},{0,1,0,1},{0,0,0,1}};

    int cannot[][] = {{0,1,1,1},{0,1,0,0},{0,1,0,0}};
    int had[][] = {{0,1,1,0},{0,1,0,0},{0,1,0,0}};
    int many[][] = {{0,1,1,1},{0,1,0,0},{0,1,1,0}};
    int spirit[][] = {{0,1,0,1},{0,1,1,0},{0,1,1,0}};
    int world[][] = {{0,1,0,1},{0,1,1,1},{0,1,0,1}};
    int their[][] = {{0,1,0,1},{0,1,1,0},{0,1,1,1}};
    int ound[][] = {{0,1,1,1},{0,0,0,1},{0,1,0,0}};
    int ance[][] = {{0,1,1,0},{0,0,0,1},{0,1,0,0}};
    int sion[][] = {{0,1,1,1},{0,0,0,1},{0,1,1,0}};
    int less[][] = {{0,1,0,1},{0,0,1,0},{0,1,1,0}};
    int ount[][] = {{0,1,0,1},{0,0,1,1},{0,1,1,0}};
    int ence[][] = {{0,0,1,0},{0,1,0,1},{0,1,0,0}};
    int ong[][] = {{0,0,1,1},{0,1,1,1},{0,1,0,0}};
    int ness[][] = {{0,0,0,1},{0,1,1,0},{0,1,1,0}};
    int ful[][] = {{0,0,1,0},{0,1,1,0},{0,1,1,0}};
    int tion[][] = {{0,0,1,1},{0,1,0,1},{0,1,1,0}};
    int ment[][] = {{0,0,0,1},{0,1,1,1},{0,1,1,0}};
    int ity[][] = {{0,0,1,1},{0,1,0,1},{0,1,1,1}};

    int about[][]={{1,0,1,0},{0,0,1,0},{0,0,0,0}};
    int above[][]={{1,0,1,0,1,0},{0,0,1,0,1,0},{0,0,0,0,1,1}};
    int according[][]={{1,0,1,1},{0,0,0,0},{0,0,0,0}};
    int across[][]={{1,0,1,1,1,0},{0,0,0,0,1,1},{0,0,0,0,1,0}};
    int after[][]={{1,0,1,1},{0,0,1,0},{0,0,0,0}};
    int afternoon[][]={{1,0,1,1,1,1},{0,0,1,0,0,1},{0,0,0,0,1,0}};
    int afterward[][]={{1,0,1,1,0,1},{0,0,1,0,0,1},{0,0,0,0,0,1}};
    int again[][]={{1,0,1,1},{0,0,1,1},{0,0,0,0}};
    int against[][]={{1,0,1,1,0,1},{0,0,1,1,0,0},{0,0,0,0,1,0}};
    int almost[][]={{1,0,1,0,1,1},{0,0,1,0,0,0},{0,0,0,0,0,0}};
    int already[][]={{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,1,0,1,0}};
    int also[][]={{1,0,1,0},{0,0,1,0},{0,0,1,0}};
    int although[][]={{1,0,1,0,1,1},{0,0,1,0,0,1},{0,0,1,0,0,1}};
    int altogether[][]={{1,0,1,0,0,1},{0,0,1,0,1,1},{0,0,1,0,1,0}};
    int always[][]={{1,0,1,0,0,1},{0,0,1,0,1,1},{0,0,1,0,0,1}};
    int because[][]={{0,0,1,1},{1,0,0,0},{1,0,0,0}};
    int before[][]={{0,0,1,1},{1,0,1,0},{1,0,0,0}};
    int behind[][]={{0,0,1,0},{1,0,1,1},{1,0,0,0}};
    int below[][]={{0,0,1,0},{1,0,1,0},{1,0,1,0}};
    int beneath[][]={{0,0,1,1},{1,0,0,1},{1,0,1,0}};
    int beside[][]={{0,0,0,1},{1,0,1,0},{1,0,1,0}};
    int between[][]={{0,0,0,1},{1,0,1,1},{1,0,1,0}};
    int beyond[][]={{0,0,1,1},{1,0,0,1},{1,0,1,1}};
    int conceive[][]={{0,0,1,1,1,0},{1,1,0,0,1,0},{0,0,0,0,1,1}};
    int conceiving[][]={{0,0,1,1,1,0,1,1},{1,1,0,0,1,0,1,1},{0,0,0,0,1,1,0,0}};
    int could[][]={{1,1,1,1},{0,0,0,1},{0,0,0,0}};
    int deceive[][]={{1,1,1,1,1,0},{1,0,0,0,1,0},{0,0,0,0,1,1}};
    int deceiving[][]={{1,1,1,1,1,0,1,1},{1,0,0,0,1,0,1,1},{0,0,0,0,1,1,0,0}};
    int declare[][]={{1,1,1,1,1,0},{1,0,0,0,1,0},{0,0,0,0,1,0}};
    int declaring[][]={{1,1,1,1,1,0,0,0},{1,0,0,0,1,0,0,0},{0,0,0,0,1,0,0,0}};
    int either[][]={{1,0,0,1},{0,1,1,0},{0,0,0,0}};
    int herself[][]={{1,0,1,1,1,1},{1,1,1,1,1,0},{0,0,0,1,0,0}};
    int him[][]={{1,0,1,1},{1,1,0,0},{0,0,1,0}};
    int himself[][]={{1,0,1,1,1,1},{1,1,0,0,1,0},{0,0,1,0,0,0}};
    int immediate[][]={{0,1,1,1,1,1},{1,0,0,0,0,0},{0,0,1,0,1,0}};
    int its[][]={{1,1,0,1},{0,0,1,0},{1,1,1,0}};
    int itself[][]={{1,1,1,1},{0,0,1,0},{1,1,0,0}};
    int much[][]={{1,1,1,0},{0,0,0,0},{1,0,0,1}};
    int must[][]={{1,1,0,1},{0,0,0,0},{1,0,1,0}};
    int myself[][]={{1,1,1,1,1,1},{0,0,0,1,1,0},{1,0,1,1,0,0}};
    int necessary[][]={{1,1,1,0,1,1},{0,1,0,1,0,0},{1,0,0,0,0,0}};
    int neither[][]={{1,1,1,0,0,1},{0,1,0,1,1,0},{1,0,0,0,0,0}};
    int oneself[][]={{0,0,1,0,1,1},{0,1,0,1,1,0},{0,0,1,0,0,0}};
    int ourselves[][]={{1,0,1,0,1,0,0,1},{1,1,1,1,1,0,1,0},{0,1,1,0,1,1,1,0}};
    int paid[][]={{1,1,1,1},{1,0,0,1},{1,0,0,0}};
    int perceive[][]={{1,1,1,1,1,1,1,0},{1,0,1,1,0,0,1,0},{1,0,0,1,0,0,1,1}};
    int perceiving[][]={{1,1,1,1,1,1,1,0,1,1},{1,0,1,1,0,0,1,0,1,1},{1,0,0,1,0,0,1,1,0,0}};
    int perhaps[][]={{1,1,1,1,1,0},{1,0,1,1,1,1},{1,0,0,1,0,0}};
    int receive[][]={{1,0,1,1,1,0},{1,1,0,0,1,0},{1,0,0,0,1,1}};
    int receiving[][]={{1,0,1,1,1,0,1,1},{1,1,0,0,1,0,1,1},{1,0,0,0,1,1,0,0}};
    int rejoice[][]={{1,0,0,1,1,1},{1,1,1,1,0,0},{1,0,0,0,0,0}};
    int rejoicing[][]={{1,0,0,1,1,1,1,1},{1,1,1,1,0,0,1,1},{1,0,0,0,0,0,0,0}};
    int said[][]={{0,1,1,1},{1,0,0,1},{1,0,0,0}};
    int should[][]={{1,1,1,1},{0,0,0,1},{0,1,0,0}};
    int such[][]={{0,1,1,0},{1,0,0,0},{1,0,0,1}};
    int themselves[][]={{0,1,1,1,1,0,0,1},{1,0,0,0,1,0,1,0},{1,1,1,0,1,1,1,0}};
    int thyself[][]={{1,1,1,1,1,1},{0,1,0,1,1,0},{0,1,1,1,0,0}};
    int today[][]={{0,1,1,1},{1,1,0,1},{1,0,0,0}};
    int together[][]={{0,1,1,1,1,0},{1,1,1,1,1,1},{1,0,0,0,1,0}};
    int tomorrow[][]={{0,1,1,1},{1,1,0,0},{1,0,1,0}};
    int tonight[][]={{0,1,1,1},{1,1,0,1},{1,0,1,0}};
    int would[][]={{0,1,1,1},{1,1,0,1},{0,1,0,0}};
    int your[][]={{1,1,1,0},{0,1,1,1},{1,1,1,0}};
    int yourself[][]={{1,1,1,0,1,1},{0,1,1,1,1,0},{1,1,1,0,0,0}};
    int yourselves[][]={{1,1,1,0,1,0,0,1},{0,1,1,1,1,0,1,0},{1,1,1,0,1,1,1,0}};

    public ArrayList<int[][]> alphabet_Array = new ArrayList<>();  //점자의 배열정보를 저장하는 연결리스트
    public ArrayList<Integer> alphabet_dot_count = new ArrayList<>(); // 몇개의 칸으로 구성되어 있는지를 저장하는 연결리스트
    public ArrayList<String> alphabet_name = new ArrayList<>();//점자의 글자를 저장하는 연결리스트
    public ArrayList<Integer> alphabet_temp_Array = new ArrayList<>(); //약자 및 약어 점자의 인덱스 정보를 저장하는 연결리스트

    public ArrayList<int[][]> alphabet_abbreviation_Array = new ArrayList<>();  //점자의 배열정보를 저장하는 연결리스트
    public ArrayList<Integer> alphabet_abbreviation_dot_count = new ArrayList<>(); // 몇개의 칸으로 구성되어 있는지를 저장하는 연결리스트
    public ArrayList<String> alphabet_abbreviation_name = new ArrayList<>();//점자의 글자를 저장하는 연결리스트


    public static String name [] ={"ound", "ance", "sion", "less", "ount", "ence", "ong", "ness",
        "ful", "tion", "ment", "ity"
        ,"was", "ea", "be","bb","dd", "con","cc", "enough", "were","gg", "his","in",
        "but", "can", "do", "every", "from", "go", "have", "just",
        "knowledge", "like", "more", "not", "people", "quite", "rather", "so", "that", "us", "very", "will",
        "it", "you", "as", "and", "for", "of", "the", "with", "child", "shall", "this", "which", "out", "still"
        ,"a","b","c","d","e","f","g","h","i","j","k","l","m", "n", "o", "p", "q",
            "r", "s", "t", "u", "v", "w", "x", "y", "z","A","B","C","D","E","F","G","H","I","J","K","L","M","N",
            "O","P","Q","R","S","T","U","V","W","X","Y","Z",
            "ch", "gh", "sh", "th", "wh", "ed", "er", "ou", "ow", "st", "ar", "ing", "en",
            "day", "ever", "father", "here", "know", "lord", "mother", "under", "name", "one", "part", "question", "right",
            "some", "time", "work", "young", "there", "character", "through", "where", "ought", "upon", "word", "these", "those",
            "whose", "cannot", "had", "many", "spirit", "world", "their"};

    public static String abbreviation_name[] = {"about","above","according","across","after","afternoon","afterward","again","against",
            "almost","already","also","although","altogether","always","because","before","behind","below","beneath","beside","between",
            "beyond","conceive","conceiving","could","deceive","deceiving","declare","declaring","either","herself","him","himself",
            "immediate","its","itself","much","must","myself","necessary","neither","oneself","ourselves","paid","perceive","perceiving",
            "perhaps","receive","receiving","rejoice","rejoicing","said","should","such","themselves","thyself","today","together","tomorrow",
            "tonight","would","your","yourself","yourselves",};




    public Trans_dot_alphabet(){
        //어미약자 단어나 줄의 첫 머리에서 사용할수 없음
        alphabet_Array.add(ound);
        alphabet_Array.add(ance);
        alphabet_Array.add(sion);
        alphabet_Array.add(less);
        alphabet_Array.add(ount);
        alphabet_Array.add(ence);
        alphabet_Array.add(ong);
        alphabet_Array.add(ness);
        alphabet_Array.add(ful);
        alphabet_Array.add(tion);
        alphabet_Array.add(ment);
        alphabet_Array.add(ity);

        //하위부분약자 단어의 제일 처음이 아니면 사용 불가
        alphabet_Array.add(was);
        alphabet_Array.add(ea);
        alphabet_Array.add(be);
        alphabet_Array.add(bb);
        alphabet_Array.add(dd);
        alphabet_Array.add(con);
        alphabet_Array.add(cc);
        alphabet_Array.add(enough);
        alphabet_Array.add(were);
        alphabet_Array.add(gg);
        alphabet_Array.add(his);
        alphabet_Array.add(in);

        //약자 단어는 뒤에 다른게 붙어있으면 안됨 독립된 그 자체만 가능
        alphabet_Array.add(but);
        alphabet_Array.add(can);
        alphabet_Array.add(do_);
        alphabet_Array.add(every);
        alphabet_Array.add(from);
        alphabet_Array.add(go);
        alphabet_Array.add(have);
        alphabet_Array.add(just);
        alphabet_Array.add(knowledge);
        alphabet_Array.add(like);
        alphabet_Array.add(more);
        alphabet_Array.add(not);
        alphabet_Array.add(people);
        alphabet_Array.add(quite);
        alphabet_Array.add(rather);
        alphabet_Array.add(so);
        alphabet_Array.add(that);
        alphabet_Array.add(us);
        alphabet_Array.add(very);
        alphabet_Array.add(will);
        alphabet_Array.add(it);
        alphabet_Array.add(you);
        alphabet_Array.add(as);
        alphabet_Array.add(and);
        alphabet_Array.add(for_);
        alphabet_Array.add(of);
        alphabet_Array.add(the);
        alphabet_Array.add(with);
        alphabet_Array.add(child);
        alphabet_Array.add(shall);
        alphabet_Array.add(this_);
        alphabet_Array.add(which);
        alphabet_Array.add(out);
        alphabet_Array.add(still);


        alphabet_Array.add(a);
        alphabet_Array.add(b);
        alphabet_Array.add(c);
        alphabet_Array.add(d);
        alphabet_Array.add(e);
        alphabet_Array.add(f);
        alphabet_Array.add(g);
        alphabet_Array.add(h);
        alphabet_Array.add(i);
        alphabet_Array.add(j);
        alphabet_Array.add(k);
        alphabet_Array.add(l);
        alphabet_Array.add(m);
        alphabet_Array.add(n);
        alphabet_Array.add(o);
        alphabet_Array.add(p);
        alphabet_Array.add(q);
        alphabet_Array.add(r);
        alphabet_Array.add(s);
        alphabet_Array.add(t);
        alphabet_Array.add(u);
        alphabet_Array.add(v);
        alphabet_Array.add(w);
        alphabet_Array.add(x);
        alphabet_Array.add(y);
        alphabet_Array.add(z);
        alphabet_Array.add(capital_a);
        alphabet_Array.add(capital_b);
        alphabet_Array.add(capital_c);
        alphabet_Array.add(capital_d);
        alphabet_Array.add(capital_e);
        alphabet_Array.add(capital_f);
        alphabet_Array.add(capital_g);
        alphabet_Array.add(capital_h);
        alphabet_Array.add(capital_i);
        alphabet_Array.add(capital_j);
        alphabet_Array.add(capital_k);
        alphabet_Array.add(capital_l);
        alphabet_Array.add(capital_m);
        alphabet_Array.add(capital_n);
        alphabet_Array.add(capital_o);
        alphabet_Array.add(capital_p);
        alphabet_Array.add(capital_q);
        alphabet_Array.add(capital_r);
        alphabet_Array.add(capital_s);
        alphabet_Array.add(capital_t);
        alphabet_Array.add(capital_u);
        alphabet_Array.add(capital_v);
        alphabet_Array.add(capital_w);
        alphabet_Array.add(capital_x);
        alphabet_Array.add(capital_y);
        alphabet_Array.add(capital_z);


        alphabet_Array.add(ch);
        alphabet_Array.add(gh);
        alphabet_Array.add(sh);
        alphabet_Array.add(th);
        alphabet_Array.add(wh);
        alphabet_Array.add(ed);
        alphabet_Array.add(er);
        alphabet_Array.add(ou);
        alphabet_Array.add(ow);
        alphabet_Array.add(st);
        alphabet_Array.add(ar);
        alphabet_Array.add(ing);
        alphabet_Array.add(en);



        alphabet_Array.add(day);
        alphabet_Array.add(ever);
        alphabet_Array.add(father);
        alphabet_Array.add(here);
        alphabet_Array.add(know);
        alphabet_Array.add(lord);
        alphabet_Array.add(mother);
        alphabet_Array.add(under);
        alphabet_Array.add(name_);
        alphabet_Array.add(one);
        alphabet_Array.add(part);
        alphabet_Array.add(question);
        alphabet_Array.add(right);
        alphabet_Array.add(some);
        alphabet_Array.add(time);
        alphabet_Array.add(work);
        alphabet_Array.add(young);
        alphabet_Array.add(there);
        alphabet_Array.add(character);
        alphabet_Array.add(through);
        alphabet_Array.add(where);
        alphabet_Array.add(ought);

        alphabet_Array.add(upon);
        alphabet_Array.add(word);
        alphabet_Array.add(these);
        alphabet_Array.add(those);
        alphabet_Array.add(whose);

        alphabet_Array.add(cannot);
        alphabet_Array.add(had);
        alphabet_Array.add(many);
        alphabet_Array.add(spirit);
        alphabet_Array.add(world);
        alphabet_Array.add(their);


        for(int i = 0 ; i<alphabet_Array.size() ; i++){
            alphabet_name.add(name[i]);
            alphabet_dot_count.add((alphabet_Array.get(i)[0].length)/2);
        }


        alphabet_abbreviation_Array.add(about);
        alphabet_abbreviation_Array.add(above);
        alphabet_abbreviation_Array.add(according);
        alphabet_abbreviation_Array.add(across);
        alphabet_abbreviation_Array.add(after);
        alphabet_abbreviation_Array.add(afternoon);
        alphabet_abbreviation_Array.add(afterward);
        alphabet_abbreviation_Array.add(again);
        alphabet_abbreviation_Array.add(against);
        alphabet_abbreviation_Array.add(almost);
        alphabet_abbreviation_Array.add(already);
        alphabet_abbreviation_Array.add(also);
        alphabet_abbreviation_Array.add(although);
        alphabet_abbreviation_Array.add(altogether);
        alphabet_abbreviation_Array.add(always);
        alphabet_abbreviation_Array.add(because);
        alphabet_abbreviation_Array.add(before);
        alphabet_abbreviation_Array.add(behind);
        alphabet_abbreviation_Array.add(below);
        alphabet_abbreviation_Array.add(beneath);
        alphabet_abbreviation_Array.add(beside);
        alphabet_abbreviation_Array.add(between);
        alphabet_abbreviation_Array.add(beyond);
        alphabet_abbreviation_Array.add(conceive);
        alphabet_abbreviation_Array.add(conceiving);
        alphabet_abbreviation_Array.add(could);
        alphabet_abbreviation_Array.add(deceive);
        alphabet_abbreviation_Array.add(deceiving);
        alphabet_abbreviation_Array.add(declare);
        alphabet_abbreviation_Array.add(declaring);
        alphabet_abbreviation_Array.add(either);
        alphabet_abbreviation_Array.add(herself);
        alphabet_abbreviation_Array.add(him);
        alphabet_abbreviation_Array.add(himself);
        alphabet_abbreviation_Array.add(immediate);
        alphabet_abbreviation_Array.add(its);
        alphabet_abbreviation_Array.add(itself);
        alphabet_abbreviation_Array.add(much);
        alphabet_abbreviation_Array.add(must);
        alphabet_abbreviation_Array.add(myself);
        alphabet_abbreviation_Array.add(necessary);
        alphabet_abbreviation_Array.add(neither);
        alphabet_abbreviation_Array.add(oneself);
        alphabet_abbreviation_Array.add(ourselves);
        alphabet_abbreviation_Array.add(paid);
        alphabet_abbreviation_Array.add(perceive);
        alphabet_abbreviation_Array.add(perceiving);
        alphabet_abbreviation_Array.add(perhaps);
        alphabet_abbreviation_Array.add(receive);
        alphabet_abbreviation_Array.add(receiving);
        alphabet_abbreviation_Array.add(rejoice);
        alphabet_abbreviation_Array.add(rejoicing);
        alphabet_abbreviation_Array.add(said);
        alphabet_abbreviation_Array.add(should);
        alphabet_abbreviation_Array.add(such);
        alphabet_abbreviation_Array.add(themselves);
        alphabet_abbreviation_Array.add(thyself);
        alphabet_abbreviation_Array.add(today);
        alphabet_abbreviation_Array.add(together);
        alphabet_abbreviation_Array.add(tomorrow);
        alphabet_abbreviation_Array.add(tonight);
        alphabet_abbreviation_Array.add(would);
        alphabet_abbreviation_Array.add(your);
        alphabet_abbreviation_Array.add(yourself);
        alphabet_abbreviation_Array.add(yourselves);

        for(int i=0; i<alphabet_abbreviation_Array.size(); i++){
            alphabet_abbreviation_name.add(abbreviation_name[i]);
            alphabet_abbreviation_dot_count.add((alphabet_abbreviation_Array.get(i)[0].length)/2);
        }


    }



    public int Name_search(String name){
        int index=0;
        int count=0;
        int abbreviation_count =0 ;



        if(check==false){
            for(int i=0 ; i<alphabet_name.size(); i++) {
                if(first==true) {
                    if(i>=0 && i<=11)
                        continue;
                }
                if(first==false) {
                    if(i>=12 && i<=23)
                        continue;
                }

                if(alphabet_name.get(i).charAt(search_count)==name.charAt(0)){
                    if(position_check==true){
                        if(alphabet_name.get(i).length()==1){
                            alphabet_temp_Array.add(i);
                            count++;
                            abbreviation_count=0;
                            position_check=false;
                            break;
                        }
                    }
                    else {
                        alphabet_temp_Array.add(i);
                        String temp = alphabet_name.get(i);
                        count++;
                        if (alphabet_name.get(i).length() > search_count + 1)
                            abbreviation_count++;
                    }
                }
            }
            if(count==0) {
                index = -2;
                alphabet_temp_Array.clear();
                search_count=0;
            }
            else {
                if(abbreviation_count==0) {
                    index = alphabet_temp_Array.get(0);
                    alphabet_temp_Array.clear();
                }
                else {
                    if(finish == true) {
                        search_count = search_count;
                        index = -3;
                    }
                    else {
                        index = -1;
                        search_count++;
                    }
                }
                check=true;
            }
        }
        else if(check==true){
            for(int i=0 ; i<alphabet_temp_Array.size(); i++) {
                if(real_finish==true) {
                    if (position_check == true) {
                        String aa = alphabet_name.get(alphabet_temp_Array.get(i));
                        if (alphabet_name.get(alphabet_temp_Array.get(i)).length() == 1) {
                            samedata_index = alphabet_temp_Array.get(i);
                            alphabet_temp_Array.clear();
                            count++;
                            abbreviation_count = 0;
                            break;
                        }
                    }
                }
                else {
                    if (first == true) {
                        if (i >= 0 && i <= 11)
                            continue;
                    }
                    if (first == false) {
                        if (i >= 12 && i <= 22)
                            continue;
                    }
                    int size = alphabet_name.get(alphabet_temp_Array.get(i)).length();
                    String adf = alphabet_name.get(alphabet_temp_Array.get(i));
                    if (size > search_count) {
                        if (alphabet_name.get(alphabet_temp_Array.get(i)).charAt(search_count) == name.charAt(0)) {
                            count++;
                            if (alphabet_name.get(alphabet_temp_Array.get(i)).length() > search_count + 1)
                                abbreviation_count++;
                            if (alphabet_name.get(alphabet_temp_Array.get(i)).length() == search_count + 1) {
                                String a = alphabet_name.get(alphabet_temp_Array.get(i));
                                samedata_index = alphabet_temp_Array.get(i);
                                samedata = true;
                            }
                        }
                    }
                }

            }

            if(count==0) {
                if(samedata==true){
                    samedata = false;
                    index = samedata_index;
                    alphabet_temp_Array.clear();
                    check = false;
                    search_count = 0;
                    Braille_translation.same_data_check=true;
                }
                else if(samedata==false) {
                    index = -2;
                    alphabet_temp_Array.clear();
                    check = false;
                    search_count = 0;
                }
            }
            else{
                if(abbreviation_count==0) {
                    index = samedata_index;
                    check = false;
                    search_count=0;

                }
                else {
                    if(finish == true) {
                        if(samedata==true){
                            index = samedata_index;
                        }
                        else
                            index = -3;
                    }
                    else {
                        index = -1;
                        search_count++;
                    }
                }

            }


        }
        if(index>=0)
            check=false;

        if(first==true)
            first = false;

        if(name==" ")
            first = true;
        return index;
    }

    public int[][] Matrix_search(int index, int[][] matrix){
        String name = alphabet_name.get(index);
        int count = alphabet_dot_count.get(index);
        count = count *2;
        for(int i=0 ; i<3 ; i++){
            for(int j=0 ; j<count ; j++){
                matrix[i][j]=alphabet_Array.get(index)[i][j];
            }
        }

        return matrix;
    }

    public int Dot_count_search(int index){
        int count=0;
        count = alphabet_dot_count.get(index);
        return count;
    }

    public int[][] Matrix_abbreviation_search(int index, int[][] matrix){
        String name = alphabet_abbreviation_name.get(index);
        int count = alphabet_abbreviation_dot_count.get(index);
        count = count *2;
        for(int i=0 ; i<3 ; i++){
            for(int j=0 ; j<count ; j++){
                matrix[i][j]=alphabet_abbreviation_Array.get(index)[i][j];
            }
        }

        return matrix;
    }

    public int Dot_count_abbreviation_search(int index){
        int count=0;
        count = alphabet_abbreviation_dot_count.get(index);
        return count;
    }

    public int name_abbreviation_search(String text){
        int check = -1;
        for(int i=0; i<alphabet_abbreviation_name.size() ; i++){
            if(text.equals(alphabet_abbreviation_name.get(i)))
                check = i;
        }

        return check;
    }
}
