package com.example.ronik.myticketproject1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_QUESTIONS_AND_ANSWERS = "questions_answers";
    public static final String COLUMN_ID_QUESTIONS = "_id";
    public static final String COLUMN_QUESTIONS = "questions";

    public static final String COLUMN_CORRECT_ANSWER = "correct_answer";
    public static final String COLUMN_WRONG_ANSWER0 = "wrong_answer0";
    public static final String COLUMN_WRONG_ANSWER1 = "wrong_answer1";
    public static final String COLUMN_WRONG_ANSWER2 = "wrong_answer2";

    public static final String DATABASE_NAME = "AlphaTriv.db";
    private static final int DATABASE_VERSION = 1;

    Context context;
    private static final String QUESTIONS_AND_ANSWERS_TABLE_CREATE =
            "CREATE TABLE " + TABLE_QUESTIONS_AND_ANSWERS +
                    " (" + COLUMN_ID_QUESTIONS +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_QUESTIONS + " VARCHAR NOT NULL DEFAULT 'NOT_INIT_YET', " +
                    COLUMN_CORRECT_ANSWER +" VARCHAR NOT NULL DEFAULT 'NOT_INIT_YET', " +
                    COLUMN_WRONG_ANSWER0 +" VARCHAR NOT NULL DEFAULT 'NOT_INIT_YET', " +
                    COLUMN_WRONG_ANSWER1 +" VARCHAR NOT NULL DEFAULT 'NOT_INIT_YET', " +
                    COLUMN_WRONG_ANSWER2 +" VARCHAR NOT NULL DEFAULT 'NOT_INIT_YET' );";



    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(QUESTIONS_AND_ANSWERS_TABLE_CREATE);
        System.out.println("Hi");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(MySQLiteOpenHelper.class.getName(),
                "Upgrading db from version " + oldVersion + " to " +
                        newVersion + ", which willdestroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS_AND_ANSWERS);
        onCreate(sqLiteDatabase);
    }
    //=======================================================================
    public void dropTable(SQLiteDatabase sqLiteDatabase , String tableName){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableName + "" );//+ TABLE_QUESTIONS_AND_ANSWERS
    }
    //========================================================================
    public void insertQuestionsAndAnswersManually(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_QUESTIONS_AND_ANSWERS+"("+COLUMN_QUESTIONS+","+COLUMN_CORRECT_ANSWER+","+COLUMN_WRONG_ANSWER0+","+COLUMN_WRONG_ANSWER1+","+COLUMN_WRONG_ANSWER2+","+ COLUMN_ID_QUESTIONS+") VALUES('What is the English translation for the name of the German automaker Volkswagen?','People’s car','Best Car','Quality Car','Folk`s car',1);");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_QUESTIONS_AND_ANSWERS+"("+COLUMN_QUESTIONS+","+COLUMN_CORRECT_ANSWER+","+COLUMN_WRONG_ANSWER0+","+COLUMN_WRONG_ANSWER1+","+COLUMN_WRONG_ANSWER2+","+ COLUMN_ID_QUESTIONS+") VALUES('With twelve Oscar nominations and three wins, who is the most nominated male actor in Academy Awards history?','Jack Nicholson','Leonardo DiCaprio','Denzel Washington','Al Pacino',2);");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_QUESTIONS_AND_ANSWERS+"("+COLUMN_QUESTIONS+","+COLUMN_CORRECT_ANSWER+","+COLUMN_WRONG_ANSWER0+","+COLUMN_WRONG_ANSWER1+","+COLUMN_WRONG_ANSWER2+","+ COLUMN_ID_QUESTIONS+") VALUES('Tenochtitlan, founded in 1324, is now known as what city?','Mexico City','Montevideo','Costa Rica','Guatemala',3);");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_QUESTIONS_AND_ANSWERS+"("+COLUMN_QUESTIONS+","+COLUMN_CORRECT_ANSWER+","+COLUMN_WRONG_ANSWER0+","+COLUMN_WRONG_ANSWER1+","+COLUMN_WRONG_ANSWER2+","+ COLUMN_ID_QUESTIONS+") VALUES('Located in southern Siberia, what lake is the deepest and largest freshwater lake in the world?','Lake Baikal','Lake Ontario','Great Slave Lake','Lake Issyk-Kul',4);");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_QUESTIONS_AND_ANSWERS+"("+COLUMN_QUESTIONS+","+COLUMN_CORRECT_ANSWER+","+COLUMN_WRONG_ANSWER0+","+COLUMN_WRONG_ANSWER1+","+COLUMN_WRONG_ANSWER2+","+ COLUMN_ID_QUESTIONS+") VALUES('Name the actor who starred in 142 films including The Quiet Man, The Shootist, The Searchers and Stagecoach.','John Wayne','Ron Howard','James Stewart','Ben Afleck',5);");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_QUESTIONS_AND_ANSWERS+"("+COLUMN_QUESTIONS+","+COLUMN_CORRECT_ANSWER+","+COLUMN_WRONG_ANSWER0+","+COLUMN_WRONG_ANSWER1+","+COLUMN_WRONG_ANSWER2+","+ COLUMN_ID_QUESTIONS+") VALUES('What is the oldest film ever made, and when was it made? ','Roundhay Garden Scene made in 1888','A Trip to the Moon, 1902','The Execution of Mary Stuart,1895','Fight Club,1999',6);");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_QUESTIONS_AND_ANSWERS+"("+COLUMN_QUESTIONS+","+COLUMN_CORRECT_ANSWER+","+COLUMN_WRONG_ANSWER0+","+COLUMN_WRONG_ANSWER1+","+COLUMN_WRONG_ANSWER2+","+ COLUMN_ID_QUESTIONS+") VALUES('Which actress has won the most Oscars?','Katharine Hepburn','Russell Crowe','Leonardo Di Caprio','Emma Stone',7);");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_QUESTIONS_AND_ANSWERS+"("+COLUMN_QUESTIONS+","+COLUMN_CORRECT_ANSWER+","+COLUMN_WRONG_ANSWER0+","+COLUMN_WRONG_ANSWER1+","+COLUMN_WRONG_ANSWER2+","+ COLUMN_ID_QUESTIONS+") VALUES('Name the director of the Lord of the Rings trilogy. ','Peter Jackson','Christopher Nolan','Sam Raimi','Ivan Gazidis',8);");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_QUESTIONS_AND_ANSWERS+"("+COLUMN_QUESTIONS+","+COLUMN_CORRECT_ANSWER+","+COLUMN_WRONG_ANSWER0+","+COLUMN_WRONG_ANSWER1+","+COLUMN_WRONG_ANSWER2+","+ COLUMN_ID_QUESTIONS+") VALUES('Who played Neo in The Matrix? ','Keanu Reeves','Matt Damon','Ben Smith','Michael Nyqvist',9);");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_QUESTIONS_AND_ANSWERS+"("+COLUMN_QUESTIONS+","+COLUMN_CORRECT_ANSWER+","+COLUMN_WRONG_ANSWER0+","+COLUMN_WRONG_ANSWER1+","+COLUMN_WRONG_ANSWER2+","+ COLUMN_ID_QUESTIONS+") VALUES('Name the actress whose career began at the age of 3, and who went on to star in films such as Contact, Maverick and The Silence of the Lambs? ','Jodie Foster','Daniel Foster','Fisting Foster','George Foster',10);");


        insertQuestionsAndAnswersFromJSON(sqLiteDatabase);


        //randomizeQuestion(sqLiteDatabase);
    }
    public void insertQuestionsAndAnswersFromJSON(SQLiteDatabase sqLiteDatabase){

        String stHumanReadable = null;

        //String stJson = context.getResources().getString(R.string.json_questions_str);

        String stJson = "hiiiii";

        //System.out.println("Mark: " + stJson); stHumanReadable




        try {
            // Get the array from the JSONObject
            //JSONObject jsonRootObject = new JSONObject(stJson);
            //JSONArray jsonArray = jsonRootObject.optJSONArray("nutrients");


            JSONArray jsonArray = new JSONArray(stJson);




            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String question = jsonObject.optString("A").toString();
                String correctAnswer = jsonObject.optString("B").toString();
                String wrongAnswer1 = jsonObject.optString("C").toString();
                String wrongAnswer2 = jsonObject.optString("D").toString();
                String wrongAnswer3 = jsonObject.optString("E").toString();



                stHumanReadable += "\nNode "+ i +" : \n q= "+ question +
                        " \n correct answer = "+ correctAnswer +" \n wa1= "+ wrongAnswer1 +" \n "+" \n wa2= "+ wrongAnswer2 +" \n "+" \n wa3= "+ wrongAnswer3 +" \n ";
            }

        } catch (JSONException e) {e.printStackTrace();}


        System.out.println("Mark: " + stHumanReadable);


    }



    //===========================================================================================
    public void listAllQuestionsAndAnswers(SQLiteDatabase sqLiteDatabase){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ TABLE_QUESTIONS_AND_ANSWERS + " WHERE " + COLUMN_ID_QUESTIONS,null);

        ArrayList<String> arrQAA = new ArrayList<String>();

        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                arrQAA.add(cursor.getString(cursor.getColumnIndex(COLUMN_QUESTIONS)));
                cursor.moveToNext();
            }
        }
    }

    //===========================================================================================
    public void addTables(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(QUESTIONS_AND_ANSWERS_TABLE_CREATE);
    }

    //===========================================================================================

    public OneTableRawData randomizeQuestion(SQLiteDatabase sqLiteDatabase){


        OneTableRawData theRundomRow = null;

        Integer integer = (int) (7 * Math.random());
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ TABLE_QUESTIONS_AND_ANSWERS + " WHERE " + COLUMN_ID_QUESTIONS +" = ?",new String[]{Integer.toString(integer)});

        ArrayList<String> arrQAA = new ArrayList<String>();

        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                arrQAA.add(cursor.getString(cursor.getColumnIndex(COLUMN_QUESTIONS)));
                System.out.println("mark: "+ cursor.getString(cursor.getColumnIndex(COLUMN_QUESTIONS)));

                arrQAA.add(cursor.getString(cursor.getColumnIndex(COLUMN_CORRECT_ANSWER)));
                arrQAA.add(cursor.getString(cursor.getColumnIndex(COLUMN_WRONG_ANSWER0)));
                arrQAA.add(cursor.getString(cursor.getColumnIndex(COLUMN_WRONG_ANSWER1)));
                arrQAA.add(cursor.getString(cursor.getColumnIndex(COLUMN_WRONG_ANSWER2)));



                cursor.moveToNext();
            }

            String question = arrQAA.get(0);
            String correctAnswer = arrQAA.get(1);
            String wrongAnswer0 = arrQAA.get(2);
            String wrongAnswer1 = arrQAA.get(3);
            String wrongAnswer2 = arrQAA.get(4);

            theRundomRow = new OneTableRawData(question,
                    correctAnswer, wrongAnswer0, wrongAnswer1, wrongAnswer2);

            System.out.println("mark: "+ theRundomRow.question);
            System.out.println("mark: "+ theRundomRow.correctAnswer);
            System.out.println("mark: "+ theRundomRow.wrongAnswer0);
            System.out.println("mark: "+ theRundomRow.wrongAnswer1);
            System.out.println("mark: "+ theRundomRow.wrongAnswer2);


            /*
            ArrayList<String> fetchedArr = new ArrayList<String>();
            fetchedArr.add(theRundomRow.correctAnswer);
            fetchedArr.add(theRundomRow.wrongAnswer0);
            fetchedArr.add(theRundomRow.wrongAnswer1);
            fetchedArr.add(theRundomRow.wrongAnswer2);
            */


        }

        else
        {
            System.out.println("Mark: the cursor is empty!");
        }

        return theRundomRow;
    }
    //===========================================================================================





}

