package com.atritripathi.chantsjournal;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class MantraViewModel extends AndroidViewModel {

    private MantraRepository mMantraRepository;
    private LiveData<List<Mantra>> mAllMantras;

    public MantraViewModel(Application application) {
        super(application);

        mMantraRepository = new MantraRepository(application);
        mAllMantras = mMantraRepository.getAllMantras();
    }

    LiveData<List<Mantra>> getAllMantras() {
        return mAllMantras;
    }

    public void insert(Mantra mantra) {
        mMantraRepository.insert(mantra);
    }

    public void deleteMantra(Mantra mantra) {
        mMantraRepository.deleteMantra(mantra);
    }
}
