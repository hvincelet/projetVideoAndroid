package fr.enssat.deroovincelet;

import android.arch.lifecycle.LiveData;

public class VideoWebViewModel {
    private LiveDataContainerImplementation liveDataContainer = new LiveDataContainerImplementation();

    public void changeStatusByVideo(int newDuration){
        liveDataContainer.changeStatusByVideo(newDuration);
    }

    public void changeStatusByButton(String text){
        liveDataContainer.changeStatusByButton(text);
    }

    public LiveData<VideoStatus> getVideoStatus(){
        return liveDataContainer.getVideoStatus();
    }

}
