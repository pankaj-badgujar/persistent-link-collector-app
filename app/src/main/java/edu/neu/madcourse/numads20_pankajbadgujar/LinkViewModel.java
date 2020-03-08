package edu.neu.madcourse.numads20_pankajbadgujar;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class LinkViewModel extends AndroidViewModel {

    private LinkRepository repository;
    private LiveData<List<Link>> allLinks;

    public LinkViewModel(@NonNull Application application) {
        super(application);
        repository = new LinkRepository(application);
        allLinks = repository.getAllLinks();
    }

    public void addLink(Link link){
        repository.addLink(link);
    }

    public void updateLink(Link link){
        repository.updateLink(link);
    }

    public void deleteLink(Link link){
        repository.deleteLink(link);
    }

    public void deleteAllLinks(){
        repository.deleteAllLinks();
    }

    public LiveData<List<Link>> getAllLinks() {
        return allLinks;
    }
}
