<template>
	<view class="bg-img bg-mask flex align-center content" style="background-image: url('https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3673521686,254356743&fm=26&gp=0.jpg');">
		<view class="padding-xl text-white">
			<view class="padding-xs text-xxl text-bold animate__animated animate__bounce">
				外卖App
			</view>
			<view class="padding-xs text-lg" v-if="loginFormDiv">
				<view>
					<view class="form" style="color: #8F8F94;">
						<view class="cu-form-group inputWrapper">
							<view class="title">登录名</view>
							<input placeholder="请输入登录名" v-model="loginForm.loginName" name="loginName"></input>
						</view>
						<view class="cu-form-group inputWrapper">
							<view class="title">密码</view>
							<input placeholder="请输入密码" v-model="loginForm.password" name="password"></input>
						</view>
					</view>
				</view>
				<view class="loginBtn " @click="login">
					<text class="btnValue">登录</text>
				</view>
				<view class="forgotBtn">
					<text class="btnValue" @click="regist">注册</text>
				</view>
			</view>
			<view class="padding-xs text-lg" v-if="registFromDiv">
				<view class="form" style="color: #8F8F94;">
					<view class="cu-form-group inputWrapper">
						<view class="title">登录名</view>
						<input placeholder="请输入登录名" v-model="registFrom.loginName" name="loginName"></input>
					</view>
					<view class="cu-form-group inputWrapper">
						<view class="title">密码</view>
						<input placeholder="请输入密码" v-model="registFrom.password" name="password"></input>
					</view>
					<view class="cu-form-group inputWrapper">
						<view class="title">昵称</view>
						<input placeholder="昵称" v-model="registFrom.name" name="name"></input>
					</view>
			
					<view class="cu-form-group inputWrapper">
						<view class="title">手机号码</view>
						<input placeholder="手机号码" v-model="registFrom.phone" name="phone"></input>
					</view>
					<view class="cu-form-group inputWrapper">
						<view class="title">性别</view>
						<input placeholder="性别" v-model="registFrom.sex" name="sex"></input>
					</view>
				</view>
				<view class="forgotBtn">
					<text class="btnValue" @click="registSave">确定注册</text>
				</view>
				<view class="loginBtn" @click="relogin">
					<text class="btnValue">返回登录</text>
				</view>

			</view>
		</view>
	</view>
</template>

<script>
	import common from '../../common/common.js';
	export default {
		data() {
			return {
				loginFormDiv: true,
				registFromDiv: false,
				loginForm: {
					loginName: '1',
					password: '1'
				},
				current: 0,
				registFrom: {
				loginName:'',
				password: '',
				code: '2'
				}
			}
		},
		onLoad() {

		},
		methods: {
			radio: function(e) {
				this.code = e
			},
			radioChange: function(evt) {
				for (let i = 0; i < this.items.length; i++) {
					if (this.items[i].value === evt.target.value) {
						this.current = i;
						break;
					}
				}
			},
			registSave: function() {
				
				if(this.registFrom.loginName == ''||this.registFrom.password == ''){
					uni.showToast({
						title: "请填写登录名和密码",
						icon: "none"
					})
					return;
				}
				uni.request({
					url: common.webUrl + 'user/registUser',
					data: this.registFrom,
					method: 'POST',
					dataType: 'json',
					success: (res) => {
						if (res.data.resCode == '0000') {
							uni.showToast({
								title: "操作成功"
							})
							this.relogin();
						} else {
							uni.showToast({
								title: res.data.resDesc,
								icon: "none"
							})
						}
					}
				});
			},
			relogin: function() {
				this.registFromDiv = false;
				this.loginFormDiv = true;
			},
			regist: function() {
				this.registFromDiv = true;
				this.loginFormDiv = false;
			},
			login: function() {
				uni.request({
					url: common.webUrl + 'user/login',
					data: this.loginForm,
					header: {
						'custom-header': 'hello' //自定义请求头信息
					},
					method: 'GET',
					dataType: 'json',
					success: (res) => {
						if (res.data.resCode == '0000') {
							uni.showToast({
								title: "登陆成功"
							})

							var userObj = res.data.data.user;
							uni.setStorage({
								key: 'userObj',
								data: userObj,
								success: function() {
									console.log(JSON.stringify(userObj));
								}
							});
							
							
							uni.switchTab({ //跳转到TAB页面的某个页面
							     url: '/pages/home/home'
							 });
							
							/*跳转新路径*/
							
							// uni.navigateTo({ //跳转到非TAB页面
							// 	url: '../main/home'
							// });
							// uni.switchTab({ //跳转到TAB页面的某个页面
							// 	// url: '/pages/main/home'
							// });
						} else {
							uni.showToast({
								title: "登陆失败，用户名或密码错误"
							})
						}
					}
				});
			}
		}
	}
</script>

<style>
	.content {
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
		height: 40upx;
		background: white;
		border-radius: 10px;
		box-sizing: border-box;
		padding: 0 10px;
		margin-top: 10px;
	}

	.inputWrapper .input {
		width: 100%;
		height: 100%;
		font-size: 15px;
		text-align: center;
	}

	.loginBtn {
		width: 100%;
		height: 80upx;
		/* background: #61a0d2; */
		border-radius: 50upx;
		display: flex;
		justify-content: center;
		align-items: center;
		margin-top: 10px;
	}

	.loginBtn .btnValue {
		color: white;
	}

	.forgotBtn {
		text-align: center;
		color: white;
		margin-top: 10px;
	}
</style>
