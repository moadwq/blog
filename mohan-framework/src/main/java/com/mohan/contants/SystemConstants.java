package com.mohan.contants;

public class SystemConstants {

    /**
     * 文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     * 文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;
    /**
     * 友链状态为审核通过
     */
    public static final int LINK_STATUS_NORMAL = 0;
    /**
     * redis中userKey的拼接前缀
     */
    public static final String REDIS_USERKEY_PRE = "bloglogin:";

    /**
     * 文章评论类型
     */
    public static final String ARTICLE_COMMENT = "0";

    /**
     * 友链评论类型
     */
    public static final String LINK_COMMENT = "1";
}
