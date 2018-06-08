package com.sfan.app.base;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sfan.lib.app.BaseFragment;
import com.sfan.lib.app.MyToast;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zzy on 2018/6/11.
 * 空白片段
 */

public class EmptyFragment extends BaseFragment {

    @BindView(R.id.txtEmpty)
    TextView txtEmpty;
    Unbinder unbinder;

    @Override
    protected int getContentViewLayoutResID() {
        return R.layout.fragment_empty;
    }

    @Override
    protected void init(boolean create, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, mLayout);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.txtEmpty)
    public void onViewClicked() {
        RxPermissions rxPermissions = new RxPermissions(mActivity);
        rxPermissions.request(Manifest.permission.CALL_PHONE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean value) {
                if (value) {
                    MyToast.debug("同意权限");
                } else {
                    MyToast.debug("拒绝权限");
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
