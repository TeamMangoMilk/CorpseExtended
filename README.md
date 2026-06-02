# Corpse Extended

A NeoForge compatibility mod for Minecraft 1.21.1 that extends the [Corpse](https://modrinth.com/mod/corpse) mod with additional features and configuration options.

> **Requires the Corpse mod** - this is not a fork, and it cannot run without the original mod installed.

## Requirements

| Dependency | Version |
|---|---|
| Minecraft | 1.21.1 |
| NeoForge | 21.1.x |
| [Corpse](https://modrinth.com/mod/corpse) | 1.21.1-1.1.13+ |

## Features

### XP Saving

When a player dies their experience is stored inside the corpse instead of dropping on the floor. Whoever opens the corpse receives the XP.

- Toggle on/off per server (default: on)
- Configure what percentage of XP is returned (default: 100%)
- Server controls how the return message is displayed
- Players can override the message display in their own client config

### Scaled Corpse Rendering

Corpses visually match the scale of the player who died. Compatible with any mod that modifies player size via `Attributes.SCALE`.

### Smarter Item Transfer

When looting another player's corpse, items fill empty slots rather than overwriting your own armour and inventory. The original owner still has their items restored to their original slots.

## Configuration

**Server:** `config/corpse_extended-server.toml`

| Key | Default | Options | Description |
|---|---|---|---|
| `xp_saving.enabled` | `true` | `true` / `false` | Store and return player XP via the corpse |
| `xp_saving.return_percentage` | `100` | `0-100` | Percentage of stored XP given back |
| `xp_message.mode` | `ACTION_BAR` | `OFF`, `CHAT`, `ACTION_BAR` | How the XP return message is shown to players by default |

**Client:** `config/corpse_extended-client.toml` - each player sets their own

| Key | Default | Options | Description |
|---|---|---|---|
| `xp_message.override` | `SERVER` | `SERVER`, `OFF`, `CHAT`, `ACTION_BAR` | Override the server message style. `SERVER` respects the server setting |

## Licence

MIT - see [LICENCE](LICENSE).

The Corpse mod itself is **All Rights Reserved** by [Max Henkel](https://github.com/henkelmax). This mod does not include or redistribute any of its code or assets.
