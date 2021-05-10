package com.waychel.cloudcontroller;

import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.waychel.cloudcontroller.databinding.FragmentFirstBinding;

import static android.content.Context.VIBRATOR_SERVICE;

public class FirstFragment extends Fragment implements ITXLivePlayListener, View.OnTouchListener {

    private FragmentFirstBinding binding;

    private TXLivePlayer mWatchLivePlayerFront;
    private Vibrator vibrator;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        vibrator = (Vibrator)view.getContext().getSystemService(VIBRATOR_SERVICE);

        mWatchLivePlayerFront = new TXLivePlayer(view.getContext());
        TXLivePlayConfig mPlayConfig = new TXLivePlayConfig();
        //极速模式
        mPlayConfig.setAutoAdjustCacheTime(true);
        mPlayConfig.setMinAutoAdjustCacheTime(1);
        mPlayConfig.setMaxAutoAdjustCacheTime(1);

        mWatchLivePlayerFront.setConfig(mPlayConfig);
        mWatchLivePlayerFront.setRenderMode(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
        mWatchLivePlayerFront.setPlayerView(binding.watchVideoViewFont);

        mWatchLivePlayerFront.setPlayListener(this);

        mWatchLivePlayerFront.stopPlay(true);
        mWatchLivePlayerFront.enableHardwareDecode(true);
        mWatchLivePlayerFront.startPlay("rtmp://58.200.131.2:1935/livetv/hunantv", TXLivePlayer.PLAY_TYPE_LIVE_RTMP_ACC);

        binding.upTv.setOnTouchListener(this);
        binding.downTv.setOnTouchListener(this);
        binding.leftTv.setOnTouchListener(this);
        binding.rightTv.setOnTouchListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.watchVideoViewFont.onDestroy();
        binding = null;
        if (mWatchLivePlayerFront != null) {
            mWatchLivePlayerFront.stopPlay(true);
            // true代表清除最后一帧画面
        }
    }

    @Override
    public void onPlayEvent(int i, Bundle bundle) {
        if (i == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
//            if (null != progressLayout) {
//                progressLayout.setVisibility(View.GONE);
//            }
        } else if (i == TXLiveConstants.PLAY_EVT_PLAY_PROGRESS) {
            //视频播放进度，会通知当前进度和总体进度，仅在点播时有效

        } else if (i == TXLiveConstants.PLAY_EVT_PLAY_LOADING) {
//            if (null != progressLayout) {
//                progressLayout.setVisibility(View.VISIBLE);
//            }
        } else if (i == TXLiveConstants.PLAY_EVT_PLAY_END) {

        }
    }

    @Override
    public void onNetStatus(Bundle bundle) {

    }

    public void shake() {
        vibrator.vibrate(50);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch (view.getId()) {
                    case R.id.up_tv:
//                        ActionPut(ServerConstant.SERVER_ZHUANPAN_URL + "/app/back");
                        binding.upTv.setBackgroundResource(R.mipmap.icon_up_press);
                        shake();
                        break;
                    case R.id.down_tv:
//                        ActionPut(ServerConstant.SERVER_ZHUANPAN_URL + "/app/front");
                        binding.downTv.setBackgroundResource(R.mipmap.icon_down_press);
                        shake();
                        break;
                    case R.id.left_tv:
//                        ActionPut(ServerConstant.SERVER_ZHUANPAN_URL + "/app/left");
                        binding.leftTv.setBackgroundResource(R.mipmap.icon_left_press);
                        shake();
                        break;
                    case R.id.right_tv:
//                        ActionPut(ServerConstant.SERVER_ZHUANPAN_URL + "/app/right");
                        binding.rightTv.setBackgroundResource(R.mipmap.icon_right_press);
                        shake();
                        break;
                    default:
//                        ActionPut(ServerConstant.SERVER_ZHUANPAN_URL + "/app/release");
                        break;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
//                ActionPut(ServerConstant.SERVER_ZHUANPAN_URL + "/app/release");
                binding.upTv.setBackgroundResource(R.mipmap.icon_up_normal);
                binding.downTv.setBackgroundResource(R.mipmap.icon_down_normal);
                binding.leftTv.setBackgroundResource(R.mipmap.icon_left_normal);
                binding.rightTv.setBackgroundResource(R.mipmap.icon_right_normal);
                break;
            default:
                break;
        }
        return true;
    }

    private void ActionPut(String path) {

        /*UserInfoBean userInfoBean = UserInfoManager.getInstance().getUserInfoBean();
        DollActionReq dollActionReq = new DollActionReq();
        dollActionReq.setApiToken(userInfoBean.getApiToken().replace("Bearer ", ""));
        dollActionReq.setTimestamp(System.currentTimeMillis());
        dollActionReq.setAppVersion(BuildConfig.VERSION_CODE);
        dollActionReq.setUserId(userInfoBean.getId() + "");
        dollActionReq.setDeviceId(mDollBean.getDeviceId());
        dollActionReq.setJwt(GameToken);
        String sign = signUtil.encryptSignWithMD5(Constant.API_KEY, JSONUtils.toJsonString(dollActionReq));*/

        /*String url = path + "?apiToken=" + dollActionReq.getApiToken()
                + "&timestamp=" + dollActionReq.getTimestamp()
                + "&userId=" + dollActionReq.getUserId()
                + "&appVersion=" + dollActionReq.getAppVersion()
                + "&deviceId=" + mDollBean.getDeviceId()
                + "&jwt=" + GameToken + "&sign=" + sign;*/

//        HttpManager.putRequest(url, "", null);
    }
}