package com.eezy.kidfacts.util;

import android.content.Context;
import android.media.MediaPlayer;

import com.eezy.kidfacts.R;

public class MediaUtil {

    private boolean mIsSoundAllowed;
    private boolean mIsBackgroundMusic1Allowed;
    private boolean mIsBackgroundMusic2Allowed;

    private MediaPlayer mBackgroundMusic;
    private MediaPlayer mYay1;
    private MediaPlayer mYay2;
    private MediaPlayer mYay3;
    private MediaPlayer mOhNo;
    private MediaPlayer mUhOh1;
    private MediaPlayer mUhOh2;
    private MediaPlayer mUhOh3;
    private MediaPlayer mWow1;
    private MediaPlayer mWow2;
    private MediaPlayer mWow3;

    public MediaUtil(Context context) {
        mIsSoundAllowed = true;
        mIsBackgroundMusic1Allowed = true;
        mIsBackgroundMusic2Allowed = true;
        mBackgroundMusic = MediaPlayer.create(context, R.raw.background_music);
        mBackgroundMusic.setLooping(true);

        mYay1 = MediaPlayer.create(context, R.raw.yay_1);
        mYay2 = MediaPlayer.create(context, R.raw.yay_2);
        mYay3 = MediaPlayer.create(context, R.raw.yay_3);
        mOhNo = MediaPlayer.create(context, R.raw.oh_no);

        mUhOh1 = MediaPlayer.create(context, R.raw.uh_oh_1);
        mUhOh2 = MediaPlayer.create(context, R.raw.uh_oh_2);
        mUhOh3 = MediaPlayer.create(context, R.raw.uh_oh_3);

        mWow1 = MediaPlayer.create(context, R.raw.wow_1);
        mWow2 = MediaPlayer.create(context, R.raw.wow_2);
        mWow3 = MediaPlayer.create(context, R.raw.wow_3);
    }

    public void pause() {
        mBackgroundMusic.pause();
    }

    public void resume() {
        mBackgroundMusic.start();
    }

    public void allowSound(boolean allow) {
        mIsSoundAllowed = allow;
        updateMute();
    }

    public void allowBackgroundMusic1(boolean allow) {
        mIsBackgroundMusic1Allowed = allow;
        updateMute();
    }

    public void allowBackgroundMusic2(boolean allow) {
        mIsBackgroundMusic2Allowed = allow;
        updateMute();
    }

    private void updateMute() {
        if (mIsBackgroundMusic1Allowed && mIsBackgroundMusic2Allowed) {
            mBackgroundMusic.setVolume(1, 1);
        } else {
            mBackgroundMusic.setVolume(0, 0);
        }

        if (mIsSoundAllowed) {
            mYay1.setVolume(1, 1);
            mYay2.setVolume(1, 1);
            mYay3.setVolume(1, 1);
            mOhNo.setVolume(1, 1);
            mUhOh1.setVolume(1, 1);
            mUhOh2.setVolume(1, 1);
            mUhOh3.setVolume(1, 1);
            mWow1.setVolume(1, 1);
            mWow2.setVolume(1, 1);
            mWow3.setVolume(1, 1);
        } else {
            mYay1.setVolume(0, 0);
            mYay2.setVolume(0, 0);
            mYay3.setVolume(0, 0);
            mOhNo.setVolume(0, 0);
            mUhOh1.setVolume(0, 0);
            mUhOh2.setVolume(0, 0);
            mUhOh3.setVolume(0, 0);
            mWow1.setVolume(0, 0);
            mWow2.setVolume(0, 0);
            mWow3.setVolume(0, 0);
        }
    }

    public boolean isSoundAllowed() {
        return mIsSoundAllowed;
    }

    public boolean isBackgroundMusic1Allowed() {
        return mIsBackgroundMusic1Allowed;
    }

    private void say(MediaPlayer what) {
        if (what.isPlaying()) {
            what.seekTo(0);
        } else {
            what.start();
        }
    }
    public void sayYay1() {
        say(mYay1);
    }
    public void sayYay2() {
        say(mYay2);
    }
    public void sayYay3() {
        say(mYay3);
    }
    public void sayOhNo() {
        say(mOhNo);
    }
    public void sayUhOh1() {
        say(mUhOh1);
    }
    public void sayUhOh2() {
        say(mUhOh2);
    }
    public void sayUhOh3() {
        say(mUhOh3);
    }
    public void sayWow1() {
        say(mWow1);
    }
    public void sayWow2() {
        say(mWow2);
    }
    public void sayWow3() {
        say(mWow3);
    }
}
