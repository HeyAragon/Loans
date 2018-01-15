package com.hackhome.loans.ui.mine;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.hackhome.loans.R;
import com.hackhome.loans.bean.ResponseBean;
import com.hackhome.loans.common.Constants;
import com.hackhome.loans.common.eventbus.EB;
import com.hackhome.loans.common.eventbus.EventItem;
import com.hackhome.loans.common.imageloader.GlideApp;
import com.hackhome.loans.common.utils.FormatUtils;
import com.hackhome.loans.common.utils.StatusBarUtil;
import com.hackhome.loans.common.utils.ToastUtils;
import com.hackhome.loans.common.utils.UploadUtil;
import com.hackhome.loans.common.utils.UserUtil;
import com.hackhome.loans.dagger.component.ApplicationComponent;
import com.hackhome.loans.dagger.component.DaggerIUserInfoComponent;
import com.hackhome.loans.dagger.module.CommonModule;
import com.hackhome.loans.dagger.module.UserInfoModule;
import com.hackhome.loans.net.ApiConstants;
import com.hackhome.loans.presenter.contract.ICommonContract;
import com.hackhome.loans.presenter.contract.IUserInfoContract;
import com.hackhome.loans.presenter.UserInfoPresenter;
import com.hackhome.loans.ui.base.BaseActivity;
import com.hackhome.loans.presenter.CommonPresenter;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.socks.library.KLog;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * desc:
 * author: aragon
 * date: 2018/1/4 0004.
 */
public class UserInfoActivity extends BaseActivity<UserInfoPresenter> implements IUserInfoContract.UserInfoView{
    @BindView(R.id.tool_bar_back_img)
    ImageView mToolBarBackImg;
    @BindView(R.id.tool_bar_title_txt)
    TextView mToolBarTitleTxt;
    @BindView(R.id.simple_toolbar)
    Toolbar mSimpleToolbar;
    @BindView(R.id.view_gone)
    View mViewGone;
    @BindView(R.id.tv_pic)
    TextView mTvPic;
    @BindView(R.id.tv_take_photo)
    TextView mTvTakePhoto;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.ll_select_pic)
    LinearLayout mLlSelectPic;
    @BindView(R.id.iv_user_icon)
    ImageView mIvUserIcon;
    @BindView(R.id.ll_user_icon)
    LinearLayout mLlUserIcon;
    @BindView(R.id.tv_user_id)
    TextView mTvUserId;
    @BindView(R.id.tv_user_nick)
    TextView mTvUserNick;
    @BindView(R.id.tv_user_gender)
    TextView mTvUserGender;
    @BindView(R.id.tv_phone_state)
    TextView mTvPhoneState;
    @BindView(R.id.tv_exchange_password)
    TextView mTvExchangePassword;
    @BindView(R.id.tv_user_login_time)
    TextView mTvUserLoginTime;
    @BindView(R.id.btn_user_exit)
    Button mBtnUserExit;
    private Animation mOutAnimation;
    private Animation mInAnimation;
    private boolean mIsFromPic;
    private Uri mTakePhotoUri;
    private String imagePath;
    private String mNewNick;
    private String mNewGender;
    private Map<String, String> mCookiesParams;


    @Override
    public int getLayoutContentRes() {
        return R.layout.activity_user_info;
    }

    @Override
    public void loadData() {
        mCookiesParams = UserUtil.getCookiesParams();
    }

    @Override
    public void initView() {
        mSimpleToolbar.setVisibility(View.VISIBLE);
        mToolBarBackImg.setVisibility(View.VISIBLE);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.md_deep_orange_500), 0);

        GlideApp.with(this)
                .load(UserUtil.getUserIcon())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new CircleCrop())
                .into(mIvUserIcon);

        mTvUserId.setText(UserUtil.getUserID());
        mTvUserNick.setText(UserUtil.getUserNick());
        mTvUserGender.setText(UserUtil.getGender());
        mTvPhoneState.setText(UserUtil.getPhoneNum());
        mTvUserLoginTime.setText(UserUtil.getUserLastLoginTime());

    }

    @Override
    public <T> void showResponse(T t, int type) {
        if (t instanceof ResponseBean) {
            ResponseBean responseBean = (ResponseBean) t;
            ToastUtils.showToast(responseBean.getInfo());
            if (TextUtils.equals("200", responseBean.getStatus())) {

                if (type==Constants.ResponseType.TYPE_CHANGE_NICK) {
                    mTvUserNick.setText(mNewNick);
                    UserUtil.changeUserNick(mNewNick);
                } else if (type==Constants.ResponseType.TYPE_CHANGE_GENDER) {
                    mTvUserGender.setText(mNewGender);
                    UserUtil.setGender(mNewGender);
                } else if (type==Constants.ResponseType.TYPE_CHANGE_PASSWORD) {
                    UserUtil.exit();
                    finish();
                    EB.getInstance().send(EventItem.MINE_FRAGMENT_OBJECT,EventItem.EXIT_SUCCESS);
                }

            }


        }
    }

    @OnClick({R.id.tool_bar_back_img, R.id.view_gone, R.id.tv_pic, R.id.tv_take_photo,
            R.id.tv_cancel, R.id.ll_select_pic, R.id.ll_user_icon, R.id.ll_user_nick,
            R.id.ll_user_gender, R.id.tv_exchange_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tool_bar_back_img:
                finish();
                break;
            case R.id.ll_user_icon:
                getShowAnimatorSet(mLlSelectPic).start();
                mLlSelectPic.setVisibility(View.VISIBLE);
                break;
            case R.id.view_gone:

                getExitAnimatorSet(mLlSelectPic).start();

                break;
            case R.id.tv_take_photo:
                mViewGone.setClickable(false);
                mLlSelectPic.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},102);

                } else {
                    mIsFromPic = false;
                    openCamera();
                }

                break;
            case R.id.tv_pic:

                mLlSelectPic.setVisibility(View.GONE);

                mIsFromPic = true;
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 100);
                break;
            case R.id.tv_cancel:
                    getExitAnimatorSet(mLlSelectPic).start();
                break;

            case R.id.ll_user_nick:
                new MaterialDialog.Builder(this)
                        .title(getString(R.string.change_nick))
                        .widgetColor(getResources().getColor(R.color.md_deep_orange_500))
                        .positiveText(getString(R.string.confirm)).positiveColor(getResources().getColor(R.color.black))
                        .negativeText(getString(R.string.cancel)).negativeColor(getResources().getColor(R.color.md_blue_grey_200))
                        .input(getString(R.string.nick_hint), getString(R.string.default_nick), false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                checkNick(input.toString());
                            }
                        }).build().show();


                break;
            case R.id.ll_user_gender:

                final String[] genders = getResources().getStringArray(R.array.gender);
                new MaterialDialog.Builder(this)
                        .title(getString(R.string.choice_gender))
                        .items((CharSequence[]) genders)
                        .widgetColor(getResources().getColor(R.color.md_deep_orange_500))
                        .itemsColor(getResources().getColor(R.color.colorTxtTitle))
                        .negativeText("取消").negativeColor(getResources().getColor(R.color.md_blue_grey_200))
                        .positiveText("确定").positiveColor(getResources().getColor(R.color.colorTxtTitle))
                        .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
