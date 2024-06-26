package rey.bos.telegram.bot.shopping.list.bot.handler.impl.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import rey.bos.telegram.bot.shopping.list.io.entity.User;
import rey.bos.telegram.bot.shopping.list.io.entity.UserShoppingList;
import rey.bos.telegram.bot.shopping.list.service.UserService;
import rey.bos.telegram.bot.shopping.list.service.UserShoppingListService;
import rey.bos.telegram.bot.shopping.list.util.BotUtil;
import rey.bos.telegram.bot.shopping.list.util.MessageUtil;

import java.util.List;
import java.util.Optional;

import static rey.bos.telegram.bot.shopping.list.bot.handler.impl.callback.CallBackCommand.LEAVE_GROUP;
import static rey.bos.telegram.bot.shopping.list.dictionary.DictionaryKey.*;

@Slf4j
@Component
public class LeaveGroupHandler extends BotHandlerDecision {

    private final UserShoppingListService userShoppingListService;
    private final BotUtil botUtil;
    private final UserService userService;

    public LeaveGroupHandler(
        MessageUtil messageUtil, UserShoppingListService userShoppingListService,
        BotUtil botUtil, UserService userService
    ) {
        super(LEAVE_GROUP, messageUtil);
        this.userShoppingListService = userShoppingListService;
        this.botUtil = botUtil;
        this.userService = userService;
    }

    @Override
    public boolean handleAccept(User user, int messageId, long callbackId) {
        List<UserShoppingList> lists = userShoppingListService.findActiveGroupByUserId(user.getId());
        userShoppingListService.restoreMainList(user.getId());
        long ownerId = lists.stream().filter(UserShoppingList::isOwner).toList().get(0).getUserId();
        Optional<User> ownerO = userService.findActiveUserById(ownerId);
        if (ownerO.isPresent()) {
            User owner = ownerO.get();
            String ownerText = botUtil.getText(owner.getLanguageCode(), USER_LEFT_YOUR_GROUP_MESSAGE)
                .formatted(messageUtil.getLogin(user.getUserName()));
            botUtil.sendMessage(owner.getTelegramId(), ownerText);
        }
        EditMessageText message = messageUtil.buildEditMessageText(user, messageId, LEFT_GROUP_MESSAGE);
        botUtil.executeMethod(message);
        return true;
    }

    @Override
    public boolean handleReject(User user, int messageId, long callbackId) {
        EditMessageText message = messageUtil.buildEditMessageText(user, messageId, LEAVE_GROUP_CANCEL_MESSAGE);
        botUtil.executeMethod(message);
        return true;
    }

    @Override
    public boolean support(Update update) {
        return supportCallbackCommand(update, command);
    }

}
