# X-PRISON API - Custom Enchants Example
Example usage for developers how to create Custom Enchants into X-Prison

**Links:**
- [SpigotMC (Download)](https://www.spigotmc.org/resources/86845/)
- [Discord (Support)](https://discord.gg/ZeSkmEC6mG)
- [Wiki (Documentation)](https://github.com/Drawethree/X-Prison/wiki)

## API [![](https://jitpack.io/v/drawethree/X-PrisonAPI.svg)](https://jitpack.io/#drawethree/X-PrisonAPI)
How to get X-Prison API into your project:

### Gradle:
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compileOnly 'com.github.drawethree:X-PrisonAPI:1.0'
}
```

### Maven:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
	<groupId>com.github.drawethree</groupId>
        <artifactId>X-PrisonAPI</artifactId>
        <version>1.0</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

## What it does?
This simple plugin registers 2 Custom Enchants

### Invisibility Enchant
This enchant is implementation of EquipabbleEnchant, meaning it has specific logic that should be applied once player equips / unequips pickaxe with this enchant.
This enchant specifically grants / remove invisibility for player.

Configuration file: src/main/resources/invisibility.json

### Diamond Giver Enchant
This enchant is implementation of ChangeBasedEnchant and BlockBreakEnchant, meaning that there is a particular chance that enchant will be triggered on block breaking.
This enchant specifically gives player 1 diamond, if it procs.

Configuration file: src/main/resources/diamondgiver.json


