package edu.neu.madcourse.numads20_pankajbadgujar;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LinkRepository {

    private LinkDAO linkDao;
    private LiveData<List<Link>> allLinks;


    public LinkRepository(Application application){
        LinkRoomDatabase db = LinkRoomDatabase.getDatabase(application);
        linkDao = db.linkDao();
        allLinks = linkDao.getAllLinks();
    }

    public void addLink(Link newLink){
        new InsertLinkAsyncTask(linkDao).execute(newLink);
    }

    public void deleteLink(Link link){
        new DeleteLinkAsyncTask(linkDao).execute(link);
    }

    public void deleteAllLinks(){
        new DeleteAllLinksAsyncTask(linkDao).execute();

    }

    public LiveData<List<Link>> getAllLinks(){
        return allLinks;
    }


    private static class InsertLinkAsyncTask extends
            AsyncTask<Link, Void, Void> {

        private LinkDAO asyncTaskDao;

        private InsertLinkAsyncTask(LinkDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Link... links) {
            asyncTaskDao.addLink(links[0]);
            return null;
        }
    }

    private static class DeleteLinkAsyncTask extends
            AsyncTask<Link, Void, Void> {

        private LinkDAO asyncTaskDao;

        private DeleteLinkAsyncTask(LinkDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Link... links) {
            asyncTaskDao.deleteLink(links[0]);
            return null;
        }
    }

    private static class DeleteAllLinksAsyncTask extends
            AsyncTask<Void, Void, Void> {

        private LinkDAO asyncTaskDao;

        private DeleteAllLinksAsyncTask(LinkDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.deleteAllLinks();
            return null;
        }
    }




}
