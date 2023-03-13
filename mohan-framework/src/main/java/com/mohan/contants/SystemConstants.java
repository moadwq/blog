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
     * redis中后台登录用户的userKey的拼接前缀
     */
    public static final String REDIS_ADMIN_USERKEY_PRE = "adminlogin:";
    /**
     * 文章评论类型
     */
    public static final String ARTICLE_COMMENT = "0";

    /**
     * 友链评论类型
     */
    public static final String LINK_COMMENT = "1";
    /**
     * 上传图片类型
     */
    public static final String[] IMG_TYPE = {"jpeg","png","jpg"};
    /**
     * redis中，hash类型的文章id和浏览量 的键
     */
    public static final String ARTICLE_VIEWCOUNT_KEY = "article:viewCount";

    /**
     * 权限类型为菜单
     */
    public static final String MENUTYPE_MENU = "C";
    /**
     * 权限类型为按钮
     */
    public static final String MENUTYPE_BUTTON = "F";

    /**
     * 权限状态为正常
     */
    public static final int MENU_STATUS_NORMAL = 0;
    /**
     * 权限状态为失效
     */
    public static final int MENU_STATUS_DISABLE = 1;
    /**
     * 分类状态为正常
     */
    public static final int CATEGORY_STATUS_NORMAL = 0;
    /**
     * 用户类别是管理员
     */
    public static final String IS_ADMIN = "1";
}
