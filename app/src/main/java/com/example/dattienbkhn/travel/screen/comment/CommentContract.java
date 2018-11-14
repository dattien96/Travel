package com.example.dattienbkhn.travel.screen.comment;

import com.example.dattienbkhn.travel.model.app.Comment;
import com.example.dattienbkhn.travel.screen.BaseViewModel;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;

/**
 * Created by tiendatbkhn on 20/03/2018.
 */

public interface CommentContract {

    interface ViewModel extends BaseViewModel {
        void sendComment(Comment cmt);

        void loadCommentOfPlace(int idPlace);

        void onbackClick();

        void onWriteCmtClick();

        void onSendReviewClick();

        void onCancelSendClick();
    }

    interface View extends BaseViewNavigator {
        void goFinish();

        void goToFriendAct(int accId);

        void goToProfileAct(int accId,String accType);
    }
}
