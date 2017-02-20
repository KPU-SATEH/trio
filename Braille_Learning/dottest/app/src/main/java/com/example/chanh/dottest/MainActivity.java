package com.example.chanh.dottest;

import android.icu.text.UnicodeFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {


    TextView braille;
    EditText text;
    Button submit,braille_init;
    String hangel="";



    int matrix[][];
    Braille_translation Translation;
    boolean Matrix_check=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (EditText)findViewById(R.id.editText);
        submit = (Button) findViewById(R.id.button);
        braille = (TextView)findViewById(R.id.textView21);
        braille_init = (Button)findViewById(R.id.button2);

        matrix = new int[3][14];

        Translation = new Braille_translation();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matrix_init();
                hangel = text.getText().toString();
                Translation.Translation(hangel);
                Matrix_check=Matrix_copy();
                if(Matrix_check==false) {
                    Toast.makeText(MainActivity.this, "7칸이 넘어서 번역이 불가능합니다.", Toast.LENGTH_SHORT).show();
                    matrix_init();
                }
                else {
                    matrix_print();
                    Toast.makeText(MainActivity.this, Translation.get_TTs_text()+"", Toast.LENGTH_LONG).show();
                }

            }
        });

        braille_init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matrix_init();
            }
        });
    }

    public boolean Matrix_copy(){
        int Matrix_sum=42;
        int sum=0;
        for(int i=0 ; i<3 ; i++){
            for(int j=0 ; j<14 ; j++){
                matrix[i][j]= Translation.getMatrix()[i][j];
                if(matrix[i][j]==1)
                    sum+=1;
            }
        }
        if(sum==Matrix_sum)
            return false;
        else
            return true;
    }
    public void matrix_print(){                 //점자를 출력하는 함수
        int a=0;
        int b=0;
        braille.setText(matrix[0][0] + " " + matrix[0][1] + "   " + matrix[0][2] + " " + matrix[0][3] + "   " + matrix[0][4] + " " + matrix[0][5] + "   " + matrix[0][6] + " " + matrix[0][7] + "   " + matrix[0][8] + " " + matrix[0][9] + "   " + matrix[0][10] + " " + matrix[0][11] + "   " + matrix[0][12] + " " + matrix[0][13] + "\n"
                + matrix[1][0] + " " + matrix[1][1] + "   " + matrix[1][2] + " " + matrix[1][3] + "   " + matrix[1][4] + " " + matrix[1][5] + "   " + matrix[1][6] + " " + matrix[1][7] + "   " + matrix[1][8] + " " + matrix[1][9] + "   " + matrix[1][10] + " " + matrix[1][11] + "   " + matrix[1][12] + " " + matrix[1][13] + "\n"
                + matrix[2][0] + " " + matrix[2][1] + "   " + matrix[2][2] + " " + matrix[2][3] + "   " + matrix[2][4] + " " + matrix[2][5] + "   " + matrix[2][6] + " " + matrix[2][7] + "   " + matrix[2][8] + " " + matrix[2][9] + "   " + matrix[2][10] + " " + matrix[2][11] + "   " + matrix[2][12] + " " + matrix[2][13]);
    }

    public void matrix_init(){                      //점자를 초기화 하는 함수
        Translation.matrix_init();
        for(int i=0 ; i<3 ; i++){
            for(int j=0 ; j<14 ; j++){
                matrix[i][j]=0;
            }
        }
        braille.setText(matrix[0][0]+" "+matrix[0][1]+"   "+matrix[0][2]+" "+matrix[0][3]+"   "+matrix[0][4]+" "+matrix[0][5]+"   "+matrix[0][6]+" "+matrix[0][7]+"   "+matrix[0][8]+" "+matrix[0][9]+"   "+matrix[0][10]+" "+matrix[0][11]+"   "+matrix[0][12]+" "+matrix[0][13]+"\n"
                +matrix[1][0]+" "+matrix[1][1]+"   "+matrix[1][2]+" "+matrix[1][3]+"   "+matrix[1][4]+" "+matrix[1][5]+"   "+matrix[1][6]+" "+matrix[1][7]+"   "+matrix[1][8]+" "+matrix[1][9]+"   "+matrix[1][10]+" "+matrix[1][11]+"   "+matrix[1][12]+" "+matrix[1][13]+"\n"
                +matrix[2][0]+" "+matrix[2][1]+"   "+matrix[2][2]+" "+matrix[2][3]+"   "+matrix[2][4]+" "+matrix[2][5]+"   "+matrix[2][6]+" "+matrix[2][7]+"   "+matrix[2][8]+" "+matrix[2][9]+"   "+matrix[2][10]+" "+matrix[2][11]+"   "+matrix[2][12]+" "+matrix[2][13]);

    }

}

