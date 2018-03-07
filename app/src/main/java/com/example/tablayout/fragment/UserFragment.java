package com.example.tablayout.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tablayout.R;
import com.example.tablayout.UI.Express;
import com.example.tablayout.UI.HomeLocation;
import com.example.tablayout.UI.LoginActivity;
import com.example.tablayout.View.CustomDialog;
import com.example.tablayout.entitly.MyUser;
import com.example.tablayout.util.L;
import com.example.tablayout.util.UtilTools;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static android.app.Activity.RESULT_OK;

/**
 * 项目名称：TabLayout
 * 包名：com.example.tablayout.fragment
 * 文件名：UserFragment
 * 创建者：LBW
 * 创建时间：2017/12/1811:322017
 * 描述：TODO
 */

public class UserFragment extends Fragment implements View.OnClickListener {
    private View view;
    private ImageView iv_user_image;
    private Button btn_edit_user;
    private EditText et_user_name;
    private EditText et_user_gander;
    private EditText et_user_age;
    private EditText et_user_intro;
    private Button btn_confirm;
    private TextView tv_query_express;
    private TextView tv_query_location;
    private Button btn_user_quit;
    private CustomDialog dialog;
    //dialog按钮
    private Button btn_camera;
    private Button btn_photo;
    private Button btn_cancel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_fragment, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        iv_user_image = (ImageView) view.findViewById(R.id.iv_user_image);
        btn_edit_user = (Button) view.findViewById(R.id.btn_edit_user);
        et_user_name = (EditText) view.findViewById(R.id.et_user_name);
        et_user_gander = (EditText) view.findViewById(R.id.et_user_gander);
        et_user_age = (EditText) view.findViewById(R.id.et_user_age);
        et_user_intro = (EditText) view.findViewById(R.id.et_user_intro);
        btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
        tv_query_express = (TextView) view.findViewById(R.id.tv_query_express);
        tv_query_location = (TextView) view.findViewById(R.id.tv_query_location);
        btn_user_quit = (Button) view.findViewById(R.id.btn_user_quit);

        iv_user_image.setOnClickListener(this);
        btn_user_quit.setOnClickListener(this);
        btn_edit_user.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
        tv_query_express.setOnClickListener(this);
        tv_query_location.setOnClickListener(this);


        //初始化dialog
        dialog=new CustomDialog(getActivity(),0,0,R.layout.dialog_choose_photo,R.style.pop_anim_style, Gravity.BOTTOM,0);
        btn_photo= (Button) dialog.findViewById(R.id.btn_photo);
        btn_camera= (Button) dialog.findViewById(R.id.btn_camera);
        btn_cancel= (Button) dialog.findViewById(R.id.btn_cancel);
        btn_photo.setOnClickListener(this);
        btn_camera.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        //默认EditText不可编辑
        isEnable(false);

        //显示资料


        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        et_user_name.setText(userInfo.getUsername());
        et_user_age.setText(userInfo.getAge() + "");
        et_user_gander.setText(userInfo.isSex() ? "男" : "女");
        et_user_intro.setText(userInfo.getIntro());

