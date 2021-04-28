package com.example.ourdiary.init;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;



import com.example.ourdiary.db.mydiary.DBManager;
import com.example.ourdiary.main.topic.ITopic;
import com.example.ourdiary.shared.SPFManager;

public class InitTask extends AsyncTask<Long,Void, Boolean> {

    public interface InitCallBack {
        void onInitCompiled(boolean showReleaseNote);
    }

    private InitCallBack callBack;
    private Context mContext;
    boolean showReleaseNote;


    public InitTask(Context context, InitCallBack callBack) {
        this.mContext = context;
        this.callBack = callBack;
        this.showReleaseNote = SPFManager.getReleaseNoteClose(mContext);
    }

    @Override
    protected Boolean doInBackground(Long... longs) {
        try {
            DBManager dbManager = new DBManager(mContext);
            dbManager.opeDB();
            loadSampleData(dbManager);
            updateData(dbManager);
            dbManager.closeDB();
            saveCurrentVersionCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return showReleaseNote;
    }

    @Override
    protected void onPostExecute(Boolean showReleaseNote) {
        super.onPostExecute(showReleaseNote);
        callBack.onInitCompiled(showReleaseNote);
    }

    private void loadSampleData(DBManager dbManager) throws Exception {

        //Because memo function is run in version 6 ,
        //So , if version < 6 , show the sample memo data
        if ( true) {
            //Insert sample topic
            long mitsuhaMemoId = dbManager.insertTopic("ゼッタイ禁止", ITopic.TYPE_MEMO, Color.BLACK);
            long takiMemoId = dbManager.insertTopic("禁止事項 Ver.5", ITopic.TYPE_MEMO, Color.BLACK);

            dbManager.insertTopicOrder(mitsuhaMemoId, 0);
            dbManager.insertTopicOrder(takiMemoId, 1);
            //Insert sample memo
            if (mitsuhaMemoId != -1) {
                dbManager.insertMemoOrder(mitsuhaMemoId,
                        dbManager.insertMemo("女子にも触るな！", false, mitsuhaMemoId)
                        , 0);
                dbManager.insertMemoOrder(mitsuhaMemoId,
                        dbManager.insertMemo("男子に触るな！", false, mitsuhaMemoId)
                        , 1);
                dbManager.insertMemoOrder(mitsuhaMemoId,
                        dbManager.insertMemo("脚をひらくな！", true, mitsuhaMemoId)
                        , 2);
                dbManager.insertMemoOrder(mitsuhaMemoId,
                        dbManager.insertMemo("体は見ない！/触らない！！", false, mitsuhaMemoId)
                        , 3);
                dbManager.insertMemoOrder(mitsuhaMemoId,
                        dbManager.insertMemo("お風呂ぜっっったい禁止！！！！！！！", true, mitsuhaMemoId)
                        , 4);
            }
            if (takiMemoId != -1) {
                dbManager.insertMemoOrder(takiMemoId,
                        dbManager.insertMemo("司とベタベタするな.....", true, takiMemoId)
                        , 0);
                dbManager.insertMemoOrder(takiMemoId,
                        dbManager.insertMemo("奧寺先輩と馴れ馴れしくするな.....", true, takiMemoId)
                        , 1);
                dbManager.insertMemoOrder(takiMemoId,
                        dbManager.insertMemo("女言葉NG！", false, takiMemoId)
                        , 2);
                dbManager.insertMemoOrder(takiMemoId,
                        dbManager.insertMemo("遅刻するな！", true, takiMemoId)
                        , 3);
                dbManager.insertMemoOrder(takiMemoId,
                        dbManager.insertMemo("訛り禁止！", false, takiMemoId)
                        , 4);
                dbManager.insertMemoOrder(takiMemoId,
                        dbManager.insertMemo("無駄つかい禁止！", true, takiMemoId)
                        , 5);
            }
        }



    }

    private void updateData(DBManager dbManager) throws Exception {
        //Photo path modify in version 17


    }

    private void saveCurrentVersionCode() {
        //Save currentVersion
        if (true) {
            showReleaseNote = true;
        }
    }

}
