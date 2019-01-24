package com.team12.warofwords.DownloadBook;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Way yan on 11/5/2018.
 */

public interface FileDownloadClient {

    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String url);
}