        //从share中取得头像
        UtilTools.getImageToShare(getActivity(),iv_user_image);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //退出登录按钮监听
            case R.id.btn_user_quit: {
                //清除缓存用户对象
                MyUser.logOut();
                // 现在的currentUser是null了
                BmobUser currentUser = MyUser.getCurrentUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            }
            //编辑资料
            case R.id.btn_edit_user: {
                //显示确认修改按钮
                btn_confirm.setVisibility(View.VISIBLE);
                //EditText可编辑
                isEnable(true);


                break;
            }
            //确认修改
            case R.id.btn_confirm: {
                //得到输入框的值
                String name=et_user_name.getText().toString().trim();
                String age= (et_user_age.getText().toString().trim());
                String gander=et_user_gander.getText().toString();
                String intro=et_user_intro.getText().toString();
                // 判断是否为空
                if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(age)&&
                        !TextUtils.isEmpty(gander)&&
                        !TextUtils.isEmpty(intro)){
                    MyUser myUser=new MyUser();
                    myUser.setUsername(name);
                    myUser.setAge(Integer.parseInt(age));
                    myUser.setIntro(intro);
                    //判断男女
                    if (gander.equals("男")){
                        myUser.setSex(true);
                    }else if (gander.equals("女")){
                        myUser.setSex(false);
                    }else {

                        Toast.makeText(getActivity(), "请输入正确的性别！", Toast.LENGTH_SHORT).show();
                    }
                    //简介如果为空
                    if(TextUtils.isEmpty(intro)){
                        myUser.setIntro("这个人很懒，什么都没有留下。");
                    }
                    BmobUser bu=BmobUser.getCurrentUser();


                   myUser.update(bu.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                isEnable(false);
                                //EditText不可编辑
                                btn_confirm.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "修改信息成功", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(), "修改信息失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    });



                }else {
                    Toast.makeText(getActivity(), "输入框不能为空!", Toast.LENGTH_SHORT).show();
                }





                break;
            }
            //点击头像响应
            case R.id.iv_user_image:
               dialog.show();

                break;
            //dialog相应事件
            case R.id.btn_camera:
                dialog.dismiss();
                toCamera();
                break;
            case R.id.btn_photo:
                dialog.dismiss();
                toPhoto();

                break;
            case R.id.btn_cancel:
                dialog.dismiss();

                break;
            //快递查询按钮点击
            case R.id.tv_query_express:
                startActivity(new Intent(getActivity(),Express.class));

                break;
            //归属地查询按钮点击
            case R.id.tv_query_location:
                startActivity(new Intent(getActivity(),HomeLocation.class));

                break;

        }
    }
    public static final String USER_FILE_IMAGE="userImage.jpg";
    public static final int CAMERA_RESULT_CODE=100;
    public static final int PHOTO_RESULT_CODE=101;
    public static final int CUT_PHOTO_RESULT=102;

    private File tempFile=null;
    private void toPhoto() {
        L.e("点击相册选取照片");
            Intent intent=new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,PHOTO_RESULT_CODE);
    }


    private void toCamera() {
        if (Build.VERSION.SDK_INT<24){
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //判断内存卡是否可用并且存储
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),USER_FILE_IMAGE)));
            startActivityForResult(intent,CAMERA_RESULT_CODE);
        }else {
            Toast.makeText(getActivity(), "7.0版本相机", Toast.LENGTH_SHORT).show();
        }


    }

    public void isEnable(boolean is){
        et_user_name.setEnabled(is);
        et_user_age.setEnabled(is);
        et_user_gander.setEnabled(is);
        et_user_intro.setEnabled(is);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case CAMERA_RESULT_CODE:
                    tempFile=new File(Environment.getExternalStorageDirectory(),USER_FILE_IMAGE);
                    cutPhoto(Uri.fromFile(tempFile));

                    break;

                case PHOTO_RESULT_CODE:
                    L.e("跳转到剪裁");
                    cutPhoto(data.getData());

                    break;

                case CUT_PHOTO_RESULT:
                    L.e("剪裁完毕");
                    //判断是否取消
                    if (data!=null){
                        setImageToView(data);
                        //删除原来的图片
                        if (tempFile!=null){
                            tempFile.delete();
                        }
                    }


                    break;





            }



        }
    }
// 裁剪照片
    private void cutPhoto(Uri uri) {
        L.e("开始剪裁");
        if (uri==null){
            L.e("uri==null");
            return;
        }
        Intent intent=new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //设置裁剪
        intent.putExtra("crop", "true");
        // 裁剪比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        // 发送数据
        intent.putExtra("return-data",true);
        startActivityForResult(intent,CUT_PHOTO_RESULT);


    }
    //设置用户头像
    public void setImageToView(Intent data){
       Bundle bundle= data.getExtras();
        if (bundle!=null){
          Bitmap bitmap=  bundle.getParcelable("data");
            iv_user_image.setImageBitmap(bitmap);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UtilTools.putImageToShare(getActivity(),iv_user_image);
    }
}
