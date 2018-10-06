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
package me.jianbin00.newsreader.app;

import android.content.Context;
import android.net.ParseException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import me.jessyan.art.utils.ArtUtils;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import me.jianbin00.newsreader.R;
import retrofit2.HttpException;
import timber.log.Timber;

/**
 * ================================================
 * implement {@link ResponseErrorListener} to deal with network and http error.
 * 配置{@link ResponseErrorListener}处理网络错误。
 * Created by Jianbin Li on 10/04/2018
 * ================================================
 */
public class ResponseErrorListenerImpl implements ResponseErrorListener
{

    @Override
    public void handleResponseError(Context context, Throwable t)
    {
        Timber.tag("Catch-Error").w(t.getMessage());
        //这里不光只能打印错误, 还可以根据不同的错误做出不同的逻辑处理
        //这里只是对几个常用错误进行简单的处理, 展示这个类的用法, 在实际开发中请您自行对更多错误进行更严谨的处理
        String msg = context.getString(R.string.unknown_error);
        if (t instanceof UnknownHostException)
        {
            msg = context.getString(R.string.network_unavailable);
        } else if (t instanceof SocketTimeoutException)
        {
            msg = context.getString(R.string.request_timeout);
        } else if (t instanceof HttpException)
        {
            HttpException httpException = (HttpException) t;
            msg = convertStatusCode(context, httpException);
        } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException || t instanceof JsonIOException)
        {
            msg = context.getString(R.string.data_parsing_error);
        }
        ArtUtils.snackbarText(msg);
    }

    private String convertStatusCode(Context context, HttpException httpException)
    {
        String msg;

        switch (httpException.code())
        {
            case 500:
                msg = context.getString(R.string.internal_server_error);
                break;
            case 404:
                msg = context.getString(R.string.not_found);
                break;
            case 403:
                msg = context.getString(R.string.forbidden);
                break;
            case 429:
                msg = context.getString(R.string.too_many_requests);
                break;
            case 401:
                msg = context.getString(R.string.unauthorized);
                break;
            case 400:
                msg = context.getString(R.string.bad_request);
                break;
            case 307:
                msg = context.getString(R.string.temporary_redirect);
                break;
            default:
                msg = httpException.message();
                break;
        }
        return msg;
    }
}
