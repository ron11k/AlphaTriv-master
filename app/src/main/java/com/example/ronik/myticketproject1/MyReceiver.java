package com.example.ronik.myticketproject1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        System.out.println(" onReceive!");

        String theMessageFromTheIntent = intent.getStringExtra("message");
        if (theMessageFromTheIntent != null)
        {
            Toast.makeText(context, "onReceive received the message: "+
                    theMessageFromTheIntent, Toast.LENGTH_LONG).show();
        }



        Bundle IntentExtras = intent.getExtras();
        String st2 = IntentExtras.getString("message");
        boolean bool2 = false;
        if (st2 != null)
            bool2 = st2.equals("waiting 15 seconds");
        if(bool2 == true)
        {
            Toast.makeText(context, "15 seconds passed"
                    , Toast.LENGTH_LONG).show();

            // todo: Here goes your code for "your time to answer is up"
        }

    }

}

