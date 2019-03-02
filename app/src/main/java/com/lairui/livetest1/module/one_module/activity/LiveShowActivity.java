package com.lairui.livetest1.module.one_module.activity;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lairui.livetest1.R;
import com.lairui.livetest1.entity.bean.BanWarnMessage;
import com.lairui.livetest1.entity.bean.LiveAddressBean;
import com.lairui.livetest1.entity.bean.NeedLoginEvent;
import com.lairui.livetest1.message.ChatroomBarrage;
import com.lairui.livetest1.message.ChatroomFollow;
import com.lairui.livetest1.message.ChatroomGift;
import com.lairui.livetest1.message.ChatroomLike;
import com.lairui.livetest1.message.ChatroomUserBan;
import com.lairui.livetest1.message.ChatroomUserBlock;
import com.lairui.livetest1.message.ChatroomUserQuit;
import com.lairui.livetest1.message.ChatroomUserUnBan;
import com.lairui.livetest1.message.ChatroomWelcome;
import com.lairui.livetest1.module.one_module.presenter.LiveShowPresenter;
import com.lairui.livetest1.ui.adapter.ChatListAdapter;
import com.lairui.livetest1.ui.adapter.MemberAdapter;
import com.lairui.livetest1.ui.danmu.DanmuAdapter;
import com.lairui.livetest1.ui.danmu.DanmuEntity;
import com.lairui.livetest1.ui.gift.GiftSendModel;
import com.lairui.livetest1.ui.gift.GiftView;
import com.lairui.livetest1.ui.like.HeartLayout;
import com.lairui.livetest1.ui.panel.BottomPanelFragment;
import com.lairui.livetest1.ui.panel.CircleImageView;
import com.lairui.livetest1.ui.panel.HorizontalListView;
import com.lairui.livetest1.ui.panel.HostPanel;
import com.lairui.livetest1.ui.panel.InputPanel;
import com.lairui.livetest1.ui.panel.LoginPanel;
import com.lairui.livetest1.ui.panel.OnlineUserPanel;
import com.lairui.livetest1.utils.ChatroomKit;
import com.lairui.livetest1.utils.DataInterface;
import com.lzy.okgo.model.HttpParams;
import com.orzangleli.xdanmuku.DanmuContainerView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.wanou.framelibrary.base.BaseMvpActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Random;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.MessageContent;
import io.rong.message.TextMessage;

public class LiveShowActivity extends BaseMvpActivity<LiveShowPresenter> implements Handler.Callback, View.OnClickListener {
    private String roomId;
    private Handler handler = new Handler(this);
    private RelativeLayout background;
    private SurfaceView playerSurface;
    private ImageView fake;
    private RelativeLayout layoutHost;
    private CircleImageView ivHostHeader;
    private TextView tvHolderName;
    private TextView tvOnlineNum;
    private HorizontalListView hlvMember;
    private GiftView giftView;
    private ListView chatListView;
    private HostPanel hostPanel;
    private OnlineUserPanel onlineUserPanel;
    private LoginPanel loginPanel;
    private HeartLayout heartLayout;
    private BottomPanelFragment bottomPanel;
    private int giftNum = 0;
    private DanmuContainerView danmuContainerView;
    private ChatListAdapter chatListAdapter;
    private int onlineNum = 0;
    private int fansNum = 0;
    private int likeNum = 0;
    private Random random = new Random();
    private MemberAdapter memberAdapter;
    private ImageView btnHeart;
    private StandardGSYVideoPlayer videoPlayer;
    private OrientationUtils orientationUtils;
    private HttpParams httpParams = new HttpParams();

    @Override
    protected LiveShowPresenter getPresenter() {
        return new LiveShowPresenter();
    }

    @Override
    protected int getResId() {
        return R.layout.activity_live_show;
    }

