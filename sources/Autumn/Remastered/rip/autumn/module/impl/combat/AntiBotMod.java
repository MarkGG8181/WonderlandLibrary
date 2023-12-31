package rip.autumn.module.impl.combat;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import rip.autumn.annotations.Label;
import rip.autumn.events.player.MotionUpdateEvent;
import rip.autumn.module.Module;
import rip.autumn.module.ModuleCategory;
import rip.autumn.module.annotations.Aliases;
import rip.autumn.module.annotations.Category;
import rip.autumn.module.option.impl.EnumOption;

@Label("Anti Bot")
@Category(ModuleCategory.COMBAT)
@Aliases({"antibot"})
public final class AntiBotMod extends Module {
   private final EnumOption mode;
   public final Set bots;

   public AntiBotMod() {
      this.mode = new EnumOption("Mode", AntiBotMod.Mode.HYPIXEL);
      this.bots = new HashSet();
      this.addOptions(this.mode);
      this.setMode(this.mode);
   }

   @Listener(MotionUpdateEvent.class)
   public void onMotionUpdate(MotionUpdateEvent event) {
      switch(mode.getValue().toString()) {
      case "HYPIXEL":
         if (event.isPre()) {
            List playerEntities = mc.theWorld.playerEntities;
            int i = 0;

            for(int playerEntitiesSize = playerEntities.size(); i < playerEntitiesSize; ++i) {
               EntityPlayer player = (EntityPlayer)playerEntities.get(i);
               if (player.getName().startsWith("§") && player.getName().contains("§c") || this.isEntityBot(player) && !player.getDisplayName().getFormattedText().contains("NPC")) {
                  mc.theWorld.removeEntity(player);
               }
            }
         }
         break;
      }
   }

   private boolean isEntityBot(Entity entity) {
      if (!(entity instanceof EntityPlayer)) {
         return false;
      } else if (mc.getCurrentServerData() == null) {
         return false;
      } else {
         return mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel") && entity.getDisplayName().getFormattedText().startsWith("ยง") || !this.isOnTab(entity) && mc.thePlayer.ticksExisted > 100;
      }
   }

   private boolean isOnTab(Entity entity) {
      Iterator playerInfoIterator = mc.getNetHandler().getPlayerInfoMap().iterator();

      NetworkPlayerInfo info;
      do {
         if (!playerInfoIterator.hasNext()) {
            return false;
         }

         info = (NetworkPlayerInfo)playerInfoIterator.next();
      } while(!info.getGameProfile().getName().equals(entity.getName()));

      return true;
   }

   private enum Mode {
      HYPIXEL;
   }
}
