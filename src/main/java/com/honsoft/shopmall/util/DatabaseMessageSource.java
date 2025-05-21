package com.honsoft.shopmall.util;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import com.honsoft.shopmall.entity.Message;
import com.honsoft.shopmall.repository.MessageRepository;

@Component
public class DatabaseMessageSource implements MessageSource {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return messageRepository
            .findByCodeAndLocale(code, locale.toString())
            .map(Message::getMessage)
            .orElse(defaultMessage);
    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        return messageRepository
            .findByCodeAndLocale(code, locale.toString())
            .map(Message::getMessage)
            .orElseThrow(() -> new NoSuchMessageException(code, locale));
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        for (String code : resolvable.getCodes()) {
            Optional<Message> message = messageRepository.findByCodeAndLocale(code, locale.toString());
            if (message.isPresent()) {
                return message.get().getMessage();
            }
        }
        if (resolvable.getDefaultMessage() != null) {
            return resolvable.getDefaultMessage();
        }
        throw new NoSuchMessageException(Arrays.toString(resolvable.getCodes()), locale);
    }
}