    @Override
    protected void initView() {
        videoPlayer = findViewById(R.id.detail_player);
        background = findViewById(R.id.background);
        playerSurface = findViewById(R.id.player_surface);
        fake = findViewById(R.id.fake);
        layoutHost = findViewById(R.id.layout_host);
        ivHostHeader = findViewById(R.id.iv_host_header);
        tvHolderName = findViewById(R.id.tv_holder_name);
        tvOnlineNum = findViewById(R.id.tv_room_onlive_people);
        hlvMember = findViewById(R.id.gv_room_member);
        giftView = findViewById(R.id.giftView);
        danmuContainerView = findViewById(R.id.danmuContainerView);
        chatListView = findViewById(R.id.chat_listview);
        bottomPanel = (BottomPanelFragment) getSupportFragmentManager().findFragmentById(R.id.bottom_bar);
        btnHeart = bottomPanel.getView().findViewById(R.id.btn_heart);
        // 当前房间用户信息视图
        hostPanel = findViewById(R.id.host_panel);
        // 在线用户列表
        onlineUserPanel = findViewById(R.id.online_user_panel);
        // 如果未登录,登录视图
        loginPanel = findViewById(R.id.login_panel);
        // 点赞效果视图
        heartLayout = findViewById(R.id.heart_layout);
        // 当前用户信息点击事件
        background.setOnClickListener(this);
        // 点赞功能点击事件
        btnHeart.setOnClickListener(this);
        // 点击查看当前账户信息
        layoutHost.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (mBundle != null) {
            roomId = mBundle.getString("liveId");

        }
        ChatroomKit.addEventHandler(handler);
        // 设置弹幕布局
        danmuContainerView.setAdapter(new DanmuAdapter(this));

        // 初始化礼物控件
        giftView.setViewCount(2);
        giftView.init();

        // 房间中成员列表
        memberAdapter = new MemberAdapter(this, DataInterface.getUserList(roomId), true);
        hlvMember.setAdapter(memberAdapter);

        // 设置聊天信息列表
        chatListAdapter = new ChatListAdapter(this);
        chatListView.setAdapter(chatListAdapter);
        ChatroomWelcome welcomeMessage = new ChatroomWelcome();
        welcomeMessage.setId(ChatroomKit.getCurrentUser().getUserId());
        ChatroomKit.sendMessage(welcomeMessage);
        // 设置发送监听
        bottomPanel.setInputPanelListener(new InputPanel.InputPanelListener() {
            @Override
            public void onSendClick(String text, int type) {
                if (DataInterface.isBanStatus()) {
                    BanWarnMessage banWarnMessage = new BanWarnMessage();
                    io.rong.imlib.model.Message message = io.rong.imlib.model.Message.obtain(ChatroomKit.getCurrentUser().getUserId(), Conversation.ConversationType.CHATROOM, banWarnMessage);
                    chatListAdapter.addMessage(message);
                    chatListAdapter.notifyDataSetChanged();
                    return;
                }
                // 信息类型是文本类型
                if (type == InputPanel.TYPE_TEXTMESSAGE) {
                    final TextMessage content = TextMessage.obtain(text);
                    ChatroomKit.sendMessage(content);
                } else if (type == InputPanel.TYPE_BARRAGE) {
                    // 信息类型是弹幕类型
                    ChatroomBarrage barrage = new ChatroomBarrage();
                    barrage.setContent(text);
                    ChatroomKit.sendMessage(barrage);
                }

            }
        });
        //  设置禁言
        bottomPanel.setBanListener(new BottomPanelFragment.BanListener() {
            @Override
            public void addBanWarn() {
                BanWarnMessage banWarnMessage = new BanWarnMessage();
                io.rong.imlib.model.Message message = io.rong.imlib.model.Message.obtain(ChatroomKit.getCurrentUser().getUserId(), Conversation.ConversationType.CHATROOM, banWarnMessage);
                chatListAdapter.addMessage(message);
                chatListAdapter.notifyDataSetChanged();
            }
        });
        // 当前聊天室内用户列表
        hlvMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (onlineUserPanel.getVisibility() == View.VISIBLE) {
                    onlineUserPanel.setVisibility(View.GONE);
                } else {
                    onlineUserPanel.setVisibility(View.VISIBLE);
                }
                hostPanel.setVisibility(View.GONE);
            }
        });

        joinChatRoom(roomId);

        initPlayser();
        httpParams.put("operate", "roomGroup-liveAddress");
        mPresenter.getLiveAddress(httpParams);
    }

    private void initPlayser() {
        orientationUtils = new OrientationUtils(this, videoPlayer);
        videoPlayer.setHideKey(true);

        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void joinChatRoom(final String roomId) {
        ChatroomKit.joinChatRoom(roomId, 5, new RongIMClient.OperationCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Toast.makeText(LiveShowActivity.this, "聊天室加入失败! errorCode = " + errorCode, Toast.LENGTH_SHORT).show();
            }
        });
    }

    long banStartTime = 0;

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            // 接收和发送信息处理
            case ChatroomKit.MESSAGE_ARRIVED:
            case ChatroomKit.MESSAGE_SENT: {
                // 创建消息对象
                MessageContent messageContent = ((io.rong.imlib.model.Message) msg.obj).getContent();
                //  发送方的userId
                String sendUserId = ((io.rong.imlib.model.Message) msg.obj).getSenderUserId();
                // 弹幕消息处理
                if (messageContent instanceof ChatroomBarrage) {
                    ChatroomBarrage barrage = (ChatroomBarrage) messageContent;
                    DanmuEntity danmuEntity = new DanmuEntity();
                    danmuEntity.setContent(barrage.getContent());
                    danmuEntity.setPortrait(Uri.parse(""));
                    danmuEntity.setName(sendUserId);
                    danmuEntity.setType(barrage.getType());
                    danmuContainerView.addDanmu(danmuEntity);
                } else if (messageContent instanceof ChatroomGift) {
                    // 礼物消息处理
                    ChatroomGift gift = (ChatroomGift) messageContent;
                    if (gift.getNumber() > 0) {
                        GiftSendModel model = new GiftSendModel(gift.getNumber());
                        model.setGiftRes(DataInterface.getGiftInfo(gift.getId()).getGiftRes());
                        model.setNickname(sendUserId);
                        model.setSig("送出" + DataInterface.getGiftNameById(gift.getId()));
                        model.setUserAvatarRes("");
                        giftView.addGift(model);

                        giftNum = giftNum + gift.getNumber();
                        hostPanel.setGiftNum(giftNum);
                    }
                } else {
                    // 消息列表添加新消息
                    chatListAdapter.addMessage((io.rong.imlib.model.Message) msg.obj);

                    // 如果是欢迎消息
                    if (messageContent instanceof ChatroomWelcome) {
                        onlineNum++;
                        tvOnlineNum.setText(onlineNum + "");
                    } else if (messageContent instanceof ChatroomUserQuit) {
                        // 退出直播间
                        onlineNum--;
                        tvOnlineNum.setText(onlineNum + "");
                    } else if (messageContent instanceof ChatroomFollow) {
                        // 关注
                        fansNum++;
                        hostPanel.setFansNum(fansNum);
                    } else if (messageContent instanceof ChatroomLike) {
                        // 赞
                        likeNum = likeNum + ((ChatroomLike) messageContent).getCounts();
                        hostPanel.setLikeNum(likeNum);
                        //出点赞的心
                        for (int i = 0; i < ((ChatroomLike) messageContent).getCounts(); i++) {
                            heartLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    int rgb = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
                                    heartLayout.addHeart(rgb);
                                }
                            });
                        }
                    } else if (messageContent instanceof ChatroomUserBan) {
                        // 禁言
                        if (DataInterface.isLoginStatus() && ChatroomKit.getCurrentUser().getUserId().equals(((ChatroomUserBan) messageContent).getId())) {
                            banStartTime = System.currentTimeMillis();
                            startBan(banStartTime, ((ChatroomUserBan) messageContent).getDuration());
                        }
                    } else if (messageContent instanceof ChatroomUserUnBan) {
                        // 解禁
                        if (DataInterface.isLoginStatus() && ChatroomKit.getCurrentUser().getUserId().equals(((ChatroomUserUnBan) messageContent).getId())) {
                            DataInterface.setBanStatus(false);
                        }
                    } else if (messageContent instanceof ChatroomUserBlock) {
                        // 禁封
                        if (DataInterface.isLoginStatus() && ChatroomKit.getCurrentUser().getUserId().equals(((ChatroomUserBlock) messageContent).getId())) {
                            new AlertDialog.Builder(LiveShowActivity.this).setTitle("已被管理员禁封").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            }).setCancelable(false).show();

                        }
                    }
                }
                break;
            }
            case ChatroomKit.MESSAGE_SEND_ERROR: {
                break;
            }
            default:
        }
        chatListAdapter.notifyDataSetChanged();
        return false;
    }

    public void startBan(final long thisBanStartTime, long duration) {
        DataInterface.setBanStatus(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (banStartTime == thisBanStartTime) {
                    DataInterface.setBanStatus(false);
                }
            }
        }, duration * 1000 * 60);
    }

    long currentTime = 0;
    int clickCount = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.background:
                bottomPanel.onBackAction();
                hostPanel.setVisibility(View.GONE);
                onlineUserPanel.setVisibility(View.GONE);
                loginPanel.setVisibility(View.GONE);
                break;
            case R.id.btn_heart:
