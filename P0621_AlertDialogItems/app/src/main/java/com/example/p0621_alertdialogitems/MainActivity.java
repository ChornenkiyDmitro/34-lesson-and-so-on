 package com.example.p0621_alertdialogitems;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

 public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";
    final int DIALOG_ITEMS = 1;
    final int DIALOG_ADAPTER = 2;
    final int DIALOG_CURSOR = 3;
    int cnt = 0;
    DB db;
    Cursor cursor;
    String data[] = {"one", "two", "three", "four"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DB(this);
        db.open();
        cursor = db.getAllData();
        startManagingCursor(cursor);
    }
    public void onclick(View v){
        changeCount();
        switch (v.getId()) {
            case R.id.btnItems:
                showDialog(DIALOG_ITEMS);
                break;
            case R.id.btnAdapter:
                showDialog(DIALOG_ADAPTER);
                break;
            case R.id.btnCursor:
                showDialog(DIALOG_CURSOR);
                break;
            default:
                break;
        }
    }

     protected Dialog onCreateDialog(int id) {
         AlertDialog.Builder adb = new AlertDialog.Builder(this);
         switch (id) {
             // массив
             case DIALOG_ITEMS:
                 adb.setTitle(R.string.items);
                 adb.setSingleChoiceItems(data, myClickListener);
                 break;
             // адаптер
             case DIALOG_ADAPTER:
                 adb.setTitle(R.string.adapter);
                 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                         android.R.layout.select_dialog_singlechoice, data);
                 adb.setSingleChoiceItems(adapter, myClickListener);
                 break;
             // курсор
             case DIALOG_CURSOR:
                 adb.setTitle(R.string.cursor);
                 adb.setSingleChoiceItems(cursor, -1, DB.COLUMN_TXT, myClickListener);
                 break;
         }
         return adb.create();
     }
protected void onPrepareDialog(int id, Dialog dialog){
        AlertDialog aDialog = (AlertDialog) dialog;
    ListAdapter LAdapter = aDialog.getListView().getAdapter();
    switch (id){
        case DIALOG_ITEMS:
        case DIALOG_ADAPTER:
            if(LAdapter instanceof BaseAdapter){
                BaseAdapter bAdapter = (BaseAdapter) LAdapter;
                bAdapter.notifyDataSetChanged();
            }break;
        case DIALOG_CURSOR:
            break;
        default:
            break;
    }
};
     // обработчик нажатия на пункт списка диалога или кнопку
     View.OnClickListener myClickListener = new View.OnClickListener();
         public void onClick(DialogInterface dialog, int which) {
            Log.d(LOG_TAG, "which =" + which);
        }
    };
    void changeCount(){
        cnt++;
        data[3] = String.valueOf(cnt);
        db.changeRec(4, String.valueOf(cnt));
    }
    @Override
     protected void onDestroy(){
        super.onDestroy();
        db.close();
    }

    }
