package com.ady.xpbooster.desktop;

/**
 * Created by ADY on 4.5.2017.
 */

public class DummyPlayServices implements com.ady.xpbooster.PlayServices {
    @Override
    public void signIn() {

    }

    @Override
    public void signOut() {

    }

    @Override
    public void rateGame() {

    }

    @Override
    public void unlockAchievement(String str) {

    }

    @Override
    public void submitScore(int highScore) {

    }

    @Override
    public void submitLevel(int highLevel) {

    }

    @Override
    public void showAchievement() {

    }

    @Override
    public void showScore() {

    }

    @Override
    public void showLevel() {

    }

    @Override
    public boolean isSignedIn() {
        return false;
    }

    @Override
    public void showBannerAd() {

    }

    @Override
    public void hideBannerAd() {

    }

    @Override
    public void showInterstitialAd(Runnable then) {

    }

    @Override
    public void showRewardedVideo() {

    }

    @Override
    public boolean isRewardEarned() {
        return false;
    }
}
