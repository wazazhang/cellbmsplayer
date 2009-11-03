/* !---- DO NOT EDIT: This file autogenerated by com\sun\gluegen\JavaEmitter.java on Mon Jul 31 16:26:59 PDT 2006 ----! */

#include <jni.h>

#include <assert.h>

 #include <dlfcn.h>
 #include <inttypes.h>

/*   Java->C glue code:
 *   Java package: com.sun.gluegen.runtime.UnixDynamicLinkerImpl
 *    Java method: int dlclose(long arg0)
 *     C function: int dlclose(void * );
 */
JNIEXPORT jint JNICALL 
Java_com_sun_gluegen_runtime_UnixDynamicLinkerImpl_dlclose__J(JNIEnv *env, jclass _unused, jlong arg0) {
  int _res;
  _res = dlclose((void *) (intptr_t) arg0);
  return _res;
}


/*   Java->C glue code:
 *   Java package: com.sun.gluegen.runtime.UnixDynamicLinkerImpl
 *    Java method: java.lang.String dlerror()
 *     C function: char *  dlerror(void);
 */
JNIEXPORT jstring JNICALL 
Java_com_sun_gluegen_runtime_UnixDynamicLinkerImpl_dlerror__(JNIEnv *env, jclass _unused) {
  char *  _res;
  _res = dlerror();
  if (_res == NULL) return NULL;  return (*env)->NewStringUTF(env, _res);
}


/*   Java->C glue code:
 *   Java package: com.sun.gluegen.runtime.UnixDynamicLinkerImpl
 *    Java method: long dlopen(java.lang.String arg0, int arg1)
 *     C function: void *  dlopen(const char * , int);
 */
JNIEXPORT jlong JNICALL 
Java_com_sun_gluegen_runtime_UnixDynamicLinkerImpl_dlopen__Ljava_lang_String_2I(JNIEnv *env, jclass _unused, jstring arg0, jint arg1) {
  const char* _UTF8arg0 = NULL;
  void *  _res;
  if (arg0 != NULL) {
    if (arg0 != NULL) {
      _UTF8arg0 = (*env)->GetStringUTFChars(env, arg0, (jboolean*)NULL);
    if (_UTF8arg0 == NULL) {
      (*env)->ThrowNew(env, (*env)->FindClass(env, "java/lang/OutOfMemoryError"),
                       "Failed to get UTF-8 chars for argument \"arg0\" in native dispatcher for \"dlopen\"");
      return 0;
    }
    }
  }
  _res = dlopen((char *) _UTF8arg0, (int) arg1);
  if (arg0 != NULL) {
    (*env)->ReleaseStringUTFChars(env, arg0, _UTF8arg0);
  }
  return (jlong) (intptr_t) _res;
}


/*   Java->C glue code:
 *   Java package: com.sun.gluegen.runtime.UnixDynamicLinkerImpl
 *    Java method: long dlsym(long arg0, java.lang.String arg1)
 *     C function: void *  dlsym(void * , const char * );
 */
JNIEXPORT jlong JNICALL 
Java_com_sun_gluegen_runtime_UnixDynamicLinkerImpl_dlsym__JLjava_lang_String_2(JNIEnv *env, jclass _unused, jlong arg0, jstring arg1) {
  const char* _UTF8arg1 = NULL;
  void *  _res;
  if (arg1 != NULL) {
    if (arg1 != NULL) {
      _UTF8arg1 = (*env)->GetStringUTFChars(env, arg1, (jboolean*)NULL);
    if (_UTF8arg1 == NULL) {
      (*env)->ThrowNew(env, (*env)->FindClass(env, "java/lang/OutOfMemoryError"),
                       "Failed to get UTF-8 chars for argument \"arg1\" in native dispatcher for \"dlsym\"");
      return 0;
    }
    }
  }
  _res = dlsym((void *) (intptr_t) arg0, (char *) _UTF8arg1);
  if (arg1 != NULL) {
    (*env)->ReleaseStringUTFChars(env, arg1, _UTF8arg1);
  }
  return (jlong) (intptr_t) _res;
}


