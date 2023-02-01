package com.example.tasks.service.repository.local;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences
 */
public class SecurityPreferences {

        private SharedPreferences mSharedPreferences;

        public SecurityPreferences(Context context){
            //MODE PRIVATE = Apenas essa aplicação pode ter acesso
            this.mSharedPreferences = context.getSharedPreferences("TasksShared", Context.MODE_PRIVATE);
        }

        //Salva a chave de acessoo
        public void storeString( String key , String value){
            this.mSharedPreferences.edit().putString( key,value ).apply();
        }

        // Busca a chave de acesso
        public String getStoredString( String key ){
            return this.mSharedPreferences.getString( key,"");
        }

        // Remove a chave de acesso
        public void remove(String key) {
            this.mSharedPreferences.edit().remove( key ).apply();
        }

}
