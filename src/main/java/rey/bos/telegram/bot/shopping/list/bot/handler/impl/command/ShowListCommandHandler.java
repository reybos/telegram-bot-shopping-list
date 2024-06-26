package rey.bos.telegram.bot.shopping.list.bot.handler.impl.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import rey.bos.telegram.bot.shopping.list.bot.handler.BotHandler;
import rey.bos.telegram.bot.shopping.list.bot.helper.ShoppingListHelper;
import rey.bos.telegram.bot.shopping.list.io.entity.ShoppingList;
import rey.bos.telegram.bot.shopping.list.io.entity.User;
import rey.bos.telegram.bot.shopping.list.io.repository.params.MessageParams;
import rey.bos.telegram.bot.shopping.list.service.MessageShoppingListService;
import rey.bos.telegram.bot.shopping.list.service.ShoppingListService;
import rey.bos.telegram.bot.shopping.list.util.BotUtil;

import java.util.List;

import static rey.bos.telegram.bot.shopping.list.bot.handler.impl.command.MenuCommand.MENU_COMMAND_SHOW_LIST;
import static rey.bos.telegram.bot.shopping.list.dictionary.DictionaryKey.EMPTY_LIST_MESSAGE;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShowListCommandHandler extends BotHandler {

    private final ShoppingListService shoppingListService;
    private final BotUtil botUtil;
    private final TelegramClient telegramClient;
    private final MessageShoppingListService messageShoppingListService;
    private final ShoppingListHelper shoppingListHelper;

    @Override
    public boolean handle(Update update, User user) {
        logCall(user.getId(), MENU_COMMAND_SHOW_LIST.getCommand(), "");
        ShoppingList shoppingList;
        try {
            shoppingList = shoppingListService.findActiveList(user.getId());
        } catch (IllegalStateException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        if (shoppingList.getItems().isEmpty()) {
            botUtil.sendMessageByKey(user.getTelegramId(), user.getLanguageCode(), EMPTY_LIST_MESSAGE);
            return true;
        }
        SendMessage message = shoppingListHelper.buildShoppingListSendMessage(user, shoppingList);
        List<MessageParams> oldMessages;
        try {
            Message sentMessage = telegramClient.execute(message);
            oldMessages = messageShoppingListService.saveShoppingListMessage(
                user.getId(), shoppingList.getId(), sentMessage.getMessageId()
            );
        } catch (TelegramApiException e) {
            log.error("Can't execute command", e);
            return false;
        }
        for (MessageParams messageParams : oldMessages) {
            DeleteMessage deleteMessage = DeleteMessage.builder()
                .chatId(messageParams.getTelegramId())
                .messageId(messageParams.getMessageId())
                .build();
            botUtil.executeMethod(deleteMessage);
        }
        return true;
    }

    @Override
    public boolean support(Update update) {
        return supportMenuCommand(update, MENU_COMMAND_SHOW_LIST);
    }

}
