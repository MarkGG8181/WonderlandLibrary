// 
// Decompiled by Procyon v0.6.0
// 

package org.newdawn.slick.muffin;

import java.io.EOFException;
import java.io.DataInputStream;
import java.util.Iterator;
import java.util.Set;
import javax.jnlp.FileContents;
import java.io.DataOutputStream;
import java.io.IOException;
import org.newdawn.slick.util.Log;
import java.net.URL;
import javax.jnlp.BasicService;
import javax.jnlp.ServiceManager;
import javax.jnlp.PersistenceService;
import java.util.HashMap;

public class WebstartMuffin implements Muffin
{
    public void saveFile(final HashMap scoreMap, final String fileName) throws IOException {
        PersistenceService ps;
        URL configURL;
        try {
            ps = (PersistenceService)ServiceManager.lookup("javax.jnlp.PersistenceService");
            final BasicService bs = (BasicService)ServiceManager.lookup("javax.jnlp.BasicService");
            final URL baseURL = bs.getCodeBase();
            configURL = new URL(baseURL, fileName);
        }
        catch (final Exception e) {
            Log.error(e);
            throw new IOException("Failed to save state: ");
        }
        try {
            ps.delete(configURL);
        }
        catch (final Exception e) {
            Log.info("No exisiting Muffin Found - First Save");
        }
        try {
            ps.create(configURL, 1024L);
            final FileContents fc = ps.get(configURL);
            final DataOutputStream oos = new DataOutputStream(fc.getOutputStream(false));
            final Set keys = scoreMap.keySet();
            final Iterator i = keys.iterator();
            while (i.hasNext()) {
                final String key = i.next();
                oos.writeUTF(key);
                if (fileName.endsWith("Number")) {
                    final double value = scoreMap.get(key);
                    oos.writeDouble(value);
                }
                else {
                    final String value2 = scoreMap.get(key);
                    oos.writeUTF(value2);
                }
            }
            oos.flush();
            oos.close();
        }
        catch (final Exception e) {
            Log.error(e);
            throw new IOException("Failed to store map of state data");
        }
    }
    
    public HashMap loadFile(final String fileName) throws IOException {
        final HashMap hashMap = new HashMap();
        try {
            final PersistenceService ps = (PersistenceService)ServiceManager.lookup("javax.jnlp.PersistenceService");
            final BasicService bs = (BasicService)ServiceManager.lookup("javax.jnlp.BasicService");
            final URL baseURL = bs.getCodeBase();
            final URL configURL = new URL(baseURL, fileName);
            final FileContents fc = ps.get(configURL);
            final DataInputStream ois = new DataInputStream(fc.getInputStream());
            if (fileName.endsWith("Number")) {
                String key;
                while ((key = ois.readUTF()) != null) {
                    final double value = ois.readDouble();
                    hashMap.put(key, new Double(value));
                }
            }
            else {
                String key;
                while ((key = ois.readUTF()) != null) {
                    final String value2 = ois.readUTF();
                    hashMap.put(key, value2);
                }
            }
            ois.close();
        }
        catch (final EOFException e) {}
        catch (final IOException e2) {}
        catch (final Exception e3) {
            Log.error(e3);
            throw new IOException("Failed to load state from webstart muffin");
        }
        return hashMap;
    }
}
