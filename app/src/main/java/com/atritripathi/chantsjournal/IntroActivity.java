package com.atritripathi.chantsjournal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fragments for the tutorial
        SliderPage sliderPage = new SliderPage();
        sliderPage.setTitle("Chants Journal");
        sliderPage.setTitleColor(getResources().getColor(R.color.black));
        sliderPage.setDescription(getString(R.string.welcome_tutorial));
        sliderPage.setDescColor(getResources().getColor(R.color.black));
        sliderPage.setImageDrawable(R.drawable.buddha_transparent);
        sliderPage.setBgColor(getResources().getColor(R.color.melon));
        addSlide(AppIntroFragment.newInstance(sliderPage));

        SliderPage sliderPage1 = new SliderPage();
        sliderPage1.setTitle("Adding a Mantra");
        sliderPage1.setTitleColor(getResources().getColor(R.color.black));
        sliderPage1.setDescription(getString(R.string.add_mantra_tutorial));
        sliderPage1.setDescColor(getResources().getColor(R.color.black));
        sliderPage1.setImageDrawable(R.drawable.tutorial_add_mantra);
        sliderPage1.setBgColor(getResources().getColor(R.color.melon));
        addSlide(AppIntroFragment.newInstance(sliderPage1));

        SliderPage sliderPage2 = new SliderPage();
        sliderPage2.setTitle("Deleting a Mantra");
        sliderPage2.setTitleColor(getResources().getColor(R.color.black));
        sliderPage2.setDescription(getString(R.string.delete_mantra_tutorial));
        sliderPage2.setDescColor(getResources().getColor(R.color.black));
        sliderPage2.setImageDrawable(R.drawable.tutorial_delete_mantra);
        sliderPage2.setBgColor(getResources().getColor(R.color.melon));
        addSlide(AppIntroFragment.newInstance(sliderPage2));

        SliderPage sliderPage3 = new SliderPage();
        sliderPage3.setTitle("Get Tips & Suggestions");
        sliderPage3.setTitleColor(getResources().getColor(R.color.black));
        sliderPage3.setDescription(getString(R.string.help_button_tutorial));
        sliderPage3.setDescColor(getResources().getColor(R.color.black));
        sliderPage3.setImageDrawable(R.drawable.tutorial_help_button);
        sliderPage3.setBgColor(getResources().getColor(R.color.melon));
        addSlide(AppIntroFragment.newInstance(sliderPage3));

        // Override bar/separator color.
        setBarColor(getResources().getColor(R.color.status_bg));
        setSeparatorColor(getResources().getColor(R.color.status_bg));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}