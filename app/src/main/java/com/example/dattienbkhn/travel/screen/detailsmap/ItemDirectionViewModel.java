package com.example.dattienbkhn.travel.screen.detailsmap;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.text.Html;
import android.util.Log;

import com.example.dattienbkhn.travel.network.message.map.direction.Step;

/**
 * Created by dattienbkhn on 05/03/2018.
 */

public class ItemDirectionViewModel extends BaseObservable {
    private Step step;

    public final ObservableField<String> mDirectionName = new ObservableField<>();
    public final ObservableField<String> mDirectionSuggest = new ObservableField<>();

    public ItemDirectionViewModel(Step step) {
        this.step = step;

        String s1 = "" + Html.fromHtml(step.getHtmlInstructions() + "");
        String s2 = "";
        String s3 = "";
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) == '\n') {
                s2 += s1.substring(0, i);
                s3 += s1.substring(i+2);
                break;
            }
        }

        mDirectionName.set(s2);
        mDirectionSuggest.set(s3);
    }

    @Bindable
    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }
}
