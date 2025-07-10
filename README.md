# LoginManager

一个用于Minecraft Bukkit/Spigot服务器的自动登录管理插件。

## 功能特性

- 自动记录玩家登录密码
- 同IP地址免登录功能
- 异地登录提醒
- 可配置的自动登录时效
- 支持OP账号自动登录控制
- 多种消息提醒方式（聊天框/标题）

## 编译要求

- Java 8 或更高版本
- Gradle 7.6 或更高版本

## 编译方法

1. 克隆或下载项目
2. 在项目根目录执行：
   ```bash
   ./gradlew build
   ```
3. 编译完成的插件文件位于 `build/libs/` 目录

## 安装

1. 将编译好的 `.jar` 文件放入服务器的 `plugins` 目录
2. 重启服务器或使用插件管理器重载插件
3. 根据需要修改 `plugins/LoginManager/config.yml` 配置文件

## 配置说明

- `max_time`: 下线后多少分钟内同IP免登录（0为不限时间）
- `useon_op`: 自动登录是否可用于OP账号
- `lagging_enabled`: 滞后执行时间（tick）
- `useaddress_alert`: 启用异地登录提醒
- `message_type`: 提醒方式（title/message/none）
- `login_command`: 登录插件的登录指令

## 命令

- `/loginmanager reload` - 重载配置文件
- `/loginmanager lookup <player>` - 查看玩家登录信息
- `/loginmanager remove <player>` - 删除玩家登录信息

## 权限

- `loginmanager.admin` - 插件管理权限（默认：OP）

## 作者
* [nanfans](https://github.com/NanFans)（原作者）
* [AurLemon](https://github.com/AurLemon)（二次修改）
