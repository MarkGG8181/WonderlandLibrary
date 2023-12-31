// 
// Decompiled by Procyon v0.6.0
// 

package fr.litarvan.openauth.microsoft.model.response;

public class MinecraftProfile
{
    private final String id;
    private final String name;
    private final MinecraftSkin[] skins;
    
    public MinecraftProfile(final String id, final String name, final MinecraftSkin[] skins) {
        this.id = id;
        this.name = name;
        this.skins = skins;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public MinecraftSkin[] getSkins() {
        return this.skins;
    }
    
    public static class MinecraftSkin
    {
        private final String id;
        private final String state;
        private final String url;
        private final String variant;
        private final String alias;
        
        public MinecraftSkin(final String id, final String state, final String url, final String variant, final String alias) {
            this.id = id;
            this.state = state;
            this.url = url;
            this.variant = variant;
            this.alias = alias;
        }
        
        public String getId() {
            return this.id;
        }
        
        public String getState() {
            return this.state;
        }
        
        public String getUrl() {
            return this.url;
        }
        
        public String getVariant() {
            return this.variant;
        }
        
        public String getAlias() {
            return this.alias;
        }
    }
}
