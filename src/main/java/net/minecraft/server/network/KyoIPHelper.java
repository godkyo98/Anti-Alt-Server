package net.minecraft.server.network;

import java.net.SocketAddress;

public class KyoIPHelper {
    // Hàm này vô tư lấy biến connection vì nó nằm "chung nhà" với Mojang
    public static SocketAddress getAddress(ServerGamePacketListenerImpl handler) {
        return handler.connection.getRemoteAddress();
    }
}