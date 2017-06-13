package com.ninis.grimmoa.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ninis.grimmoa.MainActivity;
import com.ninis.grimmoa.R;
import com.ninis.grimmoa.adapter.CardItemDecoration;
import com.ninis.grimmoa.adapter.GrimItemAdapter;
import com.ninis.grimmoa.custom.ItemClickSupport;
import com.ninis.grimmoa.data.ImgPostData;
import com.ninis.grimmoa.network.RetrofitClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.refactor.lib.colordialog.PromptDialog;
import io.github.yuweiguocn.lib.squareloading.SquareLoading;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gypark on 2017. 3. 20..
 */

public class MainFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.rv_img_post_list)
    RecyclerView recyclerView;
    @BindView(R.id.squareLoading)
    SquareLoading squareLoading;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rl_toolbar_more_area)
    RelativeLayout rlToolbarMore;
    @BindView(R.id.iv_scroll_top_btn)
    ImageView ivScrollTopBtn;

    private GrimItemAdapter adapter;
    private MainActivity.OnPageModeEvent onPageModeEvent;

    private PromptDialog infoDialog;

    private int iNextPage = 1;

    public static MainFragment create() {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRecyclerView();
        initOtherView();
        initDialog();
        loadData();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(
                new CardItemDecoration(
                        (int) getActivity().getResources().getDimension(R.dimen.img_post_spacing)
                )
        );

        adapter = new GrimItemAdapter() {
            @Override
            public void load() {
                Log.d("NINIS_LOG", "GrimItemAdapter load().... : " + iNextPage);
                loadDataPage();
            }
        };
        recyclerView.setAdapter(adapter);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                if (v.getTag() != null) {
                    String link = (String) v.getTag();
                    if (onPageModeEvent != null) {
                        onPageModeEvent.moveWebViewPage(link);
                    }
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    private void initOtherView() {
        rlToolbarMore.setOnClickListener(this);
    }

    private void initDialog() {
        infoDialog = new PromptDialog(getActivity())
                .setDialogType(PromptDialog.DIALOG_TYPE_INFO)
                .setAnimationEnable(true)
                .setTitleText(getString(R.string.dlg_info_title))
                .setContentText(
                        getDlgMsg()
                )
                .setPositiveListener(getString(R.string.txt_confirm), new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                });
    }

    private void loadData() {
        squareLoading.setVisibility(View.VISIBLE);
        RetrofitClient.getInstance().getImgPosts(true).enqueue(new Callback<List<ImgPostData>>() {
            @Override
            public void onResponse(Call<List<ImgPostData>> call, Response<List<ImgPostData>> response) {
                squareLoading.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

                Log.d("NINIS", "onResponse]\n" + response.toString());

                if (response.body() != null) {
                    Log.d("NINIS", "Size : " + response.body().size());
                    adapter.setItems(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ImgPostData>> call, Throwable t) {
                squareLoading.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                Log.d("NINIS", "onFailure]\n" + t.getMessage());
            }
        });
    }

    private void loadDataPage() {
        squareLoading.setVisibility(View.VISIBLE);
        RetrofitClient.getInstance().getImgPosts(true, iNextPage+1).enqueue(new Callback<List<ImgPostData>>() {
            @Override
            public void onResponse(Call<List<ImgPostData>> call, Response<List<ImgPostData>> response) {
                squareLoading.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

                Log.d("NINIS", "onResponse]\n" + response.toString());

                if (response.body() != null) {
                    Log.d("NINIS", "Size : " + response.body().size());
                    adapter.addItems(response.body());
                }

                iNextPage++;
            }

            @Override
            public void onFailure(Call<List<ImgPostData>> call, Throwable t) {
                squareLoading.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                Log.d("NINIS", "onFailure]\n" + t.getMessage());
            }
        });
    }

    public void setOnPageModeEvent(MainActivity.OnPageModeEvent onPageModeEvent) {
        this.onPageModeEvent = onPageModeEvent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_toolbar_more_area:
                infoDialog.show();
                break;
        }
    }

    private SpannableString getDlgMsg() {
        String msg = getString(R.string.dlg_ingo_content) +  "\n" +
                getString(R.string.dlg_info_board_list) + "\n연락처 : " +
                getString(R.string.dlg_info_contact)
                ;

        // Linkify the message
        final SpannableString s = new SpannableString(msg);
        Linkify.addLinks(s, Linkify.EMAIL_ADDRESSES);

        return s;
    }

    @OnClick(R.id.iv_scroll_top_btn)
    public void onScorllTopBtn() {
        if( recyclerView != null ) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();
            layoutManager.scrollToPositionWithOffset(0, 0);
        }
    }
}