//                                mTvUserGender.setText(genders[which]);

                                if (mCookiesParams != null&& !TextUtils.equals(UserUtil.getGender(),genders[which])) {
                                    mCookiesParams.put("sex", String.valueOf(which));
                                    mNewGender = genders[which];
                                    mPresenter.changeNickOrGenderOrPassword(mCookiesParams,ApiConstants.TYPE_EDIT_USER_INFO,Constants.ResponseType.TYPE_CHANGE_GENDER);
                                }

                                return false;
                            }
                        }).build().show();

                break;
            case R.id.tv_exchange_password:
                final MaterialDialog materialDialog = new MaterialDialog.Builder(this)
                        .customView(R.layout.change_password_layout, false)
                        .build();
                materialDialog.show();
                View customView = materialDialog.getCustomView();
                final MaterialEditText oldPass = customView.findViewById(R.id.dialog_old_password);
                final MaterialEditText newPass = customView.findViewById(R.id.dialog_new_password);
                final MaterialEditText repeatPass = customView.findViewById(R.id.dialog_repeat_new_password);
                TextView textView = customView.findViewById(R.id.dialog_change_password_confirm);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (UserUtil.canModifyPass()) {
                            if (TextUtils.isEmpty(oldPass.getText())) {
                                ToastUtils.showToast("请输入原密码");
                                return;
                            }

                        }
                        //判断不为空前后一致即可
                        if (TextUtils.isEmpty(newPass.getText())) {
                            ToastUtils.showToast("请输入新密码");
                            return;
                        }
                        if (!TextUtils.equals(newPass.getText(), repeatPass.getText())) {
                            ToastUtils.showToast("两次密码不一致");
                            return;
                        }
                        if (mCookiesParams != null) {
                            mCookiesParams.put(ApiConstants.TYPE_PASSA, oldPass.getText().toString());
                            mCookiesParams.put(ApiConstants.TYPE_PASS, newPass.getText().toString());
                            mCookiesParams.put(ApiConstants.TYPE_PASSS, repeatPass.getText().toString());
                            mPresenter.changeNickOrGenderOrPassword(
                                    mCookiesParams,
                                    ApiConstants.TYPE_EDIT_USER_PASSWORD,
                                    Constants.ResponseType.TYPE_CHANGE_PASSWORD
                                    );

                        }
                        materialDialog.dismiss();
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            //获取到图片
            case 100:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
                        addPic(0, data);
                    else addPic(1, data);
                } else ToastUtils.showToast(getString(R.string.cancel));
                break;
            //裁剪图片
            case 101:
                if (resultCode == RESULT_OK) {
                    String picFilePath;
                    Bitmap bitmap;
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        if (mIsFromPic) {
                            picFilePath = imagePath;
//                            LogUtil.i("压缩前      " + new File(imagePath).length() / 1024 + "K");
                            if (new File(imagePath).length() > 1048576) {
                                BitmapFactory.decodeFile(imagePath, options);
                                options.inSampleSize = calculateInSampleSize(options, 100, 1000);
                                options.inPreferredConfig = Bitmap.Config.RGB_565;
                                options.inJustDecodeBounds = false;
                                bitmap = BitmapFactory.decodeFile(imagePath, options);
                            } else
                                bitmap = BitmapFactory.decodeFile(imagePath);
                        } else {
                            picFilePath = mTakePhotoUri.getPath();
                            InputStream inputStream = getContentResolver().openInputStream(mTakePhotoUri);
//                            LogUtil.i("压缩前      " + new File(mTakePhotoUri.getPath()).length() / 1024 + "K");
                            if (new File(mTakePhotoUri.getPath()).length() > 1048576) {
                                BitmapFactory.decodeStream(inputStream, new Rect(2, 2, 2, 2), options);
                                options.inSampleSize = calculateInSampleSize(options, 100, 1000);
                                options.inPreferredConfig = Bitmap.Config.RGB_565;
                                options.inJustDecodeBounds = false;
                                bitmap = BitmapFactory.decodeStream(inputStream, new Rect(2, 2, 2, 2), options);
                            } else
                                bitmap = BitmapFactory.decodeStream(inputStream);
                        }
//                        GlideApp.with(this)
//                                .load(bitmap)
//                                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                .transform(new CircleCrop())
//                                .into(mIvUserIcon);
                        mIvUserIcon.setImageBitmap(bitmap);
                        zipFile(picFilePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else ToastUtils.showToast(getString(R.string.pic_crop_error));
                break;
            //拍照获取图片
            case 102:
                if (resultCode == RESULT_OK)
                    cropPhoto(mTakePhotoUri);
                else ToastUtils.showToast(getString(R.string.crop_cancel));
                break;
        }
    }

    private void checkNick(String nick) {
        if (TextUtils.equals(nick, UserUtil.getUserNick())) {
            ToastUtils.showToast( "和以前的昵称相同");
            return;
        }
        if (TextUtils.isEmpty(nick)) {
            ToastUtils.showToast("昵称不能为空");
            return;
        }
        if (!TextUtils.equals(nick, strFilter(nick))) {
            ToastUtils.showToast("昵称不能含有特殊字符");
            return;
        }
        if (nick.length() <= 1) {
            ToastUtils.showToast( "您设置的昵称过短~!");
            return;
        }
        if (nick.length() > 8) {
            ToastUtils.showToast("您设置的昵称过长~!");
            return;
        }

        mNewNick = nick;

        if (mCookiesParams != null) {
            mCookiesParams.put(ApiConstants.TYPE_EDIT_USER_NICK, FormatUtils.escape(nick));
            mPresenter.changeNickOrGenderOrPassword(mCookiesParams,ApiConstants.TYPE_EDIT_USER_INFO, Constants.ResponseType.TYPE_CHANGE_NICK);
        }

    }

    // 只允许字母、数字和汉字
    public String strFilter(String str) throws PatternSyntaxException {
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5_-]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);

        return m.replaceAll("").trim();
    }

    /**
     * 打开系统相机
     */
    private void openCamera() {
        File fileParent = new File(Environment.getExternalStorageDirectory(),
                "wnqk/icon");
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
        File file = new File(fileParent.getAbsoluteFile(), "user_icon.jpg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTakePhotoUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);//通过FileProvider创建一个content类型的Uri
        } else {
            mTakePhotoUri = Uri.fromFile(file);
        }
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mTakePhotoUri);//将拍取的照片保存到指定URI
        startActivityForResult(intent, 102);
    }

    /**
     * 裁剪
     */
    private void cropPhoto(Uri uri) {
        File file = new File(Environment.getExternalStorageDirectory(),
                "wnqk/icon" + File.separator + "user_icon_small.jpg");

        if (!mIsFromPic) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mTakePhotoUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);//通过FileProvider创建一个content类型的Uri
            } else {
                mTakePhotoUri = Uri.fromFile(file);
            }
        }
        Uri outputUri = Uri.fromFile(file);
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 720);
        intent.putExtra("outputY", 460);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat",
                Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, 101);
    }

    private void zipFile(final String picFilePath) {
        Luban.with(this)
                .load(picFilePath)                               // 传人要压缩的图片列表
                .ignoreBy(100)                                  // 忽略不压缩图片的大小
                .setTargetDir(Environment.getExternalStorageDirectory() + File.separator +
                        "wnqk/icon" + File.separator)  // 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        //  压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // 压缩成功后调用，返回压缩后的图片文件
//                        uploadFile(file.getAbsolutePath());
                        uploadFile(file);
                        KLog.i("压缩后图片的大小   --------  " + (file.length() / 1024) + "K");
                    }

                    @Override
                    public void onError(Throwable e) {
                        //  当压缩过程出现问题时调用
                        e.printStackTrace();
                        uploadFile(new File(picFilePath));

                    }
                }).launch();    //启动压缩
    }

    public void uploadFile(File file) {
        if (mCookiesParams != null) {
            mPresenter.uploadPic(
                    ApiConstants.TYPE_UPLOAD,mCookiesParams,
                    UploadUtil.prepareFilePart(ApiConstants.TYPE_UPLOAD_PIC,file),
                    Constants.ResponseType.TYPE_CHANGE_USER_LOGO
            );

        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void addPic(int one, Intent data) {
        Uri imageUri = null;
        switch (one) {
            case 0:
                imageUri = data.getData();
                imagePath = getImagePath(imageUri, null);
                break;
            case 1:
                imagePath = null;
                imageUri = data.getData();
                if (DocumentsContract.isDocumentUri(this, imageUri)) {
                    String docId = DocumentsContract.getDocumentId(imageUri);
                    if ("com.android.providers.media.documents".equals(imageUri.getAuthority())) {
                        String id = docId.split(":")[1];
                        String selection = MediaStore.Images.Media._ID + "=" + id;
                        imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                    } else if ("com.android.downloads.documents".equals(imageUri.getAuthority())) {
                        Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                        imagePath = getImagePath(contentUri, null);
                    }
                } else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
                    imagePath = getImagePath(imageUri, null);
                } else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
                    imagePath = imageUri.getPath();
                }
                cropPhoto(imageUri);
                break;
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection老获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 指定图片的缩放比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(android.graphics.BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 原始图片的宽、高
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        /**
         * 压缩方式一
         */
        // 计算压缩的比例：分为宽高比例
        final int heightRatio = Math.round((float) height
                / (float) reqHeight);
        final int widthRatio = Math.round((float) width / (float) reqWidth);
        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
//      }
        return inSampleSize;
    }

    public AnimatorSet getShowAnimatorSet(View view) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationY", new float[]{750F, 0.0F}).setDuration(400L),
                ObjectAnimator.ofFloat(view, "alpha", new float[]{0.4F, 1.0F}).setDuration(400L));
        set.setInterpolator(new OvershootInterpolator(1.2f));
        return set;
    }

    public AnimatorSet getExitAnimatorSet(final View view) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationY", new float[]{0.0F,750F }).setDuration(400L),
                ObjectAnimator.ofFloat(view, "alpha", new float[]{1.0F, 0.4F}).setDuration(400L));
        set.setInterpolator(new AnticipateInterpolator(1.2f));

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }
            @Override
            public void onAnimationEnd(Animator animation) {

            }
            @Override
            public void onAnimationCancel(Animator animation) {

            }
            @Override
            public void onAnimationRepeat(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        return set;
    }


    @Override
    public void initInjector(ApplicationComponent applicationComponent) {
        DaggerIUserInfoComponent.builder()
                .applicationComponent(applicationComponent)
                .userInfoModule(new UserInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onRetry() {

    }
}
