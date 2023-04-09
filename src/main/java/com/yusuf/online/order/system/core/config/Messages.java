package com.yusuf.online.order.system.core.config;

import java.util.Locale;
import java.util.ResourceBundle;
import org.springframework.context.i18n.LocaleContextHolder;

public class Messages {

  public static String getMessageForLocale(String messageKey) {
    final Locale locale = LocaleContextHolder.getLocale();
    return ResourceBundle.getBundle("messages", locale)
        .getString(messageKey);
  }

}

