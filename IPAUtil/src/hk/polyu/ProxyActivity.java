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
        startActivity(targetIntent);//�����Ӵ�����Ŀ����ת��

        finish();//Ϊ���㲻�ɼ�Ҫ���ֶ�ɾ������
    }

}
