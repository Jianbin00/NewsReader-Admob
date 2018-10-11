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
package me.jianbin00.newsreader.mvp.model.entity;

import me.jianbin00.newsreader.mvp.model.api.Api;

/**
 * ================================================
 * 如果你服务器返回的数据格式固定为这种方式(这里只提供思想,服务器返回的数据格式可能不一致,可根据自家服务器返回的格式作更改)
 * 指定范型即可改变 {@code data} 字段的类型, 达到重用 {@link BaseResponse}
 * Created by Jianbin 10/8/2018
 * ================================================
 */
public class BaseResponse<T> //implements Serializable
{
    String status;
    int totalResults;
    T data;

    public String getStatus()
    {
        return status;
    }

    public int getTotalResults()
    {
        return totalResults;
    }

    public T getData()
    {
        return data;
    }

    /**
     * 请求是否成功
     *
     * @return
     */
    public boolean isSuccess()
    {
        if (status.equals(Api.RequestSuccess))
        {
            return true;
        } else
        {
            return false;
        }
    }
}
