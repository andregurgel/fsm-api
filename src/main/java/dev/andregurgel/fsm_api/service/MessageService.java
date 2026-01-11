package dev.andregurgel.fsm_api.service;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageService {

    private final MessageSource messageSource;

    public MessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String get(String code) {
        return get(code, null, LocaleContextHolder.getLocale());
    }

    public String get(String code, Object... args) {
        return get(code, args, LocaleContextHolder.getLocale());
    }

    public String get(String code, Object[] args, Locale locale) {
        return messageSource.getMessage(code, args, code, locale);
    }
}
