package kyo.anti.alt.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import kyo.anti.alt.IPDatabase;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class AntiAltCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("unalt")
                // Chuẩn kiểm tra OP trên 26.2
                .requires(source -> {
                    if (source.getPlayer() == null) return true; // Console luôn có quyền
                    return source.getServer().getPlayerList().isOp(source.getPlayer().nameAndId());
                })
                .then(Commands.argument("playerName", StringArgumentType.string())
                        .executes(context -> {
                            String target = StringArgumentType.getString(context, "playerName");
                            boolean found = false;

                            // Quét toàn bộ DB và xóa IP chứa tên người chơi này
                            var iterator = IPDatabase.db.entrySet().iterator();
                            while (iterator.hasNext()) {
                                var entry = iterator.next();
                                if (entry.getValue().name.equalsIgnoreCase(target)) {
                                    iterator.remove();
                                    found = true;
                                }
                            }

                            if (found) {
                                IPDatabase.save();
                                context.getSource().sendSystemMessage(Component.literal("§a[+] Đã gỡ liên kết IP của người chơi: §e" + target));
                            } else {
                                context.getSource().sendSystemMessage(Component.literal("§c[!] Không tìm thấy dữ liệu IP của: " + target));
                            }
                            return 1;
                        })
                )
        );
    }
}