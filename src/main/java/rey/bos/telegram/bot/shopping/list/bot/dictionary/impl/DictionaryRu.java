package rey.bos.telegram.bot.shopping.list.bot.dictionary.impl;

import org.springframework.stereotype.Component;
import rey.bos.telegram.bot.shopping.list.bot.dictionary.Dictionary;
import rey.bos.telegram.bot.shopping.list.bot.dictionary.DictionaryKey;
import rey.bos.telegram.bot.shopping.list.io.LanguageCode;

import java.util.HashMap;
import java.util.Map;

import static rey.bos.telegram.bot.shopping.list.bot.dictionary.DictionaryKey.*;

@Component
public class DictionaryRu implements Dictionary {

    private final LanguageCode languageCode;
    private final Map<DictionaryKey, String> dictionary;

    public DictionaryRu() {
        languageCode = LanguageCode.RU;
        dictionary = new HashMap<>();
        addValues();
    }

    @Override
    public boolean isSuitable(LanguageCode languageCode) {
        return languageCode == this.languageCode;
    }

    @Override
    public String get(DictionaryKey key) {
        return dictionary.get(key);
    }

    private void addValues() {
        dictionary.put(SOMETHING_WENT_WRONG, """
            Что-то пошло не так 😿 Если проблемы сохраняются, напишите создателю бота @reybos, он попробует помочь.
            """
        );
        dictionary.put(ERROR_ITEM_ADD_TOO_LONG_ITEM, """
            Слишком длинное сообщение, должно содержать не более 30 символов.
            """
        );
        dictionary.put(ERROR_ITEM_ADD_TOO_LONG_LIST, """
            В текущем списке слишком много элементов. Удалите ненужные или полностью очистите список с помощью команды /clear_list
            """
        );
        dictionary.put(ACTION_ITEM_ADDED_TO_LIST, """
            Элемент успешно добавлен в список.
            """
        );
        dictionary.put(EMPTY_LIST_MESSAGE, """
            Список пуст, отправьте сообщение боту что бы добавить элемент в список.
            """
        );
        dictionary.put(NOT_EMPTY_LIST_MESSAGE, """
            <b>Ваш список 📋</b>
                    
            Нажмите на любом элементе, чтобы удалить его.
            """
        );
        dictionary.put(UNSUPPORTED_COMMAND, """
            Команда не поддерживается, список доступных команд можно посмотреть в меню или вызвав команду /help 
            """
        );
        dictionary.put(ERROR_EMPTY_MENTION_IN_JOIN, """
            Чтобы объединить списки покупок с другим пользователем, введите команду /join @login, где @login - это имя пользователя telegram, с которым вы хотите объединиться. При объединении вы будете использовать общий список с другим пользователем, ваш текущий список станет неактивным.
            """
        );
        dictionary.put(ERROR_TOO_MANY_MENTION_IN_JOIN, """
            Вы указали слишком много пользователей в запросе, можно присоединиться только к одному
            """
        );
        dictionary.put(ERROR_HAS_JOIN_REQUEST, """
            Вы уже сделали запросы на объединение с пользователями: %s, дождитесь от них подтверждения, либо отмените текущие заявки что бы сделать новую.
                    
            Отменить текущие заявки?
            """
        );
        dictionary.put(CONFIRM_MSG, """
            Да ✅"""
        );
        dictionary.put(REJECT_MSG, """
            Нет ❌"""
        );
        dictionary.put(USER_NOT_EXIST, """
            %s не найден среди пользователей бота. Чтобы объединиться с пользователем в группу, попросите его начать использовать этого бота @shoppy_guru_bot
            """
        );
        dictionary.put(ERROR_OWNER_HAS_ACTIVE_GROUP, """
            Сейчас вы состоите в одной группе с %s, владельцем которой являетесь. Чтобы вступить в группу с %s, нужно распустить текущую, после этого %s будет использовать свой собственный список покупок.
            
            Распустить текущую группу?
            """
        );
        dictionary.put(ERROR_MEMBER_OF_GROUP, """
            Сейчас вы состоите в одной группе с %s. Чтобы вступить в группу с %s, нужно выйти из текущей, после этого текущий список покупок будет не доступен.
            
            Выйти из группы?
            """
        );
        dictionary.put(ACCEPT_JOIN_REQUEST_WITHOUT_ACTIVE_GROUP, """
            Пользователь %s хочет использовать с вами общий список покупок. Если вы примете запрос, вы станете владельцем списка и сможете добавлять других пользователей в группу.
            
            Принимаете запрос?
            """
        );
        dictionary.put(ACCEPT_JOIN_REQUEST_WITH_OWN_ACTIVE_GROUP, """
            Пользователь %s хочет использовать с вами общий список покупок. Но вы уже ведете список с %s. Если примите запрос, будете пользоваться одним списком все вместе.
            
            Принимаете запрос?
            """
        );
        dictionary.put(ACCEPT_JOIN_REQUEST_WITH_ACTIVE_GROUP, """
            Пользователь %s хочет использовать с вами общий список покупок. Если вы примете запрос, вы станете владельцем списка и сможете добавлять других пользователей в группу. Также вы покинете текущую группу c %s и перестанете использовать текущий список покупок.
            
            Принимаете запрос?
            """
        );
        dictionary.put(SEND_JOIN_REQUEST_SUCCESS, """
            Запрос на объединение списков отправлен пользователю %s, заявка будет действовать в течении 1 суток. Если за это время она не будет принята, нужно будет отправить запрос повторно.
            """
        );
        dictionary.put(CANT_SEND_MESSAGE, """
            %s заблокировал бота. Попросите пользователя начать им пользоваться @shoppy_guru_bot и повторите запрос
            """
        );
    }

}
