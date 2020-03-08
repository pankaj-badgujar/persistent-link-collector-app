package edu.neu.madcourse.numads20_pankajbadgujar;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Link.class}, version = 1)
public abstract class LinkRoomDatabase extends RoomDatabase {

    public abstract LinkDAO linkDao();
    private static LinkRoomDatabase INSTANCE;


    public static synchronized LinkRoomDatabase getDatabase(Context context){
        if (INSTANCE == null){

            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    LinkRoomDatabase.class,
                    "link_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private LinkDAO asyncDao;

        private PopulateDbAsyncTask(LinkRoomDatabase db){
            asyncDao = db.linkDao();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            asyncDao.addLink(new Link("Example Link", "www.google.com"));
            return null;
        }
    }
}
