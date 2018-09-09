package com.atritripathi.chantsjournal;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class MantraRepository {

    private MantraDao mMantraDao;
    private LiveData<List<Mantra>> mAllMantras;

    public MantraRepository(Application application) {
        MantraDatabase db = MantraDatabase.getDatabase(application);
        mMantraDao = db.mantraDao();
        mAllMantras = mMantraDao.getAllMantras();
    }

    LiveData<List<Mantra>> getAllMantras() {
        return mAllMantras;
    }

    public void insert(Mantra mantra) {
        new insertAsyncTask(mMantraDao).execute(mantra);
    }

    private static class insertAsyncTask extends AsyncTask<Mantra, Void, Void> {

        private MantraDao mAsyncTaskDao;

        insertAsyncTask(MantraDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Mantra... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
