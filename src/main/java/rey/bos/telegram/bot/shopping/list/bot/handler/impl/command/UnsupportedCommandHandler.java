package rey.bos.telegram.bot.shopping.list.bot.handler.impl.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import rey.bos.telegram.bot.shopping.list.bot.handler.BotHandler;
import rey.bos.telegram.bot.shopping.list.io.entity.User;
import rey.bos.telegram.bot.shopping.list.util.BotUtil;

import java.util.Arrays;

import static rey.bos.telegram.bot.shopping.list.bot.handler.impl.MessageEntityType.BOT_COMMAND;
import static rey.bos.telegram.bot.shopping.list.dictionary.DictionaryKey.UNSUPPORTED_COMMAND;

@Component
@RequiredArgsConstructor
@Slf4j
public class UnsupportedCommandHandler extends BotHandler {

    private final BotUtil botUtil;

    @Override
    public boolean handle(Update update, User user) {
        botUtil.sendMessageByKey(user.getTelegramId(), user.getLanguageCode(), UNSUPPORTED_COMMAND);
        return true;
    }

    @Override
    public boolean support(Update update) {
        return update.hasMessage() && update.getMessage().hasText()
            && (!CollectionUtils.isEmpty(update.getMessage().getEntities())
                && update.getMessage().getEntities()
                    .stream()
                    .anyMatch(entity -> entity.getType().equals(BOT_COMMAND.getDescription()))
                && update.getMessage().getEntities()
                    .stream()
                    .filter(entity -> entity.getType().equals(BOT_COMMAND.getDescription()))
                    .map(MessageEntity::getText)
                    .noneMatch(
                        text -> Arrays.stream(MenuCommand.values()).map(MenuCommand::getCommand).anyMatch(text::equals)
                    )
        );
    }

}
