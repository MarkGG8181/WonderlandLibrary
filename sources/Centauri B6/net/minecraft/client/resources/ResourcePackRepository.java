package net.minecraft.client.resources;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.client.resources.FileResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository.2;
import net.minecraft.client.resources.ResourcePackRepository.3;
import net.minecraft.client.resources.ResourcePackRepository.Entry;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.HttpUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourcePackRepository {
   private static final Logger logger = LogManager.getLogger();
   private static final FileFilter resourcePackFilter = new FileFilter() {
      public boolean accept(File p_accept_1_) {
         boolean flag = p_accept_1_.isFile() && p_accept_1_.getName().endsWith(".zip");
         boolean flag1 = p_accept_1_.isDirectory() && (new File(p_accept_1_, "pack.mcmeta")).isFile();
         return flag || flag1;
      }
   };
   private final File dirResourcepacks;
   public final IResourcePack rprDefaultResourcePack;
   private final File dirServerResourcepacks;
   public final IMetadataSerializer rprMetadataSerializer;
   private IResourcePack resourcePackInstance;
   private final ReentrantLock lock = new ReentrantLock();
   private ListenableFuture field_177322_i;
   private List repositoryEntriesAll = Lists.newArrayList();
   private List repositoryEntries = Lists.newArrayList();

   public ResourcePackRepository(File dirResourcepacksIn, File dirServerResourcepacksIn, IResourcePack rprDefaultResourcePackIn, IMetadataSerializer rprMetadataSerializerIn, GameSettings settings) {
      this.dirResourcepacks = dirResourcepacksIn;
      this.dirServerResourcepacks = dirServerResourcepacksIn;
      this.rprDefaultResourcePack = rprDefaultResourcePackIn;
      this.rprMetadataSerializer = rprMetadataSerializerIn;
      this.fixDirResourcepacks();
      this.updateRepositoryEntriesAll();
      Iterator<String> iterator = settings.resourcePacks.iterator();

      while(iterator.hasNext()) {
         String s = (String)iterator.next();

         for(Entry resourcepackrepository$entry : this.repositoryEntriesAll) {
            if(resourcepackrepository$entry.getResourcePackName().equals(s)) {
               if(resourcepackrepository$entry.func_183027_f() == 1 || settings.resourcePackNames.contains(resourcepackrepository$entry.getResourcePackName())) {
                  this.repositoryEntries.add(resourcepackrepository$entry);
                  break;
               }

               iterator.remove();
               logger.warn("Removed selected resource pack {} because it\'s no longer compatible", new Object[]{resourcepackrepository$entry.getResourcePackName()});
            }
         }
      }

   }

   public IResourcePack getResourcePackInstance() {
      return this.resourcePackInstance;
   }

   public List getRepositoryEntries() {
      return ImmutableList.copyOf(this.repositoryEntries);
   }

   public void setRepositories(List p_148527_1_) {
      this.repositoryEntries.clear();
      this.repositoryEntries.addAll(p_148527_1_);
   }

   public void func_148529_f() {
      this.lock.lock();

      try {
         if(this.field_177322_i != null) {
            this.field_177322_i.cancel(true);
         }

         this.field_177322_i = null;
         if(this.resourcePackInstance != null) {
            this.resourcePackInstance = null;
            Minecraft.getMinecraft().scheduleResourcesRefresh();
         }
      } finally {
         this.lock.unlock();
      }

   }

   public ListenableFuture downloadResourcePack(String url, String hash) {
      String s;
      if(hash.matches("^[a-f0-9]{40}$")) {
         s = hash;
      } else {
         s = "legacy";
      }

      File file1 = new File(this.dirServerResourcepacks, s);
      this.lock.lock();

      try {
         this.func_148529_f();
         if(file1.exists() && hash.length() == 40) {
            try {
               String s1 = Hashing.sha1().hashBytes(Files.toByteArray(file1)).toString();
               if(s1.equals(hash)) {
                  ListenableFuture listenablefuture1 = this.setResourcePackInstance(file1);
                  ListenableFuture var18 = listenablefuture1;
                  return var18;
               }

               logger.warn("File " + file1 + " had wrong hash (expected " + hash + ", found " + s1 + "). Deleting it.");
               FileUtils.deleteQuietly(file1);
            } catch (IOException var14) {
               logger.warn("File " + file1 + " couldn\'t be hashed. Deleting it.", var14);
               FileUtils.deleteQuietly(file1);
            }
         }

         this.func_183028_i();
         GuiScreenWorking guiscreenworking = new GuiScreenWorking();
         Map<String, String> map = Minecraft.getSessionInfo();
         Minecraft minecraft = Minecraft.getMinecraft();
         Futures.getUnchecked(minecraft.addScheduledTask((Runnable)(new 2(this, minecraft, guiscreenworking))));
         SettableFuture<Object> settablefuture = SettableFuture.create();
         this.field_177322_i = HttpUtil.downloadResourcePack(file1, url, map, 52428800, guiscreenworking, minecraft.getProxy());
         Futures.addCallback(this.field_177322_i, new 3(this, file1, settablefuture));
         ListenableFuture listenablefuture = this.field_177322_i;
         ListenableFuture var10 = listenablefuture;
         return var10;
      } finally {
         this.lock.unlock();
      }
   }

   public void updateRepositoryEntriesAll() {
      List<Entry> list = Lists.newArrayList();

      for(File file1 : this.getResourcePackFiles()) {
         Entry resourcepackrepository$entry = new Entry(this, file1);
         if(!this.repositoryEntriesAll.contains(resourcepackrepository$entry)) {
            try {
               resourcepackrepository$entry.updateResourcePack();
               list.add(resourcepackrepository$entry);
            } catch (Exception var61) {
               list.remove(resourcepackrepository$entry);
            }
         } else {
            int i = this.repositoryEntriesAll.indexOf(resourcepackrepository$entry);
            if(i > -1 && i < this.repositoryEntriesAll.size()) {
               list.add(this.repositoryEntriesAll.get(i));
            }
         }
      }

      this.repositoryEntriesAll.removeAll(list);

      for(Entry resourcepackrepository$entry1 : this.repositoryEntriesAll) {
         resourcepackrepository$entry1.closeResourcePack();
      }

      this.repositoryEntriesAll = list;
   }

   private List getResourcePackFiles() {
      return this.dirResourcepacks.isDirectory()?Arrays.asList(this.dirResourcepacks.listFiles(resourcePackFilter)):Collections.emptyList();
   }

   public ListenableFuture setResourcePackInstance(File p_177319_1_) {
      this.resourcePackInstance = new FileResourcePack(p_177319_1_);
      return Minecraft.getMinecraft().scheduleResourcesRefresh();
   }

   public List getRepositoryEntriesAll() {
      return ImmutableList.copyOf(this.repositoryEntriesAll);
   }

   public File getDirResourcepacks() {
      return this.dirResourcepacks;
   }

   private void fixDirResourcepacks() {
      if(this.dirResourcepacks.exists()) {
         if(!this.dirResourcepacks.isDirectory() && (!this.dirResourcepacks.delete() || !this.dirResourcepacks.mkdirs())) {
            logger.warn("Unable to recreate resourcepack folder, it exists but is not a directory: " + this.dirResourcepacks);
         }
      } else if(!this.dirResourcepacks.mkdirs()) {
         logger.warn("Unable to create resourcepack folder: " + this.dirResourcepacks);
      }

   }

   private void func_183028_i() {
      List<File> list = Lists.newArrayList(FileUtils.listFiles(this.dirServerResourcepacks, TrueFileFilter.TRUE, (IOFileFilter)null));
      Collections.sort(list, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
      int i = 0;

      for(File file1 : list) {
         if(i++ >= 10) {
            logger.info("Deleting old server resource pack " + file1.getName());
            FileUtils.deleteQuietly(file1);
         }
      }

   }
}
