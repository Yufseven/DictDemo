package god.dictdemo.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private var BASE_URL = "http://fanyi.youdao.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun baseUrl(url: String): ServiceCreator {
        this.BASE_URL = url
        return this
    }

    fun baseUrl() = BASE_URL

    // ================================================================================================
    fun <T> create(serviceJava: Class<T>): T =
        retrofit.create(serviceJava)    // 使用: ServiceCreator.create(AppService::class.java)

    inline fun <reified T> create(): T =
        create(T::class.java)           // 使用: ServiceCreator.create<AppService>()
}