package net.minecraft.util;

public interface IProgressUpdate {
   void setDoneWorking();

   void displaySavingString(String var1);

   void displayLoadingString(String var1);

   void resetProgressAndMessage(String var1);

   void setLoadingProgress(int var1);
}
