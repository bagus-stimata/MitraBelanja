package com.desgreen.mitrabelanja;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.erp.distribution.sfa.model.FMaterialGroup3;
import com.erp.distribution.sfa.security_model.FUser;
import com.erp.distribution.sfa.security_repository.FUserServiceRest;
import com.erp.distribution.sfa.service_rest.FMaterialGroup1ServiceRest;
import com.erp.distribution.sfa.service_rest.FMaterialGroup2ServiceRest;
import com.erp.distribution.sfa.service_rest.FMaterialGroup3ServiceRest;
import com.erp.distribution.sfa.service_rest.FMaterialServiceRest;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    FUserServiceRest fUserServiceRest;
    FMaterialServiceRest fMaterialServiceRest;
    FMaterialGroup1ServiceRest fMaterialGroup1ServiceRest;
    FMaterialGroup2ServiceRest fMaterialGroup2ServiceRest;
    FMaterialGroup3ServiceRest fMaterialGroup3ServiceRest;

    //    private FUserRepository repository;


    private LiveData<List<FUser>> listFUserLive;
    private List<FUser> listFUser = new ArrayList<>();

    protected MutableLiveData<FUser> itemHeader;

    public MainViewModel(@NonNull Application application) {
        super(application);
//        repository = new FUserRepository(application);
//        listFUserLive = repository.getAllFUserLive();

        fUserServiceRest = new FUserServiceRest(application.getApplicationContext());
        fMaterialServiceRest = new FMaterialServiceRest(application.getApplicationContext());
        fMaterialGroup1ServiceRest = new FMaterialGroup1ServiceRest(application.getApplicationContext());
        fMaterialGroup2ServiceRest = new FMaterialGroup2ServiceRest(application.getApplicationContext());
        fMaterialGroup3ServiceRest = new FMaterialGroup3ServiceRest(application.getApplicationContext());

    }

    public FUser insert(FUser fUser) {
//        repository.insert(fUser);
        return fUser;
    }

    public FUser update(FUser fUser) {
//        repository.update(fUser);
        return fUser;
    }
    public FUser delete(FUser fUser) {
//        repository.delete(fUser);
        return fUser;
    }
//    public void deleteAllFUser() {
//        repository.deleteAllFUser();
//    }
//    public LiveData<List<FUser>> getAllFUserLive() {
//        return listFUserLive;
//    }
//    public List<FUser> getAllFUser() {
//        return repository.getAllFUser();
//    }
//
//    public LiveData<FUser> getItemHeader() {
//        return itemHeader;
//    }


    public FUserServiceRest getfUserServiceRest() {
        return fUserServiceRest;
    }

    public FMaterialServiceRest getfMaterialServiceRest() {
        return fMaterialServiceRest;
    }

    public FMaterialGroup3ServiceRest getfMaterialGroup3ServiceRest() {
        return fMaterialGroup3ServiceRest;
    }

    public FMaterialGroup1ServiceRest getfMaterialGroup1ServiceRest() {
        return fMaterialGroup1ServiceRest;
    }

    public FMaterialGroup2ServiceRest getfMaterialGroup2ServiceRest() {
        return fMaterialGroup2ServiceRest;
    }
}
