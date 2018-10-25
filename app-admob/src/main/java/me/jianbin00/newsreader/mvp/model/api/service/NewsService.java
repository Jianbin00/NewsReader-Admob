/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.jianbin00.newsreader.mvp.model.api.service;

import io.reactivex.Observable;
import me.jianbin00.newsreader.BuildConfig;
import me.jianbin00.newsreader.mvp.model.entity.NewsResponse;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于用户的一些 API
 * <p>
 * Created by JessYan on 08/05/2016 12:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface NewsService
{
    String API_HEADER = "X-Api-Key";
    String API_KEY = BuildConfig.NEWSAPI_KEY;

    /*@Headers({API_HEADER + API_KEY})
    @GET("/v2/top-headlines")
    Observable<List<Article>> getTopNewsFromSource(@Query("sources") String source,@Query("pageSize")int pageSize);*/

    @Headers(API_HEADER + ":" + API_KEY)
    @GET("v2/top-headlines")
    Observable<NewsResponse> getTopNewsFromCountry(@Query("country") String country, @Query("page") int page, @Query("pageSize") int pageSize);

    @Headers({API_HEADER + ":" + API_KEY})
    @GET("/v2/top-headlines")
    Observable<NewsResponse> getTopNewsFromLanguage(@Query("language") String language, @Query("page") int page, @Query("pageSize") int pageSize);

    @Headers({API_HEADER + ":" + API_KEY})
    @GET("/v2/top-headlines")
    Observable<NewsResponse> getTopNewsFromCategory(@Query("category") String category, @Query("page") int page, @Query("pageSize") int pageSize);
}
