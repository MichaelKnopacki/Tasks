package com.example.tasks.service.repository;

import android.content.Context;

import com.example.tasks.R;
import com.example.tasks.service.constants.TaskConstants;
import com.example.tasks.service.listener.APIListener;
import com.example.tasks.service.model.PriorityModel;
import com.example.tasks.service.repository.local.PriorityDao;
import com.example.tasks.service.repository.local.TaskDatabase;
import com.example.tasks.service.repository.remote.PriorityService;
import com.example.tasks.service.repository.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PriorityRepository extends BaseRepository {

    private PriorityService mPriorityService;
    private PriorityDao mPriorityDao;

    // Instânciando o serviço do Priority
    public PriorityRepository(Context context) {
        super( context );
        this.mPriorityService = RetrofitClient.createService( PriorityService.class );
        this.mPriorityDao = TaskDatabase.getDataBase( context ).priorityDao();
        this.mContext = context;
    }

    // Listando as prioridades
    public void all(APIListener<List<PriorityModel>> listener) {

        if (!super.isConnectionAvailable()){
            listener.onFailure( mContext.getString(R.string.ERROR_INTERNET_CONNECTION)  );
            return;
        }

        Call<List<PriorityModel>> call = this.mPriorityService.all();
        call.enqueue( new Callback<List<PriorityModel>>() {
            @Override
            public void onResponse(Call<List<PriorityModel>> call, Response<List<PriorityModel>> response) {

                if (response.code() == TaskConstants.HTTP.SUCCESS) {
                    listener.onSuccess( response.body() );
                } else {
                    listener.onFailure( handleFailure( response.errorBody() ) );
                }
            }

            @Override
            public void onFailure(Call<List<PriorityModel>> call, Throwable t) {
                listener.onFailure( (mContext.getString( R.string.ERROR_UNEXPECTED )) );
            }
        } );
    }

    public List<PriorityModel> getList() {

        return this.mPriorityDao.list();
    }

    public String getDescription(int id) {
        return this.mPriorityDao.getDescription(id);
    }

    public void save(List<PriorityModel> list) {
        this.mPriorityDao.clear();
        this.mPriorityDao.save( list );
    }
}
