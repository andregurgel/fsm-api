package dev.andregurgel.fsm_api.commons.infrastructure.util;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

@Configuration
public class I18nConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasenames("classpath:i18n/messages");
        ms.setDefaultEncoding(StandardCharsets.UTF_8.name());
        ms.setFallbackToSystemLocale(false);
        ms.setUseCodeAsDefaultMessage(true);
        return ms;
    }

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver lr = new AcceptHeaderLocaleResolver();
        lr.setDefaultLocale(new Locale("pt", "BR"));
        lr.setSupportedLocales(List.of(new Locale("pt", "BR"), Locale.ENGLISH));
        return lr;
    }
}