//                if (DataInterface.isLoginStatus()) {
                heartLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        int rgb = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
                        heartLayout.addHeart(rgb);
                    }
                });
                clickCount++;
                currentTime = System.currentTimeMillis();
                checkAfter(currentTime);
//                } else {
//                    EventBus.getDefault().post(new NeedLoginEvent(true));
//                }
                break;
            case R.id.layout_host:
                // 点击当前房间头像
                onlineUserPanel.setVisibility(View.GONE);
                if (hostPanel.getVisibility() == View.VISIBLE) {
                    hostPanel.setVisibility(View.GONE);
                } else {
                    hostPanel.setVisibility(View.VISIBLE);
                }
                break;
            default:
        }
    }

    //500毫秒后做检查，如果没有继续点击了，发消息
    public void checkAfter(final long lastTime) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (lastTime == currentTime) {
                    ChatroomLike likeMessage = new ChatroomLike();
                    likeMessage.setCounts(clickCount);
                    ChatroomKit.sendMessage(likeMessage);

                    clickCount = 0;
                }
            }
        }, 500);
    }

    @Subscribe
    public void onEventMainThread(NeedLoginEvent event) {
        if (event.isNeedLogin()) {
            loginPanel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        ChatroomKit.quitChatRoom(new RongIMClient.OperationCallback() {
            @Override
            public void onSuccess() {
                ChatroomKit.removeEventHandler(handler);
                if (DataInterface.isLoginStatus()) {
                    Toast.makeText(LiveShowActivity.this, "退出聊天室成功", Toast.LENGTH_SHORT).show();

                    ChatroomUserQuit userQuit = new ChatroomUserQuit();
                    userQuit.setId(ChatroomKit.getCurrentUser().getUserId());
                    ChatroomKit.sendMessage(userQuit);
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                ChatroomKit.removeEventHandler(handler);
                Toast.makeText(LiveShowActivity.this, "退出聊天室失败! errorCode = " + errorCode, Toast.LENGTH_SHORT).show();

            }
        });

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null) {
            orientationUtils.releaseListener();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (!bottomPanel.onBackAction()) {
            finish();
            return;
        }
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }

    public void setAddress(LiveAddressBean liveAddressBean) {
        LiveAddressBean.PullBean pull = liveAddressBean.getPull();
        String rtmpurl = pull.getRtmpurl();
        String url = "rtmp://192.168.199.216:1935/live/home";
        videoPlayer.setUp(rtmpurl, false, "");
        videoPlayer.startPlayLogic();
    }

    public void setAddressError() {
        String url = "rtmp://192.168.199.216:1935/live/home";
        videoPlayer.setUp(url, false, "");
        videoPlayer.startPlayLogic();
    }
}
