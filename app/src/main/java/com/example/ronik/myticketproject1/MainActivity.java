package com.example.ronik.myticketproject1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase = null;
    MySQLiteOpenHelper openHelper = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        this.deleteDatabase("AlphaTriv.db");

        openHelper = new MySQLiteOpenHelper(this);

        sqLiteDatabase = openHelper.getWritableDatabase();


        openHelper.dropTable(sqLiteDatabase, "questions_answers");

        openHelper.addTables(sqLiteDatabase);

        openHelper.insertQuestionsAndAnswersManually(sqLiteDatabase);

        TextView tvQ = (TextView) findViewById(R.id.textView);
        MyRadioButton cans = (MyRadioButton) findViewById(R.id.rb0);
        MyRadioButton wansw0 = (MyRadioButton) findViewById(R.id.rb1);
        MyRadioButton wansw1 = (MyRadioButton) findViewById(R.id.rb2);
        MyRadioButton wansw2 = (MyRadioButton) findViewById(R.id.rb3);



        populateTheScreenWithQuestionAndAnswers(tvQ, cans, wansw0, wansw1, wansw2 );

        /*

        OneTableRawData otrd = randomizeAQuestion();

        //OneTableRawData otrd = openHelper.randomizeQuestion(sqLiteDatabase);


        TextView tv = findViewById(R.id.textView);
        tv.setText(otrd.getQuestion());

        tv = findViewById(R.id.rb0);
        tv.setText(otrd.getCorrectAnswer());

        tv = findViewById(R.id.rb1);
        tv.setText(otrd.getWrongAnswer0());

        tv = findViewById(R.id.rb2);
        tv.setText(otrd.getWrongAnswer1());

        tv = findViewById(R.id.rb3);
        tv.setText(otrd.getWrongAnswer2());
        */



        //openHelper.listAllQuestionsAndAnswers(sqLiteDatabase);


        //===========================================================

        registerToGetBroadcastWithPendingIntent();
    }


    /*
    void randomizeQuestionForTheScreen(){
        TextView tvQ = (TextView) findViewById(R.id.textView);
        MyRadioButton cans = (MyRadioButton) findViewById(R.id.rb0);
        MyRadioButton wansw0 = (MyRadioButton) findViewById(R.id.rb1);
        MyRadioButton wansw1 = (MyRadioButton) findViewById(R.id.rb2);
        MyRadioButton wansw2 = (MyRadioButton) findViewById(R.id.rb3);


        ////new AsyncTaskDBRequest(openHelper ,tvQ, cans, wansw0, wansw1, wansw2).execute();

        populateTheScreenWithQuestionAndAnswers(tvQ, cans, wansw0, wansw1, wansw2);

    }*/

    private void populateTheScreenWithQuestionAndAnswers(TextView tvQ,
                                                         MyRadioButton radio0,
                                                         MyRadioButton radio1,
                                                         MyRadioButton radio2,
                                                         MyRadioButton radio3) {
            String st = givemeTheJSON();

            OneTableRawData theRandomizedQuestion = randomizeAQuestion(st);


            // Randomize which radio button will display the correct answer
        Random rand = new Random();
        int x = rand.nextInt(3);


        switch(x)
        {
            case (0):
            {
                radio0.setText(theRandomizedQuestion.getCorrectAnswer());
                radio0.setTheCorrectAnswer(true);
                radio1.setText(theRandomizedQuestion.getWrongAnswer0());
                radio2.setText(theRandomizedQuestion.getWrongAnswer1());
                radio3.setText(theRandomizedQuestion.getWrongAnswer2());
                break;
            }

            case (1):
            {
                radio0.setText(theRandomizedQuestion.getWrongAnswer0());
                radio1.setText(theRandomizedQuestion.getCorrectAnswer());
                radio1.setTheCorrectAnswer(true);
                radio2.setText(theRandomizedQuestion.getWrongAnswer1());
                radio3.setText(theRandomizedQuestion.getWrongAnswer2());
                break;
            }

            case (2):
            {
                radio0.setText(theRandomizedQuestion.getWrongAnswer1());
                radio1.setText(theRandomizedQuestion.getWrongAnswer0());
                radio2.setText(theRandomizedQuestion.getCorrectAnswer());
                radio2.setTheCorrectAnswer(true);
                radio3.setText(theRandomizedQuestion.getWrongAnswer2());
                break;
            }

            case (3):
            {
                radio0.setText(theRandomizedQuestion.getWrongAnswer2());
                radio1.setText(theRandomizedQuestion.getWrongAnswer0());
                radio2.setText(theRandomizedQuestion.getWrongAnswer1());
                radio3.setText(theRandomizedQuestion.getCorrectAnswer());
                radio3.setTheCorrectAnswer(true);
                break;
            }
        }



            tvQ.setText(theRandomizedQuestion.getQuestion());

    }

    private OneTableRawData randomizeAQuestion(String st) {
        ArrayList<OneTableRawData> arr = new ArrayList<OneTableRawData>();
        String stHumanReadable = null;

        JSONArray jsonArray = null;

        // Parse the JSON st into the arrayList
        try {
             jsonArray = new JSONArray(st);
        } catch (JSONException e) {
            e.printStackTrace();
        }

            for(int i=0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = jsonArray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String q = jsonObject.optString("A");
                String ca =jsonObject.optString("B");
                String wa0 = jsonObject.optString("C");
                String wa1 = jsonObject.optString("D");
                String wa2 = jsonObject.optString("E");

                arr.add(new OneTableRawData(q, ca, wa0, wa1, wa2));

                stHumanReadable += "\nNode " + i + " : \n q= " + q +
                        " \n ca= " + ca + " \n wa0= " + wa0 + " \n wa1= " + wa1 + " \n wa2= " + wa2;

            }

            System.out.println("Mark: " +stHumanReadable);


        // x = randomize a number between 0 and arrayList.size
        Random rand = new Random();
        int x = rand.nextInt(arr.size());



        // return the OneTableRawData from the item from the "x" position in the arrayList
        return  arr.get(x);

    }

    /*


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rb0: {
                if (checked) {
                    //
                }
                break;
            }
            case R.id.rb1: {
                if (checked) {
                    //
                }
                break;
            }
            case R.id.rb2: {
                if (checked) {
                    //
                }
                break;
            }
            case R.id.rb3: {
                if (checked) {
                    //
                }
                    break;
            }
        }
    }
    */


    public void registerToGetBroadcastWithPendingIntent()
    {
        Intent intent = new Intent(this, MyReceiver.class);
        intent.putExtra("message", "waiting 15 seconds");
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(this.getApplicationContext(),
                        5, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis()
                        + (15 * 1000), pendingIntent);
    }

    public void close(){
        openHelper.close();
    }


    private String givemeTheJSON() {
        return "[\n" +
                " {\n" +
                "   \"A\": \"Questions\",\n" +
                "   \"B\": \"CorrectAnswer\",\n" +
                "   \"C\": \"WrongAnswer0\",\n" +
                "   \"D\": \"WrongAnswer1\",\n" +
                "   \"E\": \"WrongAnswer2\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What is the English translation for the name of the German automaker Volkswagen?\",\n" +
                "   \"B\": \"People’s car\",\n" +
                "   \"C\": \"Folk's car\",\n" +
                "   \"D\": \"Men's car\",\n" +
                "   \"E\": \"German car\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"With twelve Oscar nominations and three wins, who is the most nominated male actor in Academy Awards history?\",\n" +
                "   \"B\": \"Jack Nicholson\",\n" +
                "   \"C\": \"Leonardo Di Caprio\",\n" +
                "   \"D\": \"Denzel Washington\",\n" +
                "   \"E\": \"Al Pacino\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Tenochtitlan, founded in 1324, is now known as what city?\",\n" +
                "   \"B\": \"Mexico City\",\n" +
                "   \"C\": \"Montevideo\",\n" +
                "   \"D\": \"Santiago\",\n" +
                "   \"E\": \"Brazilia\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Located in southern Siberia, what lake is the deepest and largest freshwater lake in the world?\",\n" +
                "   \"B\": \"Lake Baikal\",\n" +
                "   \"C\": \"Great Slave Lake\",\n" +
                "   \"D\": \"Lake Ontario\",\n" +
                "   \"E\": \"Lake Issyk-Kul\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Name the actor who starred in 142 films including The Quiet Man, The Shootist, The Searchers and Stagecoach\",\n" +
                "   \"B\": \"John Wayne\",\n" +
                "   \"C\": \"Ron Howard\",\n" +
                "   \"D\": \"James Stewart\",\n" +
                "   \"E\": \"Ben Afleck\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What is the oldest film ever made, and when was it made? \",\n" +
                "   \"B\": \"Roundhay Garden Scene made in 1888\",\n" +
                "   \"C\": \"A Trip to the Moon, 1902\",\n" +
                "   \"D\": \"The Execution of Mary Stuart,1895\",\n" +
                "   \"E\": \"Fight Club,1999\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Which actress has won the most Oscars? \",\n" +
                "   \"B\": \"Katharine Hepburn\",\n" +
                "   \"C\": \"Russell Crowe\",\n" +
                "   \"D\": \"Leonardo Di Caprio\",\n" +
                "   \"E\": \"Emma stone\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Name the director of the Lord of the Rings trilogy. \",\n" +
                "   \"B\": \"Peter Jackson.\",\n" +
                "   \"C\": \"Christopher Nolan\",\n" +
                "   \"D\": \"Sam Raimi\",\n" +
                "   \"E\": \"Ivan Gazidis\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Who played Neo in The Matrix? \",\n" +
                "   \"B\": \"Keanu Reeves\",\n" +
                "   \"C\": \"Matt Damon\",\n" +
                "   \"D\": \"Ben Smith\",\n" +
                "   \"E\": \"Michael Nyqvist\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Name the actress whose career began at the age of 3, and who went on to star in films such as Contact, Maverick and The Silence of the Lambs?\",\n" +
                "   \"B\": \"Jodie Foster\",\n" +
                "   \"C\": \"Daniel Foster\",\n" +
                "   \"D\": \"Fisting Foster\",\n" +
                "   \"E\": \"George Foster\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Bray Studios, near Windsor in Berkshire, was home to which famous brand of horror films? \",\n" +
                "   \"B\": \"Hammer Horror\",\n" +
                "   \"C\": \"Shild Horror\",\n" +
                "   \"D\": \"Home Horror\",\n" +
                "   \"E\": \"Main Horror\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Which is the only American state to begin with the letter 'p'? \",\n" +
                "   \"B\": \"Pennsylvania\",\n" +
                "   \"C\": \"Philadelphia\",\n" +
                "   \"D\": \"Phoenix\",\n" +
                "   \"E\": \"Panama\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Name the world's biggest island. \",\n" +
                "   \"B\": \"Greenland\",\n" +
                "   \"C\": \"Neverland\",\n" +
                "   \"D\": \"Ozoki Yaka\",\n" +
                "   \"E\": \"Rain Island\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What is the world's longest river? \",\n" +
                "   \"B\": \"Amazon\",\n" +
                "   \"C\": \"Volga\",\n" +
                "   \"D\": \"Nile \",\n" +
                "   \"E\": \"Niagara\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Name the world's largest ocean.\",\n" +
                "   \"B\": \"Pacific\",\n" +
                "   \"C\": \"Arctic\",\n" +
                "   \"D\": \"South\",\n" +
                "   \"E\": \"Silent\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What is the diameter of Earth?\",\n" +
                "   \"B\": \"8,000 miles\",\n" +
                "   \"C\": \"7,000 miles\",\n" +
                "   \"D\": \"7500 miles\",\n" +
                "   \"E\": \"5000 miles\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Which four British cities have underground rail systems?\",\n" +
                "   \"B\": \"Liverpool\",\n" +
                "   \"C\": \"Cardiff\",\n" +
                "   \"D\": \"Wales\",\n" +
                "   \"E\": \"Marylebone\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What is the capital city of Spain?\",\n" +
                "   \"B\": \"Madrid\",\n" +
                "   \"C\": \"Barcelona\",\n" +
                "   \"D\": \"Valencia\",\n" +
                "   \"E\": \"Seville\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Which country is Prague in? \",\n" +
                "   \"B\": \"Czech Republic\",\n" +
                "   \"C\": \"Netherland\",\n" +
                "   \"D\": \"Sweden\",\n" +
                "   \"E\": \"Denemark\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Which English town was a forerunner of the Parks Movement and the first city in Europe to have a street tram system?\",\n" +
                "   \"B\": \"Birkenhead\",\n" +
                "   \"C\": \"Birdhead\",\n" +
                "   \"D\": \"Headbirk\",\n" +
                "   \"E\": \"Hamburg\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What is the capital of Turkey? \",\n" +
                "   \"B\": \"Ankara\",\n" +
                "   \"C\": \"Kanbara\",\n" +
                "   \"D\": \"Ottawa\",\n" +
                "   \"E\": \"Istanbul\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What is the national animal of China? \",\n" +
                "   \"B\": \"Giant panda\",\n" +
                "   \"C\": \"Dog\",\n" +
                "   \"D\": \"Cat\",\n" +
                "   \"E\": \"Lion\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Which Turkish city has the name of a cartoon character? \",\n" +
                "   \"B\": \"Batman\",\n" +
                "   \"C\": \"Spiderman\",\n" +
                "   \"D\": \"Hulk\",\n" +
                "   \"E\": \"Thanos\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What is the noisiest city in the world? \",\n" +
                "   \"B\": \"Hong Kong\",\n" +
                "   \"C\": \"Tokyo\",\n" +
                "   \"D\": \"Bagdad\",\n" +
                "   \"E\": \"New York\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What is the name of the desert area in Mexico? \",\n" +
                "   \"B\": \"Sonora\",\n" +
                "   \"C\": \"Bonora\",\n" +
                "   \"D\": \"Gonora\",\n" +
                "   \"E\": \"New Mexicana\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What is a very cold part of Russia?\",\n" +
                "   \"B\": \"Siberia\",\n" +
                "   \"C\": \"Moscow\",\n" +
                "   \"D\": \"Antartica\",\n" +
                "   \"E\": \"Saint Peterburg\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"How many continents are there? \",\n" +
                "   \"B\": \"Seven\",\n" +
                "   \"C\": \"Six\",\n" +
                "   \"D\": \"One\",\n" +
                "   \"E\": \"Ten\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"On which Italian island is Palermo? \",\n" +
                "   \"B\": \"Sicily\",\n" +
                "   \"C\": \"Venice\",\n" +
                "   \"D\": \"Milano\",\n" +
                "   \"E\": \"Southampton\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"How many time zones are there in the world? \",\n" +
                "   \"B\": \"Twenty-four\",\n" +
                "   \"C\": \"Twenty-two\",\n" +
                "   \"D\": \"Twenty-three\",\n" +
                "   \"E\": \"Twenty-five\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Which is the largest desert on earth? \",\n" +
                "   \"B\": \"Sahara\",\n" +
                "   \"C\": \"Bahara\",\n" +
                "   \"D\": \"Sing Min Yun\",\n" +
                "   \"E\": \"DesertStorm\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Which country did once have the name Rhodesia? \",\n" +
                "   \"B\": \"Zimbabwe\",\n" +
                "   \"C\": \"South Africa\",\n" +
                "   \"D\": \"Ethiopia\",\n" +
                "   \"E\": \"Zanzibar\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"How many players has a hockey team got on the ice? \",\n" +
                "   \"B\": \"Six\",\n" +
                "   \"C\": \"Seven\",\n" +
                "   \"D\": \"Eight\",\n" +
                "   \"E\": \"Eleven\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What was the Olympic city of 1992 ? \",\n" +
                "   \"B\": \"Barcelona\",\n" +
                "   \"C\": \"Madrid\",\n" +
                "   \"D\": \"Stockholm\",\n" +
                "   \"E\": \"Berlin\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What is the largest stadium in the world ? \",\n" +
                "   \"B\": \"Azteca Stadium in Mexico city\",\n" +
                "   \"C\": \"Camp Nou\",\n" +
                "   \"D\": \"Emirates Stadium\",\n" +
                "   \"E\": \"Alianz Arena\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What is the name of the Barcelona football stadium ? \",\n" +
                "   \"B\": \"Camp Nou\",\n" +
                "   \"C\": \"Baranbeau\",\n" +
                "   \"D\": \"Barcelona Stadium\",\n" +
                "   \"E\": \"The Ancient Stadium\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What is the capital of Ecuador? \",\n" +
                "   \"B\": \"Quito\",\n" +
                "   \"C\": \"Santiago\",\n" +
                "   \"D\": \"Caracas\",\n" +
                "   \"E\": \"Brazilia\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What is the capital of Turkmenistan? \",\n" +
                "   \"B\": \"Ashgabat\",\n" +
                "   \"C\": \"Islamabad\",\n" +
                "   \"D\": \"Tashkent\",\n" +
                "   \"E\": \"New Turkey\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Complete this phrase. As sick as a...\",\n" +
                "   \"B\": \"Parrot\",\n" +
                "   \"C\": \"Penguin\",\n" +
                "   \"D\": \"Puffin\",\n" +
                "   \"E\": \"Partridge\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Which legal document states a person's wishes regarding the disposal of their property after death?\",\n" +
                "   \"B\": \"Will\",\n" +
                "   \"C\": \"Shall\",\n" +
                "   \"D\": \"Would\",\n" +
                "   \"E\": \"Should\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Complete the title of the James Bond film The Man With The Golden...\",\n" +
                "   \"B\": \"Gun\",\n" +
                "   \"C\": \"Tooth\",\n" +
                "   \"D\": \"Delicious\",\n" +
                "   \"E\": \"Eagle\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Which of these fruits shares its name with something superior or desirable?\",\n" +
                "   \"B\": \"Plum\",\n" +
                "   \"C\": \"Apricot\",\n" +
                "   \"D\": \"Mango\",\n" +
                "   \"E\": \"Grapefruit\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"In which sport do two teams pull at the opposite ends of a rope?\",\n" +
                "   \"B\": \" Tug of war\",\n" +
                "   \"C\": \"Ice hockey\",\n" +
                "   \"D\": \" Basketball\",\n" +
                "   \"E\": \"Polo\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Where would a cowboy wear his chaps?\",\n" +
                "   \"B\": \"On his legs\",\n" +
                "   \"C\": \" On his arms\",\n" +
                "   \"D\": \" On his hands\",\n" +
                "   \"E\": \" On his head\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Which of these zodiac signs is not represented by an animal that grows horns?\",\n" +
                "   \"B\": \"Aquarius\",\n" +
                "   \"C\": \"Capricorn\",\n" +
                "   \"D\": \"Aries\",\n" +
                "   \"E\": \"Taurus\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Sherpas and Gurkhas are native to which country?\",\n" +
                "   \"B\": \"Nepal\",\n" +
                "   \"C\": \"Morocco\",\n" +
                "   \"D\": \"Ecuador \",\n" +
                "   \"E\": \"Russia\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \" Prime Minister Tony Blair was born in which country?\",\n" +
                "   \"B\": \"Scotland\",\n" +
                "   \"C\": \" Northern Ireland \",\n" +
                "   \"D\": \"Wales\",\n" +
                "   \"E\": \"England\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Whose autobiography has the title A Long Walk To Freedom?\",\n" +
                "   \"B\": \"Nelson Mandela\",\n" +
                "   \"C\": \" Ranulph Fiennes \",\n" +
                "   \"D\": \"Mikhail Gorbachev\",\n" +
                "   \"E\": \"Mother Teresa\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Duffle coats are named after a town in which country?\",\n" +
                "   \"B\": \"Belgium\",\n" +
                "   \"C\": \"Holland\",\n" +
                "   \"D\": \"Germany\",\n" +
                "   \"E\": \"Austria\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Complete this stage instruction in Shakespeare's The Winter's Tale: \\\"Exit, pursued by a ...\\\"\",\n" +
                "   \"B\": \"Bear\",\n" +
                "   \"C\": \"Tiger\",\n" +
                "   \"D\": \"Dog  \",\n" +
                "   \"E\": \"Clown\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"The young of which creature is known as a squab?\",\n" +
                "   \"B\": \"Pigeon\",\n" +
                "   \"C\": \"Octopus\",\n" +
                "   \"D\": \"Salmon\",\n" +
                "   \"E\": \"Horse\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \" Who is the patron saint of Spain?\",\n" +
                "   \"B\": \"St James\",\n" +
                "   \"C\": \" St John  \",\n" +
                "   \"D\": \" St Peter\",\n" +
                "   \"E\": \" St Benedict\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Which king was married to Eleanor of Aquitaine?\",\n" +
                "   \"B\": \" Henry II\",\n" +
                "   \"C\": \" Henry V\",\n" +
                "   \"D\": \" Henry I  \",\n" +
                "   \"E\": \" Richard I\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"This shaven-headed American actor of Greek parentage was nominated for an Academy Award in The Birdman of Alcatraz:\",\n" +
                "   \"B\": \"Telly Savalas\",\n" +
                "   \"C\": \"Donald Pleasence \",\n" +
                "   \"D\": \"Samuel L. Jackson\",\n" +
                "   \"E\": \"Yul Brynner\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Which of the following is found inside an \\\"Eskimo Pie\\\"?\",\n" +
                "   \"B\": \"Ice cream\",\n" +
                "   \"C\": \"Chocolate\",\n" +
                "   \"D\": \"Caribou meat\",\n" +
                "   \"E\": \"Goldfish\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What is the only U.S State that only borders one other?\",\n" +
                "   \"B\": \"Maine\",\n" +
                "   \"C\": \"Rhode Island \",\n" +
                "   \"D\": \"Washington\",\n" +
                "   \"E\": \"Florida\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Name the number that is three more than one-fifth of one-tenth of one-half of 5,000.\",\n" +
                "   \"B\": \"53\",\n" +
                "   \"C\": \"103\",\n" +
                "   \"D\": \"503\",\n" +
                "   \"E\": \"108\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What’s the missing number?\",\n" +
                "   \"B\": \"21\",\n" +
                "   \"C\": \"20\",\n" +
                "   \"D\": \"25\",\n" +
                "   \"E\": \"17\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What's the oldest continuously inhabited city in the world?\",\n" +
                "   \"B\": \"Istanbul, Turkey\",\n" +
                "   \"C\": \"Athens, Greece\",\n" +
                "   \"D\": \"Jerusalem\",\n" +
                "   \"E\": \"Damascus, Syria\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What's the only U.S State with a spanish motto?\",\n" +
                "   \"B\": \"California\",\n" +
                "   \"C\": \"Idaho\",\n" +
                "   \"D\": \"Montana\",\n" +
                "   \"E\": \"Arizona\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"The United States animal is a bald eagle… but in this context, “bald” doesn’t mean “hairless.” The “bald” part of the bird’s name comes from an Old English word meaning what?\",\n" +
                "   \"B\": \"White\",\n" +
                "   \"C\": \"Swooping\",\n" +
                "   \"D\": \"Majestic\",\n" +
                "   \"E\": \"Beautiful\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Name one primary colours.\",\n" +
                "   \"B\": \"Red\",\n" +
                "   \"C\": \"Orange\",\n" +
                "   \"D\": \"Black\",\n" +
                "   \"E\": \"White\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What is the painting 'La Gioconda' more usually known as? \",\n" +
                "   \"B\": \"The Mona Lisa.\",\n" +
                "   \"C\": \"The Starry Night\",\n" +
                "   \"D\": \"Salvator Mundi\",\n" +
                "   \"E\": \"The Milkmaid\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What does the term 'piano' mean? \",\n" +
                "   \"B\": \"To be played softly.\",\n" +
                "   \"C\": \"To be played rough\",\n" +
                "   \"D\": \"To be played quietly\",\n" +
                "   \"E\": \"To be played sharply\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Name the Spanish artist, sculptor and draughtsman famous for co-founding the Cubist movement. \",\n" +
                "   \"B\": \"Pablo Picasso.\",\n" +
                "   \"C\": \"Leonardo da Vinci\",\n" +
                "   \"D\": \"Diego Velázquez\",\n" +
                "   \"E\": \"Vincent van Gogh\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"How many valves does a trumpet have? \",\n" +
                "   \"B\": \"Three\",\n" +
                "   \"C\": \"Two\",\n" +
                "   \"D\": \"Four\",\n" +
                "   \"E\": \"Only One\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Which singer’s real name is Stefani Joanne Angelina Germanotta?\",\n" +
                "   \"B\": \"Lady Gaga\",\n" +
                "   \"C\": \"Rihanna\",\n" +
                "   \"D\": \"Sia\",\n" +
                "   \"E\": \"Gwen Stefani\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"How many players are there in a baseball team?\",\n" +
                "   \"B\": \"Nine\",\n" +
                "   \"C\": \"Eight\",\n" +
                "   \"D\": \"Seven\",\n" +
                "   \"E\": \"Ten\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"The average human body contains how many pints of blood?\",\n" +
                "   \"B\": \"Nine\",\n" +
                "   \"C\": \"Ten\",\n" +
                "   \"D\": \"Twenty\",\n" +
                "   \"E\": \"Five\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Which is the highest waterfall in the world?\",\n" +
                "   \"B\": \"Angel Falls\",\n" +
                "   \"C\": \"Victoria Falls\",\n" +
                "   \"D\": \"Saltos del Mocono\",\n" +
                "   \"E\": \"Niagara Falls\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Which of the planets is closest to the sun?\",\n" +
                "   \"B\": \"Mercury\",\n" +
                "   \"C\": \"Moon\",\n" +
                "   \"D\": \"Earth\",\n" +
                "   \"E\": \"Mars\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"According to Greek mythology who was the first woman on earth?\",\n" +
                "   \"B\": \"Pandora\",\n" +
                "   \"C\": \"Afrodita\",\n" +
                "   \"D\": \"Venom\",\n" +
                "   \"E\": \"Giselle\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"In the sport of Judo, what color belt follows an orange belt?\",\n" +
                "   \"B\": \"Green\",\n" +
                "   \"C\": \"Blue\",\n" +
                "   \"D\": \"Black\",\n" +
                "   \"E\": \"White\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"How many letters are there in the German alphabet?\",\n" +
                "   \"B\": \"30\",\n" +
                "   \"C\": \"29\",\n" +
                "   \"D\": \"31\",\n" +
                "   \"E\": \"27\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Little Cuba is the nickname of which US city?\",\n" +
                "   \"B\": \"Miami\",\n" +
                "   \"C\": \"Los Angeles\",\n" +
                "   \"D\": \"Venice\",\n" +
                "   \"E\": \"Florida\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What is the capital city of Peru?\",\n" +
                "   \"B\": \"Lima\",\n" +
                "   \"C\": \"Buenos Aires\",\n" +
                "   \"D\": \"Quito\",\n" +
                "   \"E\": \"Caracas\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"In which year did the Bay of Pigs invasion take place?\",\n" +
                "   \"B\": \"1961\",\n" +
                "   \"C\": \"1964\",\n" +
                "   \"D\": \"1959\",\n" +
                "   \"E\": \"1966\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"?Professional footballer Lionel Messi was born in which country\",\n" +
                "   \"B\": \"Argentina\",\n" +
                "   \"C\": \"Brazil\",\n" +
                "   \"D\": \"Spain\",\n" +
                "   \"E\": \"Sweden\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What was the name of the first manned mission to land on the moon?\",\n" +
                "   \"B\": \"Apollo 11\",\n" +
                "   \"C\": \"Apollo 10\",\n" +
                "   \"D\": \"Apollo 12\",\n" +
                "   \"E\": \"Moon 11\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"In the game of chess, how many pawns does each player start with?\",\n" +
                "   \"B\": \"Eight\",\n" +
                "   \"C\": \"Seven\",\n" +
                "   \"D\": \"Nine\",\n" +
                "   \"E\": \"Ten\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What temperature does water boil at? \",\n" +
                "   \"B\": \"100C\",\n" +
                "   \"C\": \"120C\",\n" +
                "   \"D\": \"280C\",\n" +
                "   \"E\": \"92.888C\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Where was Lope de Vega born? \",\n" +
                "   \"B\": \"Madrid\",\n" +
                "   \"C\": \"Barcelona\",\n" +
                "   \"D\": \"Valencia\",\n" +
                "   \"E\": \"London\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What year did the Spanish Civil War end? \",\n" +
                "   \"B\": \"1939\",\n" +
                "   \"C\": \"1941\",\n" +
                "   \"D\": \"1943\",\n" +
                "   \"E\": \"1963\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \" When did the First World War start? \",\n" +
                "   \"B\": \"1914\",\n" +
                "   \"C\": \"1913\",\n" +
                "   \"D\": \"1939\",\n" +
                "   \"E\": \"1947\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Where is the smallest bone in the body?\",\n" +
                "   \"B\": \"Ear\",\n" +
                "   \"C\": \"Nose\",\n" +
                "   \"D\": \"Butt\",\n" +
                "   \"E\": \"Leg\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What colour is a panda?\",\n" +
                "   \"B\": \"Black and White\",\n" +
                "   \"C\": \"Black \",\n" +
                "   \"D\": \"Black and Yellow\",\n" +
                "   \"E\": \"White and Blue\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Who cut Van Gogh’s ear?\",\n" +
                "   \"B\": \"He did\",\n" +
                "   \"C\": \"His Mother\",\n" +
                "   \"D\": \"His Father\",\n" +
                "   \"E\": \"His Wife\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What are the first three words of the bible? \",\n" +
                "   \"B\": \"In The Beginning\",\n" +
                "   \"C\": \"In The End\",\n" +
                "   \"D\": \"In The Last\",\n" +
                "   \"E\": \"In The Name\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"How many children has Queen Elizabeth the Second got? \",\n" +
                "   \"B\": \"4\",\n" +
                "   \"C\": \"3\",\n" +
                "   \"D\": \"2\",\n" +
                "   \"E\": \"0\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"When did the American Civil War end? \",\n" +
                "   \"B\": \"1865\",\n" +
                "   \"C\": \"1965\",\n" +
                "   \"D\": \"1875\",\n" +
                "   \"E\": \"1855\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What’s the capital of Kenya? \",\n" +
                "   \"B\": \"Nairobi\",\n" +
                "   \"C\": \"Kenya City\",\n" +
                "   \"D\": \"Ashgabat\",\n" +
                "   \"E\": \"Ankara\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"How many squares are there on a chess board? \",\n" +
                "   \"B\": \"64\",\n" +
                "   \"C\": \"59\",\n" +
                "   \"D\": \"63\",\n" +
                "   \"E\": \"68\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What language has the most words?\",\n" +
                "   \"B\": \"English\",\n" +
                "   \"C\": \"Russian\",\n" +
                "   \"D\": \"Hebrew\",\n" +
                "   \"E\": \"Spanish\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What money do they use in Japan? \",\n" +
                "   \"B\": \"Yen\",\n" +
                "   \"C\": \"Euro\",\n" +
                "   \"D\": \"Dollar\",\n" +
                "   \"E\": \"Jeeps\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Where was El Greco born? \",\n" +
                "   \"B\": \"Greece\",\n" +
                "   \"C\": \"Cyprus\",\n" +
                "   \"D\": \"Italy\",\n" +
                "   \"E\": \"Turkey\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What year did Elvis Presley die? \",\n" +
                "   \"B\": \"1977\",\n" +
                "   \"C\": \"1974\",\n" +
                "   \"D\": \"1975\",\n" +
                "   \"E\": \"1967\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"Who did Madonna marry? \",\n" +
                "   \"B\": \"Sean Penn\",\n" +
                "   \"C\": \"Sean Paul\",\n" +
                "   \"D\": \"Justin Timberlake\",\n" +
                "   \"E\": \"Jason Derulo\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"When was President Kennedy killed? \",\n" +
                "   \"B\": \"1963\",\n" +
                "   \"C\": \"1965\",\n" +
                "   \"D\": \"1958\",\n" +
                "   \"E\": \"1970\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What nationality was Marco Polo? \",\n" +
                "   \"B\": \"Italian\",\n" +
                "   \"C\": \"Russian\",\n" +
                "   \"D\": \"Spanish\",\n" +
                "   \"E\": \"Turkish\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"who won world cup 2014?\",\n" +
                "   \"B\": \"Germany\",\n" +
                "   \"C\": \"Argentina\",\n" +
                "   \"D\": \"Spain\",\n" +
                "   \"E\": \"France\"\n" +
                " },\n" +
                " {\n" +
                "   \"A\": \"What is PLUTO in solar system?\",\n" +
                "   \"B\": \"Dwarf planet\",\n" +
                "   \"C\": \"a Gnome planet\",\n" +
                "   \"D\": \"a Star\",\n" +
                "   \"E\": \"Country\"\n" +
                " }\n" +
                "]";
    }

    public void checkAnswer (View view)
    {
        boolean theUserIsCorrect = false;

        MyRadioButton temp = (MyRadioButton)view;

        Boolean b1 = temp.isTheCorrectAnswer;
        Boolean b2 = temp.isSelected();
        Boolean b3 = temp.isChecked();








        System.out.println("Mark: " + b1 + " " + b2+ " " + b3);

        // Check which radio button was checked
        MyRadioButton rb0 = (MyRadioButton)findViewById(R.id.rb0);
        MyRadioButton rb1 = (MyRadioButton)findViewById(R.id.rb1);
        MyRadioButton rb2 = (MyRadioButton)findViewById(R.id.rb2);
        MyRadioButton rb3 = (MyRadioButton)findViewById(R.id.rb3);



        if (rb0.isChecked() == true && rb0.isTheCorrectAnswer == true)
        {

            theUserIsCorrect = true;


            System.out.println("Mark: rb 0");
                theUserIsRight(rb0);
        }

        else if (rb1.isChecked() == true && rb1.isTheCorrectAnswer == true)
        {
            theUserIsCorrect = true;

            System.out.println("Mark: rb 1");

            theUserIsRight(rb1);
        }

        else if (rb2.isChecked() == true && rb2.isTheCorrectAnswer == true)
        {
            theUserIsCorrect = true;

            System.out.println("Mark: rb 2");

            theUserIsRight(rb2);
        }

        else if (rb3.isChecked() == true && rb3.isTheCorrectAnswer == true)
        {
            theUserIsCorrect = true;

            System.out.println("Mark: rb 3");

            theUserIsRight(rb3);
        }

        else{
            theUserIsWrong(view);

        }

        if (theUserIsCorrect == true)
        {
            setContentView(R.layout.layout_with_web_view);
            WebView webview = (WebView) findViewById(R.id.webView);
            webview.getSettings().setLoadWithOverviewMode(true);
            webview.getSettings().setUseWideViewPort(true);
            webview.getSettings().setJavaScriptEnabled(true);

            webview.loadUrl("https://bestraveler.000webhostapp.com/");


        }


    }

    private void theUserIsWrong(View view) {

        view.setBackgroundColor(Color.parseColor("#FF0000"));
        Toast.makeText(MainActivity.this, "Sorry, try next time:)", Toast.LENGTH_SHORT).show();

        MainActivity.this.finish();
    }


    /*

    private void theUserIsWrong() {
        // the user was wrong,
        // change his selected answer to red
        // Check which radio button was selected
        MyRadioButton rb0 = (MyRadioButton)findViewById(R.id.rb0);
        MyRadioButton rb1 = (MyRadioButton)findViewById(R.id.rb1);
        MyRadioButton rb2 = (MyRadioButton)findViewById(R.id.rb2);
        MyRadioButton rb3 = (MyRadioButton)findViewById(R.id.rb3);

        if (rb0.isSelected() == true)
        {
            //  change rb0 background to red
        }

        else if (rb1.isSelected() == true)
        {
            //  change rb1 background to red
        }


        else if (rb2.isSelected() == true)
        {
            //  change rb2 background to red
        }

        else if (rb3.isSelected() == true)
        {
            //  change rb3 background to red
        }
    }
    */


    private void theUserIsRight(MyRadioButton ca) {

        // the user answered correctly,
        //   Change the color of the correct answer on the screen to green

        ca.setBackgroundColor(Color.parseColor("#00FF00"));


    }

}
