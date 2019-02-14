package fr.enssat.deroovincelet;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

public class LiveDataContainerImplementation implements LiveDataContainer {

    private MutableLiveData<VideoStatus> videoStatus = new MutableLiveData<>();
    private int previousDuration = 0;

    public LiveDataContainerImplementation() {
        videoStatus.setValue(new VideoStatus(0));
    }

    @Override
    public void changeStatusByVideo(int newDuration) {
        if(newDuration != previousDuration){
            videoStatus.setValue(new VideoStatus(newDuration));
            Log.i("VIDEO_DURATION_CHANGED:",""+newDuration+"s");
            previousDuration = newDuration;
        }
    }

    @Override
    public void changeStatusByButton(String text) {
        int newDuration = 0;
        if(text.equals("TITLE")) newDuration = 25000;
        if(text.equals("BUTTERFLIES")) newDuration = 76000;
        if(text.equals("ASSAULT")) newDuration = 158000;
        if(text.equals("PAYBACK")) newDuration = 275000;
        if(text.equals("CAST")) newDuration = 491000;
        videoStatus.setValue(new VideoStatus(newDuration));
    }

    @Override
    public LiveData<VideoStatus> getVideoStatus() {
        return videoStatus;
    }
}
