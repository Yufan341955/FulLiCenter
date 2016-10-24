package ucai.cn.fulicenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ucai.cn.fulicenter.FuLiCenterApplication;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.activity.LoginActivity;
import ucai.cn.fulicenter.activity.MainActivity;
import ucai.cn.fulicenter.bean.UserAvatar;
import ucai.cn.fulicenter.utils.ImageLoader;
import ucai.cn.fulicenter.utils.MFGT;

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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_person, container, false);
        ButterKnife.bind(this, layout);
        mContext= (MainActivity) getContext();

        super.onCreateView(inflater, container, savedInstanceState);

        return layout;
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        UserAvatar user= FuLiCenterApplication.getUser();
        if(user==null){
            MFGT.startActivity(mContext, LoginActivity.class);
        }else {
         mPersionUserNick.setText(user.getMuserNick());
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),mContext,mPersionUserAvatar);

        }
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
