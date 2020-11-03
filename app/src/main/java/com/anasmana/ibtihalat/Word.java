package com.anasmana.ibtihalat;

public class Word {
    private String mIbtihalName;

    private int mAudioResourceId;

    public Word(String ibtihalName, int audioResourceId) {
        mIbtihalName = ibtihalName;
        mAudioResourceId = audioResourceId;
    }

    public String getIbtihalName() {
        return mIbtihalName;
    }

    public int getmAudioResourceId() {
        return mAudioResourceId;
    }
}