package chen.easyview.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.base.BaseActivity;
import com.socks.library.KLog;

import chen.easyview.R;

public class BaiduTtsActivity extends BaseActivity implements SpeechSynthesizerListener {

//    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
//    private static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
//    private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";
//    private static final String LICENSE_FILE_NAME = "temp_license";
//    private static final String ENGLISH_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female_en.dat";
//    private static final String ENGLISH_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male_en.dat";
//    private static final String ENGLISH_TEXT_MODEL_NAME = "bd_etts_text_en.dat";

    private static final int PRINT = 0;
    private static final int UI_CHANGE_INPUT_TEXT_SELECTION = 1;
    private static final int UI_CHANGE_SYNTHES_TEXT_SELECTION = 2;
    private static final String TAG = "MainActivity";

    Button mButton5;

    private SpeechSynthesizer speechSynthesizer;
    private EditText mInput;
    private String mSampleDirPath;

    @Override
    protected int getContentView() {
        return R.layout.activity_baidu_tts;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initialEnv();
        initBD();
        mInput = (EditText) findViewById(R.id.editText);
        mButton5 = findViewById(R.id.button5);
        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    //离线资源，在本项目不使用，有需要去yuyin.baidu.com下载
    private void initialEnv() {
        if (mSampleDirPath == null) {
            String sdcardPath = Environment.getExternalStorageDirectory().toString();
            mSampleDirPath = sdcardPath + "/EasyView";
        }
//        AssetsUtils.copyFromAssetsToSdcard(BaiduTtsActivity.this, false, SPEECH_FEMALE_MODEL_NAME, SPEECH_FEMALE_MODEL_NAME);
//        AssetsUtils.copyFromAssetsToSdcard(BaiduTtsActivity.this,false, SPEECH_MALE_MODEL_NAME, SPEECH_MALE_MODEL_NAME);
//        AssetsUtils.copyFromAssetsToSdcard(BaiduTtsActivity.this, false, TEXT_MODEL_NAME, TEXT_MODEL_NAME);
//        AssetsUtils.copyFromAssetsToSdcard(BaiduTtsActivity.this,false, LICENSE_FILE_NAME, LICENSE_FILE_NAME);
//        AssetsUtils.copyFromAssetsToSdcard(BaiduTtsActivity.this,false, "english/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME, ENGLISH_SPEECH_FEMALE_MODEL_NAME);
//        AssetsUtils.copyFromAssetsToSdcard(BaiduTtsActivity.this,false, "english/" + ENGLISH_SPEECH_MALE_MODEL_NAME, ENGLISH_SPEECH_MALE_MODEL_NAME);
//        AssetsUtils.copyFromAssetsToSdcard(BaiduTtsActivity.this,false, "english/" + ENGLISH_TEXT_MODEL_NAME, ENGLISH_TEXT_MODEL_NAME);
    }

    private void initBD() {
        // 获取 tts 实例
        speechSynthesizer = SpeechSynthesizer.getInstance();
        // 设置 app 上下文（必需参数）
        speechSynthesizer.setContext(BaiduTtsActivity.this);
        // 设置 tts 监听器
        speechSynthesizer.setSpeechSynthesizerListener(this);
        // 文本模型文件路径 (离线引擎使用)
//        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mSampleDirPath + "/"
//                + TEXT_MODEL_NAME);
//        // 声学模型文件路径 (离线引擎使用)
//        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mSampleDirPath + "/"
//                + SPEECH_FEMALE_MODEL_NAME);
        // 本地授权文件路径,如未设置将使用默认路径.设置临时授权文件路径，LICENCE_FILE_NAME请替换成临时授权文件的实际路径，仅在使用临时license文件时需要进行设置，如果在[应用管理]中开通了正式离线授权，不需要设置该参数，建议将该行代码删除（离线引擎）
        // 如果合成结果出现临时授权文件将要到期的提示，说明使用了临时授权文件，请删除临时授权即可。
//        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, mSampleDirPath + "/"
//                + LICENSE_FILE_NAME);

        // 请替换为语音开发者平台上注册应用得到的 App ID (离线授权)
        speechSynthesizer.setAppId("9269602");
        // 请替换为语音开发者平台注册应用得到的 apikey 和 secretkey (在线授权)
        speechSynthesizer.setApiKey("PqlGOGDEYHqTLwy7virhSIHz", "MAF6780DRmndfeMg97DGXAZyGbchx9s6");
        // 授权检测接口
        // AuthInfo authInfo = speechSynthesizer.auth(TtsMode);
        // 引擎初始化接口
        speechSynthesizer.initTts(TtsMode.ONLINE);
    }


    private void speak() {
        String text = this.mInput.getText().toString();
        //需要合成的文本text的长度不能超过1024个GBK字节。
        if (TextUtils.isEmpty(mInput.getText())) {
            text = "欢迎使用百度语音合成SDK,百度语音为你提供支持。";
            mInput.setText(text);
        }
        int result = speechSynthesizer.speak(text);
        if (result < 0) {
            toPrint("error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }

    @Override
    protected void onPause() {
        speechSynthesizer.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        speechSynthesizer.resume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        speechSynthesizer.release();
        super.onDestroy();
    }

    /*
     * @param arg0
     */
    @Override
    public void onSynthesizeStart(String utteranceId) {
        toPrint("onSynthesizeStart utteranceId=" + utteranceId);
    }

    /**
     * 合成数据和进度的回调接口，分多次回调
     *
     * @param utteranceId
     * @param data        合成的音频数据。该音频数据是采样率为16K，2字节精度，单声道的pcm数据。
     * @param progress    文本按字符划分的进度，比如:你好啊 进度是0-3
     */
    @Override
    public void onSynthesizeDataArrived(String utteranceId, byte[] data, int progress) {
        // toPrint("onSynthesizeDataArrived");
        mHandler.sendMessage(mHandler.obtainMessage(UI_CHANGE_SYNTHES_TEXT_SELECTION, progress, 0));
    }

    /**
     * 合成正常结束，每句合成正常结束都会回调，如果过程中出错，则回调onError，不再回调此接口
     *
     * @param utteranceId
     */
    @Override
    public void onSynthesizeFinish(String utteranceId) {
        toPrint("onSynthesizeFinish utteranceId=" + utteranceId);
    }

    /**
     * 播放开始，每句播放开始都会回调
     *
     * @param utteranceId
     */
    @Override
    public void onSpeechStart(String utteranceId) {
        toPrint("onSpeechStart utteranceId=" + utteranceId);
    }

    /**
     * 播放进度回调接口，分多次回调
     *
     * @param utteranceId
     * @param progress    文本按字符划分的进度，比如:你好啊 进度是0-3
     */
    @Override
    public void onSpeechProgressChanged(String utteranceId, int progress) {
        // toPrint("onSpeechProgressChanged");
        mHandler.sendMessage(mHandler.obtainMessage(UI_CHANGE_INPUT_TEXT_SELECTION, progress, 0));
    }

    /**
     * 播放正常结束，每句播放正常结束都会回调，如果过程中出错，则回调onError,不再回调此接口
     *
     * @param utteranceId
     */
    @Override
    public void onSpeechFinish(String utteranceId) {
        toPrint("onSpeechFinish utteranceId=" + utteranceId);
    }

    /**
     * 当合成或者播放过程中出错时回调此接口
     *
     * @param utteranceId
     * @param error       包含错误码和错误信息
     */
    @Override
    public void onError(String utteranceId, SpeechError error) {
        toPrint("onError error=" + "(" + error.code + ")" + error.description + "--utteranceId=" + utteranceId);
    }

    private Handler mHandler = new Handler() {

        /*
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case PRINT:
                    print(msg);
                    break;
                case UI_CHANGE_INPUT_TEXT_SELECTION:
                    if (msg.arg1 <= mInput.getText().length()) {
                        mInput.setSelection(0, msg.arg1);
                    }
                    break;
                case UI_CHANGE_SYNTHES_TEXT_SELECTION:
                    SpannableString colorfulText = new SpannableString(mInput.getText().toString());
                    if (msg.arg1 <= colorfulText.toString().length()) {
                        colorfulText.setSpan(new ForegroundColorSpan(Color.GRAY), 0, msg.arg1, Spannable
                                .SPAN_EXCLUSIVE_EXCLUSIVE);
                        mInput.setText(colorfulText);
                    }
                    break;
                default:
                    break;
            }
        }

    };

    private void toPrint(String str) {
        Message msg = Message.obtain();
        msg.obj = str;
        this.mHandler.sendMessage(msg);
    }

    private void print(Message msg) {
        String message = (String) msg.obj;
        if (message != null) {
            KLog.w(message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

}
