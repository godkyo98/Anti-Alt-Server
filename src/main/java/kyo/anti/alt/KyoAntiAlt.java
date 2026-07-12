package kyo.anti.alt;

import kyo.anti.alt.command.AntiAltCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.network.KyoIPHelper;
import java.net.InetSocketAddress;

public class KyoAntiAlt implements ModInitializer {

	@Override
	public void onInitialize() {
		IPDatabase.load();

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			String ip = getIP(handler);
			String uuid = handler.player.getUUID().toString();
			String name = handler.player.nameAndId().name();

			if (!IPDatabase.checkAndBind(ip, uuid, name)) {
				String owner = IPDatabase.getOwner(ip);
				handler.disconnect(Component.literal(
						"§c[!] TỪ CHỐI TRUY CẬP §c[!]\n\n" +
								"§fĐịa chỉ mạng này đã được khóa độc quyền với tài khoản: §e" + owner + "\n" +
								"§fVui lòng liên hệ Admin để gỡ liên kết nếu bạn muốn dùng nick mới!"
				));
			}
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			AntiAltCommand.register(dispatcher);
		});

		System.out.println("[KyoAntiAlt] Đã kích hoạt cơ chế: 1 IP = 1 UUID!");
	}

	private String getIP(ServerGamePacketListenerImpl handler) {
		// Nhờ Helper lấy giúp IP mà không bị vướng quyền Protected
		var address = KyoIPHelper.getAddress(handler);
		if (address instanceof InetSocketAddress inet) {
			return inet.getAddress().getHostAddress();
		}
		return address.toString();
	}
}