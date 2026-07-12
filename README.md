# 🛡️ Kyo Anti-Alt (Standalone Security Module)

**Kyo Anti-Alt** là một Server-side Mod (Mod chạy thuần phía máy chủ) gọn nhẹ, hiệu năng cao được phát triển riêng cho nền tảng **Minecraft 26.2 (Fabric - Mojang Mappings)**. Mod được thiết kế để bảo vệ nền kinh tế máy chủ và triệt tiêu hoàn toàn vấn nạn tạo tài khoản phụ (Clone/Alts) để cày cuốc hoặc phá hoại bằng cơ chế thiết lập thiết diện "Lá chắn IP": **Cố định cứng 1 IP vĩnh viễn với đúng 1 Định danh UUID duy nhất**.

---

## ✨ Các Tính Năng Đột Phá

* **🔒 Cơ Chế Khóa Cứng Độc Quyền (1 IP = 1 UUID):** Tự động bắt mạch địa chỉ mạng IP của người chơi ngay trong lần đăng nhập đầu tiên và trói chặt IP đó vào UUID của tài khoản. 
* **🚫 Bàn Tay Sắt Chặn Đăng Nhập:** Bất kỳ tài khoản thứ hai (hoặc n nick clone) nào cố tình kết nối từ địa chỉ IP đã bị khóa sẽ lập tức bị hệ thống đá văng (Kick) ở màn hình chờ cùng thông báo hiển thị rõ ràng tài khoản chính chủ sở hữu IP đó.
* **🌐 Hoạt Động 100% Server-Side:** Người chơi sử dụng Client Vanilla hoàn toàn không cần cài thêm bất kỳ mod nào. Đặc biệt tương thích hoàn hảo với người chơi Bedrock Edition (Điện thoại, Console) đi qua GeyserMC và Floodgate.
* **⚡ Hiệu Năng Luồng Luôn Tối Ưu:** Hệ thống tra cứu bộ nhớ sử dụng cấu trúc `ConcurrentHashMap` an toàn luồng (Thread-safe), kết hợp cơ chế đọc/ghi bất đồng bộ xuống ổ cứng thông qua thư viện GSON giúp Server giữ vững mốc 60 TPS kể cả khi chịu tải lớn.
* **🛠️ Thân Thiện Quản Trị Viên:** Hỗ trợ lệnh nóng trong game hoặc tại Console để tháo gỡ liên kết IP linh hoạt khi người chơi có nhu cầu đổi nick chính đáng.

---

## 💻 Hệ Thống Lệnh Quản Trị

Hệ thống lệnh sử dụng thư viện xử lý Brigadier gốc của Mojang, đảm bảo tốc độ phản hồi lệnh trong tích tắc.

| Lệnh | Phân Quyền | Chức Năng |
| :--- | :--- | :--- |
| `/unalt <playerName>` | **Chỉ Admin (OP)** | Xóa bỏ hoàn toàn liên kết IP của người chơi được chỉ định khỏi cơ sở dữ liệu để họ có thể đăng nhập bằng tài khoản mới. |

*Lưu ý: Lệnh này có thể gõ trực tiếp từ cửa sổ Console của VPS/Hosting mà không cần vào game.*

---

## 📂 Cơ Sở Dữ Liệu & Cấu Hình (`kyo_anti.alt.json`)

Sau khi khởi chạy Server lần đầu, Mod sẽ tự động sản sinh file lưu trữ dữ liệu tại đường dẫn `config/kyo_antialt.json`. 

File được thiết kế dưới cấu trúc JSON phân cấp cực kỳ tường minh, sếp hoặc các quản trị viên có thể mở ra chỉnh sửa thủ công bằng Notepad khi tắt máy chủ:

```json
{
  "26.165.220.5": {
    "uuid": " ",
    "name": " "
  },
  "112.4.22.19": {
    "uuid": " ",
    "name": " "
  }
}
```