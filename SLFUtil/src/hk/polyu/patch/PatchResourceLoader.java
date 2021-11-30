package hk.polyu.patch;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashMap;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.util.ArrayMap;


public class PatchResourceLoader {

    private static AssetManager originalAssetManager = null;

    public static void loadPatchResources(Context context, String patchFilePath) {
    	try {
    		AssetManager newAssetManager = AssetManager.class.newInstance();
    		MethodUtils.invokeMethod(newAssetManager, "addAssetPath", patchFilePath);
    		MethodUtils.invokeMethod(newAssetManager, "ensureStringBlocks");
    		replaceAssetManager(context, newAssetManager);//由于 AssetManager 处理有关布局内容的查询，为了实现布局文件替换，我们创建了一个新的 AssetManager 实例加载包含原始布局定义的asset文件，并使用它来替换由 安卓框架创建的原始asset文件，这样新实例将回答来自混淆app的查询（例如，findViewById()）。

    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    private static void replaceAssetManager(Context context, AssetManager newAssetManager) throws Exception {
        Collection<WeakReference<Resources>> references;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Class<?> resourcesManagerClass = Class.forName("android.app.ResourcesManager");
            Object resourcesManager = MethodUtils.invokeStaticMethod(resourcesManagerClass, "getInstance");

            if (FieldUtils.getField(resourcesManagerClass, "mActiveResources") != null) {
                ArrayMap<?, WeakReference<Resources>> arrayMap =
                        (ArrayMap) FieldUtils.readField(resourcesManager, "mActiveResources", true);
                references = arrayMap.values();
            } else {
                references = (Collection) FieldUtils.readField(resourcesManager, "mResourceReferences", true);
            }
        } else {
            HashMap<?, WeakReference<Resources>> map =
                    (HashMap) FieldUtils.readField(ActivityThreadCompat.instance(), "mActiveResources", true);
            references = map.values();
        }

        AssetManager assetManager = context != null ? context.getAssets() : null;
        for (WeakReference<Resources> wr : references) {
            Resources resources = wr.get();
            
            if (resources == null) continue;

            try {
            	FieldUtils.writeField(resources, "mAssets", newAssetManager);
                originalAssetManager = assetManager;
            } catch (Throwable ignore) {
                Object resourceImpl = FieldUtils.readField(resources, "mResourcesImpl", true);
                FieldUtils.writeField(resourceImpl, "mAssets", newAssetManager);
            }

            resources.updateConfiguration(resources.getConfiguration(), resources.getDisplayMetrics());
            
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (WeakReference<Resources> wr : references) {
                Resources resources = wr.get();
                if (resources == null) continue;

                // android.util.Pools$SynchronizedPool<TypedArray>
                Object typedArrayPool = FieldUtils.readField(resources, "mTypedArrayPool", true);

                // Clear all the pools
                while (MethodUtils.invokeMethod(typedArrayPool, "acquire") != null) ;
            }
        }
    }

}
