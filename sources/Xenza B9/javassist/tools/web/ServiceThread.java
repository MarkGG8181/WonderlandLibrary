// 
// Decompiled by Procyon v0.6.0
// 

package javassist.tools.web;

import java.io.IOException;
import java.net.Socket;

class ServiceThread extends Thread
{
    Webserver web;
    Socket sock;
    
    public ServiceThread(final Webserver w, final Socket s) {
        this.web = w;
        this.sock = s;
    }
    
    @Override
    public void run() {
        try {
            this.web.process(this.sock);
        }
        catch (final IOException ex) {}
    }
}
