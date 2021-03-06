package com.emir.gjg.constant;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

public class MailConstants {
    //HEADER
    public static final String CONFIRM_ACCOUNT_HEADER = " Hesabınızı Aktifleştirin";
    public static final String RESET_PASSWORD_HEADER  = "GoodJobGames Şifre Değişiklik Talebi";

    //BODY
    public static final String CONFIRM_ACCOUNT_BODY = "Hesabınızın aktifleştirilmesi için linke tıklayınız: ";
    public static final String RESET_PASSWORD_BODY  = "Şifrenizi yenilemek için tıklayınız: ";

    //URL
    public static final String CONFIRM_ACCOUNT_URL = "/kayit-onay?token=%s";
    public static final String RESET_PASSWORD_URL  = "/hesap/sifre-yenile?activationToken=%s" ;
    public static final String CLIENT_URL = "https://client-url.co/";

}
