package com.south.worker.data;

import android.support.annotation.NonNull;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * 描述   ：用户模块
 * <p>
 * 作者   ：Created by DuanRui on 2018/5/31.
 */

public class UserRepository  implements UserDataSource{

    private static UserRepository instance;
    @NonNull
    private final UserDataSource mUserRemoteDataSource;

    private UserRepository(@NonNull UserDataSource userRemoteDataSource) {
        mUserRemoteDataSource = checkNotNull(userRemoteDataSource);

    }

    public static UserRepository getInstance(@NonNull UserDataSource userRemoteDataSource) {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository(userRemoteDataSource);
                }
            }
        }
        return instance;
    }
}
