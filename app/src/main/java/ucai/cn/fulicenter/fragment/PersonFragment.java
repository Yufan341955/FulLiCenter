package ucai.cn.fulicenter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.FuLiCenterApplication;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.activity.CollectActivity;
import ucai.cn.fulicenter.activity.LoginActivity;
import ucai.cn.fulicenter.activity.MainActivity;
import ucai.cn.fulicenter.activity.UpdatePersonActivity;
import ucai.cn.fulicenter.bean.MessageBean;
import ucai.cn.fulicenter.bean.Result;
import ucai.cn.fulicenter.bean.UserAvatar;
import ucai.cn.fulicenter.utils.ImageLoader;
import ucai.cn.fulicenter.utils.L;
import ucai.cn.fulicenter.utils.MFGT;
import ucai.cn.fulicenter.utils.NetDao;
import ucai.cn.fulicenter.utils.OkHttpUtils;

/**
 * Created by Administrator on 2016/10/24.
 */
public class PersonFragment extends BaseFragment {
    @Bind(R.id.m_Persion_Message)
    ImageView mPersionMessage;
    @Bind(R.id.m_Persion_Setting)
    TextView mPersionSetting;
    @Bind(R.id.m_Persion_UserAvatar)
    ImageView mPersionUserAvatar;
    @Bind(R.id.m_Persion_UserNick)
    TextView mPersionUserNick;
    @Bind(R.id.m_Persion_Collect_Treasure)
    TextView mPersionCollectTreasure;
    @Bind(R.id.m_Persion_Collect_Shop)
    TextView mPersionCollectShop;
    @Bind(R.id.m_Persion_Footprint)
    TextView mPersionFootprint;
    @Bind(R.id.m_Persion_My_All_Card_Iv)
    ImageView mPersionMyAllCardIv;
    @Bind(R.id.m_Persion_My_Online_Shop_IV)
    ImageView mPersionMyOnlineShopIV;
    @Bind(R.id.m_Persion_My_Member_Card_IV)
    ImageView mPersionMyMemberCardIV;

    MainActivity mContext;
    @Bind(R.id.m_Persion_My_Member_Card)
    RelativeLayout mPersionMyMemberCard;
    @Bind(R.id.lin_collect)
    LinearLayout linCollect;
    @Bind(R.id.m_Persion_See_Buyed_Treasure)
    LinearLayout mPersionSeeBuyedTreasure;
    @Bind(R.id.m_Persion_My_All_Card)
    RelativeLayout mPersionMyAllCard;
    @Bind(R.id.m_Persion_My_Live_Card)
    RelativeLayout mPersionMyLiveCard;
    @Bind(R.id.m_Persion_My_Online_Shop)
    RelativeLayout mPersionMyOnlineShop;
    UserAvatar user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_person, container, false);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getContext();

        super.onCreateView(inflater, container, savedInstanceState);

        return layout;
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        UserAvatar user = FuLiCenterApplication.getUser();
        if (user == null) {
           // MFGT.startActivity(mContext, LoginActivity.class);
            return;
        } else {
            downloadUserByUserName(user.getMuserName());
            getCollectCount(user.getMuserName());
//            mPersionUserNick.setText(user.getMuserNick());
//            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, mPersionUserAvatar);

        }
    }

    private void getCollectCount(String muserName) {
        NetDao.getCollectCount(mContext, muserName, new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {

                L.e("result=" + result.toString());
                if (result.isSuccess()) {
                    L.e("CollectCount=" + result.getMsg());
                    mPersionCollectTreasure.setText(result.getMsg());
                    L.e("CollectCount=" + result.getMsg());
                } else {
                    L.e("NOTonSuccess");
                    mPersionCollectTreasure.setText(result.getMsg());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void downloadUserByUserName(String muserName) {
        NetDao.findUserByUserName(mContext, muserName, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                if (result != null) {
                    if (result.isRetMsg()) {
                        String json = result.getRetData().toString();
                        Gson gson = new Gson();
                        user = gson.fromJson(json, UserAvatar.class);
                        mPersionUserNick.setText(user.getMuserNick());
                        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, mPersionUserAvatar);

                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.m_Persion_Setting, R.id.m_Persion_UserAvatar,R.id.lin_collect})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.m_Persion_Setting:
                MFGT.startActivity(mContext, UpdatePersonActivity.class);
                break;
            case R.id.m_Persion_UserAvatar:
                MFGT.startActivity(mContext, UpdatePersonActivity.class);
                break;
            case R.id.lin_collect:
                L.e("startActivity(CollectActivity.class)");
                Intent intent=new Intent(mContext, CollectActivity.class);
                intent.putExtra("userName",user.getMuserName());
                MFGT.startActivity(mContext,intent);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }


}
