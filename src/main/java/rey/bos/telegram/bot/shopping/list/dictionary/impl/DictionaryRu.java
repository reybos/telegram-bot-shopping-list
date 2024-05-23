package rey.bos.telegram.bot.shopping.list.dictionary.impl;

import org.springframework.stereotype.Component;
import rey.bos.telegram.bot.shopping.list.dictionary.Dictionary;
import rey.bos.telegram.bot.shopping.list.dictionary.DictionaryKey;
import rey.bos.telegram.bot.shopping.list.io.LanguageCode;

import java.util.HashMap;
import java.util.Map;

import static rey.bos.telegram.bot.shopping.list.dictionary.DictionaryKey.*;

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
        dictionary.put(JOIN_COMMAND_MESSAGE, """
            Чтобы объединить список покупок с другим пользователем, пришлите боту логин другого пользователя, например @login, мы отправим ему запрос на объединение.
            При объединении списков вы будете использовать общий список с другим пользователем, ваш текущий список станет неактивным.
            """
        );
        dictionary.put(ERROR_TOO_MANY_MENTION_IN_JOIN, """
            Вы указали слишком много пользователей в запросе, можно присоединиться только к одному
            """
        );
        dictionary.put(ERROR_HAS_ACTIVE_JOIN_REQUEST, """
            Вы уже сделали запросы на объединение с пользователями: %s, дождитесь от них подтверждения, либо отмените текущие заявки что бы сделать новую.
                    
            Отменить текущие заявки?
            """
        );
        dictionary.put(REJECT_JOINING_PROCCESS, """
            Создание заявки на объединение списков отменено.
            """
        );
        dictionary.put(ACTIVE_JOIN_REQUEST_CLEARED, """
            Активные запросы на объединение списков отменены.
            Пожалуйста, пришлите повторно логин пользователя с которым хотите объединить списки.
            """
        );
        dictionary.put(CONFIRM_MSG, """
            Да ✅"""
        );
        dictionary.put(REJECT_MSG, """
            Нет ❌"""
        );
        dictionary.put(USER_NOT_EXIST, """
            %s не найден среди пользователей бота, либо заблокировал его.
            
            Чтобы объединиться с пользователем в группу, он должен быть активным пользователем бота @shoppy_guru_bot
            """
        );
        dictionary.put(ERROR_SENDER_IS_OWNER_ACTIVE_GROUP, """
            Сейчас вы состоите в одной группе с %s, владельцем которой являетесь.
            Чтобы вступить в группу с %s, нужно распустить текущую, после этого %s будет использовать свой собственный список покупок.
            
            Распустить текущую группу?
            """
        );
        dictionary.put(ERROR_SENDER_IS_MEMBER_OF_GROUP, """
            Сейчас вы состоите в одной группе с %s.
            Чтобы вступить в группу с %s, нужно выйти из текущей, после этого текущий список покупок будет не доступен.
            
            Выйти из группы?
            """
        );
        dictionary.put(OWNER_ACCEPT_JOIN_REQUEST_WITHOUT_ACTIVE_GROUP, """
            Пользователь %s хочет использовать с вами общий список покупок.
            Если вы примете запрос, вы станете владельцем списка и сможете добавлять других пользователей в группу.
            
            Принимаете запрос?
            """
        );
        dictionary.put(OWNER_ACCEPT_JOIN_REQUEST_WITH_OWN_ACTIVE_GROUP, """
            Пользователь %s хочет использовать с вами общий список покупок.
            Но вы уже ведете список с %s. Если примите запрос, будете пользоваться одним списком все вместе.
            
            Принимаете запрос?
            """
        );
        dictionary.put(OWNER_ACCEPT_JOIN_REQUEST_WITH_ACTIVE_GROUP, """
            Пользователь %s хочет использовать с вами общий список покупок.
            Если вы примете запрос, вы станете владельцем списка и сможете добавлять других пользователей в группу. Также вы покинете текущую группу c %s и перестанете использовать текущий список покупок.
            
            Принимаете запрос?
            """
        );
        dictionary.put(SEND_JOIN_REQUEST_SUCCESS, """
            Запрос на объединение списков отправлен пользователю %s, заявка будет действовать в течении 1 суток. 
            Если за это время она не будет принята, нужно будет отправить запрос повторно.
            """
        );
        dictionary.put(CANT_SEND_MESSAGE, """
            %s заблокировал бота. Попросите пользователя начать им пользоваться @shoppy_guru_bot и повторите запрос
            """
        );
        dictionary.put(CANT_FIND_ACTIVE_JOIN_REQUEST, """
            Заявка на объдинение списков не найдена.
            Возможно она уже истекла или была отменена ранее.
            """
        );
        dictionary.put(OWNER_MSG_JOIN_REQUEST_REJECTED, """
            Заявка на объединение списков с пользователем %s отклонена.
            """
        );
        dictionary.put(SENDER_MSG_JOIN_REQUEST_REJECTED, """
            Ваша заявка на объединение списков с пользователем %s была отклонена.
            """
        );
        dictionary.put(ERROR_MENTION_THEMSELF, """
            Нельзя указывать самого себя в запросе на объединение списков.
            """
        );
        dictionary.put(JOIN_REQUEST_ACCEPTED_OWNER, """
            Вы приняли запрос на объединение списков покупок с пользователем %s.
            
            Теперь вы являетесь владельцем группы и сможете принимать в нее других пользователей, в этом случае вы будете использовать список все вместе.
            """
        );
        dictionary.put(JOIN_REQUEST_ACCEPTED_SENDER, """
            Заявка на объединение списков с %s одобрена, теперь вы используете один список.
            """
        );
        dictionary.put(GREETING_FOR_START, """
            Приветствую тебя, %s! 👋
            Я помогу вам легко вести список покупок. Можете использовать список сами или объединиться с другими пользователями. Добавляйте и редактируйте покупки удобно и быстро!
            
            <b>Что я умею:</b>
            🔸 Любое отправленное сообщение будет добавлено в список. Исключая команды и упоминания пользователей.
            🔸 Вызови команду /list чтобы посмотреть текущий список.
            🔸 Вызови команду /clear и после подтверждения, я очищу весь текущий список.
            🔸 Вызови команду /join и я расскажу как сделать один общий список с другим пользователем.
            🔸 Вызови команду /group чтобы посмотреть с кем ты ведешь один список.
            🔸 Вызови команду /language для смены языка.
            🔸 Вызови команду /request для включения/выключения входящих запросов на объединение списков от других пользователей
            
            Создатель: @reybos
            """
        );
        dictionary.put(CHANGE_LANGUAGE_MESSAGE, """
            <b>Выберите язык бота</b>
            """
        );
        dictionary.put(ENGLISH_LANGUAGE, """
            EN 🇺🇸"""
        );
        dictionary.put(RUSSIAN_LANGUAGE, """
            RU 🇷🇺"""
        );
        dictionary.put(CHANGE_LANGUAGE_SUCCESS, """
            Язык успешно изменен ✅
            """
        );
        dictionary.put(CLEAR_LIST_COMMAND_MESSAGE, """
            Все позиции в текущем списке будут удалены.
            
            Очистить?
            """
        );
        dictionary.put(CLEAR_LIST_REJECTED, """
            Команда очистки списка отменена.
            """
        );
        dictionary.put(CLEAR_LIST_ACCEPTED, """
            Текущий список был очищен.
            """
        );
        dictionary.put(REFRESH_LIST_BUTTON, """
            обновить список %s"""
        );
        dictionary.put(EMPTY_GROUP_MESSAGE, """
            Вы еще не объединились ни с кем в группу.
            Вызовите команду /join, чтобы узнать, как это сделать.
            """
        );
        dictionary.put(OWNER_GROUP_MESSAGE, """
            Вы являетесь владельцем группы и можете удалить из нее участников.
            
            Для удаления из группы нажмите на кнопку с нужным пользователем.
            """
        );
        dictionary.put(MEMBER_GROUP_MESSAGE, """
            Вы ведете один список вместе с %s.
            
            Можете выйти из группы нажав на кнопку ниже.
            """
        );
        dictionary.put(LEAVE_GROUP_BUTTON, """
            покинуть группу 🚜"""
        );
        dictionary.put(LEAVE_GROUP_CONFIRM_MESSAGE, """
            Вы собираетесь выйти из текущей группы и вести собственный список.
            
            Продолжить?
            """
        );
        dictionary.put(USER_LEFT_YOUR_GROUP_MESSAGE, """
            Пользователь %s покинул вашу группу и потерял доступ к вашему списку.
            """
        );
        dictionary.put(LEAVE_GROUP_CANCEL_MESSAGE, """
            Команда выхода из группы отменена.
            """
        );
        dictionary.put(LEFT_GROUP_MESSAGE, """
            Вы покинули группу и теперь ведете свой собственный список ✅
            """
        );
        dictionary.put(REMOVE_USER_FROM_GROUP_CONFIRM_MESSAGE, """
            Удалить %s из группы?
            """
        );
        dictionary.put(REMOVE_FROM_GROUP_CANCEL_MESSAGE, """
            Команда удаления %s из группы отменена.
            """
        );
        dictionary.put(USER_REMOVED_FROM_GROUP_MESSAGE, """
            Пользователь %s удален из группы ✅
            """
        );
        dictionary.put(YOU_REMOVED_FROM_GROUP_MESSAGE, """
            Пользователь %s удалил вас из группы, теперь вы ведете свой собственный список.
            """
        );
        dictionary.put(ERROR_OWNER_CANT_LEAVE_GROUP, """
            Вы являетесь владельцем списка и не можете покинуть группу.
            Удалите всех участников используя команду /show_group и повторите запрос.
            """
        );
        dictionary.put(LEAVE_GROUP_BEFORE_JOIN_SUCCESS_MESSAGE, """
            Вы покинули текущую группу.
            Пожалуйста, пришлите повторно логин пользователя с которым хотите объединить списки.
            """
        );
        dictionary.put(ERROR_MEMBER_CANT_DISBAND_GROUP, """
            Вы являетесь членом группы и не можете ее распустить.
            Покиньте группу используя команду /show_group и повторите запрос.
            """
        );
        dictionary.put(DISBAND_GROUP_SUCCESS_MESSAGE, """
            Вы распустили текущую группу.
            Пожалуйста, пришлите повторно логин пользователя с которым хотите объединить списки.
            """
        );
        dictionary.put(JOIN_REQUEST_CANCELLED, """
            %s отменил заявку на объединение списка с вами.
            """
        );
        dictionary.put(JOIN_REQUEST_EXPIRED_SENDER_MESSAGE, """
            Ваш запрос на объединение списков с %s просрочился.
            Повторите запрос, если необходимо.
            """
        );
        dictionary.put(JOIN_REQUEST_EXPIRED_OWNER_MESSAGE, """
            Заявка на объединение списков от %s просрочилась.
            """
        );
        dictionary.put(OWNER_BLOCKED_BOT_YOU_REMOVED_FROM_GROUP_MESSAGE, """
            %s заблокировал бота, вы исключены из его группы и продолжите использовать собственный список.
            """
        );
        dictionary.put(USER_BLOCKED_BOT_AND_LEFT_YOUR_GROUP_MESSAGE, """
            %s заблокировал бота и покинул вашу группу. Теперь у него нет доступа к вашему списку.
            """
        );
        dictionary.put(INCOMING_REQUEST_SETTING_MESSAGE, """
            Сейчас у вас %s возможность получать запросы на объединение списков от других пользователей.
            
            %s ее?
            """
        );
        dictionary.put(INCOMING_REQUEST_SETTING_CANCEL_MESSAGE, """
            Команда изменения настроек входящих запросов отменена.
            """
        );
        dictionary.put(SWITCHED_JOIN_REQUEST_SETTING_MESSAGE, """
            Запросы на объединение списков от других пользователей %s.
            """
        );
        dictionary.put(OWNER_BLOCK_JOIN_REQUEST_MESSAGE, """
            %s запретил входящие запросы на объединение списков.
            """
        );
    }

}
