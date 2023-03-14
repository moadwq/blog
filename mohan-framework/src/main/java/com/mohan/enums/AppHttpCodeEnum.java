package com.mohan.enums;

import lombok.Getter;

/**
 * 响应状态的枚举类
 */
@Getter
public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    NICKNAME_EXIST(502,"昵称已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    REQUIRE_PASSWORD(505, "必需填写密码"),
    REQUIRE_NICKNAME(506, "必需填写昵称"),
    REQUIRE_EMAIL(507, "必需填写邮箱"),
    LOGIN_ERROR(508,"用户名或密码错误"),
    CONTENT_NOT_NULL(509, "评论内容不能为空"),
    FILETYPE_ERROR(510,"文件类型错误，请上传jpg/png/jpeg文件"),

    NO_ADMIN(511,"普通用户不能登录"),
    MENU_FAILED(512, "上级菜单不能是自己"),
    HAVE_CHILDREN_MENU(513, "拥有子菜单，不能删除");

    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }
}
