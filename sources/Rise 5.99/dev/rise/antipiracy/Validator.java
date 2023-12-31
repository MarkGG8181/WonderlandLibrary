package dev.rise.antipiracy;

import dev.rise.Rise;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.passive.EntitySheep;
import store.intent.hwid.HWID;
import store.intent.intentguard.annotation.Native;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;

@Native
public final class Validator {

    @Native
    public static void validate() {
        try {
            final Method hardwareIdMethod = HWID.class.getMethod("getHardwareID");
            //final Method base64Method = HWID.class.getMethod("base64", String.class, String.class);

            if (!Modifier.isNative(hardwareIdMethod.getModifiers())) {
                for (; ; ) {
                    Minecraft.getMinecraft().entityRenderer = null;
                    Minecraft.getMinecraft().mouseHelper = null;

                    Minecraft.getMinecraft().thePlayer = (EntityPlayerSP) (Object) new EntitySheep(null);
                }
            }
        } catch (final Exception exception) {
            for (; ; ) {
                Minecraft.getMinecraft().entityRenderer = null;
                Minecraft.getMinecraft().mouseHelper = null;

                Minecraft.getMinecraft().thePlayer = (EntityPlayerSP) (Object) new EntitySheep(null);
            }
        }

        final String xd = new String(new char[]{'h', 't', 't', 'p', 's', ':', '/', '/', 'i', 'n', 't', 'e', 'n', 't', '.', 's', 't', 'o', 'r', 'e', '/', 'p', 'r', 'o', 'd', 'u', 'c', 't', '/', '2', '5', '/', 'w', 'h', 'i', 't', 'e', 'l', 'i', 's', 't', '?', 'h', 'w', 'i', 'd', '='});
        final String xd2 = new String(new char[]{'U', 's', 'e', 'r', '-', 'A', 'g', 'e', 'n', 't'});
        final String xd3 = new String(new char[]{'M', 'o', 'z', 'i', 'l', 'l', 'a', '/', '5', '.', '0', ' ', '(', 'W', 'i', 'n', 'd', 'o', 'w', 's', ' ', 'N', 'T', ' ', '6', '.', '1', ';', ' ', 'W', 'O', 'W', '6', '4', ';', ' ', 'r', 'v', ':', '2', '5', '.', '0', ')', ' ', 'G', 'e', 'c', 'k', 'o', '/', '2', '0', '1', '0', '0', '1', '0', '1', ' ', 'F', 'i', 'r', 'e', 'f', 'o', 'x', '/', '2', '5', '.', '0'});
        final String xd4 = new String(new char[]{'琀', '爀', '甀', '攀'});
        final String xd5 = new String(new char[]{'昀', '愀', '氀', '猀', '攀'});

        try {
            final HttpsURLConnection connection =
                    (HttpsURLConnection) new URL(xd + HWID.getHardwareID())
                            .openConnection();

            connection.addRequestProperty(xd2, xd3);


            final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String currentln;
            final ArrayList<String> response = new ArrayList<>();

            while ((currentln = in.readLine()) != null) {
                response.add(currentln);
            }

            final StringBuilder fax = new StringBuilder();

            for (int i = 0; i < xd4.length(); i++) {
                fax.append(Character.reverseBytes(xd4.charAt(i)));
            }

            final StringBuilder notFax = new StringBuilder();

            for (int i = 0; i < xd5.length(); i++) {
                notFax.append(Character.reverseBytes(xd5.charAt(i)));
            }

            if (!response.contains(fax.toString()) || response.contains(notFax.toString())) {
                for (; ; ) {
                    Minecraft.getMinecraft().entityRenderer = null;
                }
            }
        } catch (final Exception e) {
            for (; ; ) {
                Minecraft.getMinecraft().mouseHelper = null;
            }
        }
        Rise.lol = true;
    }
}
