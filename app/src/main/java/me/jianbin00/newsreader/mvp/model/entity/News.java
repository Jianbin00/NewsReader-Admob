package me.jianbin00.newsreader.mvp.model.entity;

import java.util.List;

/**
 * Jianbin Li
 * 2018/10/4
 */
public class News
{

    /**
     * status : ok
     * totalResults : 10
     * articles : [{"source":{"id":"bbc-news","name":"BBC News"},"author":"BBC News",
     * "title":"China gripped by rare prison break","description":"It is very unusual for
     * an inmate to escape from detention in China, and just as unusual for media to report it.",
     * "url":"http://www.bbc.co.uk/news/world-asia-china-45751426","urlToImage":
     * "https://ichef.bbci.co.uk/news/1024/branded_news/5DF3/production/_103715042_inmates.png",
     * "publishedAt":"2018-10-05T04:02:38Z","content":"Image copyright China National Radio Image
     * caption Zhang Guilin (L) and Wang Lei (R) have escaped a prison in northeastern Liaoning
     * province In China, prison escapes are few and far between. So the case of two men escaping
     * from a Liaoning prison has turned in\u2026 [+4292 chars]"}
     * ...]
     */

    private String status;
    private int totalResults;
    private List<ArticlesBean> articles;

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public int getTotalResults()
    {
        return totalResults;
    }

    public void setTotalResults(int totalResults)
    {
        this.totalResults = totalResults;
    }

    public List<ArticlesBean> getArticles()
    {
        return articles;
    }

    public void setArticles(List<ArticlesBean> articles)
    {
        this.articles = articles;
    }

    public static class ArticlesBean
    {
        /**
         * source : {"id":"bbc-news","name":"BBC News"}
         * author : BBC News
         * title : China gripped by rare prison break
         * description : It is very unusual for an inmate to escape from detention in China, and just as unusual for media to report it.
         * url : http://www.bbc.co.uk/news/world-asia-china-45751426
         * urlToImage : https://ichef.bbci.co.uk/news/1024/branded_news/5DF3/production/_103715042_inmates.png
         * publishedAt : 2018-10-05T04:02:38Z
         * content : Image copyright China National Radio Image caption Zhang Guilin (L) and Wang Lei (R) have escaped a prison in northeastern Liaoning province In China, prison escapes are few and far between. So the case of two men escaping from a Liaoning prison has turned in… [+4292 chars]
         */

        private SourceBean source;
        private String author;
        private String title;
        private String description;
        private String url;
        private String urlToImage;
        private String publishedAt;
        private String content;

        public SourceBean getSource()
        {
            return source;
        }

        public void setSource(SourceBean source)
        {
            this.source = source;
        }

        public String getAuthor()
        {
            return author;
        }

        public void setAuthor(String author)
        {
            this.author = author;
        }

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public String getDescription()
        {
            return description;
        }

        public void setDescription(String description)
        {
            this.description = description;
        }

        public String getUrl()
        {
            return url;
        }

        public void setUrl(String url)
        {
            this.url = url;
        }

        public String getUrlToImage()
        {
            return urlToImage;
        }

        public void setUrlToImage(String urlToImage)
        {
            this.urlToImage = urlToImage;
        }

        public String getPublishedAt()
        {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt)
        {
            this.publishedAt = publishedAt;
        }

        public String getContent()
        {
            return content;
        }

        public void setContent(String content)
        {
            this.content = content;
        }

        public static class SourceBean
        {
            /**
             * id : bbc-news
             * name : BBC News
             */

            private String id;
            private String name;

            public String getId()
            {
                return id;
            }

            public void setId(String id)
            {
                this.id = id;
            }

            public String getName()
            {
                return name;
            }

            public void setName(String name)
            {
                this.name = name;
            }
        }
    }
}
