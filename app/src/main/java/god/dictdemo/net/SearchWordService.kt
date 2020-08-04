package god.dictdemo.net

import god.dictdemo.model.SearchWordResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchWordService {
    @GET("openapi.do?keyfrom=youdao111&key=60638690&type=data&doctype=json&version=1.1")
    fun searchWord(@Query("q") keyword:String): Call<SearchWordResponse>
}