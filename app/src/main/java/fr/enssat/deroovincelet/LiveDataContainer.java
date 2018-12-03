package fr.enssat.deroovincelet;

import android.arch.lifecycle.LiveData;

public interface LiveDataContainer {
    void changeStatusByVideo(int newDuration);
    void changeStatusByButton(String text);
    LiveData<VideoStatus> getVideoStatus();
}
