package rey.bos.telegram.bot.shopping.list.bot.handler;

import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import rey.bos.telegram.bot.shopping.list.bot.handler.impl.callback.CallBackCommand;
import rey.bos.telegram.bot.shopping.list.bot.handler.impl.command.MenuCommand;
import rey.bos.telegram.bot.shopping.list.shared.dto.UserDto;

public abstract class BotHandler {

    public static final String BOT_COMMAND_TYPE = "bot_command";

    public abstract boolean handle(Update update, UserDto user);

    public abstract boolean support(Update update);

    public boolean supportMenuCommand(Update update, MenuCommand command) {
        return update.hasMessage() && update.getMessage().hasText()
            && (
                !CollectionUtils.isEmpty(update.getMessage().getEntities())
                && update.getMessage().getEntities()
                    .stream()
                    .filter(entity -> entity.getType().equals(BOT_COMMAND_TYPE))
                    .map(MessageEntity::getText)
                    .anyMatch(command.getCommand()::equals)
        );
    }

    public boolean supportCallbackCommand(Update update, CallBackCommand command) {
        return update.hasCallbackQuery()
            && update.getCallbackQuery().getData().startsWith(command.getCommand());
    }

}
