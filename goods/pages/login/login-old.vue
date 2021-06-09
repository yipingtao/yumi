<template>
	<view class="bg-img bg-mask flex align-center content" style="background-image: url('http://img4.imgtn.bdimg.com/it/u=2007968052,3819673298&fm=26&gp=0.jpg');">
			<view class="padding-xl text-white">
				<view class="padding-xs text-xxl text-bold">
					 外卖App
				</view>
				<view class="padding-xs text-lg">
					<view class="form" style="color: #8F8F94;">
						<view class="cu-form-group inputWrapper" >
							<view class="title">用户名</view>
							<input placeholder="请输入用户名" v-model="loginForm.loginName" name="loginName" disabled="true"></input>
						</view>
						<view class="cu-form-group inputWrapper">
							<view class="title">密码</view>
							<input placeholder="请输入密码" v-model="loginForm.password" name="password"></input>
						</view>
					</view>
					
					<view class="loginBtn" @click="login">
						<text class="btnValue" >登录</text>
					</view>
				<!-- 	<view class="forgotBtn" open-type="getUserInfo" @getuserinfo="wexinLogin" withCredentials="true">
						<text class="btnValue">微信授权登录</text>
					</view> -->
					<!-- <button class="" open-type="getUserInfo" @getuserinfo="wexinLogin" withCredentials="true">微信授权获取用户信息</button> -->
				
				
				</view>
			</view>
	</view>
</template>

<script>
	import common from '../../common/common.js';
	export default {
		data() {
			return {
	        loginForm: {
					loginName: '1',
					password: '1'
				}
			}
		},
		onLoad() {

		},
		methods: {
			login() {
				uni.request({
					url: common.webUrl + 'user/getUser',
					data: this.loginForm,
					header: {
						'custom-header': 'hello' //自定义请求头信息
					},
					method: 'POST',
					dataType: 'json',
					success: (res) => {
						if (res.data.resCode == '0000' && res.data.data.length > 0) {
							uni.showToast({
								title:"登陆成功"
							})

                            var userObj = res.data.data[0];
							uni.setStorage({
								key: 'userObj',
								data: userObj,
								success: function () {
									console.log(JSON.stringify(userObj));
								}
							});
	                     /*跳转新路径*/
						 // uni.navigateTo({ //跳转到非TAB页面
						 //     url: '../index/index'
						 // });
						 uni.switchTab({ //跳转到TAB页面的某个页面
						     url: '/pages/home/home'
						 });
						} else {
							// plus.nativeUI.alert("登陆失败");
							uni.showToast({
								title:"登陆失败"
							})
						}
					}
				});
			},
			wexinLogin:function(res){
				    if (!res.detail.iv) {
				        uni.showToast({
				            title: "您取消了授权,登录失败",
				            icon: "none"
				        });
				        return false;
				    }
					console.log('-------用户授权，并获取用户基本信息和加密数据------')
					console.log(res.detail.userInfo);
					this.loginForm = {
						loginName: res.detail.userInfo.nickName,
						password: '111111'
					}
			        uni.request({
			        	url: common.webUrl + 'user/weixinUserLogin',
			        	data: {
							"loginName":this.loginForm.loginName,
							"photo":res.detail.avatarUrl,
							"password":"111111",
							"name":this.loginForm.loginName
						},
			        	method: 'POST',
			        	dataType: 'json',
			        	success: (res) => {
			        		if (res.data.resCode == '0000') {
								   this.login()
			        		}
			        	}
			        });
			
			}
		}
	}
</script>

<style>
	.content{
		height: 100vh;
	}
	.avatarWrapper {
		height: 30vh;
		width: 100vw;
		display: flex;
		justify-content: center; //属性定义了项目在主轴上的对齐方式
		align-items: flex-end; //属性定义项目在交叉轴上如何对齐
	}

	.avatar {
		width: 300upx;
		height: 300upx;
		overflow: hidden;
	}

	.avatar .img {
		width: 100%;
	}

	.form {
		padding: 0 100upx;
		margin-top: 80px;
	}

	.inputWrapper {
		width: 100%;
		height: 80upx;
		background: white;
		border-radius: 20px;
		box-sizing: border-box;
		padding: 0 20px;
		margin-top: 25px;
	}
	
	.inputWrapper .input{
		width: 100%;
		height: 100%;
		font-size: 15px;
		text-align: center;
	}
	
	.loginBtn{
		width: 100%;
		height: 80upx;
		/* background: #61a0d2; */
		border-radius: 50upx;
		display: flex;
		justify-content: center;
		align-items: center;
		margin-top: 50px;
	}
	.loginBtn .btnValue { 
		color: white;
	}
	
	.forgotBtn{
		text-align: center;
		color: white;
		margin-top: 20px;
	}
</style>
