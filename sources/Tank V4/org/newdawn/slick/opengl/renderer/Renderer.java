package org.newdawn.slick.opengl.renderer;

public class Renderer {
   public static final int IMMEDIATE_RENDERER = 1;
   public static final int VERTEX_ARRAY_RENDERER = 2;
   public static final int DEFAULT_LINE_STRIP_RENDERER = 3;
   public static final int QUAD_BASED_LINE_STRIP_RENDERER = 4;
   private static SGL renderer = new ImmediateModeOGLRenderer();
   private static LineStripRenderer lineStripRenderer = new DefaultLineStripRenderer();

   public static void setRenderer(int var0) {
      switch(var0) {
      case 1:
         setRenderer(new ImmediateModeOGLRenderer());
         return;
      case 2:
         setRenderer(new VAOGLRenderer());
         return;
      default:
         throw new RuntimeException("Unknown renderer type: " + var0);
      }
   }

   public static void setLineStripRenderer(int var0) {
      switch(var0) {
      case 3:
         setLineStripRenderer(new DefaultLineStripRenderer());
         return;
      case 4:
         setLineStripRenderer(new QuadBasedLineStripRenderer());
         return;
      default:
         throw new RuntimeException("Unknown line strip renderer type: " + var0);
      }
   }

   public static void setLineStripRenderer(LineStripRenderer var0) {
      lineStripRenderer = var0;
   }

   public static void setRenderer(SGL var0) {
      renderer = var0;
   }

   public static SGL get() {
      return renderer;
   }

   public static LineStripRenderer getLineStripRenderer() {
      return lineStripRenderer;
   }
}
