package hk.polyu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ProxyActivity extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("polyu", "ProxyActivity");
        
        Intent targetIntent = GlobalIntent.instance;
        startActivity(targetIntent);//创建从代理活动到目标活动的转换

        finish();//为满足不可见要求手动删除代理
    }

}
